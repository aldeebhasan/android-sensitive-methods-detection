package java.io;

import java.util.*;
import java.util.stream.*;

public class BufferedReader extends Reader
{
    private Reader in;
    private char[] cb;
    private int nChars;
    private int nextChar;
    private static final int INVALIDATED = -2;
    private static final int UNMARKED = -1;
    private int markedChar;
    private int readAheadLimit;
    private boolean skipLF;
    private boolean markedSkipLF;
    private static int defaultCharBufferSize;
    private static int defaultExpectedLineLength;
    
    public BufferedReader(final Reader in, final int n) {
        super(in);
        this.markedChar = -1;
        this.readAheadLimit = 0;
        this.skipLF = false;
        this.markedSkipLF = false;
        if (n <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        this.in = in;
        this.cb = new char[n];
        final boolean b = false;
        this.nChars = (b ? 1 : 0);
        this.nextChar = (b ? 1 : 0);
    }
    
    public BufferedReader(final Reader reader) {
        this(reader, BufferedReader.defaultCharBufferSize);
    }
    
    private void ensureOpen() throws IOException {
        if (this.in == null) {
            throw new IOException("Stream closed");
        }
    }
    
    private void fill() throws IOException {
        int nextChar;
        if (this.markedChar <= -1) {
            nextChar = 0;
        }
        else {
            final int n = this.nextChar - this.markedChar;
            if (n >= this.readAheadLimit) {
                this.markedChar = -2;
                this.readAheadLimit = 0;
                nextChar = 0;
            }
            else {
                if (this.readAheadLimit <= this.cb.length) {
                    System.arraycopy(this.cb, this.markedChar, this.cb, 0, n);
                    this.markedChar = 0;
                    nextChar = n;
                }
                else {
                    final char[] cb = new char[this.readAheadLimit];
                    System.arraycopy(this.cb, this.markedChar, cb, 0, n);
                    this.cb = cb;
                    this.markedChar = 0;
                    nextChar = n;
                }
                final int n2 = n;
                this.nChars = n2;
                this.nextChar = n2;
            }
        }
        int i;
        do {
            i = this.in.read(this.cb, nextChar, this.cb.length - nextChar);
        } while (i == 0);
        if (i > 0) {
            this.nChars = nextChar + i;
            this.nextChar = nextChar;
        }
    }
    
    @Override
    public int read() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            while (true) {
                if (this.nextChar >= this.nChars) {
                    this.fill();
                    if (this.nextChar >= this.nChars) {
                        return -1;
                    }
                }
                if (!this.skipLF) {
                    break;
                }
                this.skipLF = false;
                if (this.cb[this.nextChar] != '\n') {
                    break;
                }
                ++this.nextChar;
            }
            return this.cb[this.nextChar++];
        }
    }
    
    private int read1(final char[] array, final int n, final int n2) throws IOException {
        if (this.nextChar >= this.nChars) {
            if (n2 >= this.cb.length && this.markedChar <= -1 && !this.skipLF) {
                return this.in.read(array, n, n2);
            }
            this.fill();
        }
        if (this.nextChar >= this.nChars) {
            return -1;
        }
        if (this.skipLF) {
            this.skipLF = false;
            if (this.cb[this.nextChar] == '\n') {
                ++this.nextChar;
                if (this.nextChar >= this.nChars) {
                    this.fill();
                }
                if (this.nextChar >= this.nChars) {
                    return -1;
                }
            }
        }
        final int min = Math.min(n2, this.nChars - this.nextChar);
        System.arraycopy(this.cb, this.nextChar, array, n, min);
        this.nextChar += min;
        return min;
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return 0;
            }
            int read1 = this.read1(array, n, n2);
            if (read1 <= 0) {
                return read1;
            }
            while (read1 < n2 && this.in.ready()) {
                final int read2 = this.read1(array, n + read1, n2 - read1);
                if (read2 <= 0) {
                    break;
                }
                read1 += read2;
            }
            return read1;
        }
    }
    
    String readLine(final boolean b) throws IOException {
        StringBuilder sb = null;
        synchronized (this.lock) {
            this.ensureOpen();
            int n = (b || this.skipLF) ? 1 : 0;
            while (true) {
                if (this.nextChar >= this.nChars) {
                    this.fill();
                }
                if (this.nextChar >= this.nChars) {
                    if (sb != null && sb.length() > 0) {
                        return sb.toString();
                    }
                    return null;
                }
                else {
                    boolean b2 = false;
                    char c = '\0';
                    if (n != 0 && this.cb[this.nextChar] == '\n') {
                        ++this.nextChar;
                    }
                    this.skipLF = false;
                    n = 0;
                    int i;
                    for (i = this.nextChar; i < this.nChars; ++i) {
                        c = this.cb[i];
                        if (c == '\n' || c == '\r') {
                            b2 = true;
                            break;
                        }
                    }
                    final int nextChar = this.nextChar;
                    this.nextChar = i;
                    if (b2) {
                        String string;
                        if (sb == null) {
                            string = new String(this.cb, nextChar, i - nextChar);
                        }
                        else {
                            sb.append(this.cb, nextChar, i - nextChar);
                            string = sb.toString();
                        }
                        ++this.nextChar;
                        if (c == '\r') {
                            this.skipLF = true;
                        }
                        return string;
                    }
                    if (sb == null) {
                        sb = new StringBuilder(BufferedReader.defaultExpectedLineLength);
                    }
                    sb.append(this.cb, nextChar, i - nextChar);
                }
            }
        }
    }
    
    public String readLine() throws IOException {
        return this.readLine(false);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("skip value is negative");
        }
        synchronized (this.lock) {
            this.ensureOpen();
            long n2;
            long n3;
            for (n2 = n; n2 > 0L; n2 -= n3, this.nextChar = this.nChars) {
                if (this.nextChar >= this.nChars) {
                    this.fill();
                }
                if (this.nextChar >= this.nChars) {
                    break;
                }
                if (this.skipLF) {
                    this.skipLF = false;
                    if (this.cb[this.nextChar] == '\n') {
                        ++this.nextChar;
                    }
                }
                n3 = this.nChars - this.nextChar;
                if (n2 <= n3) {
                    this.nextChar += (int)n2;
                    n2 = 0L;
                    break;
                }
            }
            return n - n2;
        }
    }
    
    @Override
    public boolean ready() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.skipLF) {
                if (this.nextChar >= this.nChars && this.in.ready()) {
                    this.fill();
                }
                if (this.nextChar < this.nChars) {
                    if (this.cb[this.nextChar] == '\n') {
                        ++this.nextChar;
                    }
                    this.skipLF = false;
                }
            }
            return this.nextChar < this.nChars || this.in.ready();
        }
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public void mark(final int readAheadLimit) throws IOException {
        if (readAheadLimit < 0) {
            throw new IllegalArgumentException("Read-ahead limit < 0");
        }
        synchronized (this.lock) {
            this.ensureOpen();
            this.readAheadLimit = readAheadLimit;
            this.markedChar = this.nextChar;
            this.markedSkipLF = this.skipLF;
        }
    }
    
    @Override
    public void reset() throws IOException {
        synchronized (this.lock) {
            this.ensureOpen();
            if (this.markedChar < 0) {
                throw new IOException((this.markedChar == -2) ? "Mark invalid" : "Stream not marked");
            }
            this.nextChar = this.markedChar;
            this.skipLF = this.markedSkipLF;
        }
    }
    
    @Override
    public void close() throws IOException {
        synchronized (this.lock) {
            if (this.in == null) {
                return;
            }
            try {
                this.in.close();
            }
            finally {
                this.in = null;
                this.cb = null;
            }
        }
    }
    
    public Stream<String> lines() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<? extends String>)new Iterator<String>() {
            String nextLine = null;
            
            @Override
            public boolean hasNext() {
                if (this.nextLine != null) {
                    return true;
                }
                try {
                    this.nextLine = BufferedReader.this.readLine();
                    return this.nextLine != null;
                }
                catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            }
            
            @Override
            public String next() {
                if (this.nextLine != null || this.hasNext()) {
                    final String nextLine = this.nextLine;
                    this.nextLine = null;
                    return nextLine;
                }
                throw new NoSuchElementException();
            }
        }, 272), false);
    }
    
    static {
        BufferedReader.defaultCharBufferSize = 8192;
        BufferedReader.defaultExpectedLineLength = 80;
    }
}
