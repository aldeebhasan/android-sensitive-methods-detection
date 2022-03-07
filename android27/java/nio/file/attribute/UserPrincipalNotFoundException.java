package java.nio.file.attribute;

import java.io.*;

public class UserPrincipalNotFoundException extends IOException
{
    static final long serialVersionUID = -5369283889045833024L;
    private final String name;
    
    public UserPrincipalNotFoundException(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
