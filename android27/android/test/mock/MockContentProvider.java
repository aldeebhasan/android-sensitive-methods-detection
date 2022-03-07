package android.test.mock;

import android.net.*;
import android.database.*;
import android.content.pm.*;
import java.util.*;
import android.content.*;
import android.os.*;
import android.content.res.*;

public class MockContentProvider extends ContentProvider
{
    protected MockContentProvider() {
        throw new RuntimeException("Stub!");
    }
    
    public MockContentProvider(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public MockContentProvider(final Context context, final String readPermission, final String writePermission, final PathPermission[] pathPermissions) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getType(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int bulkInsert(final Uri uri, final ContentValues[] values) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void attachInfo(final Context context, final ProviderInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ContentProviderResult[] applyBatch(final ArrayList<ContentProviderOperation> operations) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] getStreamTypes(final Uri url, final String mimeTypeFilter) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AssetFileDescriptor openTypedAssetFile(final Uri url, final String mimeType, final Bundle opts) {
        throw new RuntimeException("Stub!");
    }
}
