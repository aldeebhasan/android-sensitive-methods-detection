package android.telecom;

import java.io.*;

public static final class RttCall
{
    public static final int RTT_MODE_FULL = 1;
    public static final int RTT_MODE_HCO = 2;
    public static final int RTT_MODE_VCO = 3;
    
    RttCall() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRttAudioMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRttMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public void write(final String input) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public String read() {
        throw new RuntimeException("Stub!");
    }
    
    public String readImmediately() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
