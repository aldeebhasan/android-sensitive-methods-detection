package android.media.audiofx;

public class Virtualizer extends AudioEffect
{
    public static final int PARAM_STRENGTH = 1;
    public static final int PARAM_STRENGTH_SUPPORTED = 0;
    public static final int VIRTUALIZATION_MODE_AUTO = 1;
    public static final int VIRTUALIZATION_MODE_BINAURAL = 2;
    public static final int VIRTUALIZATION_MODE_OFF = 0;
    public static final int VIRTUALIZATION_MODE_TRANSAURAL = 3;
    
    public Virtualizer(final int priority, final int audioSession) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getStrengthSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStrength(final short strength) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public short getRoundedStrength() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canVirtualize(final int inputChannelMask, final int virtualizationMode) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getSpeakerAngles(final int inputChannelMask, final int virtualizationMode, final int[] angles) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean forceVirtualizationMode(final int virtualizationMode) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
    
    public int getVirtualizationMode() throws IllegalStateException, UnsupportedOperationException {
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
        public short strength;
        
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
        void onParameterChange(final Virtualizer p0, final int p1, final int p2, final short p3);
    }
}
