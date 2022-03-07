package java.math;

import java.io.*;

public final class MathContext implements Serializable
{
    private static final int DEFAULT_DIGITS = 9;
    private static final RoundingMode DEFAULT_ROUNDINGMODE;
    private static final int MIN_DIGITS = 0;
    private static final long serialVersionUID = 5579720004786848255L;
    public static final MathContext UNLIMITED;
    public static final MathContext DECIMAL32;
    public static final MathContext DECIMAL64;
    public static final MathContext DECIMAL128;
    final int precision;
    final RoundingMode roundingMode;
    
    public MathContext(final int n) {
        this(n, MathContext.DEFAULT_ROUNDINGMODE);
    }
    
    public MathContext(final int precision, final RoundingMode roundingMode) {
        if (precision < 0) {
            throw new IllegalArgumentException("Digits < 0");
        }
        if (roundingMode == null) {
            throw new NullPointerException("null RoundingMode");
        }
        this.precision = precision;
        this.roundingMode = roundingMode;
    }
    
    public MathContext(final String s) {
        if (s == null) {
            throw new NullPointerException("null String");
        }
        int int1;
        try {
            if (!s.startsWith("precision=")) {
                throw new RuntimeException();
            }
            final int index = s.indexOf(32);
            int1 = Integer.parseInt(s.substring(10, index));
            if (!s.startsWith("roundingMode=", index + 1)) {
                throw new RuntimeException();
            }
            this.roundingMode = RoundingMode.valueOf(s.substring(index + 1 + 13, s.length()));
        }
        catch (RuntimeException ex) {
            throw new IllegalArgumentException("bad string format");
        }
        if (int1 < 0) {
            throw new IllegalArgumentException("Digits < 0");
        }
        this.precision = int1;
    }
    
    public int getPrecision() {
        return this.precision;
    }
    
    public RoundingMode getRoundingMode() {
        return this.roundingMode;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MathContext)) {
            return false;
        }
        final MathContext mathContext = (MathContext)o;
        return mathContext.precision == this.precision && mathContext.roundingMode == this.roundingMode;
    }
    
    @Override
    public int hashCode() {
        return this.precision + this.roundingMode.hashCode() * 59;
    }
    
    @Override
    public String toString() {
        return "precision=" + this.precision + " roundingMode=" + this.roundingMode.toString();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.precision < 0) {
            throw new StreamCorruptedException("MathContext: invalid digits in stream");
        }
        if (this.roundingMode == null) {
            throw new StreamCorruptedException("MathContext: null roundingMode in stream");
        }
    }
    
    static {
        DEFAULT_ROUNDINGMODE = RoundingMode.HALF_UP;
        UNLIMITED = new MathContext(0, RoundingMode.HALF_UP);
        DECIMAL32 = new MathContext(7, RoundingMode.HALF_EVEN);
        DECIMAL64 = new MathContext(16, RoundingMode.HALF_EVEN);
        DECIMAL128 = new MathContext(34, RoundingMode.HALF_EVEN);
    }
}
