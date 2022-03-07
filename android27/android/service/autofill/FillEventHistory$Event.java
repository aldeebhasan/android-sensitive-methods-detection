package android.service.autofill;

public static final class Event
{
    public static final int TYPE_AUTHENTICATION_SELECTED = 2;
    public static final int TYPE_DATASET_AUTHENTICATION_SELECTED = 1;
    public static final int TYPE_DATASET_SELECTED = 0;
    public static final int TYPE_SAVE_SHOWN = 3;
    
    Event() {
        throw new RuntimeException("Stub!");
    }
    
    public int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDatasetId() {
        throw new RuntimeException("Stub!");
    }
}
