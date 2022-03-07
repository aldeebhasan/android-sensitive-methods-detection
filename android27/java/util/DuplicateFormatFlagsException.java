package java.util;

public class DuplicateFormatFlagsException extends IllegalFormatException
{
    private static final long serialVersionUID = 18890531L;
    private String flags;
    
    public DuplicateFormatFlagsException(final String flags) {
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
        return String.format("Flags = '%s'", this.flags);
    }
}
