package java.net;

public final class StandardSocketOptions
{
    public static final SocketOption<Boolean> SO_BROADCAST;
    public static final SocketOption<Boolean> SO_KEEPALIVE;
    public static final SocketOption<Integer> SO_SNDBUF;
    public static final SocketOption<Integer> SO_RCVBUF;
    public static final SocketOption<Boolean> SO_REUSEADDR;
    public static final SocketOption<Integer> SO_LINGER;
    public static final SocketOption<Integer> IP_TOS;
    public static final SocketOption<NetworkInterface> IP_MULTICAST_IF;
    public static final SocketOption<Integer> IP_MULTICAST_TTL;
    public static final SocketOption<Boolean> IP_MULTICAST_LOOP;
    public static final SocketOption<Boolean> TCP_NODELAY;
    
    static {
        SO_BROADCAST = new StdSocketOption<Boolean>("SO_BROADCAST", Boolean.class);
        SO_KEEPALIVE = new StdSocketOption<Boolean>("SO_KEEPALIVE", Boolean.class);
        SO_SNDBUF = new StdSocketOption<Integer>("SO_SNDBUF", Integer.class);
        SO_RCVBUF = new StdSocketOption<Integer>("SO_RCVBUF", Integer.class);
        SO_REUSEADDR = new StdSocketOption<Boolean>("SO_REUSEADDR", Boolean.class);
        SO_LINGER = new StdSocketOption<Integer>("SO_LINGER", Integer.class);
        IP_TOS = new StdSocketOption<Integer>("IP_TOS", Integer.class);
        IP_MULTICAST_IF = new StdSocketOption<NetworkInterface>("IP_MULTICAST_IF", NetworkInterface.class);
        IP_MULTICAST_TTL = new StdSocketOption<Integer>("IP_MULTICAST_TTL", Integer.class);
        IP_MULTICAST_LOOP = new StdSocketOption<Boolean>("IP_MULTICAST_LOOP", Boolean.class);
        TCP_NODELAY = new StdSocketOption<Boolean>("TCP_NODELAY", Boolean.class);
    }
    
    private static class StdSocketOption<T> implements SocketOption<T>
    {
        private final String name;
        private final Class<T> type;
        
        StdSocketOption(final String name, final Class<T> type) {
            this.name = name;
            this.type = type;
        }
        
        @Override
        public String name() {
            return this.name;
        }
        
        @Override
        public Class<T> type() {
            return this.type;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
}
