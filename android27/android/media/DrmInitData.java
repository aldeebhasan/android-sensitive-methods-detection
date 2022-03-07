package android.media;

import java.util.*;

public abstract class DrmInitData
{
    DrmInitData() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract SchemeInitData get(final UUID p0);
    
    public static final class SchemeInitData
    {
        public final byte[] data;
        public final String mimeType;
        
        SchemeInitData() {
            this.data = null;
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object obj) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }
    }
}
