package android.net.sip;

public interface SipRegistrationListener
{
    void onRegistering(final String p0);
    
    void onRegistrationDone(final String p0, final long p1);
    
    void onRegistrationFailed(final String p0, final int p1, final String p2);
}
