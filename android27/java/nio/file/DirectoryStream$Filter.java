package java.nio.file;

import java.io.*;

@FunctionalInterface
public interface Filter<T>
{
    boolean accept(final T p0) throws IOException;
}
