package android.print;

import android.os.*;

public final class PrintJobInfo implements Parcelable
{
    public static final Creator<PrintJobInfo> CREATOR;
    public static final int STATE_BLOCKED = 4;
    public static final int STATE_CANCELED = 7;
    public static final int STATE_COMPLETED = 5;
    public static final int STATE_CREATED = 1;
    public static final int STATE_FAILED = 6;
    public static final int STATE_QUEUED = 2;
    public static final int STATE_STARTED = 3;
    
    PrintJobInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public PrintJobId getId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public PrinterId getPrinterId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getState() {
        throw new RuntimeException("Stub!");
    }
    
    public long getCreationTime() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCopies() {
        throw new RuntimeException("Stub!");
    }
    
    public PageRange[] getPages() {
        throw new RuntimeException("Stub!");
    }
    
    public PrintAttributes getAttributes() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasAdvancedOption(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public String getAdvancedStringOption(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public int getAdvancedIntOption(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static final class Builder
    {
        public Builder(final PrintJobInfo prototype) {
            throw new RuntimeException("Stub!");
        }
        
        public void setCopies(final int copies) {
            throw new RuntimeException("Stub!");
        }
        
        public void setAttributes(final PrintAttributes attributes) {
            throw new RuntimeException("Stub!");
        }
        
        public void setPages(final PageRange[] pages) {
            throw new RuntimeException("Stub!");
        }
        
        public void putAdvancedOption(final String key, final String value) {
            throw new RuntimeException("Stub!");
        }
        
        public void putAdvancedOption(final String key, final int value) {
            throw new RuntimeException("Stub!");
        }
        
        public PrintJobInfo build() {
            throw new RuntimeException("Stub!");
        }
    }
}
