package java.util;

public class IllegalFormatPrecisionException extends IllegalFormatException
{
    private static final long serialVersionUID = 18711008L;
    private int p;
    
    public IllegalFormatPrecisionException(final int p) {
        this.p = p;
    }
    
    public int getPrecision() {
        return this.p;
    }
    
    @Override
    public String getMessage() {
        return Integer.toString(this.p);
    }
}
