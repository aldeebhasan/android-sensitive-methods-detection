package android.media;

import java.util.*;

public interface OnKeyStatusChangeListener
{
    void onKeyStatusChange(final MediaDrm p0, final byte[] p1, final List<KeyStatus> p2, final boolean p3);
}
