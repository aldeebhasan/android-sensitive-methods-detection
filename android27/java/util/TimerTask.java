package java.util;

public abstract class TimerTask implements Runnable
{
    final Object lock;
    int state;
    static final int VIRGIN = 0;
    static final int SCHEDULED = 1;
    static final int EXECUTED = 2;
    static final int CANCELLED = 3;
    long nextExecutionTime;
    long period;
    
    protected TimerTask() {
        this.lock = new Object();
        this.state = 0;
        this.period = 0L;
    }
    
    @Override
    public abstract void run();
    
    public boolean cancel() {
        synchronized (this.lock) {
            final boolean b = this.state == 1;
            this.state = 3;
            return b;
        }
    }
    
    public long scheduledExecutionTime() {
        synchronized (this.lock) {
            return (this.period < 0L) ? (this.nextExecutionTime + this.period) : (this.nextExecutionTime - this.period);
        }
    }
}
