package android.preference;

import android.content.*;
import android.util.*;
import android.graphics.drawable.*;
import android.view.*;
import android.app.*;
import android.os.*;

public abstract class DialogPreference extends Preference implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener, PreferenceManager.OnActivityDestroyListener
{
    public DialogPreference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public DialogPreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public DialogPreference(final Context context, final AttributeSet attrs) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public DialogPreference(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void setDialogTitle(final CharSequence dialogTitle) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDialogTitle(final int dialogTitleResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDialogTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDialogMessage(final CharSequence dialogMessage) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDialogMessage(final int dialogMessageResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDialogMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDialogIcon(final Drawable dialogIcon) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDialogIcon(final int dialogIconRes) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getDialogIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPositiveButtonText(final CharSequence positiveButtonText) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPositiveButtonText(final int positiveButtonTextResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getPositiveButtonText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setNegativeButtonText(final CharSequence negativeButtonText) {
        throw new RuntimeException("Stub!");
    }
    
    public void setNegativeButtonText(final int negativeButtonTextResId) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getNegativeButtonText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDialogLayoutResource(final int dialogLayoutResId) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDialogLayoutResource() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onPrepareDialogBuilder(final AlertDialog.Builder builder) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onClick() {
        throw new RuntimeException("Stub!");
    }
    
    protected void showDialog(final Bundle state) {
        throw new RuntimeException("Stub!");
    }
    
    protected View onCreateDialogView() {
        throw new RuntimeException("Stub!");
    }
    
    protected void onBindDialogView(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDismiss(final DialogInterface dialog) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onDialogClosed(final boolean positiveResult) {
        throw new RuntimeException("Stub!");
    }
    
    public Dialog getDialog() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onActivityDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        throw new RuntimeException("Stub!");
    }
}
