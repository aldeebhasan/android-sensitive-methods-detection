package java.nio.channels;

import java.nio.channels.spi.*;

public abstract static class SourceChannel extends AbstractSelectableChannel implements ReadableByteChannel, ScatteringByteChannel
{
    protected SourceChannel(final SelectorProvider selectorProvider) {
        super(selectorProvider);
    }
    
    @Override
    public final int validOps() {
        return 1;
    }
}
