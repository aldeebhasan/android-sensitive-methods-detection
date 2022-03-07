package java.net;

import java.io.*;

public final class Inet4Address extends InetAddress
{
    static final int INADDRSZ = 4;
    private static final long serialVersionUID = 3286316764910316507L;
    
    Inet4Address() {
        this.holder().hostName = null;
        this.holder().address = 0;
        this.holder().family = 1;
    }
    
    Inet4Address(final String s, final byte[] array) {
        this.holder().hostName = s;
        this.holder().family = 1;
        if (array != null && array.length == 4) {
            this.holder().address = ((array[3] & 0xFF) | (array[2] << 8 & 0xFF00) | (array[1] << 16 & 0xFF0000) | (array[0] << 24 & 0xFF000000));
        }
        this.holder().originalHostName = s;
    }
    
    Inet4Address(final String s, final int address) {
        this.holder().hostName = s;
        this.holder().family = 1;
        this.holder().address = address;
        this.holder().originalHostName = s;
    }
    
    private Object writeReplace() throws ObjectStreamException {
        final InetAddress inetAddress = new InetAddress();
        inetAddress.holder().hostName = this.holder().getHostName();
        inetAddress.holder().address = this.holder().getAddress();
        inetAddress.holder().family = 2;
        return inetAddress;
    }
    
    @Override
    public boolean isMulticastAddress() {
        return (this.holder().getAddress() & 0xF0000000) == 0xE0000000;
    }
    
    @Override
    public boolean isAnyLocalAddress() {
        return this.holder().getAddress() == 0;
    }
    
    @Override
    public boolean isLoopbackAddress() {
        return this.getAddress()[0] == 127;
    }
    
    @Override
    public boolean isLinkLocalAddress() {
        final int address = this.holder().getAddress();
        return (address >>> 24 & 0xFF) == 0xA9 && (address >>> 16 & 0xFF) == 0xFE;
    }
    
    @Override
    public boolean isSiteLocalAddress() {
        final int address = this.holder().getAddress();
        return (address >>> 24 & 0xFF) == 0xA || ((address >>> 24 & 0xFF) == 0xAC && (address >>> 16 & 0xF0) == 0x10) || ((address >>> 24 & 0xFF) == 0xC0 && (address >>> 16 & 0xFF) == 0xA8);
    }
    
    @Override
    public boolean isMCGlobal() {
        final byte[] address = this.getAddress();
        return (address[0] & 0xFF) >= 224 && (address[0] & 0xFF) <= 238 && ((address[0] & 0xFF) != 0xE0 || address[1] != 0 || address[2] != 0);
    }
    
    @Override
    public boolean isMCNodeLocal() {
        return false;
    }
    
    @Override
    public boolean isMCLinkLocal() {
        final int address = this.holder().getAddress();
        return (address >>> 24 & 0xFF) == 0xE0 && (address >>> 16 & 0xFF) == 0x0 && (address >>> 8 & 0xFF) == 0x0;
    }
    
    @Override
    public boolean isMCSiteLocal() {
        final int address = this.holder().getAddress();
        return (address >>> 24 & 0xFF) == 0xEF && (address >>> 16 & 0xFF) == 0xFF;
    }
    
    @Override
    public boolean isMCOrgLocal() {
        final int address = this.holder().getAddress();
        return (address >>> 24 & 0xFF) == 0xEF && (address >>> 16 & 0xFF) >= 192 && (address >>> 16 & 0xFF) <= 195;
    }
    
    @Override
    public byte[] getAddress() {
        final int address = this.holder().getAddress();
        return new byte[] { (byte)(address >>> 24 & 0xFF), (byte)(address >>> 16 & 0xFF), (byte)(address >>> 8 & 0xFF), (byte)(address & 0xFF) };
    }
    
    @Override
    public String getHostAddress() {
        return numericToTextFormat(this.getAddress());
    }
    
    @Override
    public int hashCode() {
        return this.holder().getAddress();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof Inet4Address && ((InetAddress)o).holder().getAddress() == this.holder().getAddress();
    }
    
    static String numericToTextFormat(final byte[] array) {
        return (array[0] & 0xFF) + "." + (array[1] & 0xFF) + "." + (array[2] & 0xFF) + "." + (array[3] & 0xFF);
    }
    
    private static native void init();
    
    static {
        init();
    }
}
