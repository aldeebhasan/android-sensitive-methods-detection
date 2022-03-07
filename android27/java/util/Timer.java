package java.util;

import java.util.concurrent.atomic.*;

public class Timer
{
    private final TaskQueue queue;
    private final TimerThread thread;
    private final Object threadReaper;
    private static final AtomicInteger nextSerialNumber;
    
    private static int serialNumber() {
        return Timer.nextSerialNumber.getAndIncrement();
    }
    
    public Timer() {
        this("Timer-" + serialNumber());
    }
    
    public Timer(final boolean b) {
        this("Timer-" + serialNumber(), b);
    }
    
    public Timer(final String name) {
        this.queue = new TaskQueue();
        this.thread = new TimerThread(this.queue);
        this.threadReaper = new Object() {
            @Override
            protected void finalize() throws Throwable {
                synchronized (Timer.this.queue) {
                    Timer.this.thread.newTasksMayBeScheduled = false;
                    Timer.this.queue.notify();
                }
            }
        };
        this.thread.setName(name);
        this.thread.start();
    }
    
    public Timer(final String name, final boolean daemon) {
        this.queue = new TaskQueue();
        this.thread = new TimerThread(this.queue);
        this.threadReaper = new Object() {
            @Override
            protected void finalize() throws Throwable {
                synchronized (Timer.this.queue) {
                    Timer.this.thread.newTasksMayBeScheduled = false;
                    Timer.this.queue.notify();
                }
            }
        };
        this.thread.setName(name);
        this.thread.setDaemon(daemon);
        this.thread.start();
    }
    
    public void schedule(final TimerTask timerTask, final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Negative delay.");
        }
        this.sched(timerTask, System.currentTimeMillis() + n, 0L);
    }
    
    public void schedule(final TimerTask timerTask, final Date date) {
        this.sched(timerTask, date.getTime(), 0L);
    }
    
    public void schedule(final TimerTask timerTask, final long n, final long n2) {
        if (n < 0L) {
            throw new IllegalArgumentException("Negative delay.");
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException("Non-positive period.");
        }
        this.sched(timerTask, System.currentTimeMillis() + n, -n2);
    }
    
    public void schedule(final TimerTask timerTask, final Date date, final long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("Non-positive period.");
        }
        this.sched(timerTask, date.getTime(), -n);
    }
    
    public void scheduleAtFixedRate(final TimerTask timerTask, final long n, final long n2) {
        if (n < 0L) {
            throw new IllegalArgumentException("Negative delay.");
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException("Non-positive period.");
        }
        this.sched(timerTask, System.currentTimeMillis() + n, n2);
    }
    
    public void scheduleAtFixedRate(final TimerTask timerTask, final Date date, final long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("Non-positive period.");
        }
        this.sched(timerTask, date.getTime(), n);
    }
    
    private void sched(final TimerTask timerTask, final long nextExecutionTime, long period) {
        if (nextExecutionTime < 0L) {
            throw new IllegalArgumentException("Illegal execution time.");
        }
        if (Math.abs(period) > 4611686018427387903L) {
            period >>= 1;
        }
        synchronized (this.queue) {
            if (!this.thread.newTasksMayBeScheduled) {
                throw new IllegalStateException("Timer already cancelled.");
            }
            synchronized (timerTask.lock) {
                if (timerTask.state != 0) {
                    throw new IllegalStateException("Task already scheduled or cancelled");
                }
                timerTask.nextExecutionTime = nextExecutionTime;
                timerTask.period = period;
                timerTask.state = 1;
            }
            this.queue.add(timerTask);
            if (this.queue.getMin() == timerTask) {
                this.queue.notify();
            }
        }
    }
    
    public void cancel() {
        synchronized (this.queue) {
            this.thread.newTasksMayBeScheduled = false;
            this.queue.clear();
            this.queue.notify();
        }
    }
    
    public int purge() {
        int n = 0;
        synchronized (this.queue) {
            for (int i = this.queue.size(); i > 0; --i) {
                if (this.queue.get(i).state == 3) {
                    this.queue.quickRemove(i);
                    ++n;
                }
            }
            if (n != 0) {
                this.queue.heapify();
            }
        }
        return n;
    }
    
    static {
        nextSerialNumber = new AtomicInteger(0);
    }
}
