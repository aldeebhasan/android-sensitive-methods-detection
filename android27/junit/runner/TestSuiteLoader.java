package junit.runner;

public interface TestSuiteLoader
{
    Class load(final String p0) throws ClassNotFoundException;
    
    Class reload(final Class p0) throws ClassNotFoundException;
}
