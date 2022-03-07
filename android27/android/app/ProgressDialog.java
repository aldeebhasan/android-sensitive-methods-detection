package android.app;

import android.content.*;
import android.os.*;
import android.graphics.drawable.*;
import java.text.*;

@Deprecated
public class ProgressDialog extends AlertDialog
{
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    
    public ProgressDialog(final Context context) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public ProgressDialog(final Context context, final int theme) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public static ProgressDialog show(final Context context, final CharSequence title, final CharSequence message) {
        throw new RuntimeException("Stub!");
    }
    
    public static ProgressDialog show(final Context context, final CharSequence title, final CharSequence message, final boolean indeterminate) {
        throw new RuntimeException("Stub!");
    }
    
    public static ProgressDialog show(final Context context, final CharSequence title, final CharSequence message, final boolean indeterminate, final boolean cancelable) {
        throw new RuntimeException("Stub!");
    }
    
    public static ProgressDialog show(final Context context, final CharSequence title, final CharSequence message, final boolean indeterminate, final boolean cancelable, final DialogInterface.OnCancelListener cancelListener) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onStop() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgress(final int value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSecondaryProgress(final int secondaryProgress) {
        throw new RuntimeException("Stub!");
    }
    
    public int getProgress() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSecondaryProgress() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMax() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMax(final int max) {
        throw new RuntimeException("Stub!");
    }
    
    public void incrementProgressBy(final int diff) {
        throw new RuntimeException("Stub!");
    }
    
    public void incrementSecondaryProgressBy(final int diff) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressDrawable(final Drawable d) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIndeterminateDrawable(final Drawable d) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIndeterminate(final boolean indeterminate) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isIndeterminate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setMessage(final CharSequence message) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressStyle(final int style) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressNumberFormat(final String format) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProgressPercentFormat(final NumberFormat format) {
        throw new RuntimeException("Stub!");
    }
}
