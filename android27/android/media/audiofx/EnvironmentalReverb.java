package android.media.audiofx;

public class EnvironmentalReverb extends AudioEffect
{
    public static final int PARAM_DECAY_HF_RATIO = 3;
    public static final int PARAM_DECAY_TIME = 2;
    public static final int PARAM_DENSITY = 9;
    public static final int PARAM_DIFFUSION = 8;
    public static final int PARAM_REFLECTIONS_DELAY = 5;
    public static final int PARAM_REFLECTIONS_LEVEL = 4;
    public static final int PARAM_REVERB_DELAY = 7;
    public static final int PARAM_REVERB_LEVEL = 6;
    public static final int PARAM_ROOM_HF_LEVEL = 1;
    public static final int PARAM_ROOM_LEVEL = 0;
    
    public EnvironmentalReverb(final int priority, final int audioSession) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        throw new RuntimeException("Stub!");
    }
    
    public void setRoomLevel(final short room) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getRoomLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setRoomHFLevel(final short roomHF) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getRoomHFLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDecayTime(final int decayTime) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public int getDecayTime() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDecayHFRatio(final short decayHFRatio) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getDecayHFRatio() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setReflectionsLevel(final short reflectionsLevel) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getReflectionsLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setReflectionsDelay(final int reflectionsDelay) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public int getReflectionsDelay() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setReverbLevel(final short reverbLevel) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getReverbLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setReverbDelay(final int reverbDelay) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public int getReverbDelay() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDiffusion(final short diffusion) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getDiffusion() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setDensity(final short density) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getDensity() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setParameterListener(final OnParameterChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public void setProperties(final Settings settings) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
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
    
    public interface OnParameterChangeListener
    {
        void onParameterChange(final EnvironmentalReverb p0, final int p1, final int p2, final int p3);
    }
}
