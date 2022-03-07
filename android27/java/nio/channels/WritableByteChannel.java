package java.nio.channels;

import java.nio.*;
import java.io.*;

public interface WritableByteChannel extends Channel
{
    int write(final ByteBuffer p0) throws IOException;
}
