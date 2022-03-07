package java.nio.file.attribute;

import java.util.*;
import java.io.*;
import java.nio.*;

public interface UserDefinedFileAttributeView extends FileAttributeView
{
    String name();
    
    List<String> list() throws IOException;
    
    int size(final String p0) throws IOException;
    
    int read(final String p0, final ByteBuffer p1) throws IOException;
    
    int write(final String p0, final ByteBuffer p1) throws IOException;
    
    void delete(final String p0) throws IOException;
}
