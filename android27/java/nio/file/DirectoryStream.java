package java.nio.file;

import java.util.*;
import java.io.*;

public interface DirectoryStream<T> extends Closeable, Iterable<T>
{
    Iterator<T> iterator();
    
    @FunctionalInterface
    public interface Filter<T>
    {
        boolean accept(final T p0) throws IOException;
    }
}
