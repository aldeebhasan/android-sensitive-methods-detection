package java.lang;

public static class Subset
{
    private String name;
    
    protected Subset(final String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }
    
    @Override
    public final int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public final String toString() {
        return this.name;
    }
}
