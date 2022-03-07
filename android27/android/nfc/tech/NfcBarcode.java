package android.nfc.tech;

import android.nfc.*;
import java.io.*;

public final class NfcBarcode extends BasicTagTechnology
{
    public static final int TYPE_KOVIO = 1;
    public static final int TYPE_UNKNOWN = -1;
    
    NfcBarcode() {
        throw new RuntimeException("Stub!");
    }
    
    public static NfcBarcode get(final Tag tag) {
        throw new RuntimeException("Stub!");
    }
    
    public int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getBarcode() {
        throw new RuntimeException("Stub!");
    }
}
