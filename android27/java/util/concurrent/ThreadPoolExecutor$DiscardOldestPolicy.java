package java.util.concurrent;

public static class DiscardOldestPolicy implements RejectedExecutionHandler
{
    @Override
    public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
        if (!threadPoolExecutor.isShutdown()) {
            threadPoolExecutor.getQueue().poll();
            threadPoolExecutor.execute(runnable);
        }
    }
}
