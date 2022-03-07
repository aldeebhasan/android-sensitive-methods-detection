package java.nio.channels;

import java.io.*;

public interface Channel extends Closeable
{
    boolean isOpen();
    
    void close() throws IOException;
}
