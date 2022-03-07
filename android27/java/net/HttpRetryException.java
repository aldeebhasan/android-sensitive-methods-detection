package java.net;

import java.io.*;

public class HttpRetryException extends IOException
{
    private static final long serialVersionUID = -9186022286469111381L;
    private int responseCode;
    private String location;
    
    public HttpRetryException(final String s, final int responseCode) {
        super(s);
        this.responseCode = responseCode;
    }
    
    public HttpRetryException(final String s, final int responseCode, final String location) {
        super(s);
        this.responseCode = responseCode;
        this.location = location;
    }
    
    public int responseCode() {
        return this.responseCode;
    }
    
    public String getReason() {
        return super.getMessage();
    }
    
    public String getLocation() {
        return this.location;
    }
}
