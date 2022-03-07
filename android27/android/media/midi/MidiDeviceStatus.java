package android.media.midi;

import android.os.*;

public final class MidiDeviceStatus implements Parcelable
{
    public static final Creator<MidiDeviceStatus> CREATOR;
    
    MidiDeviceStatus() {
        throw new RuntimeException("Stub!");
    }
    
    public MidiDeviceInfo getDeviceInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInputPortOpen(final int portNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public int getOutputPortOpenCount(final int portNumber) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
