package android.nfc.tech;

import java.io.*;
import android.nfc.*;

public final class NdefFormatable extends BasicTagTechnology
{
    NdefFormatable() {
        throw new RuntimeException("Stub!");
    }
    
    public static NdefFormatable get(final Tag tag) {
        throw new RuntimeException("Stub!");
    }
    
    public void format(final NdefMessage firstMessage) throws IOException, FormatException {
        throw new RuntimeException("Stub!");
    }
    
    public void formatReadOnly(final NdefMessage firstMessage) throws IOException, FormatException {
        throw new RuntimeException("Stub!");
    }
}
