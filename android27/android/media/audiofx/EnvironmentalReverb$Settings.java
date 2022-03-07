package android.media.audiofx;

public static class Settings
{
    public short decayHFRatio;
    public int decayTime;
    public short density;
    public short diffusion;
    public int reflectionsDelay;
    public short reflectionsLevel;
    public int reverbDelay;
    public short reverbLevel;
    public short roomHFLevel;
    public short roomLevel;
    
    public Settings() {
        throw new RuntimeException("Stub!");
    }
    
    public Settings(final String settings) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
