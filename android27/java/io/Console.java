package java.io;

import java.nio.charset.*;
import java.util.*;
import sun.nio.cs.*;
import sun.misc.*;

public final class Console implements Flushable
{
    private Object readLock;
    private Object writeLock;
    private Reader reader;
    private Writer out;
    private PrintWriter pw;
    private Formatter formatter;
    private Charset cs;
    private char[] rcb;
    private static boolean echoOff;
    private static Console cons;
    
    public PrintWriter writer() {
        return this.pw;
    }
    
    public Reader reader() {
        return this.reader;
    }
    
    public Console format(final String s, final Object... array) {
        this.formatter.format(s, array).flush();
        return this;
    }
    
    public Console printf(final String s, final Object... array) {
        return this.format(s, array);
    }
    
    public String readLine(final String s, final Object... array) {
        String s2 = null;
        synchronized (this.writeLock) {
            synchronized (this.readLock) {
                if (s.length() != 0) {
                    this.pw.format(s, array);
                }
                try {
                    final char[] readline = this.readline(false);
                    if (readline != null) {
                        s2 = new String(readline);
                    }
                }
                catch (IOException ex) {
                    throw new IOError(ex);
                }
            }
        }
        return s2;
    }
    
    public String readLine() {
        return this.readLine("", new Object[0]);
    }
    
    public char[] readPassword(final String s, final Object... array) {
        char[] readline = null;
        synchronized (this.writeLock) {
            synchronized (this.readLock) {
                try {
                    Console.echoOff = echo(false);
                }
                catch (IOException t) {
                    throw new IOError(t);
                }
                Throwable t = null;
                try {
                    if (s.length() != 0) {
                        this.pw.format(s, array);
                    }
                    readline = this.readline(true);
                }
                catch (IOException ex) {
                    t = new IOError(ex);
                }
                finally {
                    try {
                        Console.echoOff = echo(true);
                    }
                    catch (IOException ex2) {
                        if (t == null) {
                            t = new IOError(ex2);
                        }
                        else {
                            t.addSuppressed(ex2);
                        }
                    }
                    if (t != null) {
                        throw t;
                    }
                }
                this.pw.println();
            }
        }
        return readline;
    }
    
    public char[] readPassword() {
        return this.readPassword("", new Object[0]);
    }
    
    @Override
    public void flush() {
        this.pw.flush();
    }
    
    private static native String encoding();
    
    private static native boolean echo(final boolean p0) throws IOException;
    
    private char[] readline(final boolean b) throws IOException {
        int read = this.reader.read(this.rcb, 0, this.rcb.length);
        if (read < 0) {
            return null;
        }
        if (this.rcb[read - 1] == '\r') {
            --read;
        }
        else if (this.rcb[read - 1] == '\n' && --read > 0 && this.rcb[read - 1] == '\r') {
            --read;
        }
        final char[] array = new char[read];
        if (read > 0) {
            System.arraycopy(this.rcb, 0, array, 0, read);
            if (b) {
                Arrays.fill(this.rcb, 0, read, ' ');
            }
        }
        return array;
    }
    
    private char[] grow() {
        assert Thread.holdsLock(this.readLock);
        final char[] rcb = new char[this.rcb.length * 2];
        System.arraycopy(this.rcb, 0, rcb, 0, this.rcb.length);
        return this.rcb = rcb;
    }
    
    private static native boolean istty();
    
    private Console() {
        this.readLock = new Object();
        this.writeLock = new Object();
        final String encoding = encoding();
        if (encoding != null) {
            try {
                this.cs = Charset.forName(encoding);
            }
            catch (Exception ex) {}
        }
        if (this.cs == null) {
            this.cs = Charset.defaultCharset();
        }
        this.out = StreamEncoder.forOutputStreamWriter(new FileOutputStream(FileDescriptor.out), this.writeLock, this.cs);
        this.pw = new PrintWriter(this.out, true) {
            @Override
            public void close() {
            }
        };
        this.formatter = new Formatter(this.out);
        this.reader = new LineReader(StreamDecoder.forInputStreamReader(new FileInputStream(FileDescriptor.in), this.readLock, this.cs));
        this.rcb = new char[1024];
    }
    
    static {
        try {
            SharedSecrets.getJavaLangAccess().registerShutdownHook(0, false, new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Console.echoOff) {
                            echo(true);
                        }
                    }
                    catch (IOException ex) {}
                }
            });
        }
        catch (IllegalStateException ex) {}
        SharedSecrets.setJavaIOAccess(new JavaIOAccess() {
            @Override
            public Console console() {
                if (istty()) {
                    if (Console.cons == null) {
                        Console.cons = new Console(null);
                    }
                    return Console.cons;
                }
                return null;
            }
            
            @Override
            public Charset charset() {
                return Console.cons.cs;
            }
        });
    }
    
    class LineReader extends Reader
    {
        private Reader in;
        private char[] cb;
        private int nChars;
        private int nextChar;
        boolean leftoverLF;
        
        LineReader(final Reader in) {
            this.in = in;
            this.cb = new char[1024];
            final boolean b = false;
            this.nChars = (b ? 1 : 0);
            this.nextChar = (b ? 1 : 0);
            this.leftoverLF = false;
        }
        
        @Override
        public void close() {
        }
        
        @Override
        public boolean ready() throws IOException {
            return this.in.ready();
        }
        
        @Override
        public int read(char[] array, final int n, final int n2) throws IOException {
            int n3 = n;
            int length = n + n2;
            if (n < 0 || n > array.length || n2 < 0 || length < 0 || length > array.length) {
                throw new IndexOutOfBoundsException();
            }
            synchronized (Console.this.readLock) {
                boolean b = false;
                do {
                    if (this.nextChar >= this.nChars) {
                        int i;
                        do {
                            i = this.in.read(this.cb, 0, this.cb.length);
                        } while (i == 0);
                        if (i > 0) {
                            this.nChars = i;
                            this.nextChar = 0;
                            if (i < this.cb.length && this.cb[i - 1] != '\n' && this.cb[i - 1] != '\r') {
                                b = true;
                            }
                        }
                        else {
                            if (n3 - n == 0) {
                                return -1;
                            }
                            return n3 - n;
                        }
                    }
                    if (this.leftoverLF && array == Console.this.rcb && this.cb[this.nextChar] == '\n') {
                        ++this.nextChar;
                    }
                    this.leftoverLF = false;
                    while (this.nextChar < this.nChars) {
                        final char[] array2 = array;
                        final int n4 = n3++;
                        final char c = this.cb[this.nextChar];
                        array2[n4] = c;
                        final char c2 = c;
                        this.cb[this.nextChar++] = '\0';
                        if (c2 == '\n') {
                            return n3 - n;
                        }
                        if (c2 == '\r') {
                            if (n3 == length) {
                                if (array != Console.this.rcb) {
                                    this.leftoverLF = true;
                                    return n3 - n;
                                }
                                array = Console.this.grow();
                                final int length2 = array.length;
                            }
                            if (this.nextChar == this.nChars && this.in.ready()) {
                                this.nChars = this.in.read(this.cb, 0, this.cb.length);
                                this.nextChar = 0;
                            }
                            if (this.nextChar < this.nChars && this.cb[this.nextChar] == '\n') {
                                array[n3++] = '\n';
                                ++this.nextChar;
                            }
                            return n3 - n;
                        }
                        if (n3 != length) {
                            continue;
                        }
                        if (array != Console.this.rcb) {
                            return n3 - n;
                        }
                        array = Console.this.grow();
                        length = array.length;
                    }
                } while (!b);
                return n3 - n;
            }
        }
    }
}
