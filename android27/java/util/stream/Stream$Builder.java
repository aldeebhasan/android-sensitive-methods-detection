package java.util.stream;

import java.util.function.*;

public interface Builder<T> extends Consumer<T>
{
    void accept(final T p0);
    
    default Builder<T> add(final T t) {
        this.accept(t);
        return this;
    }
    
    Stream<T> build();
}
