package java.util.concurrent;

import sun.misc.*;
import java.util.function.*;
import java.util.concurrent.locks.*;

public class CompletableFuture<T> implements Future<T>, CompletionStage<T>
{
    volatile Object result;
    volatile Completion stack;
    static final AltResult NIL;
    private static final boolean useCommonPool;
    private static final Executor asyncPool;
    static final int SYNC = 0;
    static final int ASYNC = 1;
    static final int NESTED = -1;
    private static final int SPINS;
    private static final Unsafe UNSAFE;
    private static final long RESULT;
    private static final long STACK;
    private static final long NEXT;
    
    final boolean internalComplete(final Object o) {
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.RESULT, null, o);
    }
    
    final boolean casStack(final Completion completion, final Completion completion2) {
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.STACK, completion, completion2);
    }
    
    final boolean tryPushStack(final Completion completion) {
        final Completion stack = this.stack;
        lazySetNext(completion, stack);
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.STACK, stack, completion);
    }
    
    final void pushStack(final Completion completion) {
        while (!this.tryPushStack(completion)) {}
    }
    
    final boolean completeNull() {
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.RESULT, null, CompletableFuture.NIL);
    }
    
    final Object encodeValue(final T t) {
        return (t == null) ? CompletableFuture.NIL : t;
    }
    
    final boolean completeValue(final T t) {
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.RESULT, null, (t == null) ? CompletableFuture.NIL : t);
    }
    
    static AltResult encodeThrowable(final Throwable t) {
        return new AltResult((t instanceof CompletionException) ? t : new CompletionException(t));
    }
    
    final boolean completeThrowable(final Throwable t) {
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.RESULT, null, encodeThrowable(t));
    }
    
    static Object encodeThrowable(Throwable t, final Object o) {
        if (!(t instanceof CompletionException)) {
            t = new CompletionException(t);
        }
        else if (o instanceof AltResult && t == ((AltResult)o).ex) {
            return o;
        }
        return new AltResult(t);
    }
    
    final boolean completeThrowable(final Throwable t, final Object o) {
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.RESULT, null, encodeThrowable(t, o));
    }
    
    Object encodeOutcome(final T t, final Throwable t2) {
        return (t2 == null) ? ((t == null) ? CompletableFuture.NIL : t) : encodeThrowable(t2);
    }
    
    static Object encodeRelay(final Object o) {
        final Throwable ex;
        return (o instanceof AltResult && (ex = ((AltResult)o).ex) != null && !(ex instanceof CompletionException)) ? new AltResult(new CompletionException(ex)) : o;
    }
    
    final boolean completeRelay(final Object o) {
        return CompletableFuture.UNSAFE.compareAndSwapObject(this, CompletableFuture.RESULT, null, encodeRelay(o));
    }
    
    private static <T> T reportGet(final Object o) throws InterruptedException, ExecutionException {
        if (o == null) {
            throw new InterruptedException();
        }
        if (!(o instanceof AltResult)) {
            return (T)o;
        }
        Throwable ex;
        if ((ex = ((AltResult)o).ex) == null) {
            return null;
        }
        if (ex instanceof CancellationException) {
            throw (CancellationException)ex;
        }
        final Throwable cause;
        if (ex instanceof CompletionException && (cause = ex.getCause()) != null) {
            ex = cause;
        }
        throw new ExecutionException(ex);
    }
    
    private static <T> T reportJoin(final Object o) {
        if (!(o instanceof AltResult)) {
            return (T)o;
        }
        final Throwable ex;
        if ((ex = ((AltResult)o).ex) == null) {
            return null;
        }
        if (ex instanceof CancellationException) {
            throw (CancellationException)ex;
        }
        if (ex instanceof CompletionException) {
            throw (CompletionException)ex;
        }
        throw new CompletionException(ex);
    }
    
    static Executor screenExecutor(final Executor executor) {
        if (!CompletableFuture.useCommonPool && executor == ForkJoinPool.commonPool()) {
            return CompletableFuture.asyncPool;
        }
        if (executor == null) {
            throw new NullPointerException();
        }
        return executor;
    }
    
    static void lazySetNext(final Completion completion, final Completion completion2) {
        CompletableFuture.UNSAFE.putOrderedObject(completion, CompletableFuture.NEXT, completion2);
    }
    
    final void postComplete() {
        CompletableFuture completableFuture = this;
        while (true) {
            Completion completion;
            if ((completion = completableFuture.stack) == null) {
                if (completableFuture == this) {
                    break;
                }
                completableFuture = this;
                if ((completion = this.stack) == null) {
                    break;
                }
            }
            final Completion next;
            if (completableFuture.casStack(completion, next = completion.next)) {
                if (next != null) {
                    if (completableFuture != this) {
                        this.pushStack(completion);
                        continue;
                    }
                    completion.next = null;
                }
                final CompletableFuture<?> tryFire;
                completableFuture = (((tryFire = completion.tryFire(-1)) == null) ? this : tryFire);
            }
        }
    }
    
    final void cleanStack() {
        Completion completion = null;
        Completion completion2 = this.stack;
        while (completion2 != null) {
            final Completion next = completion2.next;
            if (completion2.isLive()) {
                completion = completion2;
                completion2 = next;
            }
            else if (completion == null) {
                this.casStack(completion2, next);
                completion2 = this.stack;
            }
            else {
                completion.next = next;
                if (completion.isLive()) {
                    completion2 = next;
                }
                else {
                    completion = null;
                    completion2 = this.stack;
                }
            }
        }
    }
    
    final void push(final UniCompletion<?, ?> uniCompletion) {
        if (uniCompletion != null) {
            while (this.result == null && !this.tryPushStack(uniCompletion)) {
                lazySetNext(uniCompletion, null);
            }
        }
    }
    
    final CompletableFuture<T> postFire(final CompletableFuture<?> completableFuture, final int n) {
        if (completableFuture != null && completableFuture.stack != null) {
            if (n < 0 || completableFuture.result == null) {
                completableFuture.cleanStack();
            }
            else {
                completableFuture.postComplete();
            }
        }
        if (this.result != null && this.stack != null) {
            if (n < 0) {
                return this;
            }
            this.postComplete();
        }
        return null;
    }
    
    final <S> boolean uniApply(final CompletableFuture<S> completableFuture, final Function<? super S, ? extends T> function, final UniApply<S, T> uniApply) {
        Object result;
        if (completableFuture == null || (result = completableFuture.result) == null || function == null) {
            return false;
        }
        if (this.result == null) {
            if (result instanceof AltResult) {
                final Throwable ex;
                if ((ex = ((AltResult)result).ex) != null) {
                    this.completeThrowable(ex, result);
                    return true;
                }
                result = null;
            }
            try {
                if (uniApply != null && !uniApply.claim()) {
                    return false;
                }
                this.completeValue(function.apply((Object)result));
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private <V> CompletableFuture<V> uniApplyStage(final Executor executor, final Function<? super T, ? extends V> function) {
        if (function == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>)new CompletableFuture<V>();
        if (executor != null || !completableFuture.uniApply((CompletableFuture<Object>)this, (Function<? super Object, ?>)function, (UniApply<Object, ?>)null)) {
            final UniApply uniApply = new UniApply<Object, Object>(executor, completableFuture, this, function);
            this.push(uniApply);
            uniApply.tryFire(0);
        }
        return (CompletableFuture<V>)completableFuture;
    }
    
    final <S> boolean uniAccept(final CompletableFuture<S> completableFuture, final Consumer<? super S> consumer, final UniAccept<S> uniAccept) {
        Object result;
        if (completableFuture == null || (result = completableFuture.result) == null || consumer == null) {
            return false;
        }
        if (this.result == null) {
            if (result instanceof AltResult) {
                final Throwable ex;
                if ((ex = ((AltResult)result).ex) != null) {
                    this.completeThrowable(ex, result);
                    return true;
                }
                result = null;
            }
            try {
                if (uniAccept != null && !uniAccept.claim()) {
                    return false;
                }
                consumer.accept((Object)result);
                this.completeNull();
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private CompletableFuture<Void> uniAcceptStage(final Executor executor, final Consumer<? super T> consumer) {
        if (consumer == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Void> completableFuture = new CompletableFuture<Void>();
        if (executor != null || !completableFuture.uniAccept((CompletableFuture<Object>)this, (Consumer<? super Object>)consumer, null)) {
            final UniAccept uniAccept = new UniAccept<Object>(executor, completableFuture, this, consumer);
            this.push(uniAccept);
            uniAccept.tryFire(0);
        }
        return completableFuture;
    }
    
    final boolean uniRun(final CompletableFuture<?> completableFuture, final Runnable runnable, final UniRun<?> uniRun) {
        final Object result;
        if (completableFuture == null || (result = completableFuture.result) == null || runnable == null) {
            return false;
        }
        if (this.result == null) {
            final Throwable ex;
            if (result instanceof AltResult && (ex = ((AltResult)result).ex) != null) {
                this.completeThrowable(ex, result);
            }
            else {
                try {
                    if (uniRun != null && !uniRun.claim()) {
                        return false;
                    }
                    runnable.run();
                    this.completeNull();
                }
                catch (Throwable t) {
                    this.completeThrowable(t);
                }
            }
        }
        return true;
    }
    
    private CompletableFuture<Void> uniRunStage(final Executor executor, final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Void> completableFuture = new CompletableFuture<Void>();
        if (executor != null || !completableFuture.uniRun(this, runnable, null)) {
            final UniRun uniRun = new UniRun<Object>(executor, completableFuture, this, runnable);
            this.push(uniRun);
            uniRun.tryFire(0);
        }
        return completableFuture;
    }
    
    final boolean uniWhenComplete(final CompletableFuture<T> completableFuture, final BiConsumer<? super T, ? super Throwable> biConsumer, final UniWhenComplete<T> uniWhenComplete) {
        Throwable ex = null;
        final Object result;
        if (completableFuture == null || (result = completableFuture.result) == null || biConsumer == null) {
            return false;
        }
        if (this.result == null) {
            try {
                if (uniWhenComplete != null && !uniWhenComplete.claim()) {
                    return false;
                }
                Object o;
                if (result instanceof AltResult) {
                    ex = ((AltResult)result).ex;
                    o = null;
                }
                else {
                    o = result;
                }
                biConsumer.accept((Object)o, ex);
                if (ex == null) {
                    this.internalComplete(result);
                    return true;
                }
            }
            catch (Throwable t) {
                if (ex == null) {
                    ex = t;
                }
            }
            this.completeThrowable(ex, result);
        }
        return true;
    }
    
    private CompletableFuture<T> uniWhenCompleteStage(final Executor executor, final BiConsumer<? super T, ? super Throwable> biConsumer) {
        if (biConsumer == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>)new CompletableFuture<T>();
        if (executor != null || !completableFuture.uniWhenComplete((CompletableFuture<?>)this, (BiConsumer<?, ? super Throwable>)biConsumer, (UniWhenComplete<?>)null)) {
            final UniWhenComplete uniWhenComplete = new UniWhenComplete<Object>(executor, completableFuture, this, biConsumer);
            this.push(uniWhenComplete);
            uniWhenComplete.tryFire(0);
        }
        return (CompletableFuture<T>)completableFuture;
    }
    
    final <S> boolean uniHandle(final CompletableFuture<S> completableFuture, final BiFunction<? super S, Throwable, ? extends T> biFunction, final UniHandle<S, T> uniHandle) {
        final Object result;
        if (completableFuture == null || (result = completableFuture.result) == null || biFunction == null) {
            return false;
        }
        if (this.result == null) {
            try {
                if (uniHandle != null && !uniHandle.claim()) {
                    return false;
                }
                Throwable ex;
                Object o;
                if (result instanceof AltResult) {
                    ex = ((AltResult)result).ex;
                    o = null;
                }
                else {
                    ex = null;
                    o = result;
                }
                this.completeValue(biFunction.apply((Object)o, ex));
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private <V> CompletableFuture<V> uniHandleStage(final Executor executor, final BiFunction<? super T, Throwable, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>)new CompletableFuture<V>();
        if (executor != null || !completableFuture.uniHandle((CompletableFuture<Object>)this, (BiFunction<? super Object, Throwable, ?>)biFunction, (UniHandle<Object, ?>)null)) {
            final UniHandle uniHandle = new UniHandle<Object, Object>(executor, completableFuture, this, biFunction);
            this.push(uniHandle);
            uniHandle.tryFire(0);
        }
        return (CompletableFuture<V>)completableFuture;
    }
    
    final boolean uniExceptionally(final CompletableFuture<T> completableFuture, final Function<? super Throwable, ? extends T> function, final UniExceptionally<T> uniExceptionally) {
        final Object result;
        if (completableFuture == null || (result = completableFuture.result) == null || function == null) {
            return false;
        }
        if (this.result == null) {
            try {
                final Throwable ex;
                if (result instanceof AltResult && (ex = ((AltResult)result).ex) != null) {
                    if (uniExceptionally != null && !uniExceptionally.claim()) {
                        return false;
                    }
                    this.completeValue(function.apply(ex));
                }
                else {
                    this.internalComplete(result);
                }
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private CompletableFuture<T> uniExceptionallyStage(final Function<Throwable, ? extends T> function) {
        if (function == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Object> completableFuture = (CompletableFuture<Object>)new CompletableFuture<T>();
        if (!completableFuture.uniExceptionally((CompletableFuture<?>)this, (Function<? super Throwable, ?>)function, (UniExceptionally<?>)null)) {
            final UniExceptionally uniExceptionally = new UniExceptionally<Object>(completableFuture, this, function);
            this.push(uniExceptionally);
            uniExceptionally.tryFire(0);
        }
        return (CompletableFuture<T>)completableFuture;
    }
    
    final boolean uniRelay(final CompletableFuture<T> completableFuture) {
        final Object result;
        if (completableFuture == null || (result = completableFuture.result) == null) {
            return false;
        }
        if (this.result == null) {
            this.completeRelay(result);
        }
        return true;
    }
    
    final <S> boolean uniCompose(final CompletableFuture<S> completableFuture, final Function<? super S, ? extends CompletionStage<T>> function, final UniCompose<S, T> uniCompose) {
        Object result;
        if (completableFuture == null || (result = completableFuture.result) == null || function == null) {
            return false;
        }
        if (this.result == null) {
            if (result instanceof AltResult) {
                final Throwable ex;
                if ((ex = ((AltResult)result).ex) != null) {
                    this.completeThrowable(ex, result);
                    return true;
                }
                result = null;
            }
            try {
                if (uniCompose != null && !uniCompose.claim()) {
                    return false;
                }
                final CompletableFuture completableFuture2 = ((CompletionStage)function.apply((Object)result)).toCompletableFuture();
                if (completableFuture2.result == null || !this.uniRelay(completableFuture2)) {
                    final UniRelay uniRelay = new UniRelay<Object>(this, completableFuture2);
                    completableFuture2.push(uniRelay);
                    uniRelay.tryFire(0);
                    if (this.result == null) {
                        return false;
                    }
                }
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private <V> CompletableFuture<V> uniComposeStage(final Executor executor, final Function<? super T, ? extends CompletionStage<V>> function) {
        if (function == null) {
            throw new NullPointerException();
        }
        Object result;
        if (executor == null && (result = this.result) != null) {
            if (result instanceof AltResult) {
                final Throwable ex;
                if ((ex = ((AltResult)result).ex) != null) {
                    return new CompletableFuture<V>(encodeThrowable(ex, result));
                }
                result = null;
            }
            try {
                final CompletableFuture completableFuture = ((CompletionStage)function.apply((AltResult)result)).toCompletableFuture();
                final Object result2 = completableFuture.result;
                if (result2 != null) {
                    return new CompletableFuture<V>(encodeRelay(result2));
                }
                final CompletableFuture<Object> completableFuture2 = (CompletableFuture<Object>)new CompletableFuture<V>();
                final UniRelay uniRelay = new UniRelay<Object>(completableFuture2, completableFuture);
                completableFuture.push(uniRelay);
                uniRelay.tryFire(0);
                return (CompletableFuture<V>)completableFuture2;
            }
            catch (Throwable t) {
                return new CompletableFuture<V>(encodeThrowable(t));
            }
        }
        final CompletableFuture<Object> completableFuture3 = (CompletableFuture<Object>)new CompletableFuture<V>();
        final UniCompose uniCompose = new UniCompose<Object, Object>(executor, completableFuture3, this, function);
        this.push(uniCompose);
        uniCompose.tryFire(0);
        return (CompletableFuture<V>)completableFuture3;
    }
    
    final void bipush(final CompletableFuture<?> completableFuture, final BiCompletion<?, ?, ?> biCompletion) {
        if (biCompletion != null) {
            Object result;
            while ((result = this.result) == null && !this.tryPushStack(biCompletion)) {
                lazySetNext(biCompletion, null);
            }
            if (completableFuture != null && completableFuture != this && completableFuture.result == null) {
                final CoCompletion coCompletion = (CoCompletion)((result != null) ? biCompletion : new CoCompletion(biCompletion));
                while (completableFuture.result == null && !completableFuture.tryPushStack(coCompletion)) {
                    lazySetNext(coCompletion, null);
                }
            }
        }
    }
    
    final CompletableFuture<T> postFire(final CompletableFuture<?> completableFuture, final CompletableFuture<?> completableFuture2, final int n) {
        if (completableFuture2 != null && completableFuture2.stack != null) {
            if (n < 0 || completableFuture2.result == null) {
                completableFuture2.cleanStack();
            }
            else {
                completableFuture2.postComplete();
            }
        }
        return this.postFire(completableFuture, n);
    }
    
    final <R, S> boolean biApply(final CompletableFuture<R> completableFuture, final CompletableFuture<S> completableFuture2, final BiFunction<? super R, ? super S, ? extends T> biFunction, final BiApply<R, S, T> biApply) {
        Object result;
        Object result2;
        if (completableFuture == null || (result = completableFuture.result) == null || completableFuture2 == null || (result2 = completableFuture2.result) == null || biFunction == null) {
            return false;
        }
        if (this.result == null) {
            if (result instanceof AltResult) {
                final Throwable ex;
                if ((ex = ((AltResult)result).ex) != null) {
                    this.completeThrowable(ex, result);
                    return true;
                }
                result = null;
            }
            if (result2 instanceof AltResult) {
                final Throwable ex2;
                if ((ex2 = ((AltResult)result2).ex) != null) {
                    this.completeThrowable(ex2, result2);
                    return true;
                }
                result2 = null;
            }
            try {
                if (biApply != null && !biApply.claim()) {
                    return false;
                }
                this.completeValue(biFunction.apply((Object)result, (Object)result2));
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private <U, V> CompletableFuture<V> biApplyStage(final Executor executor, final CompletionStage<U> completionStage, final BiFunction<? super T, ? super U, ? extends V> biFunction) {
        final CompletableFuture<U> completableFuture;
        if (biFunction == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Object> completableFuture2 = new CompletableFuture<Object>();
        if (executor != null || !completableFuture2.biApply((CompletableFuture<Object>)this, completableFuture, (BiFunction<? super Object, ? super U, ?>)biFunction, null)) {
            final BiApply biApply = new BiApply<Object, Object, Object>(executor, completableFuture2, this, completableFuture, biFunction);
            this.bipush(completableFuture, biApply);
            biApply.tryFire(0);
        }
        return (CompletableFuture<V>)completableFuture2;
    }
    
    final <R, S> boolean biAccept(final CompletableFuture<R> completableFuture, final CompletableFuture<S> completableFuture2, final BiConsumer<? super R, ? super S> biConsumer, final BiAccept<R, S> biAccept) {
        Object result;
        Object result2;
        if (completableFuture == null || (result = completableFuture.result) == null || completableFuture2 == null || (result2 = completableFuture2.result) == null || biConsumer == null) {
            return false;
        }
        if (this.result == null) {
            if (result instanceof AltResult) {
                final Throwable ex;
                if ((ex = ((AltResult)result).ex) != null) {
                    this.completeThrowable(ex, result);
                    return true;
                }
                result = null;
            }
            if (result2 instanceof AltResult) {
                final Throwable ex2;
                if ((ex2 = ((AltResult)result2).ex) != null) {
                    this.completeThrowable(ex2, result2);
                    return true;
                }
                result2 = null;
            }
            try {
                if (biAccept != null && !biAccept.claim()) {
                    return false;
                }
                biConsumer.accept((Object)result, (Object)result2);
                this.completeNull();
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private <U> CompletableFuture<Void> biAcceptStage(final Executor executor, final CompletionStage<U> completionStage, final BiConsumer<? super T, ? super U> biConsumer) {
        final CompletableFuture<U> completableFuture;
        if (biConsumer == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Void> completableFuture2 = new CompletableFuture<Void>();
        if (executor != null || !completableFuture2.biAccept((CompletableFuture<Object>)this, completableFuture, (BiConsumer<? super Object, ? super U>)biConsumer, null)) {
            final BiAccept biAccept = new BiAccept<Object, Object>(executor, completableFuture2, this, completableFuture, biConsumer);
            this.bipush(completableFuture, biAccept);
            biAccept.tryFire(0);
        }
        return completableFuture2;
    }
    
    final boolean biRun(final CompletableFuture<?> completableFuture, final CompletableFuture<?> completableFuture2, final Runnable runnable, final BiRun<?, ?> biRun) {
        final Object result;
        final Object result2;
        if (completableFuture == null || (result = completableFuture.result) == null || completableFuture2 == null || (result2 = completableFuture2.result) == null || runnable == null) {
            return false;
        }
        if (this.result == null) {
            final Throwable ex;
            if (result instanceof AltResult && (ex = ((AltResult)result).ex) != null) {
                this.completeThrowable(ex, result);
            }
            else {
                final Throwable ex2;
                if (result2 instanceof AltResult && (ex2 = ((AltResult)result2).ex) != null) {
                    this.completeThrowable(ex2, result2);
                }
                else {
                    try {
                        if (biRun != null && !biRun.claim()) {
                            return false;
                        }
                        runnable.run();
                        this.completeNull();
                    }
                    catch (Throwable t) {
                        this.completeThrowable(t);
                    }
                }
            }
        }
        return true;
    }
    
    private CompletableFuture<Void> biRunStage(final Executor executor, final CompletionStage<?> completionStage, final Runnable runnable) {
        final CompletableFuture<?> completableFuture;
        if (runnable == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Void> completableFuture2 = new CompletableFuture<Void>();
        if (executor != null || !completableFuture2.biRun(this, completableFuture, runnable, null)) {
            final BiRun biRun = new BiRun<Object, Object>(executor, completableFuture2, this, completableFuture, runnable);
            this.bipush(completableFuture, biRun);
            biRun.tryFire(0);
        }
        return completableFuture2;
    }
    
    boolean biRelay(final CompletableFuture<?> completableFuture, final CompletableFuture<?> completableFuture2) {
        final Object result;
        final Object result2;
        if (completableFuture == null || (result = completableFuture.result) == null || completableFuture2 == null || (result2 = completableFuture2.result) == null) {
            return false;
        }
        if (this.result == null) {
            final Throwable ex;
            if (result instanceof AltResult && (ex = ((AltResult)result).ex) != null) {
                this.completeThrowable(ex, result);
            }
            else {
                final Throwable ex2;
                if (result2 instanceof AltResult && (ex2 = ((AltResult)result2).ex) != null) {
                    this.completeThrowable(ex2, result2);
                }
                else {
                    this.completeNull();
                }
            }
        }
        return true;
    }
    
    static CompletableFuture<Void> andTree(final CompletableFuture<?>[] array, final int n, final int n2) {
        final CompletableFuture<Void> completableFuture = new CompletableFuture<Void>();
        if (n <= n2) {
            final int n3 = n + n2 >>> 1;
            final CompletableFuture<?> completableFuture2;
            if ((completableFuture2 = ((n == n3) ? array[n] : andTree(array, n, n3))) != null) {
                CompletableFuture<?> andTree;
                final CompletableFuture<?> completableFuture3 = (n == n2) ? (andTree = completableFuture2) : ((n2 == n3 + 1) ? (andTree = array[n2]) : (andTree = andTree(array, n3 + 1, n2)));
                final CompletableFuture<Object> completableFuture4 = (CompletableFuture<Object>)andTree;
                if (completableFuture3 != null) {
                    if (!completableFuture.biRelay(completableFuture2, completableFuture4)) {
                        final BiRelay biRelay = new BiRelay<Object, Object>(completableFuture, (CompletableFuture<Object>)completableFuture2, completableFuture4);
                        completableFuture2.bipush(completableFuture4, biRelay);
                        biRelay.tryFire(0);
                        return completableFuture;
                    }
                    return completableFuture;
                }
            }
            throw new NullPointerException();
        }
        completableFuture.result = CompletableFuture.NIL;
        return completableFuture;
    }
    
    final void orpush(final CompletableFuture<?> completableFuture, final BiCompletion<?, ?, ?> biCompletion) {
        if (biCompletion != null) {
            while ((completableFuture == null || completableFuture.result == null) && this.result == null) {
                if (this.tryPushStack(biCompletion)) {
                    if (completableFuture != null && completableFuture != this && completableFuture.result == null) {
                        final CoCompletion coCompletion = new CoCompletion(biCompletion);
                        while (this.result == null && completableFuture.result == null && !completableFuture.tryPushStack(coCompletion)) {
                            lazySetNext(coCompletion, null);
                        }
                        break;
                    }
                    break;
                }
                else {
                    lazySetNext(biCompletion, null);
                }
            }
        }
    }
    
    final <R, S extends R> boolean orApply(final CompletableFuture<R> completableFuture, final CompletableFuture<S> completableFuture2, final Function<? super R, ? extends T> function, final OrApply<R, S, T> orApply) {
        Object o;
        if (completableFuture == null || completableFuture2 == null || ((o = completableFuture.result) == null && (o = completableFuture2.result) == null) || function == null) {
            return false;
        }
        if (this.result == null) {
            try {
                if (orApply != null && !orApply.claim()) {
                    return false;
                }
                if (o instanceof AltResult) {
                    final Throwable ex;
                    if ((ex = ((AltResult)o).ex) != null) {
                        this.completeThrowable(ex, o);
                        return true;
                    }
                    o = null;
                }
                this.completeValue(function.apply((Object)o));
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private <U extends T, V> CompletableFuture<V> orApplyStage(final Executor executor, final CompletionStage<U> completionStage, final Function<? super T, ? extends V> function) {
        final CompletableFuture<U> completableFuture;
        if (function == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Object> completableFuture2 = new CompletableFuture<Object>();
        if (executor != null || !completableFuture2.orApply((CompletableFuture<Object>)this, completableFuture, (Function<? super Object, ?>)function, null)) {
            final OrApply orApply = new OrApply<Object, Object, Object>(executor, completableFuture2, this, completableFuture, function);
            this.orpush(completableFuture, orApply);
            orApply.tryFire(0);
        }
        return (CompletableFuture<V>)completableFuture2;
    }
    
    final <R, S extends R> boolean orAccept(final CompletableFuture<R> completableFuture, final CompletableFuture<S> completableFuture2, final Consumer<? super R> consumer, final OrAccept<R, S> orAccept) {
        Object o;
        if (completableFuture == null || completableFuture2 == null || ((o = completableFuture.result) == null && (o = completableFuture2.result) == null) || consumer == null) {
            return false;
        }
        if (this.result == null) {
            try {
                if (orAccept != null && !orAccept.claim()) {
                    return false;
                }
                if (o instanceof AltResult) {
                    final Throwable ex;
                    if ((ex = ((AltResult)o).ex) != null) {
                        this.completeThrowable(ex, o);
                        return true;
                    }
                    o = null;
                }
                consumer.accept((Object)o);
                this.completeNull();
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private <U extends T> CompletableFuture<Void> orAcceptStage(final Executor executor, final CompletionStage<U> completionStage, final Consumer<? super T> consumer) {
        final CompletableFuture<U> completableFuture;
        if (consumer == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Void> completableFuture2 = new CompletableFuture<Void>();
        if (executor != null || !completableFuture2.orAccept((CompletableFuture<Object>)this, completableFuture, (Consumer<? super Object>)consumer, null)) {
            final OrAccept orAccept = new OrAccept<Object, Object>(executor, completableFuture2, this, completableFuture, consumer);
            this.orpush(completableFuture, orAccept);
            orAccept.tryFire(0);
        }
        return completableFuture2;
    }
    
    final boolean orRun(final CompletableFuture<?> completableFuture, final CompletableFuture<?> completableFuture2, final Runnable runnable, final OrRun<?, ?> orRun) {
        Object o;
        if (completableFuture == null || completableFuture2 == null || ((o = completableFuture.result) == null && (o = completableFuture2.result) == null) || runnable == null) {
            return false;
        }
        if (this.result == null) {
            try {
                if (orRun != null && !orRun.claim()) {
                    return false;
                }
                final Throwable ex;
                if (o instanceof AltResult && (ex = ((AltResult)o).ex) != null) {
                    this.completeThrowable(ex, o);
                }
                else {
                    runnable.run();
                    this.completeNull();
                }
            }
            catch (Throwable t) {
                this.completeThrowable(t);
            }
        }
        return true;
    }
    
    private CompletableFuture<Void> orRunStage(final Executor executor, final CompletionStage<?> completionStage, final Runnable runnable) {
        final CompletableFuture<?> completableFuture;
        if (runnable == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Void> completableFuture2 = new CompletableFuture<Void>();
        if (executor != null || !completableFuture2.orRun(this, completableFuture, runnable, null)) {
            final OrRun orRun = new OrRun<Object, Object>(executor, completableFuture2, this, completableFuture, runnable);
            this.orpush(completableFuture, orRun);
            orRun.tryFire(0);
        }
        return completableFuture2;
    }
    
    final boolean orRelay(final CompletableFuture<?> completableFuture, final CompletableFuture<?> completableFuture2) {
        Object o;
        if (completableFuture == null || completableFuture2 == null || ((o = completableFuture.result) == null && (o = completableFuture2.result) == null)) {
            return false;
        }
        if (this.result == null) {
            this.completeRelay(o);
        }
        return true;
    }
    
    static CompletableFuture<Object> orTree(final CompletableFuture<?>[] array, final int n, final int n2) {
        final CompletableFuture<Object> completableFuture = new CompletableFuture<Object>();
        if (n <= n2) {
            final int n3 = n + n2 >>> 1;
            final CompletableFuture<?> completableFuture2;
            if ((completableFuture2 = ((n == n3) ? array[n] : orTree(array, n, n3))) != null) {
                CompletableFuture<?> orTree;
                final CompletableFuture<?> completableFuture3 = (n == n2) ? (orTree = completableFuture2) : ((n2 == n3 + 1) ? (orTree = array[n2]) : (orTree = orTree(array, n3 + 1, n2)));
                final CompletableFuture<Object> completableFuture4 = (CompletableFuture<Object>)orTree;
                if (completableFuture3 != null) {
                    if (!completableFuture.orRelay(completableFuture2, completableFuture4)) {
                        final OrRelay orRelay = new OrRelay<Object, Object>(completableFuture, (CompletableFuture<Object>)completableFuture2, completableFuture4);
                        completableFuture2.orpush(completableFuture4, orRelay);
                        orRelay.tryFire(0);
                        return completableFuture;
                    }
                    return completableFuture;
                }
            }
            throw new NullPointerException();
        }
        return completableFuture;
    }
    
    static <U> CompletableFuture<U> asyncSupplyStage(final Executor executor, final Supplier<U> supplier) {
        if (supplier == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Object> completableFuture = new CompletableFuture<Object>();
        executor.execute(new AsyncSupply<Object>(completableFuture, (Supplier<Object>)supplier));
        return (CompletableFuture<U>)completableFuture;
    }
    
    static CompletableFuture<Void> asyncRunStage(final Executor executor, final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        final CompletableFuture<Void> completableFuture = new CompletableFuture<Void>();
        executor.execute(new AsyncRun(completableFuture, runnable));
        return completableFuture;
    }
    
    private Object waitingGet(final boolean b) {
        Signaller signaller = null;
        boolean tryPushStack = false;
        int spins = -1;
        Object result;
        while ((result = this.result) == null) {
            if (spins < 0) {
                spins = CompletableFuture.SPINS;
            }
            else if (spins > 0) {
                if (ThreadLocalRandom.nextSecondarySeed() < 0) {
                    continue;
                }
                --spins;
            }
            else if (signaller == null) {
                signaller = new Signaller(b, 0L, 0L);
            }
            else if (!tryPushStack) {
                tryPushStack = this.tryPushStack(signaller);
            }
            else {
                if (b && signaller.interruptControl < 0) {
                    signaller.thread = null;
                    this.cleanStack();
                    return null;
                }
                if (signaller.thread == null || this.result != null) {
                    continue;
                }
                try {
                    ForkJoinPool.managedBlock(signaller);
                }
                catch (InterruptedException ex) {
                    signaller.interruptControl = -1;
                }
            }
        }
        if (signaller != null) {
            signaller.thread = null;
            if (signaller.interruptControl < 0) {
                if (b) {
                    result = null;
                }
                else {
                    Thread.currentThread().interrupt();
                }
            }
        }
        this.postComplete();
        return result;
    }
    
    private Object timedGet(final long n) throws TimeoutException {
        if (Thread.interrupted()) {
            return null;
        }
        if (n <= 0L) {
            throw new TimeoutException();
        }
        final long n2 = System.nanoTime() + n;
        final Signaller signaller = new Signaller(true, n, (n2 == 0L) ? 1L : n2);
        boolean tryPushStack = false;
        Object result;
        while ((result = this.result) == null) {
            if (!tryPushStack) {
                tryPushStack = this.tryPushStack(signaller);
            }
            else if (signaller.interruptControl < 0 || signaller.nanos <= 0L) {
                signaller.thread = null;
                this.cleanStack();
                if (signaller.interruptControl < 0) {
                    return null;
                }
                throw new TimeoutException();
            }
            else {
                if (signaller.thread == null || this.result != null) {
                    continue;
                }
                try {
                    ForkJoinPool.managedBlock(signaller);
                }
                catch (InterruptedException ex) {
                    signaller.interruptControl = -1;
                }
            }
        }
        if (signaller.interruptControl < 0) {
            result = null;
        }
        signaller.thread = null;
        this.postComplete();
        return result;
    }
    
    public CompletableFuture() {
    }
    
    private CompletableFuture(final Object result) {
        this.result = result;
    }
    
    public static <U> CompletableFuture<U> supplyAsync(final Supplier<U> supplier) {
        return asyncSupplyStage(CompletableFuture.asyncPool, supplier);
    }
    
    public static <U> CompletableFuture<U> supplyAsync(final Supplier<U> supplier, final Executor executor) {
        return asyncSupplyStage(screenExecutor(executor), supplier);
    }
    
    public static CompletableFuture<Void> runAsync(final Runnable runnable) {
        return asyncRunStage(CompletableFuture.asyncPool, runnable);
    }
    
    public static CompletableFuture<Void> runAsync(final Runnable runnable, final Executor executor) {
        return asyncRunStage(screenExecutor(executor), runnable);
    }
    
    public static <U> CompletableFuture<U> completedFuture(final U u) {
        return new CompletableFuture<U>((u == null) ? CompletableFuture.NIL : u);
    }
    
    @Override
    public boolean isDone() {
        return this.result != null;
    }
    
    @Override
    public T get() throws InterruptedException, ExecutionException {
        final Object result;
        return (T)reportGet(((result = this.result) == null) ? this.waitingGet(true) : result);
    }
    
    @Override
    public T get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        final long nanos = timeUnit.toNanos(n);
        final Object result;
        return (T)reportGet(((result = this.result) == null) ? this.timedGet(nanos) : result);
    }
    
    public T join() {
        final Object result;
        return (T)reportJoin(((result = this.result) == null) ? this.waitingGet(false) : result);
    }
    
    public T getNow(final T t) {
        final Object result;
        return (T)(((result = this.result) == null) ? t : reportJoin(result));
    }
    
    public boolean complete(final T t) {
        final boolean completeValue = this.completeValue(t);
        this.postComplete();
        return completeValue;
    }
    
    public boolean completeExceptionally(final Throwable t) {
        if (t == null) {
            throw new NullPointerException();
        }
        final boolean internalComplete = this.internalComplete(new AltResult(t));
        this.postComplete();
        return internalComplete;
    }
    
    @Override
    public <U> CompletableFuture<U> thenApply(final Function<? super T, ? extends U> function) {
        return this.uniApplyStage((Executor)null, function);
    }
    
    @Override
    public <U> CompletableFuture<U> thenApplyAsync(final Function<? super T, ? extends U> function) {
        return this.uniApplyStage(CompletableFuture.asyncPool, function);
    }
    
    @Override
    public <U> CompletableFuture<U> thenApplyAsync(final Function<? super T, ? extends U> function, final Executor executor) {
        return this.uniApplyStage(screenExecutor(executor), function);
    }
    
    @Override
    public CompletableFuture<Void> thenAccept(final Consumer<? super T> consumer) {
        return this.uniAcceptStage(null, consumer);
    }
    
    @Override
    public CompletableFuture<Void> thenAcceptAsync(final Consumer<? super T> consumer) {
        return this.uniAcceptStage(CompletableFuture.asyncPool, consumer);
    }
    
    @Override
    public CompletableFuture<Void> thenAcceptAsync(final Consumer<? super T> consumer, final Executor executor) {
        return this.uniAcceptStage(screenExecutor(executor), consumer);
    }
    
    @Override
    public CompletableFuture<Void> thenRun(final Runnable runnable) {
        return this.uniRunStage(null, runnable);
    }
    
    @Override
    public CompletableFuture<Void> thenRunAsync(final Runnable runnable) {
        return this.uniRunStage(CompletableFuture.asyncPool, runnable);
    }
    
    @Override
    public CompletableFuture<Void> thenRunAsync(final Runnable runnable, final Executor executor) {
        return this.uniRunStage(screenExecutor(executor), runnable);
    }
    
    @Override
    public <U, V> CompletableFuture<V> thenCombine(final CompletionStage<? extends U> completionStage, final BiFunction<? super T, ? super U, ? extends V> biFunction) {
        return this.biApplyStage((Executor)null, (CompletionStage<Object>)completionStage, (BiFunction<? super T, ? super Object, ? extends V>)biFunction);
    }
    
    @Override
    public <U, V> CompletableFuture<V> thenCombineAsync(final CompletionStage<? extends U> completionStage, final BiFunction<? super T, ? super U, ? extends V> biFunction) {
        return this.biApplyStage(CompletableFuture.asyncPool, (CompletionStage<Object>)completionStage, (BiFunction<? super T, ? super Object, ? extends V>)biFunction);
    }
    
    @Override
    public <U, V> CompletableFuture<V> thenCombineAsync(final CompletionStage<? extends U> completionStage, final BiFunction<? super T, ? super U, ? extends V> biFunction, final Executor executor) {
        return this.biApplyStage(screenExecutor(executor), (CompletionStage<Object>)completionStage, (BiFunction<? super T, ? super Object, ? extends V>)biFunction);
    }
    
    @Override
    public <U> CompletableFuture<Void> thenAcceptBoth(final CompletionStage<? extends U> completionStage, final BiConsumer<? super T, ? super U> biConsumer) {
        return this.biAcceptStage(null, (CompletionStage<Object>)completionStage, (BiConsumer<? super T, ? super Object>)biConsumer);
    }
    
    @Override
    public <U> CompletableFuture<Void> thenAcceptBothAsync(final CompletionStage<? extends U> completionStage, final BiConsumer<? super T, ? super U> biConsumer) {
        return this.biAcceptStage(CompletableFuture.asyncPool, (CompletionStage<Object>)completionStage, (BiConsumer<? super T, ? super Object>)biConsumer);
    }
    
    @Override
    public <U> CompletableFuture<Void> thenAcceptBothAsync(final CompletionStage<? extends U> completionStage, final BiConsumer<? super T, ? super U> biConsumer, final Executor executor) {
        return this.biAcceptStage(screenExecutor(executor), (CompletionStage<Object>)completionStage, (BiConsumer<? super T, ? super Object>)biConsumer);
    }
    
    @Override
    public CompletableFuture<Void> runAfterBoth(final CompletionStage<?> completionStage, final Runnable runnable) {
        return this.biRunStage(null, completionStage, runnable);
    }
    
    @Override
    public CompletableFuture<Void> runAfterBothAsync(final CompletionStage<?> completionStage, final Runnable runnable) {
        return this.biRunStage(CompletableFuture.asyncPool, completionStage, runnable);
    }
    
    @Override
    public CompletableFuture<Void> runAfterBothAsync(final CompletionStage<?> completionStage, final Runnable runnable, final Executor executor) {
        return this.biRunStage(screenExecutor(executor), completionStage, runnable);
    }
    
    @Override
    public <U> CompletableFuture<U> applyToEither(final CompletionStage<? extends T> completionStage, final Function<? super T, U> function) {
        return this.orApplyStage((Executor)null, (CompletionStage<Object>)completionStage, (Function<? super T, ? extends U>)function);
    }
    
    @Override
    public <U> CompletableFuture<U> applyToEitherAsync(final CompletionStage<? extends T> completionStage, final Function<? super T, U> function) {
        return this.orApplyStage(CompletableFuture.asyncPool, (CompletionStage<Object>)completionStage, (Function<? super T, ? extends U>)function);
    }
    
    @Override
    public <U> CompletableFuture<U> applyToEitherAsync(final CompletionStage<? extends T> completionStage, final Function<? super T, U> function, final Executor executor) {
        return this.orApplyStage(screenExecutor(executor), (CompletionStage<Object>)completionStage, (Function<? super T, ? extends U>)function);
    }
    
    @Override
    public CompletableFuture<Void> acceptEither(final CompletionStage<? extends T> completionStage, final Consumer<? super T> consumer) {
        return this.orAcceptStage(null, (CompletionStage<Object>)completionStage, consumer);
    }
    
    @Override
    public CompletableFuture<Void> acceptEitherAsync(final CompletionStage<? extends T> completionStage, final Consumer<? super T> consumer) {
        return this.orAcceptStage(CompletableFuture.asyncPool, (CompletionStage<Object>)completionStage, consumer);
    }
    
    @Override
    public CompletableFuture<Void> acceptEitherAsync(final CompletionStage<? extends T> completionStage, final Consumer<? super T> consumer, final Executor executor) {
        return this.orAcceptStage(screenExecutor(executor), (CompletionStage<Object>)completionStage, consumer);
    }
    
    @Override
    public CompletableFuture<Void> runAfterEither(final CompletionStage<?> completionStage, final Runnable runnable) {
        return this.orRunStage(null, completionStage, runnable);
    }
    
    @Override
    public CompletableFuture<Void> runAfterEitherAsync(final CompletionStage<?> completionStage, final Runnable runnable) {
        return this.orRunStage(CompletableFuture.asyncPool, completionStage, runnable);
    }
    
    @Override
    public CompletableFuture<Void> runAfterEitherAsync(final CompletionStage<?> completionStage, final Runnable runnable, final Executor executor) {
        return this.orRunStage(screenExecutor(executor), completionStage, runnable);
    }
    
    @Override
    public <U> CompletableFuture<U> thenCompose(final Function<? super T, ? extends CompletionStage<U>> function) {
        return this.uniComposeStage((Executor)null, function);
    }
    
    @Override
    public <U> CompletableFuture<U> thenComposeAsync(final Function<? super T, ? extends CompletionStage<U>> function) {
        return this.uniComposeStage(CompletableFuture.asyncPool, function);
    }
    
    @Override
    public <U> CompletableFuture<U> thenComposeAsync(final Function<? super T, ? extends CompletionStage<U>> function, final Executor executor) {
        return this.uniComposeStage(screenExecutor(executor), function);
    }
    
    @Override
    public CompletableFuture<T> whenComplete(final BiConsumer<? super T, ? super Throwable> biConsumer) {
        return this.uniWhenCompleteStage(null, biConsumer);
    }
    
    @Override
    public CompletableFuture<T> whenCompleteAsync(final BiConsumer<? super T, ? super Throwable> biConsumer) {
        return this.uniWhenCompleteStage(CompletableFuture.asyncPool, biConsumer);
    }
    
    @Override
    public CompletableFuture<T> whenCompleteAsync(final BiConsumer<? super T, ? super Throwable> biConsumer, final Executor executor) {
        return this.uniWhenCompleteStage(screenExecutor(executor), biConsumer);
    }
    
    @Override
    public <U> CompletableFuture<U> handle(final BiFunction<? super T, Throwable, ? extends U> biFunction) {
        return this.uniHandleStage((Executor)null, biFunction);
    }
    
    @Override
    public <U> CompletableFuture<U> handleAsync(final BiFunction<? super T, Throwable, ? extends U> biFunction) {
        return this.uniHandleStage(CompletableFuture.asyncPool, biFunction);
    }
    
    @Override
    public <U> CompletableFuture<U> handleAsync(final BiFunction<? super T, Throwable, ? extends U> biFunction, final Executor executor) {
        return this.uniHandleStage(screenExecutor(executor), biFunction);
    }
    
    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return this;
    }
    
    @Override
    public CompletableFuture<T> exceptionally(final Function<Throwable, ? extends T> function) {
        return this.uniExceptionallyStage(function);
    }
    
    public static CompletableFuture<Void> allOf(final CompletableFuture<?>... array) {
        return andTree(array, 0, array.length - 1);
    }
    
    public static CompletableFuture<Object> anyOf(final CompletableFuture<?>... array) {
        return orTree(array, 0, array.length - 1);
    }
    
    @Override
    public boolean cancel(final boolean b) {
        final boolean b2 = this.result == null && this.internalComplete(new AltResult(new CancellationException()));
        this.postComplete();
        return b2 || this.isCancelled();
    }
    
    @Override
    public boolean isCancelled() {
        final Object result;
        return (result = this.result) instanceof AltResult && ((AltResult)result).ex instanceof CancellationException;
    }
    
    public boolean isCompletedExceptionally() {
        final Object result;
        return (result = this.result) instanceof AltResult && result != CompletableFuture.NIL;
    }
    
    public void obtrudeValue(final T t) {
        this.result = ((t == null) ? CompletableFuture.NIL : t);
        this.postComplete();
    }
    
    public void obtrudeException(final Throwable t) {
        if (t == null) {
            throw new NullPointerException();
        }
        this.result = new AltResult(t);
        this.postComplete();
    }
    
    public int getNumberOfDependents() {
        int n = 0;
        for (Completion completion = this.stack; completion != null; completion = completion.next) {
            ++n;
        }
        return n;
    }
    
    @Override
    public String toString() {
        final Object result = this.result;
        final int numberOfDependents;
        return super.toString() + ((result == null) ? (((numberOfDependents = this.getNumberOfDependents()) == 0) ? "[Not completed]" : ("[Not completed, " + numberOfDependents + " dependents]")) : ((result instanceof AltResult && ((AltResult)result).ex != null) ? "[Completed exceptionally]" : "[Completed normally]"));
    }
    
    static {
        NIL = new AltResult(null);
        useCommonPool = (ForkJoinPool.getCommonPoolParallelism() > 1);
        asyncPool = (Executor)(CompletableFuture.useCommonPool ? ForkJoinPool.commonPool() : new ThreadPerTaskExecutor());
        SPINS = ((Runtime.getRuntime().availableProcessors() > 1) ? 256 : 0);
        try {
            final Unsafe unsafe = UNSAFE = Unsafe.getUnsafe();
            final Class<CompletableFuture> clazz = CompletableFuture.class;
            RESULT = unsafe.objectFieldOffset(clazz.getDeclaredField("result"));
            STACK = unsafe.objectFieldOffset(clazz.getDeclaredField("stack"));
            NEXT = unsafe.objectFieldOffset(Completion.class.getDeclaredField("next"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    static final class AltResult
    {
        final Throwable ex;
        
        AltResult(final Throwable ex) {
            this.ex = ex;
        }
    }
    
    static final class AsyncRun extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask
    {
        CompletableFuture<Void> dep;
        Runnable fn;
        
        AsyncRun(final CompletableFuture<Void> dep, final Runnable fn) {
            this.dep = dep;
            this.fn = fn;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            this.run();
            return true;
        }
        
        @Override
        public void run() {
            final CompletableFuture<Void> dep;
            final Runnable fn;
            if ((dep = this.dep) != null && (fn = this.fn) != null) {
                this.dep = null;
                this.fn = null;
                if (dep.result == null) {
                    try {
                        fn.run();
                        dep.completeNull();
                    }
                    catch (Throwable t) {
                        dep.completeThrowable(t);
                    }
                }
                dep.postComplete();
            }
        }
    }
    
    public interface AsynchronousCompletionTask
    {
    }
    
    static final class AsyncSupply<T> extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask
    {
        CompletableFuture<T> dep;
        Supplier<T> fn;
        
        AsyncSupply(final CompletableFuture<T> dep, final Supplier<T> fn) {
            this.dep = dep;
            this.fn = fn;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            this.run();
            return true;
        }
        
        @Override
        public void run() {
            final CompletableFuture<T> dep;
            final Supplier<T> fn;
            if ((dep = this.dep) != null && (fn = this.fn) != null) {
                this.dep = null;
                this.fn = null;
                if (dep.result == null) {
                    try {
                        dep.completeValue(fn.get());
                    }
                    catch (Throwable t) {
                        dep.completeThrowable(t);
                    }
                }
                dep.postComplete();
            }
        }
    }
    
    static final class BiAccept<T, U> extends BiCompletion<T, U, Void>
    {
        BiConsumer<? super T, ? super U> fn;
        
        BiAccept(final Executor executor, final CompletableFuture<Void> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3, final BiConsumer<? super T, ? super U> fn) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<Void> tryFire(final int n) {
            final CompletableFuture<U> dep;
            if ((dep = this.dep) != null) {
                final CompletableFuture<U> completableFuture = dep;
                final CompletableFuture<T> src = this.src;
                final Future<T> snd;
                if (completableFuture.biAccept(src, (CompletableFuture<U>)(snd = (Future<T>)this.snd), this.fn, (n > 0) ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return (CompletableFuture<Void>)dep.postFire(src, (CompletableFuture<?>)snd, n);
                }
            }
            return null;
        }
    }
    
    abstract static class BiCompletion<T, U, V> extends UniCompletion<T, V>
    {
        CompletableFuture<U> snd;
        
        BiCompletion(final Executor executor, final CompletableFuture<V> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> snd) {
            super(executor, completableFuture, completableFuture2);
            this.snd = snd;
        }
    }
    
    abstract static class UniCompletion<T, V> extends Completion
    {
        Executor executor;
        CompletableFuture<V> dep;
        CompletableFuture<T> src;
        
        UniCompletion(final Executor executor, final CompletableFuture<V> dep, final CompletableFuture<T> src) {
            this.executor = executor;
            this.dep = dep;
            this.src = src;
        }
        
        final boolean claim() {
            final Executor executor = this.executor;
            if (this.compareAndSetForkJoinTaskTag((short)0, (short)1)) {
                if (executor == null) {
                    return true;
                }
                this.executor = null;
                executor.execute(this);
            }
            return false;
        }
        
        @Override
        final boolean isLive() {
            return this.dep != null;
        }
    }
    
    abstract static class Completion extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask
    {
        volatile Completion next;
        
        abstract CompletableFuture<?> tryFire(final int p0);
        
        abstract boolean isLive();
        
        @Override
        public final void run() {
            this.tryFire(1);
        }
        
        public final boolean exec() {
            this.tryFire(1);
            return true;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
    }
    
    static final class BiApply<T, U, V> extends BiCompletion<T, U, V>
    {
        BiFunction<? super T, ? super U, ? extends V> fn;
        
        BiApply(final Executor executor, final CompletableFuture<V> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3, final BiFunction<? super T, ? super U, ? extends V> fn) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<V> tryFire(final int n) {
            final Future<T> dep;
            if ((dep = (Future<T>)this.dep) != null) {
                final Future<T> future = dep;
                final CompletableFuture<T> src = this.src;
                final CompletableFuture<U> snd;
                if (((CompletableFuture<V>)future).biApply((CompletableFuture<T>)src, snd = this.snd, (BiFunction<? super T, ? super U, ? extends V>)this.fn, (BiApply<T, U, V>)((n > 0) ? null : this))) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return ((CompletableFuture<V>)dep).postFire(src, snd, n);
                }
            }
            return null;
        }
    }
    
    static final class BiRelay<T, U> extends BiCompletion<T, U, Void>
    {
        BiRelay(final CompletableFuture<Void> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3) {
            super(null, completableFuture, completableFuture2, completableFuture3);
        }
        
        @Override
        final CompletableFuture<Void> tryFire(final int n) {
            final CompletableFuture<U> dep;
            if ((dep = this.dep) != null) {
                final CompletableFuture<U> completableFuture = dep;
                final CompletableFuture<T> src = this.src;
                final Future<T> snd;
                if (completableFuture.biRelay(src, (CompletableFuture<?>)(snd = (Future<T>)this.snd))) {
                    this.src = null;
                    this.snd = null;
                    this.dep = null;
                    return (CompletableFuture<Void>)dep.postFire(src, (CompletableFuture<?>)snd, n);
                }
            }
            return null;
        }
    }
    
    static final class BiRun<T, U> extends BiCompletion<T, U, Void>
    {
        Runnable fn;
        
        BiRun(final Executor executor, final CompletableFuture<Void> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3, final Runnable fn) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<Void> tryFire(final int n) {
            final CompletableFuture<U> dep;
            if ((dep = this.dep) != null) {
                final CompletableFuture<U> completableFuture = dep;
                final CompletableFuture<T> src = this.src;
                final Future<T> snd;
                if (completableFuture.biRun(src, (CompletableFuture<?>)(snd = (Future<T>)this.snd), this.fn, (n > 0) ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return (CompletableFuture<Void>)dep.postFire(src, (CompletableFuture<?>)snd, n);
                }
            }
            return null;
        }
    }
    
    static final class CoCompletion extends Completion
    {
        BiCompletion<?, ?, ?> base;
        
        CoCompletion(final BiCompletion<?, ?, ?> base) {
            this.base = base;
        }
        
        @Override
        final CompletableFuture<?> tryFire(final int n) {
            final BiCompletion<?, ?, ?> base;
            final CompletableFuture<?> tryFire;
            if ((base = this.base) == null || (tryFire = base.tryFire(n)) == null) {
                return null;
            }
            this.base = null;
            return tryFire;
        }
        
        @Override
        final boolean isLive() {
            final BiCompletion<?, ?, ?> base;
            return (base = this.base) != null && base.dep != null;
        }
    }
    
    static final class OrAccept<T, U extends T> extends BiCompletion<T, U, Void>
    {
        Consumer<? super T> fn;
        
        OrAccept(final Executor executor, final CompletableFuture<Void> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3, final Consumer<? super T> fn) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<Void> tryFire(final int n) {
            final CompletableFuture<U> dep;
            if ((dep = this.dep) != null) {
                final CompletableFuture<U> completableFuture = dep;
                final CompletableFuture<T> src = this.src;
                final Future<T> snd;
                if (completableFuture.orAccept(src, (CompletableFuture<U>)(snd = (Future<T>)this.snd), this.fn, (n > 0) ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return (CompletableFuture<Void>)dep.postFire(src, (CompletableFuture<?>)snd, n);
                }
            }
            return null;
        }
    }
    
    static final class OrApply<T, U extends T, V> extends BiCompletion<T, U, V>
    {
        Function<? super T, ? extends V> fn;
        
        OrApply(final Executor executor, final CompletableFuture<V> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3, final Function<? super T, ? extends V> fn) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<V> tryFire(final int n) {
            final Future<T> dep;
            if ((dep = (Future<T>)this.dep) != null) {
                final Future<T> future = dep;
                final CompletableFuture<T> src = this.src;
                final CompletableFuture<U> snd;
                if (((CompletableFuture<V>)future).orApply((CompletableFuture<T>)src, snd = this.snd, (Function<? super T, ? extends V>)this.fn, (OrApply<T, U, V>)((n > 0) ? null : this))) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return ((CompletableFuture<V>)dep).postFire(src, snd, n);
                }
            }
            return null;
        }
    }
    
    static final class OrRelay<T, U> extends BiCompletion<T, U, Object>
    {
        OrRelay(final CompletableFuture<Object> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3) {
            super(null, completableFuture, completableFuture2, completableFuture3);
        }
        
        @Override
        final CompletableFuture<Object> tryFire(final int n) {
            final CompletableFuture<U> dep;
            if ((dep = this.dep) != null) {
                final CompletableFuture<U> completableFuture = dep;
                final CompletableFuture<T> src = this.src;
                final Future<T> snd;
                if (completableFuture.orRelay(src, (CompletableFuture<?>)(snd = (Future<T>)this.snd))) {
                    this.src = null;
                    this.snd = null;
                    this.dep = null;
                    return (CompletableFuture<Object>)dep.postFire(src, (CompletableFuture<?>)snd, n);
                }
            }
            return null;
        }
    }
    
    static final class OrRun<T, U> extends BiCompletion<T, U, Void>
    {
        Runnable fn;
        
        OrRun(final Executor executor, final CompletableFuture<Void> completableFuture, final CompletableFuture<T> completableFuture2, final CompletableFuture<U> completableFuture3, final Runnable fn) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<Void> tryFire(final int n) {
            final CompletableFuture<U> dep;
            if ((dep = this.dep) != null) {
                final CompletableFuture<U> completableFuture = dep;
                final CompletableFuture<T> src = this.src;
                final Future<T> snd;
                if (completableFuture.orRun(src, (CompletableFuture<?>)(snd = (Future<T>)this.snd), this.fn, (n > 0) ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return (CompletableFuture<Void>)dep.postFire(src, (CompletableFuture<?>)snd, n);
                }
            }
            return null;
        }
    }
    
    static final class Signaller extends Completion implements ForkJoinPool.ManagedBlocker
    {
        long nanos;
        final long deadline;
        volatile int interruptControl;
        volatile Thread thread;
        
        Signaller(final boolean interruptControl, final long nanos, final long deadline) {
            this.thread = Thread.currentThread();
            this.interruptControl = (interruptControl ? 1 : 0);
            this.nanos = nanos;
            this.deadline = deadline;
        }
        
        @Override
        final CompletableFuture<?> tryFire(final int n) {
            final Thread thread;
            if ((thread = this.thread) != null) {
                this.thread = null;
                LockSupport.unpark(thread);
            }
            return null;
        }
        
        @Override
        public boolean isReleasable() {
            if (this.thread == null) {
                return true;
            }
            if (Thread.interrupted()) {
                final int interruptControl = this.interruptControl;
                this.interruptControl = -1;
                if (interruptControl > 0) {
                    return true;
                }
            }
            if (this.deadline != 0L) {
                if (this.nanos > 0L) {
                    final long nanos = this.deadline - System.nanoTime();
                    this.nanos = nanos;
                    if (nanos > 0L) {
                        return false;
                    }
                }
                this.thread = null;
                return true;
            }
            return false;
        }
        
        @Override
        public boolean block() {
            if (this.isReleasable()) {
                return true;
            }
            if (this.deadline == 0L) {
                LockSupport.park(this);
            }
            else if (this.nanos > 0L) {
                LockSupport.parkNanos(this, this.nanos);
            }
            return this.isReleasable();
        }
        
        @Override
        final boolean isLive() {
            return this.thread != null;
        }
    }
    
    static final class ThreadPerTaskExecutor implements Executor
    {
        @Override
        public void execute(final Runnable runnable) {
            new Thread(runnable).start();
        }
    }
    
    static final class UniAccept<T> extends UniCompletion<T, Void>
    {
        Consumer<? super T> fn;
        
        UniAccept(final Executor executor, final CompletableFuture<Void> completableFuture, final CompletableFuture<T> completableFuture2, final Consumer<? super T> fn) {
            super(executor, completableFuture, completableFuture2);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<Void> tryFire(final int n) {
            final Object dep;
            final Object src;
            if ((dep = this.dep) == null || !((CompletableFuture<V>)dep).uniAccept((CompletableFuture<T>)(src = this.src), this.fn, (n > 0) ? null : this)) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return (CompletableFuture<Void>)((CompletableFuture<V>)dep).postFire((CompletableFuture<?>)src, n);
        }
    }
    
    static final class UniApply<T, V> extends UniCompletion<T, V>
    {
        Function<? super T, ? extends V> fn;
        
        UniApply(final Executor executor, final CompletableFuture<V> completableFuture, final CompletableFuture<T> completableFuture2, final Function<? super T, ? extends V> fn) {
            super(executor, completableFuture, completableFuture2);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<V> tryFire(final int n) {
            final CompletableFuture<V> dep;
            final CompletableFuture<T> src;
            if ((dep = this.dep) == null || !dep.uniApply(src = this.src, this.fn, (n > 0) ? null : this)) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return dep.postFire(src, n);
        }
    }
    
    static final class UniCompose<T, V> extends UniCompletion<T, V>
    {
        Function<? super T, ? extends CompletionStage<V>> fn;
        
        UniCompose(final Executor executor, final CompletableFuture<V> completableFuture, final CompletableFuture<T> completableFuture2, final Function<? super T, ? extends CompletionStage<V>> fn) {
            super(executor, completableFuture, completableFuture2);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<V> tryFire(final int n) {
            final CompletableFuture<V> dep;
            final CompletableFuture<T> src;
            if ((dep = this.dep) == null || !dep.uniCompose(src = this.src, this.fn, (n > 0) ? null : this)) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return dep.postFire(src, n);
        }
    }
    
    static final class UniExceptionally<T> extends UniCompletion<T, T>
    {
        Function<? super Throwable, ? extends T> fn;
        
        UniExceptionally(final CompletableFuture<T> completableFuture, final CompletableFuture<T> completableFuture2, final Function<? super Throwable, ? extends T> fn) {
            super(null, completableFuture, completableFuture2);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<T> tryFire(final int n) {
            final Object dep;
            final Object src;
            if ((dep = this.dep) == null || !((CompletableFuture<V>)dep).uniExceptionally((CompletableFuture<V>)(src = this.src), (Function<? super Throwable, ? extends V>)this.fn, (UniExceptionally<V>)this)) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return (CompletableFuture<T>)((CompletableFuture<V>)dep).postFire((CompletableFuture<?>)src, n);
        }
    }
    
    static final class UniHandle<T, V> extends UniCompletion<T, V>
    {
        BiFunction<? super T, Throwable, ? extends V> fn;
        
        UniHandle(final Executor executor, final CompletableFuture<V> completableFuture, final CompletableFuture<T> completableFuture2, final BiFunction<? super T, Throwable, ? extends V> fn) {
            super(executor, completableFuture, completableFuture2);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<V> tryFire(final int n) {
            final CompletableFuture<V> dep;
            final CompletableFuture<T> src;
            if ((dep = this.dep) == null || !dep.uniHandle(src = this.src, this.fn, (n > 0) ? null : this)) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return dep.postFire(src, n);
        }
    }
    
    static final class UniRelay<T> extends UniCompletion<T, T>
    {
        UniRelay(final CompletableFuture<T> completableFuture, final CompletableFuture<T> completableFuture2) {
            super(null, completableFuture, completableFuture2);
        }
        
        @Override
        final CompletableFuture<T> tryFire(final int n) {
            final Object dep;
            final Object src;
            if ((dep = this.dep) == null || !((CompletableFuture<V>)dep).uniRelay((CompletableFuture<V>)(src = this.src))) {
                return null;
            }
            this.src = null;
            this.dep = null;
            return (CompletableFuture<T>)((CompletableFuture<V>)dep).postFire((CompletableFuture<?>)src, n);
        }
    }
    
    static final class UniRun<T> extends UniCompletion<T, Void>
    {
        Runnable fn;
        
        UniRun(final Executor executor, final CompletableFuture<Void> completableFuture, final CompletableFuture<T> completableFuture2, final Runnable fn) {
            super(executor, completableFuture, completableFuture2);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<Void> tryFire(final int n) {
            final Object dep;
            final Object src;
            if ((dep = this.dep) == null || !((CompletableFuture)dep).uniRun(src = this.src, this.fn, (UniRun<?>)((n > 0) ? null : this))) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return ((CompletableFuture<Void>)dep).postFire((CompletableFuture<?>)src, n);
        }
    }
    
    static final class UniWhenComplete<T> extends UniCompletion<T, T>
    {
        BiConsumer<? super T, ? super Throwable> fn;
        
        UniWhenComplete(final Executor executor, final CompletableFuture<T> completableFuture, final CompletableFuture<T> completableFuture2, final BiConsumer<? super T, ? super Throwable> fn) {
            super(executor, completableFuture, completableFuture2);
            this.fn = fn;
        }
        
        @Override
        final CompletableFuture<T> tryFire(final int n) {
            final Object dep;
            final Object src;
            if ((dep = this.dep) == null || !((CompletableFuture<V>)dep).uniWhenComplete((CompletableFuture<V>)(src = this.src), (BiConsumer<? super V, ? super Throwable>)this.fn, (UniWhenComplete<V>)((n > 0) ? null : this))) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return (CompletableFuture<T>)((CompletableFuture<V>)dep).postFire((CompletableFuture<?>)src, n);
        }
    }
}
