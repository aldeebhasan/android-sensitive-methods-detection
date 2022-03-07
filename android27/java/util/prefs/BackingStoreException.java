package java.util.prefs;

public class BackingStoreException extends Exception
{
    private static final long serialVersionUID = 859796500401108469L;
    
    public BackingStoreException(final String s) {
        super(s);
    }
    
    public BackingStoreException(final Throwable t) {
        super(t);
    }
}
