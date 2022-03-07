package java.nio.channels;

import java.nio.*;
import java.io.*;

public interface ReadableByteChannel extends Channel
{
    int read(final ByteBuffer p0) throws IOException;
}
