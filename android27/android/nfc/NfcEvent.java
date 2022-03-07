package android.nfc;

public final class NfcEvent
{
    public final NfcAdapter nfcAdapter;
    public final int peerLlcpMajorVersion;
    public final int peerLlcpMinorVersion;
    
    NfcEvent() {
        throw new RuntimeException("Stub!");
    }
}
