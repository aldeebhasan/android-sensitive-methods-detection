package java.nio.channels.spi;

import java.nio.channels.*;

public abstract class AbstractSelectionKey extends SelectionKey
{
    private volatile boolean valid;
    
    protected AbstractSelectionKey() {
        this.valid = true;
    }
    
    @Override
    public final boolean isValid() {
        return this.valid;
    }
    
    void invalidate() {
        this.valid = false;
    }
    
    @Override
    public final void cancel() {
        synchronized (this) {
            if (this.valid) {
                this.valid = false;
                ((AbstractSelector)this.selector()).cancel(this);
            }
        }
    }
}
