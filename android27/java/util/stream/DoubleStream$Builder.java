package java.util.stream;

import java.util.function.*;

public interface Builder extends DoubleConsumer
{
    void accept(final double p0);
    
    default Builder add(final double n) {
        this.accept(n);
        return this;
    }
    
    DoubleStream build();
}
