package javax.sql;

import java.util.*;
import java.sql.*;

public class StatementEvent extends EventObject
{
    static final long serialVersionUID = -8089573731826608315L;
    private SQLException exception;
    private PreparedStatement statement;
    
    public StatementEvent(final PooledConnection pooledConnection, final PreparedStatement statement) {
        super(pooledConnection);
        this.statement = statement;
        this.exception = null;
    }
    
    public StatementEvent(final PooledConnection pooledConnection, final PreparedStatement statement, final SQLException exception) {
        super(pooledConnection);
        this.statement = statement;
        this.exception = exception;
    }
    
    public PreparedStatement getStatement() {
        return this.statement;
    }
    
    public SQLException getSQLException() {
        return this.exception;
    }
}
