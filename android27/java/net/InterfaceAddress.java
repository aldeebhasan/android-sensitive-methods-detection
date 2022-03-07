package java.net;

public class InterfaceAddress
{
    private InetAddress address;
    private Inet4Address broadcast;
    private short maskLength;
    
    InterfaceAddress() {
        this.address = null;
        this.broadcast = null;
        this.maskLength = 0;
    }
    
    public InetAddress getAddress() {
        return this.address;
    }
    
    public InetAddress getBroadcast() {
        return this.broadcast;
    }
    
    public short getNetworkPrefixLength() {
        return this.maskLength;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof InterfaceAddress)) {
            return false;
        }
        final InterfaceAddress interfaceAddress = (InterfaceAddress)o;
        Label_0047: {
            if (this.address == null) {
                if (interfaceAddress.address == null) {
                    break Label_0047;
                }
            }
            else if (this.address.equals(interfaceAddress.address)) {
                break Label_0047;
            }
            return false;
        }
        if (this.broadcast == null) {
            if (interfaceAddress.broadcast == null) {
                return this.maskLength == interfaceAddress.maskLength;
            }
        }
        else if (this.broadcast.equals(interfaceAddress.broadcast)) {
            return this.maskLength == interfaceAddress.maskLength;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.address.hashCode() + ((this.broadcast != null) ? this.broadcast.hashCode() : 0) + this.maskLength;
    }
    
    @Override
    public String toString() {
        return this.address + "/" + this.maskLength + " [" + this.broadcast + "]";
    }
}
