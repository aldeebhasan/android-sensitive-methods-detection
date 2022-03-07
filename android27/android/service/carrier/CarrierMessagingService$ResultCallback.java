package android.service.carrier;

import android.os.*;

public interface ResultCallback<T>
{
    void onReceiveResult(final T p0) throws RemoteException;
}
