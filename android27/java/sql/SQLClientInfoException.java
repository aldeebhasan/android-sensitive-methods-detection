package java.sql;

import java.util.*;

public class SQLClientInfoException extends SQLException
{
    private Map<String, ClientInfoStatus> failedProperties;
    private static final long serialVersionUID = -4319604256824655880L;
    
    public SQLClientInfoException() {
        this.failedProperties = null;
    }
    
    public SQLClientInfoException(final Map<String, ClientInfoStatus> failedProperties) {
        this.failedProperties = failedProperties;
    }
    
    public SQLClientInfoException(final Map<String, ClientInfoStatus> failedProperties, final Throwable t) {
        super((t != null) ? t.toString() : null);
        this.initCause(t);
        this.failedProperties = failedProperties;
    }
    
    public SQLClientInfoException(final String s, final Map<String, ClientInfoStatus> failedProperties) {
        super(s);
        this.failedProperties = failedProperties;
    }
    
    public SQLClientInfoException(final String s, final Map<String, ClientInfoStatus> failedProperties, final Throwable t) {
        super(s);
        this.initCause(t);
        this.failedProperties = failedProperties;
    }
    
    public SQLClientInfoException(final String s, final String s2, final Map<String, ClientInfoStatus> failedProperties) {
        super(s, s2);
        this.failedProperties = failedProperties;
    }
    
    public SQLClientInfoException(final String s, final String s2, final Map<String, ClientInfoStatus> failedProperties, final Throwable t) {
        super(s, s2);
        this.initCause(t);
        this.failedProperties = failedProperties;
    }
    
    public SQLClientInfoException(final String s, final String s2, final int n, final Map<String, ClientInfoStatus> failedProperties) {
        super(s, s2, n);
        this.failedProperties = failedProperties;
    }
    
    public SQLClientInfoException(final String s, final String s2, final int n, final Map<String, ClientInfoStatus> failedProperties, final Throwable t) {
        super(s, s2, n);
        this.initCause(t);
        this.failedProperties = failedProperties;
    }
    
    public Map<String, ClientInfoStatus> getFailedProperties() {
        return this.failedProperties;
    }
}
