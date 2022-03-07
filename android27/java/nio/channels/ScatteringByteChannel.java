package java.nio.channels;

import java.nio.*;
import java.io.*;

public interface ScatteringByteChannel extends ReadableByteChannel
{
    long read(final ByteBuffer[] p0, final int p1, final int p2) throws IOException;
    
    long read(final ByteBuffer[] p0) throws IOException;
}
