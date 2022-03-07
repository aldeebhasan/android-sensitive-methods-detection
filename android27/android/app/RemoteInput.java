package android.app;

import android.content.*;
import java.util.*;
import android.net.*;
import android.os.*;

public final class RemoteInput implements Parcelable
{
    public static final Creator<RemoteInput> CREATOR;
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    
    RemoteInput() {
        throw new RuntimeException("Stub!");
    }
    
    public String getResultKey() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence[] getChoices() {
        throw new RuntimeException("Stub!");
    }
    
    public Set<String> getAllowedDataTypes() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDataOnly() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAllowFreeFormInput() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public static Map<String, Uri> getDataResultsFromIntent(final Intent intent, final String remoteInputResultKey) {
        throw new RuntimeException("Stub!");
    }
    
    public static Bundle getResultsFromIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static void addResultsToIntent(final RemoteInput[] remoteInputs, final Intent intent, final Bundle results) {
        throw new RuntimeException("Stub!");
    }
    
    public static void addDataResultToIntent(final RemoteInput remoteInput, final Intent intent, final Map<String, Uri> results) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static final class Builder
    {
        public Builder(final String resultKey) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setLabel(final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setChoices(final CharSequence[] choices) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAllowDataType(final String mimeType, final boolean doAllow) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAllowFreeFormInput(final boolean allowFreeFormTextInput) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addExtras(final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public Bundle getExtras() {
            throw new RuntimeException("Stub!");
        }
        
        public RemoteInput build() {
            throw new RuntimeException("Stub!");
        }
    }
}
