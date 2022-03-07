package android.preference;

import android.content.*;
import android.util.*;
import android.widget.*;
import android.app.*;
import android.view.*;
import android.os.*;

public final class PreferenceScreen extends PreferenceGroup implements AdapterView.OnItemClickListener, DialogInterface.OnDismissListener
{
    PreferenceScreen() {
        super(null, null);
        throw new RuntimeException("Stub!");
    }
    
    public ListAdapter getRootAdapter() {
        throw new RuntimeException("Stub!");
    }
    
    protected ListAdapter onCreateRootAdapter() {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final ListView listView) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onClick() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDismiss(final DialogInterface dialog) {
        throw new RuntimeException("Stub!");
    }
    
    public Dialog getDialog() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onItemClick(final AdapterView parent, final View view, final int position, final long id) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected boolean isOnSameScreenAsChildren() {
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
