package javax.sql;

import java.io.*;
import java.util.logging.*;
import java.sql.*;

public interface CommonDataSource
{
    PrintWriter getLogWriter() throws SQLException;
    
    void setLogWriter(final PrintWriter p0) throws SQLException;
    
    void setLoginTimeout(final int p0) throws SQLException;
    
    int getLoginTimeout() throws SQLException;
    
    Logger getParentLogger() throws SQLFeatureNotSupportedException;
}
