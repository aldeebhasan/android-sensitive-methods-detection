package android.nfc.tech;

import android.nfc.*;
import java.io.*;

public final class NfcV extends BasicTagTechnology
{
    NfcV() {
        throw new RuntimeException("Stub!");
    }
    
    public static NfcV get(final Tag tag) {
        throw new RuntimeException("Stub!");
    }
    
    public byte getResponseFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public byte getDsfId() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] transceive(final byte[] data) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxTransceiveLength() {
        throw new RuntimeException("Stub!");
    }
}
