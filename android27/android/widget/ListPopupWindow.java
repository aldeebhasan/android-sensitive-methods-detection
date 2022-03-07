package android.widget;

import android.content.*;
import android.util.*;
import android.graphics.drawable.*;
import android.view.*;

public class ListPopupWindow
{
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    public static final int WRAP_CONTENT = -2;
    
    public ListPopupWindow(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public ListPopupWindow(final Context context, final AttributeSet attrs) {
        throw new RuntimeException("Stub!");
    }
    
    public ListPopupWindow(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        throw new RuntimeException("Stub!");
    }
    
    public ListPopupWindow(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAdapter(final ListAdapter adapter) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPromptPosition(final int position) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPromptPosition() {
        throw new RuntimeException("Stub!");
    }
    
    public void setModal(final boolean modal) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isModal() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSoftInputMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSoftInputMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setListSelector(final Drawable selector) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getBackground() {
        throw new RuntimeException("Stub!");
    }
    
    public void setBackgroundDrawable(final Drawable d) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAnimationStyle(final int animationStyle) {
        throw new RuntimeException("Stub!");
    }
    
    public int getAnimationStyle() {
        throw new RuntimeException("Stub!");
    }
    
    public View getAnchorView() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAnchorView(final View anchor) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHorizontalOffset() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHorizontalOffset(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public int getVerticalOffset() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVerticalOffset(final int offset) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDropDownGravity(final int gravity) {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public void setWidth(final int width) {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentWidth(final int width) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHeight(final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void setWindowLayoutType(final int layoutType) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnItemClickListener(final AdapterView.OnItemClickListener clickListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnItemSelectedListener(final AdapterView.OnItemSelectedListener selectedListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPromptView(final View prompt) {
        throw new RuntimeException("Stub!");
    }
    
    public void postShow() {
        throw new RuntimeException("Stub!");
    }
    
    public void show() {
        throw new RuntimeException("Stub!");
    }
    
    public void dismiss() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnDismissListener(final PopupWindow.OnDismissListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInputMethodMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public int getInputMethodMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSelection(final int position) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearListSelection() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isShowing() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInputMethodNotNeeded() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean performItemClick(final int position) {
        throw new RuntimeException("Stub!");
    }
    
    public Object getSelectedItem() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSelectedItemPosition() {
        throw new RuntimeException("Stub!");
    }
    
    public long getSelectedItemId() {
        throw new RuntimeException("Stub!");
    }
    
    public View getSelectedView() {
        throw new RuntimeException("Stub!");
    }
    
    public ListView getListView() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onKeyPreIme(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public View.OnTouchListener createDragToOpenListener(final View src) {
        throw new RuntimeException("Stub!");
    }
}
