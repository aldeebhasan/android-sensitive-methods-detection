package javax.net.ssl;

import java.util.*;

public interface SSLSessionBindingListener extends EventListener
{
    void valueBound(final SSLSessionBindingEvent p0);
    
    void valueUnbound(final SSLSessionBindingEvent p0);
}
