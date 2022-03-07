package android.app.admin;

import android.content.*;
import android.os.*;
import android.net.*;

public class DeviceAdminReceiver extends BroadcastReceiver
{
    public static final String ACTION_DEVICE_ADMIN_DISABLED = "android.app.action.DEVICE_ADMIN_DISABLED";
    public static final String ACTION_DEVICE_ADMIN_DISABLE_REQUESTED = "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED";
    public static final String ACTION_DEVICE_ADMIN_ENABLED = "android.app.action.DEVICE_ADMIN_ENABLED";
    public static final String ACTION_LOCK_TASK_ENTERING = "android.app.action.LOCK_TASK_ENTERING";
    public static final String ACTION_LOCK_TASK_EXITING = "android.app.action.LOCK_TASK_EXITING";
    public static final String ACTION_PASSWORD_CHANGED = "android.app.action.ACTION_PASSWORD_CHANGED";
    public static final String ACTION_PASSWORD_EXPIRING = "android.app.action.ACTION_PASSWORD_EXPIRING";
    public static final String ACTION_PASSWORD_FAILED = "android.app.action.ACTION_PASSWORD_FAILED";
    public static final String ACTION_PASSWORD_SUCCEEDED = "android.app.action.ACTION_PASSWORD_SUCCEEDED";
    public static final String ACTION_PROFILE_PROVISIONING_COMPLETE = "android.app.action.PROFILE_PROVISIONING_COMPLETE";
    public static final int BUGREPORT_FAILURE_FAILED_COMPLETING = 0;
    public static final int BUGREPORT_FAILURE_FILE_NO_LONGER_AVAILABLE = 1;
    public static final String DEVICE_ADMIN_META_DATA = "android.app.device_admin";
    public static final String EXTRA_DISABLE_WARNING = "android.app.extra.DISABLE_WARNING";
    public static final String EXTRA_LOCK_TASK_PACKAGE = "android.app.extra.LOCK_TASK_PACKAGE";
    
    public DeviceAdminReceiver() {
        throw new RuntimeException("Stub!");
    }
    
    public DevicePolicyManager getManager(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getWho(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void onEnabled(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence onDisableRequested(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDisabled(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onPasswordChanged(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPasswordChanged(final Context context, final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onPasswordFailed(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPasswordFailed(final Context context, final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onPasswordSucceeded(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPasswordSucceeded(final Context context, final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onPasswordExpiring(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPasswordExpiring(final Context context, final Intent intent, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public void onProfileProvisioningComplete(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onReadyForUserInitialization(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLockTaskModeEntering(final Context context, final Intent intent, final String pkg) {
        throw new RuntimeException("Stub!");
    }
    
    public void onLockTaskModeExiting(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public String onChoosePrivateKeyAlias(final Context context, final Intent intent, final int uid, final Uri uri, final String alias) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSystemUpdatePending(final Context context, final Intent intent, final long receivedTime) {
        throw new RuntimeException("Stub!");
    }
    
    public void onBugreportSharingDeclined(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onBugreportShared(final Context context, final Intent intent, final String bugreportHash) {
        throw new RuntimeException("Stub!");
    }
    
    public void onBugreportFailed(final Context context, final Intent intent, final int failureCode) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSecurityLogsAvailable(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onNetworkLogsAvailable(final Context context, final Intent intent, final long batchToken, final int networkLogsCount) {
        throw new RuntimeException("Stub!");
    }
    
    public void onUserAdded(final Context context, final Intent intent, final UserHandle newUser) {
        throw new RuntimeException("Stub!");
    }
    
    public void onUserRemoved(final Context context, final Intent intent, final UserHandle removedUser) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onReceive(final Context context, final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
