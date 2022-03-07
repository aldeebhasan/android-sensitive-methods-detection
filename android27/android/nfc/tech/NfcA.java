package android.nfc.tech;

import android.nfc.*;
import java.io.*;

public final class NfcA extends BasicTagTechnology
{
    NfcA() {
        throw new RuntimeException("Stub!");
    }
    
    public static NfcA get(final Tag tag) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getAtqa() {
        throw new RuntimeException("Stub!");
    }
    
    public short getSak() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] transceive(final byte[] data) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxTransceiveLength() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTimeout(final int timeout) {
        throw new RuntimeException("Stub!");
    }
    
    public int getTimeout() {
        throw new RuntimeException("Stub!");
    }
}
