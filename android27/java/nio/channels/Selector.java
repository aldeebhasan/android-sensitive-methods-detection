package java.nio.channels;

import java.nio.channels.spi.*;
import java.io.*;
import java.util.*;

public abstract class Selector implements Closeable
{
    public static Selector open() throws IOException {
        return SelectorProvider.provider().openSelector();
    }
    
    public abstract boolean isOpen();
    
    public abstract SelectorProvider provider();
    
    public abstract Set<SelectionKey> keys();
    
    public abstract Set<SelectionKey> selectedKeys();
    
    public abstract int selectNow() throws IOException;
    
    public abstract int select(final long p0) throws IOException;
    
    public abstract int select() throws IOException;
    
    public abstract Selector wakeup();
    
    @Override
    public abstract void close() throws IOException;
}
