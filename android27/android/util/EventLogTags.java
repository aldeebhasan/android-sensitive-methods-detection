package android.util;

import java.io.*;

@Deprecated
public class EventLogTags
{
    public EventLogTags() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public EventLogTags(final BufferedReader input) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public Description get(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public Description get(final int tag) {
        throw new RuntimeException("Stub!");
    }
    
    public static class Description
    {
        public final String mName;
        public final int mTag;
        
        Description() {
            throw new RuntimeException("Stub!");
        }
    }
}
