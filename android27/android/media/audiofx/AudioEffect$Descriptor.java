package android.media.audiofx;

import java.util.*;

public static class Descriptor
{
    public String connectMode;
    public String implementor;
    public String name;
    public UUID type;
    public UUID uuid;
    
    public Descriptor() {
        throw new RuntimeException("Stub!");
    }
    
    public Descriptor(final String type, final String uuid, final String connectMode, final String name, final String implementor) {
        throw new RuntimeException("Stub!");
    }
}
