package java.util.concurrent;

public static class CallerRunsPolicy implements RejectedExecutionHandler
{
    @Override
    public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor threadPoolExecutor) {
        if (!threadPoolExecutor.isShutdown()) {
            runnable.run();
        }
    }
}
