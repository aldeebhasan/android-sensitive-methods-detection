package android.provider;

import android.net.*;
import android.graphics.*;
import java.io.*;
import android.content.*;
import java.util.*;
import android.os.*;

public final class DocumentsContract
{
    public static final String ACTION_DOCUMENT_SETTINGS = "android.provider.action.DOCUMENT_SETTINGS";
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_EXCLUDE_SELF = "android.provider.extra.EXCLUDE_SELF";
    public static final String EXTRA_INFO = "info";
    public static final String EXTRA_INITIAL_URI = "android.provider.extra.INITIAL_URI";
    public static final String EXTRA_LOADING = "loading";
    public static final String EXTRA_ORIENTATION = "android.provider.extra.ORIENTATION";
    public static final String EXTRA_PROMPT = "android.provider.extra.PROMPT";
    public static final String PROVIDER_INTERFACE = "android.content.action.DOCUMENTS_PROVIDER";
    
    DocumentsContract() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildRootsUri(final String authority) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildRootUri(final String authority, final String rootId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildRecentDocumentsUri(final String authority, final String rootId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildTreeDocumentUri(final String authority, final String documentId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildDocumentUri(final String authority, final String documentId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildDocumentUriUsingTree(final Uri treeUri, final String documentId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildChildDocumentsUri(final String authority, final String parentDocumentId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildChildDocumentsUriUsingTree(final Uri treeUri, final String parentDocumentId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildSearchDocumentsUri(final String authority, final String rootId, final String query) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isDocumentUri(final Context context, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isTreeUri(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getRootId(final Uri rootUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getDocumentId(final Uri documentUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getTreeDocumentId(final Uri documentUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getSearchDocumentsQuery(final Uri searchDocumentsUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bitmap getDocumentThumbnail(final ContentResolver resolver, final Uri documentUri, final Point size, final CancellationSignal signal) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri createDocument(final ContentResolver resolver, final Uri parentDocumentUri, final String mimeType, final String displayName) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri renameDocument(final ContentResolver resolver, final Uri documentUri, final String displayName) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean deleteDocument(final ContentResolver resolver, final Uri documentUri) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri copyDocument(final ContentResolver resolver, final Uri sourceDocumentUri, final Uri targetParentDocumentUri) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri moveDocument(final ContentResolver resolver, final Uri sourceDocumentUri, final Uri sourceParentDocumentUri, final Uri targetParentDocumentUri) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean removeDocument(final ContentResolver resolver, final Uri documentUri, final Uri parentDocumentUri) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static void ejectRoot(final ContentResolver resolver, final Uri rootUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static Path findDocumentPath(final ContentResolver resolver, final Uri treeUri) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static IntentSender createWebLinkIntent(final ContentResolver resolver, final Uri uri, final Bundle options) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Document
    {
        public static final String COLUMN_DISPLAY_NAME = "_display_name";
        public static final String COLUMN_DOCUMENT_ID = "document_id";
        public static final String COLUMN_FLAGS = "flags";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_LAST_MODIFIED = "last_modified";
        public static final String COLUMN_MIME_TYPE = "mime_type";
        public static final String COLUMN_SIZE = "_size";
        public static final String COLUMN_SUMMARY = "summary";
        public static final int FLAG_DIR_PREFERS_GRID = 16;
        public static final int FLAG_DIR_PREFERS_LAST_MODIFIED = 32;
        public static final int FLAG_DIR_SUPPORTS_CREATE = 8;
        public static final int FLAG_SUPPORTS_COPY = 128;
        public static final int FLAG_SUPPORTS_DELETE = 4;
        public static final int FLAG_SUPPORTS_MOVE = 256;
        public static final int FLAG_SUPPORTS_REMOVE = 1024;
        public static final int FLAG_SUPPORTS_RENAME = 64;
        public static final int FLAG_SUPPORTS_SETTINGS = 2048;
        public static final int FLAG_SUPPORTS_THUMBNAIL = 1;
        public static final int FLAG_SUPPORTS_WRITE = 2;
        public static final int FLAG_VIRTUAL_DOCUMENT = 512;
        public static final int FLAG_WEB_LINKABLE = 4096;
        public static final String MIME_TYPE_DIR = "vnd.android.document/directory";
        
        Document() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Root
    {
        public static final String COLUMN_AVAILABLE_BYTES = "available_bytes";
        public static final String COLUMN_CAPACITY_BYTES = "capacity_bytes";
        public static final String COLUMN_DOCUMENT_ID = "document_id";
        public static final String COLUMN_FLAGS = "flags";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_MIME_TYPES = "mime_types";
        public static final String COLUMN_ROOT_ID = "root_id";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_TITLE = "title";
        public static final int FLAG_LOCAL_ONLY = 2;
        public static final int FLAG_SUPPORTS_CREATE = 1;
        public static final int FLAG_SUPPORTS_EJECT = 32;
        public static final int FLAG_SUPPORTS_IS_CHILD = 16;
        public static final int FLAG_SUPPORTS_RECENTS = 4;
        public static final int FLAG_SUPPORTS_SEARCH = 8;
        public static final String MIME_TYPE_ITEM = "vnd.android.document/root";
        
        Root() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Path implements Parcelable
    {
        public static final Creator<Path> CREATOR;
        
        public Path(final String rootId, final List<String> path) {
            throw new RuntimeException("Stub!");
        }
        
        public String getRootId() {
            throw new RuntimeException("Stub!");
        }
        
        public List<String> getPath() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object o) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
}
