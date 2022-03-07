package android.content;

import android.net.*;
import android.database.*;
import android.os.*;
import java.io.*;
import android.content.res.*;
import java.util.*;

public class ContentProviderClient implements AutoCloseable
{
    ContentProviderClient() {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final Uri url, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder, final CancellationSignal cancellationSignal) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final Uri uri, final String[] projection, final Bundle queryArgs, final CancellationSignal cancellationSignal) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public String getType(final Uri url) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getStreamTypes(final Uri url, final String mimeTypeFilter) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public final Uri canonicalize(final Uri url) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public final Uri uncanonicalize(final Uri url) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean refresh(final Uri url, final Bundle args, final CancellationSignal cancellationSignal) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public Uri insert(final Uri url, final ContentValues initialValues) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public int bulkInsert(final Uri url, final ContentValues[] initialValues) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public int delete(final Uri url, final String selection, final String[] selectionArgs) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public int update(final Uri url, final ContentValues values, final String selection, final String[] selectionArgs) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelFileDescriptor openFile(final Uri url, final String mode) throws RemoteException, FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelFileDescriptor openFile(final Uri url, final String mode, final CancellationSignal signal) throws RemoteException, FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public AssetFileDescriptor openAssetFile(final Uri url, final String mode) throws RemoteException, FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public AssetFileDescriptor openAssetFile(final Uri url, final String mode, final CancellationSignal signal) throws RemoteException, FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openTypedAssetFileDescriptor(final Uri uri, final String mimeType, final Bundle opts) throws RemoteException, FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openTypedAssetFileDescriptor(final Uri uri, final String mimeType, final Bundle opts, final CancellationSignal signal) throws RemoteException, FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public ContentProviderResult[] applyBatch(final ArrayList<ContentProviderOperation> operations) throws RemoteException, OperationApplicationException {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle call(final String method, final String arg, final Bundle extras) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean release() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public ContentProvider getLocalContentProvider() {
        throw new RuntimeException("Stub!");
    }
}
