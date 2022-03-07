package javax.sql;

import java.sql.*;

public interface RowSetWriter
{
    boolean writeData(final RowSetInternal p0) throws SQLException;
}
