package java.lang;

import java.io.*;

public interface Appendable
{
    Appendable append(final CharSequence p0) throws IOException;
    
    Appendable append(final CharSequence p0, final int p1, final int p2) throws IOException;
    
    Appendable append(final char p0) throws IOException;
}
