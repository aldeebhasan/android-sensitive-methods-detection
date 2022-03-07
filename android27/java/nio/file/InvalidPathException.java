package java.nio.file;

public class InvalidPathException extends IllegalArgumentException
{
    static final long serialVersionUID = 4355821422286746137L;
    private String input;
    private int index;
    
    public InvalidPathException(final String input, final String s, final int index) {
        super(s);
        if (input == null || s == null) {
            throw new NullPointerException();
        }
        if (index < -1) {
            throw new IllegalArgumentException();
        }
        this.input = input;
        this.index = index;
    }
    
    public InvalidPathException(final String s, final String s2) {
        this(s, s2, -1);
    }
    
    public String getInput() {
        return this.input;
    }
    
    public String getReason() {
        return super.getMessage();
    }
    
    public int getIndex() {
        return this.index;
    }
    
    @Override
    public String getMessage() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getReason());
        if (this.index > -1) {
            sb.append(" at index ");
            sb.append(this.index);
        }
        sb.append(": ");
        sb.append(this.input);
        return sb.toString();
    }
}
