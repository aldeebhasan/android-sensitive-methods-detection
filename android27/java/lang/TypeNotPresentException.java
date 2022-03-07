package java.lang;

public class TypeNotPresentException extends RuntimeException
{
    private static final long serialVersionUID = -5101214195716534496L;
    private String typeName;
    
    public TypeNotPresentException(final String typeName, final Throwable t) {
        super("Type " + typeName + " not present", t);
        this.typeName = typeName;
    }
    
    public String typeName() {
        return this.typeName;
    }
}
