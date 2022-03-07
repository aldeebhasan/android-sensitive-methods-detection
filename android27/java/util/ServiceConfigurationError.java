package java.util;

public class ServiceConfigurationError extends Error
{
    private static final long serialVersionUID = 74132770414881L;
    
    public ServiceConfigurationError(final String s) {
        super(s);
    }
    
    public ServiceConfigurationError(final String s, final Throwable t) {
        super(s, t);
    }
}
