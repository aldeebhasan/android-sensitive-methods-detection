package java.net;

import sun.misc.*;
import java.io.*;

public class InetSocketAddress extends SocketAddress
{
    private final transient InetSocketAddressHolder holder;
    private static final long serialVersionUID = 5076001401234631237L;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long FIELDS_OFFSET;
    private static final Unsafe UNSAFE;
    
    private static int checkPort(final int n) {
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException("port out of range:" + n);
        }
        return n;
    }
    
    private static String checkHost(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("hostname can't be null");
        }
        return s;
    }
    
    public InetSocketAddress(final int n) {
        this(InetAddress.anyLocalAddress(), n);
    }
    
    public InetSocketAddress(final InetAddress inetAddress, final int n) {
        this.holder = new InetSocketAddressHolder((String)null, (inetAddress == null) ? InetAddress.anyLocalAddress() : inetAddress, checkPort(n));
    }
    
    public InetSocketAddress(final String s, final int n) {
        checkHost(s);
        InetAddress byName = null;
        String s2 = null;
        try {
            byName = InetAddress.getByName(s);
        }
        catch (UnknownHostException ex) {
            s2 = s;
        }
        this.holder = new InetSocketAddressHolder(s2, byName, checkPort(n));
    }
    
    private InetSocketAddress(final int n, final String s) {
        this.holder = new InetSocketAddressHolder(s, (InetAddress)null, n);
    }
    
    public static InetSocketAddress createUnresolved(final String s, final int n) {
        return new InetSocketAddress(checkPort(n), checkHost(s));
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("hostname", this.holder.hostname);
        putFields.put("addr", this.holder.addr);
        putFields.put("port", this.holder.port);
        objectOutputStream.writeFields();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final String s = (String)fields.get("hostname", null);
        final InetAddress inetAddress = (InetAddress)fields.get("addr", null);
        final int value = fields.get("port", -1);
        checkPort(value);
        if (s == null && inetAddress == null) {
            throw new InvalidObjectException("hostname and addr can't both be null");
        }
        InetSocketAddress.UNSAFE.putObject(this, InetSocketAddress.FIELDS_OFFSET, new InetSocketAddressHolder(s, inetAddress, value));
    }
    
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }
    
    public final int getPort() {
        return this.holder.getPort();
    }
    
    public final InetAddress getAddress() {
        return this.holder.getAddress();
    }
    
    public final String getHostName() {
        return this.holder.getHostName();
    }
    
    public final String getHostString() {
        return this.holder.getHostString();
    }
    
    public final boolean isUnresolved() {
        return this.holder.isUnresolved();
    }
    
    @Override
    public String toString() {
        return this.holder.toString();
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o != null && o instanceof InetSocketAddress && this.holder.equals(((InetSocketAddress)o).holder);
    }
    
    @Override
    public final int hashCode() {
        return this.holder.hashCode();
    }
    
    static {
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("hostname", String.class), new ObjectStreamField("addr", InetAddress.class), new ObjectStreamField("port", Integer.TYPE) };
        try {
            final Unsafe unsafe = Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(InetSocketAddress.class.getDeclaredField("holder"));
            UNSAFE = unsafe;
        }
        catch (ReflectiveOperationException ex) {
            throw new Error(ex);
        }
    }
    
    private static class InetSocketAddressHolder
    {
        private String hostname;
        private InetAddress addr;
        private int port;
        
        private InetSocketAddressHolder(final String hostname, final InetAddress addr, final int port) {
            this.hostname = hostname;
            this.addr = addr;
            this.port = port;
        }
        
        private int getPort() {
            return this.port;
        }
        
        private InetAddress getAddress() {
            return this.addr;
        }
        
        private String getHostName() {
            if (this.hostname != null) {
                return this.hostname;
            }
            if (this.addr != null) {
                return this.addr.getHostName();
            }
            return null;
        }
        
        private String getHostString() {
            if (this.hostname != null) {
                return this.hostname;
            }
            if (this.addr == null) {
                return null;
            }
            if (this.addr.holder().getHostName() != null) {
                return this.addr.holder().getHostName();
            }
            return this.addr.getHostAddress();
        }
        
        private boolean isUnresolved() {
            return this.addr == null;
        }
        
        @Override
        public String toString() {
            if (this.isUnresolved()) {
                return this.hostname + ":" + this.port;
            }
            return this.addr.toString() + ":" + this.port;
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (o == null || !(o instanceof InetSocketAddressHolder)) {
                return false;
            }
            final InetSocketAddressHolder inetSocketAddressHolder = (InetSocketAddressHolder)o;
            boolean equals;
            if (this.addr != null) {
                equals = this.addr.equals(inetSocketAddressHolder.addr);
            }
            else if (this.hostname != null) {
                equals = (inetSocketAddressHolder.addr == null && this.hostname.equalsIgnoreCase(inetSocketAddressHolder.hostname));
            }
            else {
                equals = (inetSocketAddressHolder.addr == null && inetSocketAddressHolder.hostname == null);
            }
            return equals && this.port == inetSocketAddressHolder.port;
        }
        
        @Override
        public final int hashCode() {
            if (this.addr != null) {
                return this.addr.hashCode() + this.port;
            }
            if (this.hostname != null) {
                return this.hostname.toLowerCase().hashCode() + this.port;
            }
            return this.port;
        }
    }
}
