package java.util.concurrent;

public static class DiscardPolicy implements RejectedExecutionHandler
{
    @Override
    public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
    }
}
