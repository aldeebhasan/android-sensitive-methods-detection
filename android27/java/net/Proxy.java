package java.net;

public class Proxy
{
    private Type type;
    private SocketAddress sa;
    public static final Proxy NO_PROXY;
    
    private Proxy() {
        this.type = Type.DIRECT;
        this.sa = null;
    }
    
    public Proxy(final Type type, final SocketAddress sa) {
        if (type == Type.DIRECT || !(sa instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("type " + type + " is not compatible with address " + sa);
        }
        this.type = type;
        this.sa = sa;
    }
    
    public Type type() {
        return this.type;
    }
    
    public SocketAddress address() {
        return this.sa;
    }
    
    @Override
    public String toString() {
        if (this.type() == Type.DIRECT) {
            return "DIRECT";
        }
        return this.type() + " @ " + this.address();
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (o == null || !(o instanceof Proxy)) {
            return false;
        }
        final Proxy proxy = (Proxy)o;
        if (proxy.type() != this.type()) {
            return false;
        }
        if (this.address() == null) {
            return proxy.address() == null;
        }
        return this.address().equals(proxy.address());
    }
    
    @Override
    public final int hashCode() {
        if (this.address() == null) {
            return this.type().hashCode();
        }
        return this.type().hashCode() + this.address().hashCode();
    }
    
    static {
        NO_PROXY = new Proxy();
    }
    
    public enum Type
    {
        DIRECT, 
        HTTP, 
        SOCKS;
    }
}
