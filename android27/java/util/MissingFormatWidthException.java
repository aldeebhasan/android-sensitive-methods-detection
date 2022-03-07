package java.util;

public class MissingFormatWidthException extends IllegalFormatException
{
    private static final long serialVersionUID = 15560123L;
    private String s;
    
    public MissingFormatWidthException(final String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        this.s = s;
    }
    
    public String getFormatSpecifier() {
        return this.s;
    }
    
    @Override
    public String getMessage() {
        return this.s;
    }
}
