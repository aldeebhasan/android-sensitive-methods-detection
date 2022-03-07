package android.os;

public final class PersistableBundle extends BaseBundle implements Cloneable, Parcelable
{
    public static final Creator<PersistableBundle> CREATOR;
    public static final PersistableBundle EMPTY;
    
    public PersistableBundle() {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle(final int capacity) {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle(final PersistableBundle b) {
        throw new RuntimeException("Stub!");
    }
    
    public Object clone() {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle deepCopy() {
        throw new RuntimeException("Stub!");
    }
    
    public void putPersistableBundle(final String key, final PersistableBundle value) {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle getPersistableBundle(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
        EMPTY = null;
    }
}
