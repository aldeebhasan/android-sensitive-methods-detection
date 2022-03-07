package android.nfc.tech;

import android.nfc.*;
import java.io.*;

public final class NfcF extends BasicTagTechnology
{
    NfcF() {
        throw new RuntimeException("Stub!");
    }
    
    public static NfcF get(final Tag tag) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getSystemCode() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getManufacturer() {
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
