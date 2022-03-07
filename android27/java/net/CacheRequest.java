package java.net;

import java.io.*;

public abstract class CacheRequest
{
    public abstract OutputStream getBody() throws IOException;
    
    public abstract void abort();
}
