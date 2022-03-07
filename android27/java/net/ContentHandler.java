package java.net;

import java.io.*;

public abstract class ContentHandler
{
    public abstract Object getContent(final URLConnection p0) throws IOException;
    
    public Object getContent(final URLConnection urlConnection, final Class[] array) throws IOException {
        final Object content = this.getContent(urlConnection);
        for (int i = 0; i < array.length; ++i) {
            if (array[i].isInstance(content)) {
                return content;
            }
        }
        return null;
    }
}
