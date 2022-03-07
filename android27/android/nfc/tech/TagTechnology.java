package android.nfc.tech;

import android.nfc.*;
import java.io.*;

public interface TagTechnology extends Closeable
{
    Tag getTag();
    
    void connect() throws IOException;
    
    void close() throws IOException;
    
    boolean isConnected();
}
