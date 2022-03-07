package java.util;

public class IllformedLocaleException extends RuntimeException
{
    private static final long serialVersionUID = -5245986824925681401L;
    private int _errIdx;
    
    public IllformedLocaleException() {
        this._errIdx = -1;
    }
    
    public IllformedLocaleException(final String s) {
        super(s);
        this._errIdx = -1;
    }
    
    public IllformedLocaleException(final String s, final int errIdx) {
        super(s + ((errIdx < 0) ? "" : (" [at index " + errIdx + "]")));
        this._errIdx = -1;
        this._errIdx = errIdx;
    }
    
    public int getErrorIndex() {
        return this._errIdx;
    }
}
