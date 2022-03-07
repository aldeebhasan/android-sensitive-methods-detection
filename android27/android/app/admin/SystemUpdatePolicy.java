package android.app.admin;

import android.os.*;

public class SystemUpdatePolicy implements Parcelable
{
    public static final Creator<SystemUpdatePolicy> CREATOR;
    public static final int TYPE_INSTALL_AUTOMATIC = 1;
    public static final int TYPE_INSTALL_WINDOWED = 2;
    public static final int TYPE_POSTPONE = 3;
    
    SystemUpdatePolicy() {
        throw new RuntimeException("Stub!");
    }
    
    public static SystemUpdatePolicy createAutomaticInstallPolicy() {
        throw new RuntimeException("Stub!");
    }
    
    public static SystemUpdatePolicy createWindowedInstallPolicy(final int startTime, final int endTime) {
        throw new RuntimeException("Stub!");
    }
    
    public static SystemUpdatePolicy createPostponeInstallPolicy() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPolicyType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInstallWindowStart() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInstallWindowEnd() {
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
}
