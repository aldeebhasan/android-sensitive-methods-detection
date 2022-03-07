package java.lang;

@FunctionalInterface
public interface UncaughtExceptionHandler
{
    void uncaughtException(final Thread p0, final Throwable p1);
}
