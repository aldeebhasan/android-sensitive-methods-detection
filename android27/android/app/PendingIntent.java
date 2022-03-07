package android.app;

import android.content.*;
import android.os.*;
import android.util.*;

public final class PendingIntent implements Parcelable
{
    public static final Creator<PendingIntent> CREATOR;
    public static final int FLAG_CANCEL_CURRENT = 268435456;
    public static final int FLAG_IMMUTABLE = 67108864;
    public static final int FLAG_NO_CREATE = 536870912;
    public static final int FLAG_ONE_SHOT = 1073741824;
    public static final int FLAG_UPDATE_CURRENT = 134217728;
    
    PendingIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent getActivity(final Context context, final int requestCode, final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent getActivity(final Context context, final int requestCode, final Intent intent, final int flags, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent getActivities(final Context context, final int requestCode, final Intent[] intents, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent getActivities(final Context context, final int requestCode, final Intent[] intents, final int flags, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent getBroadcast(final Context context, final int requestCode, final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent getService(final Context context, final int requestCode, final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent getForegroundService(final Context context, final int requestCode, final Intent intent, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public IntentSender getIntentSender() {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    
    public void send() throws CanceledException {
        throw new RuntimeException("Stub!");
    }
    
    public void send(final int code) throws CanceledException {
        throw new RuntimeException("Stub!");
    }
    
    public void send(final Context context, final int code, final Intent intent) throws CanceledException {
        throw new RuntimeException("Stub!");
    }
    
    public void send(final int code, final OnFinished onFinished, final Handler handler) throws CanceledException {
        throw new RuntimeException("Stub!");
    }
    
    public void send(final Context context, final int code, final Intent intent, final OnFinished onFinished, final Handler handler) throws CanceledException {
        throw new RuntimeException("Stub!");
    }
    
    public void send(final Context context, final int code, final Intent intent, final OnFinished onFinished, final Handler handler, final String requiredPermission) throws CanceledException {
        throw new RuntimeException("Stub!");
    }
    
    public void send(final Context context, final int code, final Intent intent, final OnFinished onFinished, final Handler handler, final String requiredPermission, final Bundle options) throws CanceledException {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getTargetPackage() {
        throw new RuntimeException("Stub!");
    }
    
    public String getCreatorPackage() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCreatorUid() {
        throw new RuntimeException("Stub!");
    }
    
    public UserHandle getCreatorUserHandle() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object otherObj) {
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
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static void writePendingIntentOrNullToParcel(final PendingIntent sender, final Parcel out) {
        throw new RuntimeException("Stub!");
    }
    
    public static PendingIntent readPendingIntentOrNullFromParcel(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class CanceledException extends AndroidException
    {
        public CanceledException() {
            throw new RuntimeException("Stub!");
        }
        
        public CanceledException(final String name) {
            throw new RuntimeException("Stub!");
        }
        
        public CanceledException(final Exception cause) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnFinished
    {
        void onSendFinished(final PendingIntent p0, final Intent p1, final int p2, final String p3, final Bundle p4);
    }
}
