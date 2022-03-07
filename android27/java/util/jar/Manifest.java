package java.util.jar;

import java.util.*;
import java.io.*;

public class Manifest implements Cloneable
{
    private final Attributes attr;
    private final Map<String, Attributes> entries;
    private final JarVerifier jv;
    
    public Manifest() {
        this.attr = new Attributes();
        this.entries = new HashMap<String, Attributes>();
        this.jv = null;
    }
    
    public Manifest(final InputStream inputStream) throws IOException {
        this(null, inputStream);
    }
    
    Manifest(final JarVerifier jv, final InputStream inputStream) throws IOException {
        this.attr = new Attributes();
        this.entries = new HashMap<String, Attributes>();
        this.read(inputStream);
        this.jv = jv;
    }
    
    public Manifest(final Manifest manifest) {
        this.attr = new Attributes();
        this.entries = new HashMap<String, Attributes>();
        this.attr.putAll(manifest.getMainAttributes());
        this.entries.putAll(manifest.getEntries());
        this.jv = manifest.jv;
    }
    
    public Attributes getMainAttributes() {
        return this.attr;
    }
    
    public Map<String, Attributes> getEntries() {
        return this.entries;
    }
    
    public Attributes getAttributes(final String s) {
        return this.getEntries().get(s);
    }
    
    Attributes getTrustedAttributes(final String s) {
        final Attributes attributes = this.getAttributes(s);
        if (attributes != null && this.jv != null && !this.jv.isTrustedManifestEntry(s)) {
            throw new SecurityException("Untrusted manifest entry: " + s);
        }
        return attributes;
    }
    
    public void clear() {
        this.attr.clear();
        this.entries.clear();
    }
    
    public void write(final OutputStream outputStream) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        this.attr.writeMain(dataOutputStream);
        for (final Map.Entry<String, Attributes> entry : this.entries.entrySet()) {
            final StringBuffer sb = new StringBuffer("Name: ");
            String s = entry.getKey();
            if (s != null) {
                final byte[] bytes = s.getBytes("UTF8");
                s = new String(bytes, 0, 0, bytes.length);
            }
            sb.append(s);
            sb.append("\r\n");
            make72Safe(sb);
            dataOutputStream.writeBytes(sb.toString());
            entry.getValue().write(dataOutputStream);
        }
        dataOutputStream.flush();
    }
    
    static void make72Safe(final StringBuffer sb) {
        int length = sb.length();
        if (length > 72) {
            for (int i = 70; i < length - 2; i += 72, length += 3) {
                sb.insert(i, "\r\n ");
            }
        }
    }
    
    public void read(final InputStream inputStream) throws IOException {
        final FastInputStream fastInputStream = new FastInputStream(inputStream);
        final byte[] array = new byte[512];
        this.attr.read(fastInputStream, array);
        int n = 0;
        int n2 = 0;
        int max = 2;
        String name = null;
        int n3 = 1;
        Object o = null;
        int line;
        while ((line = fastInputStream.readLine(array)) != -1) {
            if (array[--line] != 10) {
                throw new IOException("manifest line too long");
            }
            if (line > 0 && array[line - 1] == 13) {
                --line;
            }
            if (line == 0 && n3 != 0) {
                continue;
            }
            n3 = 0;
            if (name == null) {
                name = this.parseName(array, line);
                if (name == null) {
                    throw new IOException("invalid manifest format");
                }
                if (fastInputStream.peek() == 32) {
                    o = new byte[line - 6];
                    System.arraycopy(array, 6, o, 0, line - 6);
                    continue;
                }
            }
            else {
                final byte[] array2 = new byte[o.length + line - 1];
                System.arraycopy(o, 0, array2, 0, o.length);
                System.arraycopy(array, 1, array2, o.length, line - 1);
                if (fastInputStream.peek() == 32) {
                    o = array2;
                    continue;
                }
                name = new String(array2, 0, array2.length, "UTF8");
                o = null;
            }
            Attributes attributes = this.getAttributes(name);
            if (attributes == null) {
                attributes = new Attributes(max);
                this.entries.put(name, attributes);
            }
            attributes.read(fastInputStream, array);
            ++n;
            n2 += attributes.size();
            max = Math.max(2, n2 / n);
            name = null;
            n3 = 1;
        }
    }
    
    private String parseName(final byte[] array, final int n) {
        if (this.toLower(array[0]) == 110 && this.toLower(array[1]) == 97 && this.toLower(array[2]) == 109 && this.toLower(array[3]) == 101 && array[4] == 58 && array[5] == 32) {
            try {
                return new String(array, 6, n - 6, "UTF8");
            }
            catch (Exception ex) {}
        }
        return null;
    }
    
    private int toLower(final int n) {
        return (n >= 65 && n <= 90) ? (97 + (n - 65)) : n;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Manifest) {
            final Manifest manifest = (Manifest)o;
            return this.attr.equals(manifest.getMainAttributes()) && this.entries.equals(manifest.getEntries());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.attr.hashCode() + this.entries.hashCode();
    }
    
    public Object clone() {
        return new Manifest(this);
    }
    
    static class FastInputStream extends FilterInputStream
    {
        private byte[] buf;
        private int count;
        private int pos;
        
        FastInputStream(final InputStream inputStream) {
            this(inputStream, 8192);
        }
        
        FastInputStream(final InputStream inputStream, final int n) {
            super(inputStream);
            this.count = 0;
            this.pos = 0;
            this.buf = new byte[n];
        }
        
        @Override
        public int read() throws IOException {
            if (this.pos >= this.count) {
                this.fill();
                if (this.pos >= this.count) {
                    return -1;
                }
            }
            return Byte.toUnsignedInt(this.buf[this.pos++]);
        }
        
        @Override
        public int read(final byte[] array, final int n, int n2) throws IOException {
            int n3 = this.count - this.pos;
            if (n3 <= 0) {
                if (n2 >= this.buf.length) {
                    return this.in.read(array, n, n2);
                }
                this.fill();
                n3 = this.count - this.pos;
                if (n3 <= 0) {
                    return -1;
                }
            }
            if (n2 > n3) {
                n2 = n3;
            }
            System.arraycopy(this.buf, this.pos, array, n, n2);
            this.pos += n2;
            return n2;
        }
        
        public int readLine(final byte[] array, int n, final int n2) throws IOException {
            final byte[] buf = this.buf;
            int i = 0;
            while (i < n2) {
                int n3 = this.count - this.pos;
                if (n3 <= 0) {
                    this.fill();
                    n3 = this.count - this.pos;
                    if (n3 <= 0) {
                        return -1;
                    }
                }
                int n4 = n2 - i;
                if (n4 > n3) {
                    n4 = n3;
                }
                int pos = this.pos;
                while (pos < pos + n4 && buf[pos++] != 10) {}
                final int n5 = pos - this.pos;
                System.arraycopy(buf, this.pos, array, n, n5);
                n += n5;
                i += n5;
                this.pos = pos;
                if (buf[pos - 1] == 10) {
                    break;
                }
            }
            return i;
        }
        
        public byte peek() throws IOException {
            if (this.pos == this.count) {
                this.fill();
            }
            if (this.pos == this.count) {
                return -1;
            }
            return this.buf[this.pos];
        }
        
        public int readLine(final byte[] array) throws IOException {
            return this.readLine(array, 0, array.length);
        }
        
        @Override
        public long skip(long n) throws IOException {
            if (n <= 0L) {
                return 0L;
            }
            final long n2 = this.count - this.pos;
            if (n2 <= 0L) {
                return this.in.skip(n);
            }
            if (n > n2) {
                n = n2;
            }
            this.pos += (int)n;
            return n;
        }
        
        @Override
        public int available() throws IOException {
            return this.count - this.pos + this.in.available();
        }
        
        @Override
        public void close() throws IOException {
            if (this.in != null) {
                this.in.close();
                this.in = null;
                this.buf = null;
            }
        }
        
        private void fill() throws IOException {
            final boolean b = false;
            this.pos = (b ? 1 : 0);
            this.count = (b ? 1 : 0);
            final int read = this.in.read(this.buf, 0, this.buf.length);
            if (read > 0) {
                this.count = read;
            }
        }
    }
}
