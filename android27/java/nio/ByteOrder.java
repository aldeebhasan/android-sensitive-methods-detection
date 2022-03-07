package java.nio;

public final class ByteOrder
{
    private String name;
    public static final ByteOrder BIG_ENDIAN;
    public static final ByteOrder LITTLE_ENDIAN;
    
    private ByteOrder(final String name) {
        this.name = name;
    }
    
    public static ByteOrder nativeOrder() {
        return Bits.byteOrder();
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        BIG_ENDIAN = new ByteOrder("BIG_ENDIAN");
        LITTLE_ENDIAN = new ByteOrder("LITTLE_ENDIAN");
    }
}
