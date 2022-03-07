package android.telephony;

public static class SubmitPdu
{
    public byte[] encodedMessage;
    public byte[] encodedScAddress;
    
    SubmitPdu() {
        this.encodedMessage = null;
        this.encodedScAddress = null;
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
