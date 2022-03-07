package android.telephony.gsm;

@Deprecated
public static class SubmitPdu
{
    @Deprecated
    public byte[] encodedMessage;
    @Deprecated
    public byte[] encodedScAddress;
    
    public SubmitPdu() {
        this.encodedMessage = null;
        this.encodedScAddress = null;
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
