package java.io;

public class InvalidClassException extends ObjectStreamException
{
    private static final long serialVersionUID = -4333316296251054416L;
    public String classname;
    
    public InvalidClassException(final String s) {
        super(s);
    }
    
    public InvalidClassException(final String classname, final String s) {
        super(s);
        this.classname = classname;
    }
    
    @Override
    public String getMessage() {
        if (this.classname == null) {
            return super.getMessage();
        }
        return this.classname + "; " + super.getMessage();
    }
}
