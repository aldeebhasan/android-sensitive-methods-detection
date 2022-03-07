package java.util.jar;

import java.util.*;
import java.io.*;
import sun.util.logging.*;
import sun.misc.*;

public class Attributes implements Map<Object, Object>, Cloneable
{
    protected Map<Object, Object> map;
    
    public Attributes() {
        this(11);
    }
    
    public Attributes(final int n) {
        this.map = new HashMap<Object, Object>(n);
    }
    
    public Attributes(final Attributes attributes) {
        this.map = new HashMap<Object, Object>(attributes);
    }
    
    @Override
    public Object get(final Object o) {
        return this.map.get(o);
    }
    
    public String getValue(final String s) {
        return (String)this.get(new Name(s));
    }
    
    public String getValue(final Name name) {
        return (String)this.get(name);
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.map.put(o, o2);
    }
    
    public String putValue(final String s, final String s2) {
        return (String)this.put(new Name(s), s2);
    }
    
    @Override
    public Object remove(final Object o) {
        return this.map.remove(o);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return this.map.containsValue(o);
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public void putAll(final Map<?, ?> map) {
        if (!Attributes.class.isInstance(map)) {
            throw new ClassCastException();
        }
        for (final Entry<Object, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.map.clear();
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    @Override
    public Set<Object> keySet() {
        return this.map.keySet();
    }
    
    @Override
    public Collection<Object> values() {
        return this.map.values();
    }
    
    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.map.equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.map.hashCode();
    }
    
    public Object clone() {
        return new Attributes(this);
    }
    
    void write(final DataOutputStream dataOutputStream) throws IOException {
        for (final Entry<Object, Object> entry : this.entrySet()) {
            final StringBuffer sb = new StringBuffer(entry.getKey().toString());
            sb.append(": ");
            String s = entry.getValue();
            if (s != null) {
                final byte[] bytes = s.getBytes("UTF8");
                s = new String(bytes, 0, 0, bytes.length);
            }
            sb.append(s);
            sb.append("\r\n");
            Manifest.make72Safe(sb);
            dataOutputStream.writeBytes(sb.toString());
        }
        dataOutputStream.writeBytes("\r\n");
    }
    
    void writeMain(final DataOutputStream dataOutputStream) throws IOException {
        String s = Name.MANIFEST_VERSION.toString();
        String s2 = this.getValue(s);
        if (s2 == null) {
            s = Name.SIGNATURE_VERSION.toString();
            s2 = this.getValue(s);
        }
        if (s2 != null) {
            dataOutputStream.writeBytes(s + ": " + s2 + "\r\n");
        }
        for (final Entry<Object, Object> entry : this.entrySet()) {
            final String string = entry.getKey().toString();
            if (s2 != null && !string.equalsIgnoreCase(s)) {
                final StringBuffer sb = new StringBuffer(string);
                sb.append(": ");
                String s3 = entry.getValue();
                if (s3 != null) {
                    final byte[] bytes = s3.getBytes("UTF8");
                    s3 = new String(bytes, 0, 0, bytes.length);
                }
                sb.append(s3);
                sb.append("\r\n");
                Manifest.make72Safe(sb);
                dataOutputStream.writeBytes(sb.toString());
            }
        }
        dataOutputStream.writeBytes("\r\n");
    }
    
    void read(final Manifest.FastInputStream fastInputStream, final byte[] array) throws IOException {
        String s = null;
        Object o = null;
        int line;
        while ((line = fastInputStream.readLine(array)) != -1) {
            boolean b = false;
            if (array[--line] != 10) {
                throw new IOException("line too long");
            }
            if (line > 0 && array[line - 1] == 13) {
                --line;
            }
            if (line == 0) {
                break;
            }
            int n = 0;
            String s2;
            if (array[0] == 32) {
                if (s == null) {
                    throw new IOException("misplaced continuation line");
                }
                b = true;
                final byte[] array2 = new byte[o.length + line - 1];
                System.arraycopy(o, 0, array2, 0, o.length);
                System.arraycopy(array, 1, array2, o.length, line - 1);
                if (fastInputStream.peek() == 32) {
                    o = array2;
                    continue;
                }
                s2 = new String(array2, 0, array2.length, "UTF8");
                o = null;
            }
            else {
                while (array[n++] != 58) {
                    if (n >= line) {
                        throw new IOException("invalid header field");
                    }
                }
                if (array[n++] != 32) {
                    throw new IOException("invalid header field");
                }
                s = new String(array, 0, 0, n - 2);
                if (fastInputStream.peek() == 32) {
                    o = new byte[line - n];
                    System.arraycopy(array, n, o, 0, line - n);
                    continue;
                }
                s2 = new String(array, n, line - n, "UTF8");
            }
            try {
                if (this.putValue(s, s2) == null || b) {
                    continue;
                }
                PlatformLogger.getLogger("java.util.jar").warning("Duplicate name in Manifest: " + s + ".\nEnsure that the manifest does not have duplicate entries, and\nthat blank lines separate individual sections in both your\nmanifest and in the META-INF/MANIFEST.MF entry in the jar file.");
            }
            catch (IllegalArgumentException ex) {
                throw new IOException("invalid header field name: " + s);
            }
        }
    }
    
    public static class Name
    {
        private String name;
        private int hashCode;
        public static final Name MANIFEST_VERSION;
        public static final Name SIGNATURE_VERSION;
        public static final Name CONTENT_TYPE;
        public static final Name CLASS_PATH;
        public static final Name MAIN_CLASS;
        public static final Name SEALED;
        public static final Name EXTENSION_LIST;
        public static final Name EXTENSION_NAME;
        @Deprecated
        public static final Name EXTENSION_INSTALLATION;
        public static final Name IMPLEMENTATION_TITLE;
        public static final Name IMPLEMENTATION_VERSION;
        public static final Name IMPLEMENTATION_VENDOR;
        @Deprecated
        public static final Name IMPLEMENTATION_VENDOR_ID;
        @Deprecated
        public static final Name IMPLEMENTATION_URL;
        public static final Name SPECIFICATION_TITLE;
        public static final Name SPECIFICATION_VERSION;
        public static final Name SPECIFICATION_VENDOR;
        
        public Name(final String s) {
            this.hashCode = -1;
            if (s == null) {
                throw new NullPointerException("name");
            }
            if (!isValid(s)) {
                throw new IllegalArgumentException(s);
            }
            this.name = s.intern();
        }
        
        private static boolean isValid(final String s) {
            final int length = s.length();
            if (length > 70 || length == 0) {
                return false;
            }
            for (int i = 0; i < length; ++i) {
                if (!isValid(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        
        private static boolean isValid(final char c) {
            return isAlpha(c) || isDigit(c) || c == '_' || c == '-';
        }
        
        private static boolean isAlpha(final char c) {
            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }
        
        private static boolean isDigit(final char c) {
            return c >= '0' && c <= '9';
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Name && ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER.compare(this.name, ((Name)o).name) == 0;
        }
        
        @Override
        public int hashCode() {
            if (this.hashCode == -1) {
                this.hashCode = ASCIICaseInsensitiveComparator.lowerCaseHashCode(this.name);
            }
            return this.hashCode;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            MANIFEST_VERSION = new Name("Manifest-Version");
            SIGNATURE_VERSION = new Name("Signature-Version");
            CONTENT_TYPE = new Name("Content-Type");
            CLASS_PATH = new Name("Class-Path");
            MAIN_CLASS = new Name("Main-Class");
            SEALED = new Name("Sealed");
            EXTENSION_LIST = new Name("Extension-List");
            EXTENSION_NAME = new Name("Extension-Name");
            EXTENSION_INSTALLATION = new Name("Extension-Installation");
            IMPLEMENTATION_TITLE = new Name("Implementation-Title");
            IMPLEMENTATION_VERSION = new Name("Implementation-Version");
            IMPLEMENTATION_VENDOR = new Name("Implementation-Vendor");
            IMPLEMENTATION_VENDOR_ID = new Name("Implementation-Vendor-Id");
            IMPLEMENTATION_URL = new Name("Implementation-URL");
            SPECIFICATION_TITLE = new Name("Specification-Title");
            SPECIFICATION_VERSION = new Name("Specification-Version");
            SPECIFICATION_VENDOR = new Name("Specification-Vendor");
        }
    }
}
