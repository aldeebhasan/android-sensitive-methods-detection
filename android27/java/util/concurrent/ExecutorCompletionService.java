package java.util.concurrent;

public class ExecutorCompletionService<V> implements CompletionService<V>
{
    private final Executor executor;
    private final AbstractExecutorService aes;
    private final BlockingQueue<Future<V>> completionQueue;
    
    private RunnableFuture<V> newTaskFor(final Callable<V> callable) {
        if (this.aes == null) {
            return new FutureTask<V>(callable);
        }
        return this.aes.newTaskFor(callable);
    }
    
    private RunnableFuture<V> newTaskFor(final Runnable runnable, final V v) {
        if (this.aes == null) {
            return new FutureTask<V>(runnable, v);
        }
        return this.aes.newTaskFor(runnable, v);
    }
    
    public ExecutorCompletionService(final Executor executor) {
        if (executor == null) {
            throw new NullPointerException();
        }
        this.executor = executor;
        this.aes = ((executor instanceof AbstractExecutorService) ? ((AbstractExecutorService)executor) : null);
        this.completionQueue = new LinkedBlockingQueue<Future<V>>();
    }
    
    public ExecutorCompletionService(final Executor executor, final BlockingQueue<Future<V>> completionQueue) {
        if (executor == null || completionQueue == null) {
            throw new NullPointerException();
        }
        this.executor = executor;
        this.aes = ((executor instanceof AbstractExecutorService) ? ((AbstractExecutorService)executor) : null);
        this.completionQueue = completionQueue;
    }
    
    @Override
    public Future<V> submit(final Callable<V> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        final RunnableFuture<V> task = this.newTaskFor(callable);
        this.executor.execute(new QueueingFuture(task));
        return task;
    }
    
    @Override
    public Future<V> submit(final Runnable runnable, final V v) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        final RunnableFuture<V> task = this.newTaskFor(runnable, v);
        this.executor.execute(new QueueingFuture(task));
        return task;
    }
    
    @Override
    public Future<V> take() throws InterruptedException {
        return this.completionQueue.take();
    }
    
    @Override
    public Future<V> poll() {
        return this.completionQueue.poll();
    }
    
    @Override
    public Future<V> poll(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.completionQueue.poll(n, timeUnit);
    }
    
    private class QueueingFuture extends FutureTask<Void>
    {
        private final Future<V> task;
        
        QueueingFuture(final RunnableFuture<V> task) {
            super(task, null);
            this.task = task;
        }
        
        @Override
        protected void done() {
            ExecutorCompletionService.this.completionQueue.add(this.task);
        }
    }
}
