package java.security.spec;

public class ECGenParameterSpec implements AlgorithmParameterSpec
{
    private String name;
    
    public ECGenParameterSpec(final String name) {
        if (name == null) {
            throw new NullPointerException("stdName is null");
        }
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
