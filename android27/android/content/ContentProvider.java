package android.content;

import android.net.*;
import android.database.*;
import android.os.*;
import android.content.res.*;
import android.content.pm.*;
import java.util.*;
import java.io.*;

public abstract class ContentProvider implements ComponentCallbacks2
{
    public ContentProvider() {
        throw new RuntimeException("Stub!");
    }
    
    public final Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public final String getCallingPackage() {
        throw new RuntimeException("Stub!");
    }
    
    protected final void setReadPermission(final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getReadPermission() {
        throw new RuntimeException("Stub!");
    }
    
    protected final void setWritePermission(final String permission) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getWritePermission() {
        throw new RuntimeException("Stub!");
    }
    
    protected final void setPathPermissions(final PathPermission[] permissions) {
        throw new RuntimeException("Stub!");
    }
    
    public final PathPermission[] getPathPermissions() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean onCreate();
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onLowMemory() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTrimMemory(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Cursor query(final Uri p0, final String[] p1, final String p2, final String[] p3, final String p4);
    
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final Uri uri, final String[] projection, final Bundle queryArgs, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract String getType(final Uri p0);
    
    public Uri canonicalize(final Uri url) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri uncanonicalize(final Uri url) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean refresh(final Uri uri, final Bundle args, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Uri insert(final Uri p0, final ContentValues p1);
    
    public int bulkInsert(final Uri uri, final ContentValues[] values) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int delete(final Uri p0, final String p1, final String[] p2);
    
    public abstract int update(final Uri p0, final ContentValues p1, final String p2, final String[] p3);
    
    public ParcelFileDescriptor openFile(final Uri uri, final String mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelFileDescriptor openFile(final Uri uri, final String mode, final CancellationSignal signal) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public AssetFileDescriptor openAssetFile(final Uri uri, final String mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public AssetFileDescriptor openAssetFile(final Uri uri, final String mode, final CancellationSignal signal) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    protected final ParcelFileDescriptor openFileHelper(final Uri uri, final String mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getStreamTypes(final Uri uri, final String mimeTypeFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public AssetFileDescriptor openTypedAssetFile(final Uri uri, final String mimeTypeFilter, final Bundle opts) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public AssetFileDescriptor openTypedAssetFile(final Uri uri, final String mimeTypeFilter, final Bundle opts, final CancellationSignal signal) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public <T> ParcelFileDescriptor openPipeHelper(final Uri uri, final String mimeType, final Bundle opts, final T args, final PipeDataWriter<T> func) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean isTemporary() {
        throw new RuntimeException("Stub!");
    }
    
    public void attachInfo(final Context context, final ProviderInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentProviderResult[] applyBatch(final ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle call(final String method, final String arg, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void shutdown() {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    public interface PipeDataWriter<T>
    {
        void writeDataToPipe(final ParcelFileDescriptor p0, final Uri p1, final String p2, final Bundle p3, final T p4);
    }
}
