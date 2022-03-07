package javax.xml.xpath;

import java.io.*;

public class XPathException extends Exception
{
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = -1837080260374986980L;
    
    public XPathException(final String s) {
        super(s);
        if (s == null) {
            throw new NullPointerException("message can't be null");
        }
    }
    
    public XPathException(final Throwable t) {
        super(t);
        if (t == null) {
            throw new NullPointerException("cause can't be null");
        }
    }
    
    @Override
    public Throwable getCause() {
        return super.getCause();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.putFields().put("cause", super.getCause());
        objectOutputStream.writeFields();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final Throwable t = (Throwable)objectInputStream.readFields().get("cause", null);
        if (super.getCause() == null && t != null) {
            try {
                super.initCause(t);
            }
            catch (IllegalStateException ex) {
                throw new InvalidClassException("Inconsistent state: two causes");
            }
        }
    }
    
    @Override
    public void printStackTrace(final PrintStream printStream) {
        if (this.getCause() != null) {
            this.getCause().printStackTrace(printStream);
            printStream.println("--------------- linked to ------------------");
        }
        super.printStackTrace(printStream);
    }
    
    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }
    
    @Override
    public void printStackTrace(final PrintWriter printWriter) {
        if (this.getCause() != null) {
            this.getCause().printStackTrace(printWriter);
            printWriter.println("--------------- linked to ------------------");
        }
        super.printStackTrace(printWriter);
    }
    
    static {
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("cause", Throwable.class) };
    }
}
