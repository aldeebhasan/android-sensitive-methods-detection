package android.os;

public interface ClassLoaderCreator<T> extends Creator<T>
{
    T createFromParcel(final Parcel p0, final ClassLoader p1);
}
