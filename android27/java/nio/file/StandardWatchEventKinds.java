package java.nio.file;

public final class StandardWatchEventKinds
{
    public static final WatchEvent.Kind<Object> OVERFLOW;
    public static final WatchEvent.Kind<Path> ENTRY_CREATE;
    public static final WatchEvent.Kind<Path> ENTRY_DELETE;
    public static final WatchEvent.Kind<Path> ENTRY_MODIFY;
    
    static {
        OVERFLOW = new StdWatchEventKind<Object>("OVERFLOW", Object.class);
        ENTRY_CREATE = new StdWatchEventKind<Path>("ENTRY_CREATE", Path.class);
        ENTRY_DELETE = new StdWatchEventKind<Path>("ENTRY_DELETE", Path.class);
        ENTRY_MODIFY = new StdWatchEventKind<Path>("ENTRY_MODIFY", Path.class);
    }
    
    private static class StdWatchEventKind<T> implements WatchEvent.Kind<T>
    {
        private final String name;
        private final Class<T> type;
        
        StdWatchEventKind(final String name, final Class<T> type) {
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
