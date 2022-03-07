package org.xml.sax;

public class SAXException extends Exception
{
    private Exception exception;
    static final long serialVersionUID = 583241635256073760L;
    
    public SAXException() {
        this.exception = null;
    }
    
    public SAXException(final String s) {
        super(s);
        this.exception = null;
    }
    
    public SAXException(final Exception exception) {
        this.exception = exception;
    }
    
    public SAXException(final String s, final Exception exception) {
        super(s);
        this.exception = exception;
    }
    
    @Override
    public String getMessage() {
        final String message = super.getMessage();
        if (message == null && this.exception != null) {
            return this.exception.getMessage();
        }
        return message;
    }
    
    public Exception getException() {
        return this.exception;
    }
    
    @Override
    public Throwable getCause() {
        return this.exception;
    }
    
    @Override
    public String toString() {
        if (this.exception != null) {
            return super.toString() + "\n" + this.exception.toString();
        }
        return super.toString();
    }
}
