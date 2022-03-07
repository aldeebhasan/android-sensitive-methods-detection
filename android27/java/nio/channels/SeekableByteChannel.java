package java.nio.channels;

import java.nio.*;
import java.io.*;

public interface SeekableByteChannel extends ByteChannel
{
    int read(final ByteBuffer p0) throws IOException;
    
    int write(final ByteBuffer p0) throws IOException;
    
    long position() throws IOException;
    
    SeekableByteChannel position(final long p0) throws IOException;
    
    long size() throws IOException;
    
    SeekableByteChannel truncate(final long p0) throws IOException;
}
