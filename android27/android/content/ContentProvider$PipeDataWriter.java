package android.content;

import android.net.*;
import android.os.*;

public interface PipeDataWriter<T>
{
    void writeDataToPipe(final ParcelFileDescriptor p0, final Uri p1, final String p2, final Bundle p3, final T p4);
}
