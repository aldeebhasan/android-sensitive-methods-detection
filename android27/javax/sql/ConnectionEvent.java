package javax.sql;

import java.util.*;
import java.sql.*;

public class ConnectionEvent extends EventObject
{
    private SQLException ex;
    static final long serialVersionUID = -4843217645290030002L;
    
    public ConnectionEvent(final PooledConnection pooledConnection) {
        super(pooledConnection);
        this.ex = null;
    }
    
    public ConnectionEvent(final PooledConnection pooledConnection, final SQLException ex) {
        super(pooledConnection);
        this.ex = null;
        this.ex = ex;
    }
    
    public SQLException getSQLException() {
        return this.ex;
    }
}
