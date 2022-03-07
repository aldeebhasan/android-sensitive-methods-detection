package java.nio.channels.spi;

import java.nio.channels.*;
import java.io.*;

public abstract class AbstractSelectableChannel extends SelectableChannel
{
    private final SelectorProvider provider;
    private SelectionKey[] keys;
    private int keyCount;
    private final Object keyLock;
    private final Object regLock;
    boolean blocking;
    
    protected AbstractSelectableChannel(final SelectorProvider provider) {
        this.keys = null;
        this.keyCount = 0;
        this.keyLock = new Object();
        this.regLock = new Object();
        this.blocking = true;
        this.provider = provider;
    }
    
    @Override
    public final SelectorProvider provider() {
        return this.provider;
    }
    
    private void addKey(final SelectionKey selectionKey) {
        assert Thread.holdsLock(this.keyLock);
        int i = 0;
        if (this.keys != null && this.keyCount < this.keys.length) {
            for (i = 0; i < this.keys.length; ++i) {
                if (this.keys[i] == null) {
                    break;
                }
            }
        }
        else if (this.keys == null) {
            this.keys = new SelectionKey[3];
        }
        else {
            final SelectionKey[] keys = new SelectionKey[this.keys.length * 2];
            for (int j = 0; j < this.keys.length; ++j) {
                keys[j] = this.keys[j];
            }
            this.keys = keys;
            i = this.keyCount;
        }
        this.keys[i] = selectionKey;
        ++this.keyCount;
    }
    
    private SelectionKey findKey(final Selector selector) {
        synchronized (this.keyLock) {
            if (this.keys == null) {
                return null;
            }
            for (int i = 0; i < this.keys.length; ++i) {
                if (this.keys[i] != null && this.keys[i].selector() == selector) {
                    return this.keys[i];
                }
            }
            return null;
        }
    }
    
    void removeKey(final SelectionKey selectionKey) {
        synchronized (this.keyLock) {
            for (int i = 0; i < this.keys.length; ++i) {
                if (this.keys[i] == selectionKey) {
                    this.keys[i] = null;
                    --this.keyCount;
                }
            }
            ((AbstractSelectionKey)selectionKey).invalidate();
        }
    }
    
    private boolean haveValidKeys() {
        synchronized (this.keyLock) {
            if (this.keyCount == 0) {
                return false;
            }
            for (int i = 0; i < this.keys.length; ++i) {
                if (this.keys[i] != null && this.keys[i].isValid()) {
                    return true;
                }
            }
            return false;
        }
    }
    
    @Override
    public final boolean isRegistered() {
        synchronized (this.keyLock) {
            return this.keyCount != 0;
        }
    }
    
    @Override
    public final SelectionKey keyFor(final Selector selector) {
        return this.findKey(selector);
    }
    
    @Override
    public final SelectionKey register(final Selector selector, final int n, final Object o) throws ClosedChannelException {
        synchronized (this.regLock) {
            if (!this.isOpen()) {
                throw new ClosedChannelException();
            }
            if ((n & ~this.validOps()) != 0x0) {
                throw new IllegalArgumentException();
            }
            if (this.blocking) {
                throw new IllegalBlockingModeException();
            }
            SelectionKey selectionKey = this.findKey(selector);
            if (selectionKey != null) {
                selectionKey.interestOps(n);
                selectionKey.attach(o);
            }
            if (selectionKey == null) {
                synchronized (this.keyLock) {
                    if (!this.isOpen()) {
                        throw new ClosedChannelException();
                    }
                    selectionKey = ((AbstractSelector)selector).register(this, n, o);
                    this.addKey(selectionKey);
                }
            }
            return selectionKey;
        }
    }
    
    @Override
    protected final void implCloseChannel() throws IOException {
        this.implCloseSelectableChannel();
        synchronized (this.keyLock) {
            for (int n = (this.keys == null) ? 0 : this.keys.length, i = 0; i < n; ++i) {
                final SelectionKey selectionKey = this.keys[i];
                if (selectionKey != null) {
                    selectionKey.cancel();
                }
            }
        }
    }
    
    protected abstract void implCloseSelectableChannel() throws IOException;
    
    @Override
    public final boolean isBlocking() {
        synchronized (this.regLock) {
            return this.blocking;
        }
    }
    
    @Override
    public final Object blockingLock() {
        return this.regLock;
    }
    
    @Override
    public final SelectableChannel configureBlocking(final boolean blocking) throws IOException {
        synchronized (this.regLock) {
            if (!this.isOpen()) {
                throw new ClosedChannelException();
            }
            if (this.blocking == blocking) {
                return this;
            }
            if (blocking && this.haveValidKeys()) {
                throw new IllegalBlockingModeException();
            }
            this.implConfigureBlocking(blocking);
            this.blocking = blocking;
        }
        return this;
    }
    
    protected abstract void implConfigureBlocking(final boolean p0) throws IOException;
}
