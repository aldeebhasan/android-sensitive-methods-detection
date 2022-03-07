package android.accounts;

import java.io.*;
import java.util.concurrent.*;

public interface AccountManagerFuture<V>
{
    boolean cancel(final boolean p0);
    
    boolean isCancelled();
    
    boolean isDone();
    
    V getResult() throws OperationCanceledException, IOException, AuthenticatorException;
    
    V getResult(final long p0, final TimeUnit p1) throws OperationCanceledException, IOException, AuthenticatorException;
}
