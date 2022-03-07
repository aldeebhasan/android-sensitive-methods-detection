package java.util.concurrent;

public interface ManagedBlocker
{
    boolean block() throws InterruptedException;
    
    boolean isReleasable();
}
