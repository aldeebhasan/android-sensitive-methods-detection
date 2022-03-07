package java.net;

import java.util.*;
import java.io.*;

public abstract class CacheResponse
{
    public abstract Map<String, List<String>> getHeaders() throws IOException;
    
    public abstract InputStream getBody() throws IOException;
}
