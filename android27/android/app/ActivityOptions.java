package android.app;

import android.content.*;
import android.view.*;
import android.util.*;
import android.graphics.*;
import android.os.*;

public class ActivityOptions
{
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";
    
    ActivityOptions() {
        throw new RuntimeException("Stub!");
    }
    
    public static ActivityOptions makeCustomAnimation(final Context context, final int enterResId, final int exitResId) {
        throw new RuntimeException("Stub!");
    }
    
    public static ActivityOptions makeScaleUpAnimation(final View source, final int startX, final int startY, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public static ActivityOptions makeClipRevealAnimation(final View source, final int startX, final int startY, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public static ActivityOptions makeThumbnailScaleUpAnimation(final View source, final Bitmap thumbnail, final int startX, final int startY) {
        throw new RuntimeException("Stub!");
    }
    
    public static ActivityOptions makeSceneTransitionAnimation(final Activity activity, final View sharedElement, final String sharedElementName) {
        throw new RuntimeException("Stub!");
    }
    
    @SafeVarargs
    public static ActivityOptions makeSceneTransitionAnimation(final Activity activity, final Pair<View, String>... sharedElements) {
        throw new RuntimeException("Stub!");
    }
    
    public static ActivityOptions makeTaskLaunchBehind() {
        throw new RuntimeException("Stub!");
    }
    
    public static ActivityOptions makeBasic() {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityOptions setLaunchBounds(final Rect screenSpacePixelRect) {
        throw new RuntimeException("Stub!");
    }
    
    public Rect getLaunchBounds() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLaunchDisplayId() {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityOptions setLaunchDisplayId(final int launchDisplayId) {
        throw new RuntimeException("Stub!");
    }
    
    public void update(final ActivityOptions otherOptions) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle toBundle() {
        throw new RuntimeException("Stub!");
    }
    
    public void requestUsageTimeReport(final PendingIntent receiver) {
        throw new RuntimeException("Stub!");
    }
    
    public ActivityOptions setAppVerificationBundle(final Bundle bundle) {
        throw new RuntimeException("Stub!");
    }
}
