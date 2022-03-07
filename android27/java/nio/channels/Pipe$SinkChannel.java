package java.nio.channels;

import java.nio.channels.spi.*;

public abstract static class SinkChannel extends AbstractSelectableChannel implements WritableByteChannel, GatheringByteChannel
{
    protected SinkChannel(final SelectorProvider selectorProvider) {
        super(selectorProvider);
    }
    
    @Override
    public final int validOps() {
        return 4;
    }
}
