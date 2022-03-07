package java.nio.channels;

import java.io.*;

public interface AsynchronousChannel extends Channel
{
    void close() throws IOException;
}
