package javax.net.ssl;

import java.util.*;

public abstract class SNIServerName
{
    private final int type;
    private final byte[] encoded;
    private static final char[] HEXES;
    
    protected SNIServerName(final int type, final byte[] array) {
        if (type < 0) {
            throw new IllegalArgumentException("Server name type cannot be less than zero");
        }
        if (type > 255) {
            throw new IllegalArgumentException("Server name type cannot be greater than 255");
        }
        this.type = type;
        if (array == null) {
            throw new NullPointerException("Server name encoded value cannot be null");
        }
        this.encoded = array.clone();
    }
    
    public final int getType() {
        return this.type;
    }
    
    public final byte[] getEncoded() {
        return this.encoded.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final SNIServerName sniServerName = (SNIServerName)o;
        return this.type == sniServerName.type && Arrays.equals(this.encoded, sniServerName.encoded);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * 17 + this.type) + Arrays.hashCode(this.encoded);
    }
    
    @Override
    public String toString() {
        if (this.type == 0) {
            return "type=host_name (0), value=" + toHexString(this.encoded);
        }
        return "type=(" + this.type + "), value=" + toHexString(this.encoded);
    }
    
    private static String toHexString(final byte[] array) {
        if (array.length == 0) {
            return "(empty)";
        }
        final StringBuilder sb = new StringBuilder(array.length * 3 - 1);
        int n = 1;
        for (final byte b : array) {
            if (n != 0) {
                n = 0;
            }
            else {
                sb.append(':');
            }
            final int n2 = b & 0xFF;
            sb.append(SNIServerName.HEXES[n2 >>> 4]);
            sb.append(SNIServerName.HEXES[n2 & 0xF]);
        }
        return sb.toString();
    }
    
    static {
        HEXES = "0123456789ABCDEF".toCharArray();
    }
}
