package java.lang;

import java.util.*;
import java.io.*;

public class Throwable implements Serializable
{
    private static final long serialVersionUID = -3042686055658047285L;
    private transient Object backtrace;
    private String detailMessage;
    private static final StackTraceElement[] UNASSIGNED_STACK;
    private Throwable cause;
    private StackTraceElement[] stackTrace;
    private static final List<Throwable> SUPPRESSED_SENTINEL;
    private List<Throwable> suppressedExceptions;
    private static final String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";
    private static final String SELF_SUPPRESSION_MESSAGE = "Self-suppression not permitted";
    private static final String CAUSE_CAPTION = "Caused by: ";
    private static final String SUPPRESSED_CAPTION = "Suppressed: ";
    private static final Throwable[] EMPTY_THROWABLE_ARRAY;
    
    public Throwable() {
        this.cause = this;
        this.stackTrace = Throwable.UNASSIGNED_STACK;
        this.suppressedExceptions = Throwable.SUPPRESSED_SENTINEL;
        this.fillInStackTrace();
    }
    
    public Throwable(final String detailMessage) {
        this.cause = this;
        this.stackTrace = Throwable.UNASSIGNED_STACK;
        this.suppressedExceptions = Throwable.SUPPRESSED_SENTINEL;
        this.fillInStackTrace();
        this.detailMessage = detailMessage;
    }
    
    public Throwable(final String detailMessage, final Throwable cause) {
        this.cause = this;
        this.stackTrace = Throwable.UNASSIGNED_STACK;
        this.suppressedExceptions = Throwable.SUPPRESSED_SENTINEL;
        this.fillInStackTrace();
        this.detailMessage = detailMessage;
        this.cause = cause;
    }
    
    public Throwable(final Throwable cause) {
        this.cause = this;
        this.stackTrace = Throwable.UNASSIGNED_STACK;
        this.suppressedExceptions = Throwable.SUPPRESSED_SENTINEL;
        this.fillInStackTrace();
        this.detailMessage = ((cause == null) ? null : cause.toString());
        this.cause = cause;
    }
    
    protected Throwable(final String detailMessage, final Throwable cause, final boolean b, final boolean b2) {
        this.cause = this;
        this.stackTrace = Throwable.UNASSIGNED_STACK;
        this.suppressedExceptions = Throwable.SUPPRESSED_SENTINEL;
        if (b2) {
            this.fillInStackTrace();
        }
        else {
            this.stackTrace = null;
        }
        this.detailMessage = detailMessage;
        this.cause = cause;
        if (!b) {
            this.suppressedExceptions = null;
        }
    }
    
    public String getMessage() {
        return this.detailMessage;
    }
    
    public String getLocalizedMessage() {
        return this.getMessage();
    }
    
    public synchronized Throwable getCause() {
        return (this.cause == this) ? null : this.cause;
    }
    
    public synchronized Throwable initCause(final Throwable cause) {
        if (this.cause != this) {
            throw new IllegalStateException("Can't overwrite cause with " + Objects.toString(cause, "a null"), this);
        }
        if (cause == this) {
            throw new IllegalArgumentException("Self-causation not permitted", this);
        }
        this.cause = cause;
        return this;
    }
    
    @Override
    public String toString() {
        final String name = this.getClass().getName();
        final String localizedMessage = this.getLocalizedMessage();
        return (localizedMessage != null) ? (name + ": " + localizedMessage) : name;
    }
    
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }
    
    public void printStackTrace(final PrintStream printStream) {
        this.printStackTrace(new WrappedPrintStream(printStream));
    }
    
    private void printStackTrace(final PrintStreamOrWriter printStreamOrWriter) {
        final Set<Throwable> setFromMap = Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
        setFromMap.add(this);
        synchronized (printStreamOrWriter.lock()) {
            printStreamOrWriter.println(this);
            final StackTraceElement[] ourStackTrace;
            final StackTraceElement[] array = ourStackTrace = this.getOurStackTrace();
            for (int length = ourStackTrace.length, i = 0; i < length; ++i) {
                printStreamOrWriter.println("\tat " + ourStackTrace[i]);
            }
            final Throwable[] suppressed = this.getSuppressed();
            for (int length2 = suppressed.length, j = 0; j < length2; ++j) {
                suppressed[j].printEnclosedStackTrace(printStreamOrWriter, array, "Suppressed: ", "\t", setFromMap);
            }
            final Throwable cause = this.getCause();
            if (cause != null) {
                cause.printEnclosedStackTrace(printStreamOrWriter, array, "Caused by: ", "", setFromMap);
            }
        }
    }
    
    private void printEnclosedStackTrace(final PrintStreamOrWriter printStreamOrWriter, final StackTraceElement[] array, final String s, final String s2, final Set<Throwable> set) {
        assert Thread.holdsLock(printStreamOrWriter.lock());
        if (set.contains(this)) {
            printStreamOrWriter.println("\t[CIRCULAR REFERENCE:" + this + "]");
        }
        else {
            set.add(this);
            final StackTraceElement[] ourStackTrace = this.getOurStackTrace();
            int n = ourStackTrace.length - 1;
            for (int n2 = array.length - 1; n >= 0 && n2 >= 0 && ourStackTrace[n].equals(array[n2]); --n, --n2) {}
            final int n3 = ourStackTrace.length - 1 - n;
            printStreamOrWriter.println(s2 + s + this);
            for (int i = 0; i <= n; ++i) {
                printStreamOrWriter.println(s2 + "\tat " + ourStackTrace[i]);
            }
            if (n3 != 0) {
                printStreamOrWriter.println(s2 + "\t... " + n3 + " more");
            }
            final Throwable[] suppressed = this.getSuppressed();
            for (int length = suppressed.length, j = 0; j < length; ++j) {
                suppressed[j].printEnclosedStackTrace(printStreamOrWriter, ourStackTrace, "Suppressed: ", s2 + "\t", set);
            }
            final Throwable cause = this.getCause();
            if (cause != null) {
                cause.printEnclosedStackTrace(printStreamOrWriter, ourStackTrace, "Caused by: ", s2, set);
            }
        }
    }
    
    public void printStackTrace(final PrintWriter printWriter) {
        this.printStackTrace(new WrappedPrintWriter(printWriter));
    }
    
    public synchronized Throwable fillInStackTrace() {
        if (this.stackTrace != null || this.backtrace != null) {
            this.fillInStackTrace(0);
            this.stackTrace = Throwable.UNASSIGNED_STACK;
        }
        return this;
    }
    
    private native Throwable fillInStackTrace(final int p0);
    
    public StackTraceElement[] getStackTrace() {
        return this.getOurStackTrace().clone();
    }
    
    private synchronized StackTraceElement[] getOurStackTrace() {
        if (this.stackTrace == Throwable.UNASSIGNED_STACK || (this.stackTrace == null && this.backtrace != null)) {
            final int stackTraceDepth = this.getStackTraceDepth();
            this.stackTrace = new StackTraceElement[stackTraceDepth];
            for (int i = 0; i < stackTraceDepth; ++i) {
                this.stackTrace[i] = this.getStackTraceElement(i);
            }
        }
        else if (this.stackTrace == null) {
            return Throwable.UNASSIGNED_STACK;
        }
        return this.stackTrace;
    }
    
    public void setStackTrace(final StackTraceElement[] array) {
        final StackTraceElement[] stackTrace = array.clone();
        for (int i = 0; i < stackTrace.length; ++i) {
            if (stackTrace[i] == null) {
                throw new NullPointerException("stackTrace[" + i + "]");
            }
        }
        synchronized (this) {
            if (this.stackTrace == null && this.backtrace == null) {
                return;
            }
            this.stackTrace = stackTrace;
        }
    }
    
    native int getStackTraceDepth();
    
    native StackTraceElement getStackTraceElement(final int p0);
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final List<Throwable> suppressedExceptions = this.suppressedExceptions;
        this.suppressedExceptions = Throwable.SUPPRESSED_SENTINEL;
        final StackTraceElement[] stackTrace = this.stackTrace;
        this.stackTrace = Throwable.UNASSIGNED_STACK.clone();
        if (suppressedExceptions != null) {
            final int validateSuppressedExceptionsList = this.validateSuppressedExceptionsList(suppressedExceptions);
            if (validateSuppressedExceptionsList > 0) {
                final ArrayList suppressedExceptions2 = new ArrayList<Throwable>(Math.min(100, validateSuppressedExceptionsList));
                for (final Throwable t : suppressedExceptions) {
                    if (t == null) {
                        throw new NullPointerException("Cannot suppress a null exception.");
                    }
                    if (t == this) {
                        throw new IllegalArgumentException("Self-suppression not permitted");
                    }
                    suppressedExceptions2.add(t);
                }
                this.suppressedExceptions = (List<Throwable>)suppressedExceptions2;
            }
        }
        else {
            this.suppressedExceptions = null;
        }
        if (stackTrace != null) {
            final StackTraceElement[] stackTrace2 = stackTrace.clone();
            if (stackTrace2.length >= 1) {
                if (stackTrace2.length == 1 && SentinelHolder.STACK_TRACE_ELEMENT_SENTINEL.equals(stackTrace2[0])) {
                    this.stackTrace = null;
                }
                else {
                    final StackTraceElement[] array = stackTrace2;
                    for (int length = array.length, i = 0; i < length; ++i) {
                        if (array[i] == null) {
                            throw new NullPointerException("null StackTraceElement in serial stream.");
                        }
                    }
                    this.stackTrace = stackTrace2;
                }
            }
        }
    }
    
    private int validateSuppressedExceptionsList(final List<Throwable> list) throws IOException {
        boolean b;
        try {
            b = (list.getClass().getClassLoader() == null);
        }
        catch (SecurityException ex) {
            b = false;
        }
        if (!b) {
            throw new StreamCorruptedException("List implementation class was not loaded by bootstrap class loader.");
        }
        final int size = list.size();
        if (size < 0) {
            throw new StreamCorruptedException("Negative list size reported.");
        }
        return size;
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.getOurStackTrace();
        final StackTraceElement[] stackTrace = this.stackTrace;
        try {
            if (this.stackTrace == null) {
                this.stackTrace = SentinelHolder.STACK_TRACE_SENTINEL;
            }
            objectOutputStream.defaultWriteObject();
        }
        finally {
            this.stackTrace = stackTrace;
        }
    }
    
    public final synchronized void addSuppressed(final Throwable t) {
        if (t == this) {
            throw new IllegalArgumentException("Self-suppression not permitted", t);
        }
        if (t == null) {
            throw new NullPointerException("Cannot suppress a null exception.");
        }
        if (this.suppressedExceptions == null) {
            return;
        }
        if (this.suppressedExceptions == Throwable.SUPPRESSED_SENTINEL) {
            this.suppressedExceptions = new ArrayList<Throwable>(1);
        }
        this.suppressedExceptions.add(t);
    }
    
    public final synchronized Throwable[] getSuppressed() {
        if (this.suppressedExceptions == Throwable.SUPPRESSED_SENTINEL || this.suppressedExceptions == null) {
            return Throwable.EMPTY_THROWABLE_ARRAY;
        }
        return this.suppressedExceptions.toArray(Throwable.EMPTY_THROWABLE_ARRAY);
    }
    
    static {
        UNASSIGNED_STACK = new StackTraceElement[0];
        SUPPRESSED_SENTINEL = Collections.unmodifiableList((List<? extends Throwable>)new ArrayList<Throwable>(0));
        EMPTY_THROWABLE_ARRAY = new Throwable[0];
    }
    
    private abstract static class PrintStreamOrWriter
    {
        abstract Object lock();
        
        abstract void println(final Object p0);
    }
    
    private static class SentinelHolder
    {
        public static final StackTraceElement STACK_TRACE_ELEMENT_SENTINEL;
        public static final StackTraceElement[] STACK_TRACE_SENTINEL;
        
        static {
            STACK_TRACE_ELEMENT_SENTINEL = new StackTraceElement("", "", null, Integer.MIN_VALUE);
            STACK_TRACE_SENTINEL = new StackTraceElement[] { SentinelHolder.STACK_TRACE_ELEMENT_SENTINEL };
        }
    }
    
    private static class WrappedPrintStream extends PrintStreamOrWriter
    {
        private final PrintStream printStream;
        
        WrappedPrintStream(final PrintStream printStream) {
            this.printStream = printStream;
        }
        
        @Override
        Object lock() {
            return this.printStream;
        }
        
        @Override
        void println(final Object o) {
            this.printStream.println(o);
        }
    }
    
    private static class WrappedPrintWriter extends PrintStreamOrWriter
    {
        private final PrintWriter printWriter;
        
        WrappedPrintWriter(final PrintWriter printWriter) {
            this.printWriter = printWriter;
        }
        
        @Override
        Object lock() {
            return this.printWriter;
        }
        
        @Override
        void println(final Object o) {
            this.printWriter.println(o);
        }
    }
}
