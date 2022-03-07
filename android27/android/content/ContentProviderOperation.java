package android.content;

import android.os.*;
import android.net.*;

public class ContentProviderOperation implements Parcelable
{
    public static final Creator<ContentProviderOperation> CREATOR;
    
    ContentProviderOperation() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static Builder newInsert(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static Builder newUpdate(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static Builder newDelete(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static Builder newAssertQuery(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getUri() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isYieldAllowed() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInsert() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDelete() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUpdate() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAssertQuery() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isWriteOperation() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isReadOperation() {
        throw new RuntimeException("Stub!");
    }
    
    public ContentProviderResult apply(final ContentProvider provider, final ContentProviderResult[] backRefs, final int numBackRefs) throws OperationApplicationException {
        throw new RuntimeException("Stub!");
    }
    
    public ContentValues resolveValueBackReferences(final ContentProviderResult[] backRefs, final int numBackRefs) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] resolveSelectionArgsBackReferences(final ContentProviderResult[] backRefs, final int numBackRefs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class Builder
    {
        Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public ContentProviderOperation build() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withValueBackReferences(final ContentValues backReferences) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withValueBackReference(final String key, final int previousResult) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withSelectionBackReference(final int selectionArgIndex, final int previousResult) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withValues(final ContentValues values) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withValue(final String key, final Object value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withSelection(final String selection, final String[] selectionArgs) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withExpectedCount(final int count) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder withYieldAllowed(final boolean yieldAllowed) {
            throw new RuntimeException("Stub!");
        }
    }
}
