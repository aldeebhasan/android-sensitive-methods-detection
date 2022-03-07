package java.util.stream;

import java.util.function.*;

public interface Builder extends IntConsumer
{
    void accept(final int p0);
    
    default Builder add(final int n) {
        this.accept(n);
        return this;
    }
    
    IntStream build();
}
