package android.app;

import android.view.*;
import android.graphics.drawable.*;
import android.content.*;
import android.database.*;
import android.widget.*;

public static class Builder
{
    public Builder(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder(final Context context, final int themeResId) {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setTitle(final int titleId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCustomTitle(final View customTitleView) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setMessage(final int messageId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setMessage(final CharSequence message) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setIcon(final int iconId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setIcon(final Drawable icon) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setIconAttribute(final int attrId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setPositiveButton(final int textId, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setPositiveButton(final CharSequence text, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setNegativeButton(final int textId, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setNegativeButton(final CharSequence text, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setNeutralButton(final int textId, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setNeutralButton(final CharSequence text, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCancelable(final boolean cancelable) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setOnCancelListener(final OnCancelListener onCancelListener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setOnDismissListener(final OnDismissListener onDismissListener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setOnKeyListener(final DialogInterface.OnKeyListener onKeyListener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setItems(final int itemsId, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setItems(final CharSequence[] items, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setAdapter(final ListAdapter adapter, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCursor(final Cursor cursor, final DialogInterface.OnClickListener listener, final String labelColumn) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setMultiChoiceItems(final int itemsId, final boolean[] checkedItems, final OnMultiChoiceClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setMultiChoiceItems(final CharSequence[] items, final boolean[] checkedItems, final OnMultiChoiceClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setMultiChoiceItems(final Cursor cursor, final String isCheckedColumn, final String labelColumn, final OnMultiChoiceClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSingleChoiceItems(final int itemsId, final int checkedItem, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSingleChoiceItems(final Cursor cursor, final int checkedItem, final String labelColumn, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSingleChoiceItems(final CharSequence[] items, final int checkedItem, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSingleChoiceItems(final ListAdapter adapter, final int checkedItem, final DialogInterface.OnClickListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setView(final int layoutResId) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setView(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Builder setInverseBackgroundForced(final boolean useInverseBackground) {
        throw new RuntimeException("Stub!");
    }
    
    public AlertDialog create() {
        throw new RuntimeException("Stub!");
    }
    
    public AlertDialog show() {
        throw new RuntimeException("Stub!");
    }
}
