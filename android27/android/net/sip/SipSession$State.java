package android.net.sip;

public static class State
{
    public static final int DEREGISTERING = 2;
    public static final int INCOMING_CALL = 3;
    public static final int INCOMING_CALL_ANSWERING = 4;
    public static final int IN_CALL = 8;
    public static final int NOT_DEFINED = 101;
    public static final int OUTGOING_CALL = 5;
    public static final int OUTGOING_CALL_CANCELING = 7;
    public static final int OUTGOING_CALL_RING_BACK = 6;
    public static final int PINGING = 9;
    public static final int READY_TO_CALL = 0;
    public static final int REGISTERING = 1;
    
    State() {
        throw new RuntimeException("Stub!");
    }
    
    public static String toString(final int state) {
        throw new RuntimeException("Stub!");
    }
}
