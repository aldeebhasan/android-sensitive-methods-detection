package java.sql;

public interface ParameterMetaData extends Wrapper
{
    public static final int parameterNoNulls = 0;
    public static final int parameterNullable = 1;
    public static final int parameterNullableUnknown = 2;
    public static final int parameterModeUnknown = 0;
    public static final int parameterModeIn = 1;
    public static final int parameterModeInOut = 2;
    public static final int parameterModeOut = 4;
    
    int getParameterCount() throws SQLException;
    
    int isNullable(final int p0) throws SQLException;
    
    boolean isSigned(final int p0) throws SQLException;
    
    int getPrecision(final int p0) throws SQLException;
    
    int getScale(final int p0) throws SQLException;
    
    int getParameterType(final int p0) throws SQLException;
    
    String getParameterTypeName(final int p0) throws SQLException;
    
    String getParameterClassName(final int p0) throws SQLException;
    
    int getParameterMode(final int p0) throws SQLException;
}
