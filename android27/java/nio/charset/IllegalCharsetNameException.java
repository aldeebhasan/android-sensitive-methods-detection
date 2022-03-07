package java.nio.charset;

public class IllegalCharsetNameException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1457525358470002989L;
    private String charsetName;
    
    public IllegalCharsetNameException(final String charsetName) {
        super(String.valueOf(charsetName));
        this.charsetName = charsetName;
    }
    
    public String getCharsetName() {
        return this.charsetName;
    }
}
