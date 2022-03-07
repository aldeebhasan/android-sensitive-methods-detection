package java.nio.channels;

import java.io.*;
import java.nio.channels.spi.*;

public abstract class Pipe
{
    public abstract SourceChannel source();
    
    public abstract SinkChannel sink();
    
    public static Pipe open() throws IOException {
        return SelectorProvider.provider().openPipe();
    }
    
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
}
