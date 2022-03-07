package java.util;

public class IllegalFormatFlagsException extends IllegalFormatException
{
    private static final long serialVersionUID = 790824L;
    private String flags;
    
    public IllegalFormatFlagsException(final String flags) {
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
        return "Flags = '" + this.flags + "'";
    }
}
