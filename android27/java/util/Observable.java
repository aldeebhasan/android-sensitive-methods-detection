package java.util;

public class Observable
{
    private boolean changed;
    private Vector<Observer> obs;
    
    public Observable() {
        this.changed = false;
        this.obs = new Vector<Observer>();
    }
    
    public synchronized void addObserver(final Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!this.obs.contains(observer)) {
            this.obs.addElement(observer);
        }
    }
    
    public synchronized void deleteObserver(final Observer observer) {
        this.obs.removeElement(observer);
    }
    
    public void notifyObservers() {
        this.notifyObservers(null);
    }
    
    public void notifyObservers(final Object o) {
        final Object[] array;
        synchronized (this) {
            if (!this.changed) {
                return;
            }
            array = this.obs.toArray();
            this.clearChanged();
        }
        for (int i = array.length - 1; i >= 0; --i) {
            ((Observer)array[i]).update(this, o);
        }
    }
    
    public synchronized void deleteObservers() {
        this.obs.removeAllElements();
    }
    
    protected synchronized void setChanged() {
        this.changed = true;
    }
    
    protected synchronized void clearChanged() {
        this.changed = false;
    }
    
    public synchronized boolean hasChanged() {
        return this.changed;
    }
    
    public synchronized int countObservers() {
        return this.obs.size();
    }
}
