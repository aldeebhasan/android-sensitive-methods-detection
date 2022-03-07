package java.nio.channels;

import java.io.*;

public interface InterruptibleChannel extends Channel
{
    void close() throws IOException;
}
