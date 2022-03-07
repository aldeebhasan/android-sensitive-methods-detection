package java.util.prefs;

public class InvalidPreferencesFormatException extends Exception
{
    private static final long serialVersionUID = -791715184232119669L;
    
    public InvalidPreferencesFormatException(final Throwable t) {
        super(t);
    }
    
    public InvalidPreferencesFormatException(final String s) {
        super(s);
    }
    
    public InvalidPreferencesFormatException(final String s, final Throwable t) {
        super(s, t);
    }
}
