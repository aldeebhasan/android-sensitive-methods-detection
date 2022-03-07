package android.nfc.tech;

import android.nfc.*;
import java.io.*;

abstract class BasicTagTechnology implements TagTechnology
{
    BasicTagTechnology() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Tag getTag() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isConnected() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void connect() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
