package java.io;

import java.nio.charset.*;
import sun.security.action.*;
import java.security.*;
import java.util.*;

public class PrintWriter extends Writer
{
    protected Writer out;
    private final boolean autoFlush;
    private boolean trouble;
    private Formatter formatter;
    private PrintStream psOut;
    private final String lineSeparator;
    
    private static Charset toCharset(final String s) throws UnsupportedEncodingException {
        Objects.requireNonNull(s, "charsetName");
        try {
            return Charset.forName(s);
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
            throw new UnsupportedEncodingException(s);
        }
    }
    
    public PrintWriter(final Writer writer) {
        this(writer, false);
    }
    
    public PrintWriter(final Writer out, final boolean autoFlush) {
        super(out);
        this.trouble = false;
        this.psOut = null;
        this.out = out;
        this.autoFlush = autoFlush;
        this.lineSeparator = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("line.separator"));
    }
    
    public PrintWriter(final OutputStream outputStream) {
        this(outputStream, false);
    }
    
    public PrintWriter(final OutputStream outputStream, final boolean b) {
        this(new BufferedWriter(new OutputStreamWriter(outputStream)), b);
        if (outputStream instanceof PrintStream) {
            this.psOut = (PrintStream)outputStream;
        }
    }
    
    public PrintWriter(final String s) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(s))), false);
    }
    
    private PrintWriter(final Charset charset, final File file) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset)), false);
    }
    
    public PrintWriter(final String s, final String s2) throws FileNotFoundException, UnsupportedEncodingException {
        this(toCharset(s2), new File(s));
    }
    
    public PrintWriter(final File file) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file))), false);
    }
    
    public PrintWriter(final File file, final String s) throws FileNotFoundException, UnsupportedEncodingException {
        this(toCharset(s), file);
    }
    
    private void ensureOpen() throws IOException {
        if (this.out == null) {
            throw new IOException("Stream closed");
        }
    }
    
    @Override
    public void flush() {
        try {
            synchronized (this.lock) {
                this.ensureOpen();
                this.out.flush();
            }
        }
        catch (IOException ex) {
            this.trouble = true;
        }
    }
    
    @Override
    public void close() {
        try {
            synchronized (this.lock) {
                if (this.out == null) {
                    return;
                }
                this.out.close();
                this.out = null;
            }
        }
        catch (IOException ex) {
            this.trouble = true;
        }
    }
    
    public boolean checkError() {
        if (this.out != null) {
            this.flush();
        }
        if (this.out instanceof PrintWriter) {
            return ((PrintWriter)this.out).checkError();
        }
        if (this.psOut != null) {
            return this.psOut.checkError();
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
            synchronized (this.lock) {
                this.ensureOpen();
                this.out.write(n);
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
    public void write(final char[] array, final int n, final int n2) {
        try {
            synchronized (this.lock) {
                this.ensureOpen();
                this.out.write(array, n, n2);
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
    public void write(final char[] array) {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final String s, final int n, final int n2) {
        try {
            synchronized (this.lock) {
                this.ensureOpen();
                this.out.write(s, n, n2);
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
    public void write(final String s) {
        this.write(s, 0, s.length());
    }
    
    private void newLine() {
        try {
            synchronized (this.lock) {
                this.ensureOpen();
                this.out.write(this.lineSeparator);
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
        this.write(c);
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
        synchronized (this.lock) {
            this.print(b);
            this.println();
        }
    }
    
    public void println(final char c) {
        synchronized (this.lock) {
            this.print(c);
            this.println();
        }
    }
    
    public void println(final int n) {
        synchronized (this.lock) {
            this.print(n);
            this.println();
        }
    }
    
    public void println(final long n) {
        synchronized (this.lock) {
            this.print(n);
            this.println();
        }
    }
    
    public void println(final float n) {
        synchronized (this.lock) {
            this.print(n);
            this.println();
        }
    }
    
    public void println(final double n) {
        synchronized (this.lock) {
            this.print(n);
            this.println();
        }
    }
    
    public void println(final char[] array) {
        synchronized (this.lock) {
            this.print(array);
            this.println();
        }
    }
    
    public void println(final String s) {
        synchronized (this.lock) {
            this.print(s);
            this.println();
        }
    }
    
    public void println(final Object o) {
        final String value = String.valueOf(o);
        synchronized (this.lock) {
            this.print(value);
            this.println();
        }
    }
    
    public PrintWriter printf(final String s, final Object... array) {
        return this.format(s, array);
    }
    
    public PrintWriter printf(final Locale locale, final String s, final Object... array) {
        return this.format(locale, s, array);
    }
    
    public PrintWriter format(final String s, final Object... array) {
        try {
            synchronized (this.lock) {
                this.ensureOpen();
                if (this.formatter == null || this.formatter.locale() != Locale.getDefault()) {
                    this.formatter = new Formatter(this);
                }
                this.formatter.format(Locale.getDefault(), s, array);
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
        return this;
    }
    
    public PrintWriter format(final Locale locale, final String s, final Object... array) {
        try {
            synchronized (this.lock) {
                this.ensureOpen();
                if (this.formatter == null || this.formatter.locale() != locale) {
                    this.formatter = new Formatter(this, locale);
                }
                this.formatter.format(locale, s, array);
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
        return this;
    }
    
    @Override
    public PrintWriter append(final CharSequence charSequence) {
        if (charSequence == null) {
            this.write("null");
        }
        else {
            this.write(charSequence.toString());
        }
        return this;
    }
    
    @Override
    public PrintWriter append(final CharSequence charSequence, final int n, final int n2) {
        this.write(((charSequence == null) ? "null" : charSequence).subSequence(n, n2).toString());
        return this;
    }
    
    @Override
    public PrintWriter append(final char c) {
        this.write(c);
        return this;
    }
}
