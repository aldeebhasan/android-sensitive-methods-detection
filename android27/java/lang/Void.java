package java.lang;

public final class Void
{
    public static final Class<Void> TYPE;
    
    static {
        TYPE = Class.getPrimitiveClass("void");
    }
}
