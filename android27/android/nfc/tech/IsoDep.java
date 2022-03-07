package android.nfc.tech;

import android.nfc.*;
import java.io.*;

public final class IsoDep extends BasicTagTechnology
{
    IsoDep() {
        throw new RuntimeException("Stub!");
    }
    
    public static IsoDep get(final Tag tag) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTimeout(final int timeout) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTimeout() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getHistoricalBytes() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getHiLayerResponse() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] transceive(final byte[] data) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxTransceiveLength() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isExtendedLengthApduSupported() {
        throw new RuntimeException("Stub!");
    }
}
