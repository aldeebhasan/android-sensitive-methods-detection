package android.provider;

import android.net.*;
import android.accounts.*;
import android.os.*;
import android.util.*;
import android.content.*;

public static final class ProfileSyncState implements SyncStateContract.Columns
{
    public static final String CONTENT_DIRECTORY = "syncstate";
    public static final Uri CONTENT_URI;
    
    ProfileSyncState() {
        throw new RuntimeException("Stub!");
    }
    
    public static byte[] get(final ContentProviderClient provider, final Account account) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public static Pair<Uri, byte[]> getWithUri(final ContentProviderClient provider, final Account account) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public static void set(final ContentProviderClient provider, final Account account, final byte[] data) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public static ContentProviderOperation newSetOperation(final Account account, final byte[] data) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
