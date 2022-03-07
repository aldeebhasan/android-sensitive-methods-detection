package javax.net.ssl;

import java.util.*;

public interface HandshakeCompletedListener extends EventListener
{
    void handshakeCompleted(final HandshakeCompletedEvent p0);
}
