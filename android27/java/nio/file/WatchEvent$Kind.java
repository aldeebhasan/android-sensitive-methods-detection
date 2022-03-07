package java.nio.file;

public interface Kind<T>
{
    String name();
    
    Class<T> type();
}
