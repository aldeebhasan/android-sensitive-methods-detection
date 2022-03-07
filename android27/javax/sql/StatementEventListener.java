package javax.sql;

import java.util.*;

public interface StatementEventListener extends EventListener
{
    void statementClosed(final StatementEvent p0);
    
    void statementErrorOccurred(final StatementEvent p0);
}
