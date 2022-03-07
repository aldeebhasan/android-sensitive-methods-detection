package android.app;

import android.graphics.*;
import android.os.*;

public static class TaskDescription implements Parcelable
{
    public static final Creator<TaskDescription> CREATOR;
    
    public TaskDescription(final String label, final Bitmap icon, final int colorPrimary) {
        throw new RuntimeException("Stub!");
    }
    
    public TaskDescription(final String label, final Bitmap icon) {
        throw new RuntimeException("Stub!");
    }
    
    public TaskDescription(final String label) {
        throw new RuntimeException("Stub!");
    }
    
    public TaskDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public TaskDescription(final TaskDescription td) {
        throw new RuntimeException("Stub!");
    }
    
    public String getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPrimaryColor() {
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
    
    public void readFromParcel(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
