package java.util;

public class IllegalFormatWidthException extends IllegalFormatException
{
    private static final long serialVersionUID = 16660902L;
    private int w;
    
    public IllegalFormatWidthException(final int w) {
        this.w = w;
    }
    
    public int getWidth() {
        return this.w;
    }
    
    @Override
    public String getMessage() {
        return Integer.toString(this.w);
    }
}
