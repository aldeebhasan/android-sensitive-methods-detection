package java.util;

public class UnknownFormatFlagsException extends IllegalFormatException
{
    private static final long serialVersionUID = 19370506L;
    private String flags;
    
    public UnknownFormatFlagsException(final String flags) {
        if (flags == null) {
            throw new NullPointerException();
        }
        this.flags = flags;
    }
    
    public String getFlags() {
        return this.flags;
    }
    
    @Override
    public String getMessage() {
        return "Flags = " + this.flags;
    }
}
