package java.text;

import java.io.*;
import java.util.*;

public static class Field extends Format.Field
{
    private static final long serialVersionUID = 7494728892700160890L;
    private static final Map<String, Field> instanceMap;
    public static final Field INTEGER;
    public static final Field FRACTION;
    public static final Field EXPONENT;
    public static final Field DECIMAL_SEPARATOR;
    public static final Field SIGN;
    public static final Field GROUPING_SEPARATOR;
    public static final Field EXPONENT_SYMBOL;
    public static final Field PERCENT;
    public static final Field PERMILLE;
    public static final Field CURRENCY;
    public static final Field EXPONENT_SIGN;
    
    protected Field(final String s) {
        super(s);
        if (this.getClass() == Field.class) {
            Field.instanceMap.put(s, this);
        }
    }
    
    @Override
    protected Object readResolve() throws InvalidObjectException {
        if (this.getClass() != Field.class) {
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }
        final Field value = Field.instanceMap.get(this.getName());
        if (value != null) {
            return value;
        }
        throw new InvalidObjectException("unknown attribute name");
    }
    
    static {
        instanceMap = new HashMap<String, Field>(11);
        INTEGER = new Field("integer");
        FRACTION = new Field("fraction");
        EXPONENT = new Field("exponent");
        DECIMAL_SEPARATOR = new Field("decimal separator");
        SIGN = new Field("sign");
        GROUPING_SEPARATOR = new Field("grouping separator");
        EXPONENT_SYMBOL = new Field("exponent symbol");
        PERCENT = new Field("percent");
        PERMILLE = new Field("per mille");
        CURRENCY = new Field("currency");
        EXPONENT_SIGN = new Field("exponent sign");
    }
}
