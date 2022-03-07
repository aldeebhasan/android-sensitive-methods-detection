package android.test;

@Deprecated
public interface PerformanceTestCase
{
    int startPerformance(final Intermediates p0);
    
    boolean isPerformanceOnly();
    
    public interface Intermediates
    {
        void setInternalIterations(final int p0);
        
        void startTiming(final boolean p0);
        
        void addIntermediate(final String p0);
        
        void addIntermediate(final String p0, final long p1);
        
        void finishTiming(final boolean p0);
    }
}
