package android.print;

import android.os.*;
import android.app.*;

public final class PrinterInfo implements Parcelable
{
    public static final Creator<PrinterInfo> CREATOR;
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_IDLE = 1;
    public static final int STATUS_UNAVAILABLE = 3;
    
    PrinterInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public PrinterId getId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getStatus() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public PrinterCapabilitiesInfo getCapabilities() {
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
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
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
        public Builder(final PrinterId printerId, final String name, final int status) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final PrinterInfo other) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setStatus(final int status) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIconResourceId(final int iconResourceId) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setHasCustomPrinterIcon(final boolean hasCustomPrinterIcon) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setName(final String name) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDescription(final String description) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setInfoIntent(final PendingIntent infoIntent) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setCapabilities(final PrinterCapabilitiesInfo capabilities) {
            throw new RuntimeException("Stub!");
        }
        
        public PrinterInfo build() {
            throw new RuntimeException("Stub!");
        }
    }
}
