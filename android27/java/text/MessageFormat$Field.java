package java.text;

import java.io.*;

public static class Field extends Format.Field
{
    private static final long serialVersionUID = 7899943957617360810L;
    public static final Field ARGUMENT;
    
    protected Field(final String s) {
        super(s);
    }
    
    @Override
    protected Object readResolve() throws InvalidObjectException {
        if (this.getClass() != Field.class) {
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }
        return Field.ARGUMENT;
    }
    
    static {
        ARGUMENT = new Field("message argument field");
    }
}
