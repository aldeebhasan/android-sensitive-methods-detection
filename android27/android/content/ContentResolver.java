package android.content;

import android.net.*;
import java.io.*;
import android.content.res.*;
import android.os.*;
import android.database.*;
import java.util.*;
import android.accounts.*;

public abstract class ContentResolver
{
    public static final String ANY_CURSOR_ITEM_TYPE = "vnd.android.cursor.item/*";
    public static final String CURSOR_DIR_BASE_TYPE = "vnd.android.cursor.dir";
    public static final String CURSOR_ITEM_BASE_TYPE = "vnd.android.cursor.item";
    public static final String EXTRA_HONORED_ARGS = "android.content.extra.HONORED_ARGS";
    public static final String EXTRA_REFRESH_SUPPORTED = "android.content.extra.REFRESH_SUPPORTED";
    public static final String EXTRA_SIZE = "android.content.extra.SIZE";
    public static final String EXTRA_TOTAL_COUNT = "android.content.extra.TOTAL_COUNT";
    public static final int NOTIFY_SKIP_NOTIFY_FOR_DESCENDANTS = 2;
    public static final int NOTIFY_SYNC_TO_NETWORK = 1;
    public static final String QUERY_ARG_LIMIT = "android:query-arg-limit";
    public static final String QUERY_ARG_OFFSET = "android:query-arg-offset";
    public static final String QUERY_ARG_SORT_COLLATION = "android:query-arg-sort-collation";
    public static final String QUERY_ARG_SORT_COLUMNS = "android:query-arg-sort-columns";
    public static final String QUERY_ARG_SORT_DIRECTION = "android:query-arg-sort-direction";
    public static final String QUERY_ARG_SQL_SELECTION = "android:query-arg-sql-selection";
    public static final String QUERY_ARG_SQL_SELECTION_ARGS = "android:query-arg-sql-selection-args";
    public static final String QUERY_ARG_SQL_SORT_ORDER = "android:query-arg-sql-sort-order";
    public static final int QUERY_SORT_DIRECTION_ASCENDING = 0;
    public static final int QUERY_SORT_DIRECTION_DESCENDING = 1;
    public static final String SCHEME_ANDROID_RESOURCE = "android.resource";
    public static final String SCHEME_CONTENT = "content";
    public static final String SCHEME_FILE = "file";
    @Deprecated
    public static final String SYNC_EXTRAS_ACCOUNT = "account";
    public static final String SYNC_EXTRAS_DISCARD_LOCAL_DELETIONS = "discard_deletions";
    public static final String SYNC_EXTRAS_DO_NOT_RETRY = "do_not_retry";
    public static final String SYNC_EXTRAS_EXPEDITED = "expedited";
    @Deprecated
    public static final String SYNC_EXTRAS_FORCE = "force";
    public static final String SYNC_EXTRAS_IGNORE_BACKOFF = "ignore_backoff";
    public static final String SYNC_EXTRAS_IGNORE_SETTINGS = "ignore_settings";
    public static final String SYNC_EXTRAS_INITIALIZE = "initialize";
    public static final String SYNC_EXTRAS_MANUAL = "force";
    public static final String SYNC_EXTRAS_OVERRIDE_TOO_MANY_DELETIONS = "deletions_override";
    public static final String SYNC_EXTRAS_REQUIRE_CHARGING = "require_charging";
    public static final String SYNC_EXTRAS_UPLOAD = "upload";
    public static final int SYNC_OBSERVER_TYPE_ACTIVE = 4;
    public static final int SYNC_OBSERVER_TYPE_PENDING = 2;
    public static final int SYNC_OBSERVER_TYPE_SETTINGS = 1;
    
    public ContentResolver(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getType(final Uri url) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getStreamTypes(final Uri url, final String mimeTypeFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public final Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        throw new RuntimeException("Stub!");
    }
    
    public final Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public final Cursor query(final Uri uri, final String[] projection, final Bundle queryArgs, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public final Uri canonicalize(final Uri url) {
        throw new RuntimeException("Stub!");
    }
    
    public final Uri uncanonicalize(final Uri url) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean refresh(final Uri url, final Bundle args, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public final InputStream openInputStream(final Uri uri) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final OutputStream openOutputStream(final Uri uri) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final OutputStream openOutputStream(final Uri uri, final String mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final ParcelFileDescriptor openFileDescriptor(final Uri uri, final String mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final ParcelFileDescriptor openFileDescriptor(final Uri uri, final String mode, final CancellationSignal cancellationSignal) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openAssetFileDescriptor(final Uri uri, final String mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openAssetFileDescriptor(final Uri uri, final String mode, final CancellationSignal cancellationSignal) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openTypedAssetFileDescriptor(final Uri uri, final String mimeType, final Bundle opts) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openTypedAssetFileDescriptor(final Uri uri, final String mimeType, final Bundle opts, final CancellationSignal cancellationSignal) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final Uri insert(final Uri url, final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentProviderResult[] applyBatch(final String authority, final ArrayList<ContentProviderOperation> operations) throws RemoteException, OperationApplicationException {
        throw new RuntimeException("Stub!");
    }
    
    public final int bulkInsert(final Uri url, final ContentValues[] values) {
        throw new RuntimeException("Stub!");
    }
    
    public final int delete(final Uri url, final String where, final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public final int update(final Uri uri, final ContentValues values, final String where, final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public final Bundle call(final Uri uri, final String method, final String arg, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public final ContentProviderClient acquireContentProviderClient(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public final ContentProviderClient acquireContentProviderClient(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public final ContentProviderClient acquireUnstableContentProviderClient(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public final ContentProviderClient acquireUnstableContentProviderClient(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public final void registerContentObserver(final Uri uri, final boolean notifyForDescendants, final ContentObserver observer) {
        throw new RuntimeException("Stub!");
    }
    
    public final void unregisterContentObserver(final ContentObserver observer) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyChange(final Uri uri, final ContentObserver observer) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyChange(final Uri uri, final ContentObserver observer, final boolean syncToNetwork) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyChange(final Uri uri, final ContentObserver observer, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void takePersistableUriPermission(final Uri uri, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public void releasePersistableUriPermission(final Uri uri, final int modeFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public List<UriPermission> getPersistedUriPermissions() {
        throw new RuntimeException("Stub!");
    }
    
    public List<UriPermission> getOutgoingPersistedUriPermissions() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void startSync(final Uri uri, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public static void requestSync(final Account account, final String authority, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public static void requestSync(final SyncRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public static void validateSyncExtrasBundle(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void cancelSync(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static void cancelSync(final Account account, final String authority) {
        throw new RuntimeException("Stub!");
    }
    
    public static SyncAdapterType[] getSyncAdapterTypes() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean getSyncAutomatically(final Account account, final String authority) {
        throw new RuntimeException("Stub!");
    }
    
    public static void setSyncAutomatically(final Account account, final String authority, final boolean sync) {
        throw new RuntimeException("Stub!");
    }
    
    public static void addPeriodicSync(final Account account, final String authority, final Bundle extras, final long pollFrequency) {
        throw new RuntimeException("Stub!");
    }
    
    public static void removePeriodicSync(final Account account, final String authority, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public static void cancelSync(final SyncRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public static List<PeriodicSync> getPeriodicSyncs(final Account account, final String authority) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getIsSyncable(final Account account, final String authority) {
        throw new RuntimeException("Stub!");
    }
    
    public static void setIsSyncable(final Account account, final String authority, final int syncable) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean getMasterSyncAutomatically() {
        throw new RuntimeException("Stub!");
    }
    
    public static void setMasterSyncAutomatically(final boolean sync) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isSyncActive(final Account account, final String authority) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static SyncInfo getCurrentSync() {
        throw new RuntimeException("Stub!");
    }
    
    public static List<SyncInfo> getCurrentSyncs() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isSyncPending(final Account account, final String authority) {
        throw new RuntimeException("Stub!");
    }
    
    public static Object addStatusChangeListener(final int mask, final SyncStatusObserver callback) {
        throw new RuntimeException("Stub!");
    }
    
    public static void removeStatusChangeListener(final Object handle) {
        throw new RuntimeException("Stub!");
    }
}
