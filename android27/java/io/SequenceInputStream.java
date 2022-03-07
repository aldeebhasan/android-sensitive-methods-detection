package java.io;

import java.util.*;

public class SequenceInputStream extends InputStream
{
    Enumeration<? extends InputStream> e;
    InputStream in;
    
    public SequenceInputStream(final Enumeration<? extends InputStream> e) {
        this.e = e;
        try {
            this.nextStream();
        }
        catch (IOException ex) {
            throw new Error("panic");
        }
    }
    
    public SequenceInputStream(final InputStream inputStream, final InputStream inputStream2) {
        final Vector<InputStream> vector = new Vector<InputStream>(2);
        vector.addElement(inputStream);
        vector.addElement(inputStream2);
        this.e = vector.elements();
        try {
            this.nextStream();
        }
        catch (IOException ex) {
            throw new Error("panic");
        }
    }
    
    final void nextStream() throws IOException {
        if (this.in != null) {
            this.in.close();
        }
        if (this.e.hasMoreElements()) {
            this.in = (InputStream)this.e.nextElement();
            if (this.in == null) {
                throw new NullPointerException();
            }
        }
        else {
            this.in = null;
        }
    }
    
    @Override
    public int available() throws IOException {
        if (this.in == null) {
            return 0;
        }
        return this.in.available();
    }
    
    @Override
    public int read() throws IOException {
        while (this.in != null) {
            final int read = this.in.read();
            if (read != -1) {
                return read;
            }
            this.nextStream();
        }
        return -1;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.in == null) {
            return -1;
        }
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n2 > array.length - n) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        do {
            final int read = this.in.read(array, n, n2);
            if (read > 0) {
                return read;
            }
            this.nextStream();
        } while (this.in != null);
        return -1;
    }
    
    @Override
    public void close() throws IOException {
        do {
            this.nextStream();
        } while (this.in != null);
    }
}
