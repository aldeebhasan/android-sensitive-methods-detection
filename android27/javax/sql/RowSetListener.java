package javax.sql;

import java.util.*;

public interface RowSetListener extends EventListener
{
    void rowSetChanged(final RowSetEvent p0);
    
    void rowChanged(final RowSetEvent p0);
    
    void cursorMoved(final RowSetEvent p0);
}
