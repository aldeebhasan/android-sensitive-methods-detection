package java.nio.charset;

public class CodingErrorAction
{
    private String name;
    public static final CodingErrorAction IGNORE;
    public static final CodingErrorAction REPLACE;
    public static final CodingErrorAction REPORT;
    
    private CodingErrorAction(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        IGNORE = new CodingErrorAction("IGNORE");
        REPLACE = new CodingErrorAction("REPLACE");
        REPORT = new CodingErrorAction("REPORT");
    }
}
