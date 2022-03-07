package android.system;

public final class StructUtsname
{
    public final String machine;
    public final String nodename;
    public final String release;
    public final String sysname;
    public final String version;
    
    public StructUtsname(final String sysname, final String nodename, final String release, final String version, final String machine) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
