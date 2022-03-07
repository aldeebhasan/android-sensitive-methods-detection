package java.nio.charset;

public class UnsupportedCharsetException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1490765524727386367L;
    private String charsetName;
    
    public UnsupportedCharsetException(final String charsetName) {
        super(String.valueOf(charsetName));
        this.charsetName = charsetName;
    }
    
    public String getCharsetName() {
        return this.charsetName;
    }
}
