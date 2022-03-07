package javax.sql;

import java.sql.*;

public interface ConnectionPoolDataSource extends CommonDataSource
{
    PooledConnection getPooledConnection() throws SQLException;
    
    PooledConnection getPooledConnection(final String p0, final String p1) throws SQLException;
}
