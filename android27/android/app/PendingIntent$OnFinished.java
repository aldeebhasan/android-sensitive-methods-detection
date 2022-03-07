package android.app;

import android.content.*;
import android.os.*;

public interface OnFinished
{
    void onSendFinished(final PendingIntent p0, final Intent p1, final int p2, final String p3, final Bundle p4);
}
