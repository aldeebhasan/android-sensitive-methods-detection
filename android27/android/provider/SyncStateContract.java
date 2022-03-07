package android.provider;

import android.net.*;
import android.accounts.*;
import android.os.*;
import android.util.*;
import android.content.*;

public class SyncStateContract
{
    public SyncStateContract() {
        throw new RuntimeException("Stub!");
    }
    
    public static class Constants implements Columns
    {
        public static final String CONTENT_DIRECTORY = "syncstate";
        
        public Constants() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Helpers
    {
        public Helpers() {
            throw new RuntimeException("Stub!");
        }
        
        public static byte[] get(final ContentProviderClient provider, final Uri uri, final Account account) throws RemoteException {
            throw new RuntimeException("Stub!");
        }
        
        public static void set(final ContentProviderClient provider, final Uri uri, final Account account, final byte[] data) throws RemoteException {
            throw new RuntimeException("Stub!");
        }
        
        public static Uri insert(final ContentProviderClient provider, final Uri uri, final Account account, final byte[] data) throws RemoteException {
            throw new RuntimeException("Stub!");
        }
        
        public static void update(final ContentProviderClient provider, final Uri uri, final byte[] data) throws RemoteException {
            throw new RuntimeException("Stub!");
        }
        
        public static Pair<Uri, byte[]> getWithUri(final ContentProviderClient provider, final Uri uri, final Account account) throws RemoteException {
            throw new RuntimeException("Stub!");
        }
        
        public static ContentProviderOperation newSetOperation(final Uri uri, final Account account, final byte[] data) {
            throw new RuntimeException("Stub!");
        }
        
        public static ContentProviderOperation newUpdateOperation(final Uri uri, final byte[] data) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface Columns extends BaseColumns
    {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DATA = "data";
    }
}
