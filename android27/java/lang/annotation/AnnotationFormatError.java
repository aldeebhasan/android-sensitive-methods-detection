package java.lang.annotation;

public class AnnotationFormatError extends Error
{
    private static final long serialVersionUID = -4256701562333669892L;
    
    public AnnotationFormatError(final String s) {
        super(s);
    }
    
    public AnnotationFormatError(final String s, final Throwable t) {
        super(s, t);
    }
    
    public AnnotationFormatError(final Throwable t) {
        super(t);
    }
}
