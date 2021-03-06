package java.nio.charset;

public class UnmappableCharacterException extends CharacterCodingException
{
    private static final long serialVersionUID = -7026962371537706123L;
    private int inputLength;
    
    public UnmappableCharacterException(final int inputLength) {
        this.inputLength = inputLength;
    }
    
    public int getInputLength() {
        return this.inputLength;
    }
    
    @Override
    public String getMessage() {
        return "Input length = " + this.inputLength;
    }
}
