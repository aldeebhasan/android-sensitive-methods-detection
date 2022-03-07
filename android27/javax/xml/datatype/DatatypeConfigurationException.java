package javax.xml.datatype;

public class DatatypeConfigurationException extends Exception
{
    public DatatypeConfigurationException() {
    }
    
    public DatatypeConfigurationException(final String s) {
        super(s);
    }
    
    public DatatypeConfigurationException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public DatatypeConfigurationException(final Throwable t) {
        super(t);
    }
}
