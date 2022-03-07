package java.text;

public class Annotation
{
    private Object value;
    
    public Annotation(final Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[value=" + this.value + "]";
    }
}
