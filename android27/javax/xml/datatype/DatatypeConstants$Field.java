package javax.xml.datatype;

public static final class Field
{
    private final String str;
    private final int id;
    
    private Field(final String str, final int id) {
        this.str = str;
        this.id = id;
    }
    
    @Override
    public String toString() {
        return this.str;
    }
    
    public int getId() {
        return this.id;
    }
}
