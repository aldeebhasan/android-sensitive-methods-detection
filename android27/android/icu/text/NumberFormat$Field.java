package android.icu.text;

import java.text.*;
import java.io.*;

public static class Field extends Format.Field
{
    public static final Field CURRENCY;
    public static final Field DECIMAL_SEPARATOR;
    public static final Field EXPONENT;
    public static final Field EXPONENT_SIGN;
    public static final Field EXPONENT_SYMBOL;
    public static final Field FRACTION;
    public static final Field GROUPING_SEPARATOR;
    public static final Field INTEGER;
    public static final Field PERCENT;
    public static final Field PERMILLE;
    public static final Field SIGN;
    
    protected Field(final String fieldName) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Object readResolve() throws InvalidObjectException {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CURRENCY = null;
        DECIMAL_SEPARATOR = null;
        EXPONENT = null;
        EXPONENT_SIGN = null;
        EXPONENT_SYMBOL = null;
        FRACTION = null;
        GROUPING_SEPARATOR = null;
        INTEGER = null;
        PERCENT = null;
        PERMILLE = null;
        SIGN = null;
    }
}
