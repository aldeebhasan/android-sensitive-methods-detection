package android.nfc.tech;

import android.nfc.*;
import java.io.*;

public final class NfcB extends BasicTagTechnology
{
    NfcB() {
        throw new RuntimeException("Stub!");
    }
    
    public static NfcB get(final Tag tag) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getApplicationData() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getProtocolInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] transceive(final byte[] data) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxTransceiveLength() {
        throw new RuntimeException("Stub!");
    }
}
