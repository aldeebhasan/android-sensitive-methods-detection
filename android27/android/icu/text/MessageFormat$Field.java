package android.icu.text;

import java.text.*;
import java.io.*;

public static class Field extends Format.Field
{
    public static final Field ARGUMENT;
    
    protected Field(final String name) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Object readResolve() throws InvalidObjectException {
        throw new RuntimeException("Stub!");
    }
    
    static {
        ARGUMENT = null;
    }
}
