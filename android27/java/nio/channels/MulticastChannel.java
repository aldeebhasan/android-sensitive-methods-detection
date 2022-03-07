package java.nio.channels;

import java.io.*;
import java.net.*;

public interface MulticastChannel extends NetworkChannel
{
    void close() throws IOException;
    
    MembershipKey join(final InetAddress p0, final NetworkInterface p1) throws IOException;
    
    MembershipKey join(final InetAddress p0, final NetworkInterface p1, final InetAddress p2) throws IOException;
}
