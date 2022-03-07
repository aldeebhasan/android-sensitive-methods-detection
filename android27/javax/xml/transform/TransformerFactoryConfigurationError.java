package javax.xml.transform;

public class TransformerFactoryConfigurationError extends Error
{
    private static final long serialVersionUID = -6527718720676281516L;
    private Exception exception;
    
    public TransformerFactoryConfigurationError() {
        this.exception = null;
    }
    
    public TransformerFactoryConfigurationError(final String s) {
        super(s);
        this.exception = null;
    }
    
    public TransformerFactoryConfigurationError(final Exception exception) {
        super(exception.toString());
        this.exception = exception;
    }
    
    public TransformerFactoryConfigurationError(final Exception exception, final String s) {
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
}
