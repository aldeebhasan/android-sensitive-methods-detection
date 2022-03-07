package android.app;

import android.content.*;
import android.os.*;
import java.io.*;

public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener
{
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    
    public DialogFragment() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStyle(final int style, final int theme) {
        throw new RuntimeException("Stub!");
    }
    
    public void show(final FragmentManager manager, final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public int show(final FragmentTransaction transaction, final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public void dismiss() {
        throw new RuntimeException("Stub!");
    }
    
    public void dismissAllowingStateLoss() {
        throw new RuntimeException("Stub!");
    }
    
    public Dialog getDialog() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTheme() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCancelable(final boolean cancelable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isCancelable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setShowsDialog(final boolean showsDialog) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getShowsDialog() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onAttach(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDetach() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCancel(final DialogInterface dialog) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDismiss(final DialogInterface dialog) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onStop() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDestroyView() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
