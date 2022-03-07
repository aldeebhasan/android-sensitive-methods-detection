package java.io;

@Deprecated
public class LineNumberInputStream extends FilterInputStream
{
    int pushBack;
    int lineNumber;
    int markLineNumber;
    int markPushBack;
    
    public LineNumberInputStream(final InputStream inputStream) {
        super(inputStream);
        this.pushBack = -1;
        this.markPushBack = -1;
    }
    
    @Override
    public int read() throws IOException {
        int n = this.pushBack;
        if (n != -1) {
            this.pushBack = -1;
        }
        else {
            n = this.in.read();
        }
        switch (n) {
            case 13: {
                this.pushBack = this.in.read();
                if (this.pushBack == 10) {
                    this.pushBack = -1;
                }
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
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        final int read = this.read();
        if (read == -1) {
            return -1;
        }
        array[n] = (byte)read;
        int i = 1;
        try {
            while (i < n2) {
                final int read2 = this.read();
                if (read2 == -1) {
                    break;
                }
                if (array != null) {
                    array[n + i] = (byte)read2;
                }
                ++i;
            }
        }
        catch (IOException ex) {}
        return i;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        final int n2 = 2048;
        long n3 = n;
        if (n <= 0L) {
            return 0L;
        }
        final byte[] array = new byte[n2];
        while (n3 > 0L) {
            final int read = this.read(array, 0, (int)Math.min(n2, n3));
            if (read < 0) {
                break;
            }
            n3 -= read;
        }
        return n - n3;
    }
    
    public void setLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    @Override
    public int available() throws IOException {
        return (this.pushBack == -1) ? (super.available() / 2) : (super.available() / 2 + 1);
    }
    
    @Override
    public void mark(final int n) {
        this.markLineNumber = this.lineNumber;
        this.markPushBack = this.pushBack;
        this.in.mark(n);
    }
    
    @Override
    public void reset() throws IOException {
        this.lineNumber = this.markLineNumber;
        this.pushBack = this.markPushBack;
        this.in.reset();
    }
}
