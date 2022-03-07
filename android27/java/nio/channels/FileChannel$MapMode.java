package java.nio.channels;

public static class MapMode
{
    public static final MapMode READ_ONLY;
    public static final MapMode READ_WRITE;
    public static final MapMode PRIVATE;
    private final String name;
    
    private MapMode(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        READ_ONLY = new MapMode("READ_ONLY");
        READ_WRITE = new MapMode("READ_WRITE");
        PRIVATE = new MapMode("PRIVATE");
    }
}
