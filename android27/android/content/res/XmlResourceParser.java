package android.content.res;

import org.xmlpull.v1.*;
import android.util.*;

public interface XmlResourceParser extends XmlPullParser, AttributeSet, AutoCloseable
{
    void close();
}
