package javax.sql;

import java.util.*;

public interface ConnectionEventListener extends EventListener
{
    void connectionClosed(final ConnectionEvent p0);
    
    void connectionErrorOccurred(final ConnectionEvent p0);
}
