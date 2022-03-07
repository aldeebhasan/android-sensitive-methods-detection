package android.print;

import java.util.*;
import android.os.*;

public final class PrinterCapabilitiesInfo implements Parcelable
{
    public static final Creator<PrinterCapabilitiesInfo> CREATOR;
    
    PrinterCapabilitiesInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public List<PrintAttributes.MediaSize> getMediaSizes() {
        throw new RuntimeException("Stub!");
    }
    
    public List<PrintAttributes.Resolution> getResolutions() {
        throw new RuntimeException("Stub!");
    }
    
    public PrintAttributes.Margins getMinMargins() {
        throw new RuntimeException("Stub!");
    }
    
    public int getColorModes() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDuplexModes() {
        throw new RuntimeException("Stub!");
    }
    
    public PrintAttributes getDefaults() {
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
        public Builder(final PrinterId printerId) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addMediaSize(final PrintAttributes.MediaSize mediaSize, final boolean isDefault) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addResolution(final PrintAttributes.Resolution resolution, final boolean isDefault) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setMinMargins(final PrintAttributes.Margins margins) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setColorModes(final int colorModes, final int defaultColorMode) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDuplexModes(final int duplexModes, final int defaultDuplexMode) {
            throw new RuntimeException("Stub!");
        }
        
        public PrinterCapabilitiesInfo build() {
            throw new RuntimeException("Stub!");
        }
    }
}
