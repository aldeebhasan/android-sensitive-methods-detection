package java.util.logging;

public class ErrorManager
{
    private boolean reported;
    public static final int GENERIC_FAILURE = 0;
    public static final int WRITE_FAILURE = 1;
    public static final int FLUSH_FAILURE = 2;
    public static final int CLOSE_FAILURE = 3;
    public static final int OPEN_FAILURE = 4;
    public static final int FORMAT_FAILURE = 5;
    
    public ErrorManager() {
        this.reported = false;
    }
    
    public synchronized void error(final String s, final Exception ex, final int n) {
        if (this.reported) {
            return;
        }
        this.reported = true;
        String s2 = "java.util.logging.ErrorManager: " + n;
        if (s != null) {
            s2 = s2 + ": " + s;
        }
        System.err.println(s2);
        if (ex != null) {
            ex.printStackTrace();
        }
    }
}
