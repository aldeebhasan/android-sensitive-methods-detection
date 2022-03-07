package android.os;

public interface Creator<T>
{
    T createFromParcel(final Parcel p0);
    
    T[] newArray(final int p0);
}
