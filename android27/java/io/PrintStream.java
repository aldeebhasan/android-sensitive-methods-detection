package java.io;

import java.nio.charset.*;
import java.util.*;

public class PrintStream extends FilterOutputStream implements Appendable, Closeable
{
    private final boolean autoFlush;
    private boolean trouble;
    private Formatter formatter;
    private BufferedWriter textOut;
    private OutputStreamWriter charOut;
    private boolean closing;
    
    private static <T> T requireNonNull(final T t, final String s) {
        if (t == null) {
            throw new NullPointerException(s);
        }
        return t;
    }
    
    private static Charset toCharset(final String s) throws UnsupportedEncodingException {
        requireNonNull(s, "charsetName");
        try {
            return Charset.forName(s);
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
            throw new UnsupportedEncodingException(s);
        }
    }
    
    private PrintStream(final boolean autoFlush, final OutputStream outputStream) {
        super(outputStream);
        this.trouble = false;
        this.closing = false;
        this.autoFlush = autoFlush;
        this.charOut = new OutputStreamWriter(this);
        this.textOut = new BufferedWriter(this.charOut);
    }
    
    private PrintStream(final boolean autoFlush, final OutputStream outputStream, final Charset charset) {
        super(outputStream);
        this.trouble = false;
        this.closing = false;
        this.autoFlush = autoFlush;
        this.charOut = new OutputStreamWriter(this, charset);
        this.textOut = new BufferedWriter(this.charOut);
    }
    
    private PrintStream(final boolean b, final Charset charset, final OutputStream outputStream) throws UnsupportedEncodingException {
        this(b, outputStream, charset);
    }
    
    public PrintStream(final OutputStream outputStream) {
        this(outputStream, false);
    }
    
    public PrintStream(final OutputStream outputStream, final boolean b) {
        this(b, requireNonNull(outputStream, "Null output stream"));
    }
    
    public PrintStream(final OutputStream outputStream, final boolean b, final String s) throws UnsupportedEncodingException {
        this(b, requireNonNull(outputStream, "Null output stream"), toCharset(s));
    }
    
    public PrintStream(final String s) throws FileNotFoundException {
        this(false, new FileOutputStream(s));
    }
    
    public PrintStream(final String s, final String s2) throws FileNotFoundException, UnsupportedEncodingException {
        this(false, toCharset(s2), new FileOutputStream(s));
    }
    
    public PrintStream(final File file) throws FileNotFoundException {
        this(false, new FileOutputStream(file));
    }
    
    public PrintStream(final File file, final String s) throws FileNotFoundException, UnsupportedEncodingException {
        this(false, toCharset(s), new FileOutputStream(file));
    }
    
    private void ensureOpen() throws IOException {
        if (this.out == null) {
            throw new IOException("Stream closed");
        }
    }
    
    @Override
    public void flush() {
        synchronized (this) {
            try {
                this.ensureOpen();
                this.out.flush();
            }
            catch (IOException ex) {
                this.trouble = true;
            }
        }
    }
    
    @Override
    public void close() {
        synchronized (this) {
            if (!this.closing) {
                this.closing = true;
                try {
                    this.textOut.close();
                    this.out.close();
                }
                catch (IOException ex) {
                    this.trouble = true;
                }
                this.textOut = null;
                this.charOut = null;
                this.out = null;
            }
        }
    }
    
    public boolean checkError() {
        if (this.out != null) {
            this.flush();
        }
        if (this.out instanceof PrintStream) {
            return ((PrintStream)this.out).checkError();
        }
        return this.trouble;
    }
    
    protected void setError() {
        this.trouble = true;
    }
    
    protected void clearError() {
        this.trouble = false;
    }
    
    @Override
    public void write(final int n) {
        try {
            synchronized (this) {
                this.ensureOpen();
                this.out.write(n);
                if (n == 10 && this.autoFlush) {
                    this.out.flush();
                }
            }
        }
        catch (InterruptedIOException ex) {
            Thread.currentThread().interrupt();
        }
        catch (IOException ex2) {
            this.trouble = true;
        }
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) {
        try {
            synchronized (this) {
                this.ensureOpen();
                this.out.write(array, n, n2);
                if (this.autoFlush) {
                    this.out.flush();
                }
            }
        }
        catch (InterruptedIOException ex) {
            Thread.currentThread().interrupt();
        }
        catch (IOException ex2) {
            this.trouble = true;
        }
    }
    
    private void write(final char[] array) {
        try {
            synchronized (this) {
                this.ensureOpen();
                this.textOut.write(array);
                this.textOut.flushBuffer();
                this.charOut.flushBuffer();
                if (this.autoFlush) {
                    for (int i = 0; i < array.length; ++i) {
                        if (array[i] == '\n') {
                            this.out.flush();
                        }
                    }
                }
            }
        }
        catch (InterruptedIOException ex) {
            Thread.currentThread().interrupt();
        }
        catch (IOException ex2) {
            this.trouble = true;
        }
    }
    
    private void write(final String s) {
        try {
            synchronized (this) {
                this.ensureOpen();
                this.textOut.write(s);
                this.textOut.flushBuffer();
                this.charOut.flushBuffer();
                if (this.autoFlush && s.indexOf(10) >= 0) {
                    this.out.flush();
                }
            }
        }
        catch (InterruptedIOException ex) {
            Thread.currentThread().interrupt();
        }
        catch (IOException ex2) {
            this.trouble = true;
        }
    }
    
    private void newLine() {
        try {
            synchronized (this) {
                this.ensureOpen();
                this.textOut.newLine();
                this.textOut.flushBuffer();
                this.charOut.flushBuffer();
                if (this.autoFlush) {
                    this.out.flush();
                }
            }
        }
        catch (InterruptedIOException ex) {
            Thread.currentThread().interrupt();
        }
        catch (IOException ex2) {
            this.trouble = true;
        }
    }
    
    public void print(final boolean b) {
        this.write(b ? "true" : "false");
    }
    
    public void print(final char c) {
        this.write(String.valueOf(c));
    }
    
    public void print(final int n) {
        this.write(String.valueOf(n));
    }
    
    public void print(final long n) {
        this.write(String.valueOf(n));
    }
    
    public void print(final float n) {
        this.write(String.valueOf(n));
    }
    
    public void print(final double n) {
        this.write(String.valueOf(n));
    }
    
    public void print(final char[] array) {
        this.write(array);
    }
    
    public void print(String s) {
        if (s == null) {
            s = "null";
        }
        this.write(s);
    }
    
    public void print(final Object o) {
        this.write(String.valueOf(o));
    }
    
    public void println() {
        this.newLine();
    }
    
    public void println(final boolean b) {
        synchronized (this) {
            this.print(b);
            this.newLine();
        }
    }
    
    public void println(final char c) {
        synchronized (this) {
            this.print(c);
            this.newLine();
        }
    }
    
    public void println(final int n) {
        synchronized (this) {
            this.print(n);
            this.newLine();
        }
    }
    
    public void println(final long n) {
        synchronized (this) {
            this.print(n);
            this.newLine();
        }
    }
    
    public void println(final float n) {
        synchronized (this) {
            this.print(n);
            this.newLine();
        }
    }
    
    public void println(final double n) {
        synchronized (this) {
            this.print(n);
            this.newLine();
        }
    }
    
    public void println(final char[] array) {
        synchronized (this) {
            this.print(array);
            this.newLine();
        }
    }
    
    public void println(final String s) {
        synchronized (this) {
            this.print(s);
            this.newLine();
        }
    }
    
    public void println(final Object o) {
        final String value = String.valueOf(o);
        synchronized (this) {
            this.print(value);
            this.newLine();
        }
    }
    
    public PrintStream printf(final String s, final Object... array) {
        return this.format(s, array);
    }
    
    public PrintStream printf(final Locale locale, final String s, final Object... array) {
        return this.format(locale, s, array);
    }
    
    public PrintStream format(final String s, final Object... array) {
        try {
            synchronized (this) {
                this.ensureOpen();
                if (this.formatter == null || this.formatter.locale() != Locale.getDefault()) {
                    this.formatter = new Formatter((Appendable)this);
                }
                this.formatter.format(Locale.getDefault(), s, array);
            }
        }
        catch (InterruptedIOException ex) {
            Thread.currentThread().interrupt();
        }
        catch (IOException ex2) {
            this.trouble = true;
        }
        return this;
    }
    
    public PrintStream format(final Locale locale, final String s, final Object... array) {
        try {
            synchronized (this) {
                this.ensureOpen();
                if (this.formatter == null || this.formatter.locale() != locale) {
                    this.formatter = new Formatter(this, locale);
                }
                this.formatter.format(locale, s, array);
            }
        }
        catch (InterruptedIOException ex) {
            Thread.currentThread().interrupt();
        }
        catch (IOException ex2) {
            this.trouble = true;
        }
        return this;
    }
    
    @Override
    public PrintStream append(final CharSequence charSequence) {
        if (charSequence == null) {
            this.print("null");
        }
        else {
            this.print(charSequence.toString());
        }
        return this;
    }
    
    @Override
    public PrintStream append(final CharSequence charSequence, final int n, final int n2) {
        this.write(((charSequence == null) ? "null" : charSequence).subSequence(n, n2).toString());
        return this;
    }
    
    @Override
    public PrintStream append(final char c) {
        this.print(c);
        return this;
    }
}
