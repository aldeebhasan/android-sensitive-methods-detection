package android.text;

import org.xml.sax.*;

public interface TagHandler
{
    void handleTag(final boolean p0, final String p1, final Editable p2, final XMLReader p3);
}
