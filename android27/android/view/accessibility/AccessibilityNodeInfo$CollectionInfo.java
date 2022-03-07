package android.view.accessibility;

public static final class CollectionInfo
{
    public static final int SELECTION_MODE_MULTIPLE = 2;
    public static final int SELECTION_MODE_NONE = 0;
    public static final int SELECTION_MODE_SINGLE = 1;
    
    CollectionInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public static CollectionInfo obtain(final int rowCount, final int columnCount, final boolean hierarchical) {
        throw new RuntimeException("Stub!");
    }
    
    public static CollectionInfo obtain(final int rowCount, final int columnCount, final boolean hierarchical, final int selectionMode) {
        throw new RuntimeException("Stub!");
    }
    
    public int getRowCount() {
        throw new RuntimeException("Stub!");
    }
    
    public int getColumnCount() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isHierarchical() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSelectionMode() {
        throw new RuntimeException("Stub!");
    }
}
