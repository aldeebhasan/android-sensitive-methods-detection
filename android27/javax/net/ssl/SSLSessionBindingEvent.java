package javax.net.ssl;

import java.util.*;

public class SSLSessionBindingEvent extends EventObject
{
    private static final long serialVersionUID = 3989172637106345L;
    private String name;
    
    public SSLSessionBindingEvent(final SSLSession sslSession, final String name) {
        super(sslSession);
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public SSLSession getSession() {
        return (SSLSession)this.getSource();
    }
}
