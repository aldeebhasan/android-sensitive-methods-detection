package javax.net.ssl;

public enum HandshakeStatus
{
    NOT_HANDSHAKING, 
    FINISHED, 
    NEED_TASK, 
    NEED_WRAP, 
    NEED_UNWRAP;
}
