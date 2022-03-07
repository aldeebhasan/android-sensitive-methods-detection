package javax.sql;

import java.sql.*;

public interface PooledConnection
{
    Connection getConnection() throws SQLException;
    
    void close() throws SQLException;
    
    void addConnectionEventListener(final ConnectionEventListener p0);
    
    void removeConnectionEventListener(final ConnectionEventListener p0);
    
    void addStatementEventListener(final StatementEventListener p0);
    
    void removeStatementEventListener(final StatementEventListener p0);
}
