package javax.xml.transform;

public class TransformerConfigurationException extends TransformerException
{
    private static final long serialVersionUID = 1285547467942875745L;
    
    public TransformerConfigurationException() {
        super("Configuration Error");
    }
    
    public TransformerConfigurationException(final String s) {
        super(s);
    }
    
    public TransformerConfigurationException(final Throwable t) {
        super(t);
    }
    
    public TransformerConfigurationException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public TransformerConfigurationException(final String s, final SourceLocator sourceLocator) {
        super(s, sourceLocator);
    }
    
    public TransformerConfigurationException(final String s, final SourceLocator sourceLocator, final Throwable t) {
        super(s, sourceLocator, t);
    }
}
