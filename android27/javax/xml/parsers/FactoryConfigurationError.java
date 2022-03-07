package javax.xml.parsers;

public class FactoryConfigurationError extends Error
{
    private static final long serialVersionUID = -827108682472263355L;
    private Exception exception;
    
    public FactoryConfigurationError() {
        this.exception = null;
    }
    
    public FactoryConfigurationError(final String s) {
        super(s);
        this.exception = null;
    }
    
    public FactoryConfigurationError(final Exception exception) {
        super(exception.toString());
        this.exception = exception;
    }
    
    public FactoryConfigurationError(final Exception exception, final String s) {
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
