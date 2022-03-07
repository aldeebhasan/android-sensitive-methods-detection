package android.media.midi;

import java.io.*;

public final class MidiInputPort extends MidiReceiver implements Closeable
{
    MidiInputPort() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getPortNumber() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onSend(final byte[] msg, final int offset, final int count, final long timestamp) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onFlush() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
}
