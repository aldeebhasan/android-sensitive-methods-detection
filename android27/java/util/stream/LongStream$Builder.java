package java.util.stream;

import java.util.function.*;

public interface Builder extends LongConsumer
{
    void accept(final long p0);
    
    default Builder add(final long n) {
        this.accept(n);
        return this;
    }
    
    LongStream build();
}
