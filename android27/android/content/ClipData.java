package android.content;

import android.net.*;
import android.os.*;

public class ClipData implements Parcelable
{
    public static final Creator<ClipData> CREATOR;
    
    public ClipData(final CharSequence label, final String[] mimeTypes, final Item item) {
        throw new RuntimeException("Stub!");
    }
    
    public ClipData(final ClipDescription description, final Item item) {
        throw new RuntimeException("Stub!");
    }
    
    public ClipData(final ClipData other) {
        throw new RuntimeException("Stub!");
    }
    
    public static ClipData newPlainText(final CharSequence label, final CharSequence text) {
        throw new RuntimeException("Stub!");
    }
    
    public static ClipData newHtmlText(final CharSequence label, final CharSequence text, final String htmlText) {
        throw new RuntimeException("Stub!");
    }
    
    public static ClipData newIntent(final CharSequence label, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static ClipData newUri(final ContentResolver resolver, final CharSequence label, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static ClipData newRawUri(final CharSequence label, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public ClipDescription getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public void addItem(final Item item) {
        throw new RuntimeException("Stub!");
    }
    
    public void addItem(final ContentResolver resolver, final Item item) {
        throw new RuntimeException("Stub!");
    }
    
    public int getItemCount() {
        throw new RuntimeException("Stub!");
    }
    
    public Item getItemAt(final int index) {
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
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class Item
    {
        public Item(final CharSequence text) {
            throw new RuntimeException("Stub!");
        }
        
        public Item(final CharSequence text, final String htmlText) {
            throw new RuntimeException("Stub!");
        }
        
        public Item(final Intent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public Item(final Uri uri) {
            throw new RuntimeException("Stub!");
        }
        
        public Item(final CharSequence text, final Intent intent, final Uri uri) {
            throw new RuntimeException("Stub!");
        }
        
        public Item(final CharSequence text, final String htmlText, final Intent intent, final Uri uri) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getText() {
            throw new RuntimeException("Stub!");
        }
        
        public String getHtmlText() {
            throw new RuntimeException("Stub!");
        }
        
        public Intent getIntent() {
            throw new RuntimeException("Stub!");
        }
        
        public Uri getUri() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence coerceToText(final Context context) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence coerceToStyledText(final Context context) {
            throw new RuntimeException("Stub!");
        }
        
        public String coerceToHtmlText(final Context context) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
}
