package android.os;

public abstract class VibrationEffect implements Parcelable
{
    public static final Creator<VibrationEffect> CREATOR;
    public static final int DEFAULT_AMPLITUDE = -1;
    
    VibrationEffect() {
        throw new RuntimeException("Stub!");
    }
    
    public static VibrationEffect createOneShot(final long milliseconds, final int amplitude) {
        throw new RuntimeException("Stub!");
    }
    
    public static VibrationEffect createWaveform(final long[] timings, final int repeat) {
        throw new RuntimeException("Stub!");
    }
    
    public static VibrationEffect createWaveform(final long[] timings, final int[] amplitudes, final int repeat) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
