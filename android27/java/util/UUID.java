package java.util;

import java.io.*;
import java.security.*;

public final class UUID implements Serializable, Comparable<UUID>
{
    private static final long serialVersionUID = -4856846361193249489L;
    private final long mostSigBits;
    private final long leastSigBits;
    
    private UUID(final byte[] array) {
        long mostSigBits = 0L;
        long leastSigBits = 0L;
        assert array.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; ++i) {
            mostSigBits = (mostSigBits << 8 | (array[i] & 0xFF));
        }
        for (int j = 8; j < 16; ++j) {
            leastSigBits = (leastSigBits << 8 | (array[j] & 0xFF));
        }
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }
    
    public UUID(final long mostSigBits, final long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }
    
    public static UUID randomUUID() {
        final SecureRandom numberGenerator = Holder.numberGenerator;
        final byte[] array = new byte[16];
        numberGenerator.nextBytes(array);
        final byte[] array2 = array;
        final int n = 6;
        array2[n] &= 0xF;
        final byte[] array3 = array;
        final int n2 = 6;
        array3[n2] |= 0x40;
        final byte[] array4 = array;
        final int n3 = 8;
        array4[n3] &= 0x3F;
        final byte[] array5 = array;
        final int n4 = 8;
        array5[n4] |= (byte)128;
        return new UUID(array);
    }
    
    public static UUID nameUUIDFromBytes(final byte[] array) {
        MessageDigest instance;
        try {
            instance = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex) {
            throw new InternalError("MD5 not supported", ex);
        }
        final byte[] digest;
        final byte[] array2 = digest = instance.digest(array);
        final int n = 6;
        digest[n] &= 0xF;
        final byte[] array3 = array2;
        final int n2 = 6;
        array3[n2] |= 0x30;
        final byte[] array4 = array2;
        final int n3 = 8;
        array4[n3] &= 0x3F;
        final byte[] array5 = array2;
        final int n4 = 8;
        array5[n4] |= (byte)128;
        return new UUID(array2);
    }
    
    public static UUID fromString(final String s) {
        final String[] split = s.split("-");
        if (split.length != 5) {
            throw new IllegalArgumentException("Invalid UUID string: " + s);
        }
        for (int i = 0; i < 5; ++i) {
            split[i] = "0x" + split[i];
        }
        return new UUID((Long.decode(split[0]) << 16 | Long.decode(split[1])) << 16 | Long.decode(split[2]), Long.decode(split[3]) << 48 | Long.decode(split[4]));
    }
    
    public long getLeastSignificantBits() {
        return this.leastSigBits;
    }
    
    public long getMostSignificantBits() {
        return this.mostSigBits;
    }
    
    public int version() {
        return (int)(this.mostSigBits >> 12 & 0xFL);
    }
    
    public int variant() {
        return (int)(this.leastSigBits >>> (int)(64L - (this.leastSigBits >>> 62)) & this.leastSigBits >> 63);
    }
    
    public long timestamp() {
        if (this.version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        return (this.mostSigBits & 0xFFFL) << 48 | (this.mostSigBits >> 16 & 0xFFFFL) << 32 | this.mostSigBits >>> 32;
    }
    
    public int clockSequence() {
        if (this.version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        return (int)((this.leastSigBits & 0x3FFF000000000000L) >>> 48);
    }
    
    public long node() {
        if (this.version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        return this.leastSigBits & 0xFFFFFFFFFFFFL;
    }
    
    @Override
    public String toString() {
        return digits(this.mostSigBits >> 32, 8) + "-" + digits(this.mostSigBits >> 16, 4) + "-" + digits(this.mostSigBits, 4) + "-" + digits(this.leastSigBits >> 48, 4) + "-" + digits(this.leastSigBits, 12);
    }
    
    private static String digits(final long n, final int n2) {
        final long n3 = 1L << n2 * 4;
        return Long.toHexString(n3 | (n & n3 - 1L)).substring(1);
    }
    
    @Override
    public int hashCode() {
        final long n = this.mostSigBits ^ this.leastSigBits;
        return (int)(n >> 32) ^ (int)n;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (null == o || o.getClass() != UUID.class) {
            return false;
        }
        final UUID uuid = (UUID)o;
        return this.mostSigBits == uuid.mostSigBits && this.leastSigBits == uuid.leastSigBits;
    }
    
    @Override
    public int compareTo(final UUID uuid) {
        return (this.mostSigBits < uuid.mostSigBits) ? -1 : ((this.mostSigBits > uuid.mostSigBits) ? 1 : ((this.leastSigBits < uuid.leastSigBits) ? -1 : ((this.leastSigBits > uuid.leastSigBits) ? 1 : 0)));
    }
    
    private static class Holder
    {
        static final SecureRandom numberGenerator;
        
        static {
            numberGenerator = new SecureRandom();
        }
    }
}
