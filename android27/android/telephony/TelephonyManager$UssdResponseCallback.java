package android.telephony;

public abstract static class UssdResponseCallback
{
    public UssdResponseCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onReceiveUssdResponse(final TelephonyManager telephonyManager, final String request, final CharSequence response) {
        throw new RuntimeException("Stub!");
    }
    
    public void onReceiveUssdResponseFailed(final TelephonyManager telephonyManager, final String request, final int failureCode) {
        throw new RuntimeException("Stub!");
    }
}
