package java.util.concurrent;

public static class AbortPolicy implements RejectedExecutionHandler
{
    @Override
    public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
        throw new RejectedExecutionException("Task " + runnable.toString() + " rejected from " + threadPoolExecutor.toString());
    }
}
