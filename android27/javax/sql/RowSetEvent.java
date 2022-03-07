package javax.sql;

import java.util.*;

public class RowSetEvent extends EventObject
{
    static final long serialVersionUID = -1875450876546332005L;
    
    public RowSetEvent(final RowSet set) {
        super(set);
    }
}
