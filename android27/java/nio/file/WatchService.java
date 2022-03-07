package java.nio.file;

import java.io.*;
import java.util.concurrent.*;

public interface WatchService extends Closeable
{
    void close() throws IOException;
    
    WatchKey poll();
    
    WatchKey poll(final long p0, final TimeUnit p1) throws InterruptedException;
    
    WatchKey take() throws InterruptedException;
}
