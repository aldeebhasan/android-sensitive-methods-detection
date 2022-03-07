package javax.sql;

import java.sql.*;

public interface DataSource extends CommonDataSource, Wrapper
{
    Connection getConnection() throws SQLException;
    
    Connection getConnection(final String p0, final String p1) throws SQLException;
}
