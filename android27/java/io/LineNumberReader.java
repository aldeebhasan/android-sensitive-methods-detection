package java.io;

public class LineNumberReader extends BufferedReader
{
    private int lineNumber;
    private int markedLineNumber;
    private boolean skipLF;
    private boolean markedSkipLF;
    private static final int maxSkipBufferSize = 8192;
    private char[] skipBuffer;
    
    public LineNumberReader(final Reader reader) {
        super(reader);
        this.lineNumber = 0;
        this.skipBuffer = null;
    }
    
    public LineNumberReader(final Reader reader, final int n) {
        super(reader, n);
        this.lineNumber = 0;
        this.skipBuffer = null;
    }
    
    public void setLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    @Override
    public int read() throws IOException {
        synchronized (this.lock) {
            int n = super.read();
            if (this.skipLF) {
                if (n == 10) {
                    n = super.read();
                }
                this.skipLF = false;
            }
            switch (n) {
                case 13: {
                    this.skipLF = true;
                }
                case 10: {
                    ++this.lineNumber;
                    return 10;
                }
                default: {
                    return n;
                }
            }
        }
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            final int read = super.read(array, n, n2);
            for (int i = n; i < n + read; ++i) {
                final char c = array[i];
                if (this.skipLF) {
                    this.skipLF = false;
                    if (c == '\n') {
                        continue;
                    }
                }
                switch (c) {
                    case 13: {
                        this.skipLF = true;
                    }
                    case 10: {
                        ++this.lineNumber;
                        break;
                    }
                }
            }
            return read;
        }
    }
    
    @Override
    public String readLine() throws IOException {
        synchronized (this.lock) {
            final String line = super.readLine(this.skipLF);
            this.skipLF = false;
            if (line != null) {
                ++this.lineNumber;
            }
            return line;
        }
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n < 0L) {
            throw new IllegalArgumentException("skip() value is negative");
        }
        final int n2 = (int)Math.min(n, 8192L);
        synchronized (this.lock) {
            if (this.skipBuffer == null || this.skipBuffer.length < n2) {
                this.skipBuffer = new char[n2];
            }
            long n3;
            int read;
            for (n3 = n; n3 > 0L; n3 -= read) {
                read = this.read(this.skipBuffer, 0, (int)Math.min(n3, n2));
                if (read == -1) {
                    break;
                }
            }
            return n - n3;
        }
    }
    
    @Override
    public void mark(final int n) throws IOException {
        synchronized (this.lock) {
            super.mark(n);
            this.markedLineNumber = this.lineNumber;
            this.markedSkipLF = this.skipLF;
        }
    }
    
    @Override
    public void reset() throws IOException {
        synchronized (this.lock) {
            super.reset();
            this.lineNumber = this.markedLineNumber;
            this.skipLF = this.markedSkipLF;
        }
    }
}
