package java.nio.file.attribute;

import java.io.*;

public interface FileOwnerAttributeView extends FileAttributeView
{
    String name();
    
    UserPrincipal getOwner() throws IOException;
    
    void setOwner(final UserPrincipal p0) throws IOException;
}
