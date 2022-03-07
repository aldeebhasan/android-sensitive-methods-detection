package android.view.accessibility;

import android.view.*;
import java.util.*;
import android.graphics.*;
import android.os.*;

public class AccessibilityNodeInfo implements Parcelable
{
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final Creator<AccessibilityNodeInfo> CREATOR;
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY";
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    
    AccessibilityNodeInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSource(final View source) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSource(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo findFocus(final int focus) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo focusSearch(final int direction) {
        throw new RuntimeException("Stub!");
    }
    
    public int getWindowId() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean refresh() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean refreshWithExtraData(final String extraDataKey, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    public int getChildCount() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getChild(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void addChild(final View child) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeChild(final View child) {
        throw new RuntimeException("Stub!");
    }
    
    public void addChild(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeChild(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public List<AccessibilityAction> getActionList() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getActions() {
        throw new RuntimeException("Stub!");
    }
    
    public void addAction(final AccessibilityAction action) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void addAction(final int action) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void removeAction(final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeAction(final AccessibilityAction action) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getTraversalBefore() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTraversalBefore(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTraversalBefore(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getTraversalAfter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTraversalAfter(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTraversalAfter(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getAvailableExtraData() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAvailableExtraData(final List<String> extraDataKeys) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMaxTextLength(final int max) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxTextLength() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMovementGranularities(final int granularities) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMovementGranularities() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean performAction(final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean performAction(final int action, final Bundle arguments) {
        throw new RuntimeException("Stub!");
    }
    
    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(final String text) {
        throw new RuntimeException("Stub!");
    }
    
    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(final String viewId) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityWindowInfo getWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getParent() {
        throw new RuntimeException("Stub!");
    }
    
    public void setParent(final View parent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setParent(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public void getBoundsInParent(final Rect outBounds) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBoundsInParent(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public void getBoundsInScreen(final Rect outBounds) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBoundsInScreen(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isCheckable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCheckable(final boolean checkable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isChecked() {
        throw new RuntimeException("Stub!");
    }
    
    public void setChecked(final boolean checked) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFocusable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFocusable(final boolean focusable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFocused() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFocused(final boolean focused) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVisibleToUser() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVisibleToUser(final boolean visibleToUser) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAccessibilityFocused() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAccessibilityFocused(final boolean focused) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSelected() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSelected(final boolean selected) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isClickable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setClickable(final boolean clickable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isLongClickable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLongClickable(final boolean longClickable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPassword() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPassword(final boolean password) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isScrollable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setScrollable(final boolean scrollable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEditable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEditable(final boolean editable) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDrawingOrder() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDrawingOrder(final int drawingOrderInParent) {
        throw new RuntimeException("Stub!");
    }
    
    public CollectionInfo getCollectionInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCollectionInfo(final CollectionInfo collectionInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public CollectionItemInfo getCollectionItemInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCollectionItemInfo(final CollectionItemInfo collectionItemInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public RangeInfo getRangeInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRangeInfo(final RangeInfo rangeInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isContentInvalid() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentInvalid(final boolean contentInvalid) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isContextClickable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContextClickable(final boolean contextClickable) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLiveRegion() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLiveRegion(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isMultiLine() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMultiLine(final boolean multiLine) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canOpenPopup() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCanOpenPopup(final boolean opensPopup) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDismissable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDismissable(final boolean dismissable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isImportantForAccessibility() {
        throw new RuntimeException("Stub!");
    }
    
    public void setImportantForAccessibility(final boolean important) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isShowingHintText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setShowingHintText(final boolean showingHintText) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPackageName(final CharSequence packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setClassName(final CharSequence className) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setText(final CharSequence text) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getHintText() {
        throw new RuntimeException("Stub!");
    }
    
    public void setHintText(final CharSequence hintText) {
        throw new RuntimeException("Stub!");
    }
    
    public void setError(final CharSequence error) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getError() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getContentDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentDescription(final CharSequence contentDescription) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLabelFor(final View labeled) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLabelFor(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getLabelFor() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLabeledBy(final View label) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLabeledBy(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getLabeledBy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setViewIdResourceName(final String viewIdResName) {
        throw new RuntimeException("Stub!");
    }
    
    public String getViewIdResourceName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTextSelectionStart() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTextSelectionEnd() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextSelection(final int start, final int end) {
        throw new RuntimeException("Stub!");
    }
    
    public int getInputType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setInputType(final int inputType) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityNodeInfo obtain(final View source) {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityNodeInfo obtain(final View root, final int virtualDescendantId) {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityNodeInfo obtain() {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityNodeInfo obtain(final AccessibilityNodeInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public void recycle() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object object) {
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
    
    static {
        CREATOR = null;
    }
    
    public static final class AccessibilityAction
    {
        public static final AccessibilityAction ACTION_ACCESSIBILITY_FOCUS;
        public static final AccessibilityAction ACTION_CLEAR_ACCESSIBILITY_FOCUS;
        public static final AccessibilityAction ACTION_CLEAR_FOCUS;
        public static final AccessibilityAction ACTION_CLEAR_SELECTION;
        public static final AccessibilityAction ACTION_CLICK;
        public static final AccessibilityAction ACTION_COLLAPSE;
        public static final AccessibilityAction ACTION_CONTEXT_CLICK;
        public static final AccessibilityAction ACTION_COPY;
        public static final AccessibilityAction ACTION_CUT;
        public static final AccessibilityAction ACTION_DISMISS;
        public static final AccessibilityAction ACTION_EXPAND;
        public static final AccessibilityAction ACTION_FOCUS;
        public static final AccessibilityAction ACTION_LONG_CLICK;
        public static final AccessibilityAction ACTION_MOVE_WINDOW;
        public static final AccessibilityAction ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityAction ACTION_NEXT_HTML_ELEMENT;
        public static final AccessibilityAction ACTION_PASTE;
        public static final AccessibilityAction ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityAction ACTION_PREVIOUS_HTML_ELEMENT;
        public static final AccessibilityAction ACTION_SCROLL_BACKWARD;
        public static final AccessibilityAction ACTION_SCROLL_DOWN;
        public static final AccessibilityAction ACTION_SCROLL_FORWARD;
        public static final AccessibilityAction ACTION_SCROLL_LEFT;
        public static final AccessibilityAction ACTION_SCROLL_RIGHT;
        public static final AccessibilityAction ACTION_SCROLL_TO_POSITION;
        public static final AccessibilityAction ACTION_SCROLL_UP;
        public static final AccessibilityAction ACTION_SELECT;
        public static final AccessibilityAction ACTION_SET_PROGRESS;
        public static final AccessibilityAction ACTION_SET_SELECTION;
        public static final AccessibilityAction ACTION_SET_TEXT;
        public static final AccessibilityAction ACTION_SHOW_ON_SCREEN;
        
        public AccessibilityAction(final int actionId, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public int getId() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getLabel() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object other) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        static {
            ACTION_ACCESSIBILITY_FOCUS = null;
            ACTION_CLEAR_ACCESSIBILITY_FOCUS = null;
            ACTION_CLEAR_FOCUS = null;
            ACTION_CLEAR_SELECTION = null;
            ACTION_CLICK = null;
            ACTION_COLLAPSE = null;
            ACTION_CONTEXT_CLICK = null;
            ACTION_COPY = null;
            ACTION_CUT = null;
            ACTION_DISMISS = null;
            ACTION_EXPAND = null;
            ACTION_FOCUS = null;
            ACTION_LONG_CLICK = null;
            ACTION_MOVE_WINDOW = null;
            ACTION_NEXT_AT_MOVEMENT_GRANULARITY = null;
            ACTION_NEXT_HTML_ELEMENT = null;
            ACTION_PASTE = null;
            ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = null;
            ACTION_PREVIOUS_HTML_ELEMENT = null;
            ACTION_SCROLL_BACKWARD = null;
            ACTION_SCROLL_DOWN = null;
            ACTION_SCROLL_FORWARD = null;
            ACTION_SCROLL_LEFT = null;
            ACTION_SCROLL_RIGHT = null;
            ACTION_SCROLL_TO_POSITION = null;
            ACTION_SCROLL_UP = null;
            ACTION_SELECT = null;
            ACTION_SET_PROGRESS = null;
            ACTION_SET_SELECTION = null;
            ACTION_SET_TEXT = null;
            ACTION_SHOW_ON_SCREEN = null;
        }
    }
    
    public static final class RangeInfo
    {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        
        RangeInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public static RangeInfo obtain(final int type, final float min, final float max, final float current) {
            throw new RuntimeException("Stub!");
        }
        
        public int getType() {
            throw new RuntimeException("Stub!");
        }
        
        public float getMin() {
            throw new RuntimeException("Stub!");
        }
        
        public float getMax() {
            throw new RuntimeException("Stub!");
        }
        
        public float getCurrent() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class CollectionInfo
    {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        
        CollectionInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public static CollectionInfo obtain(final int rowCount, final int columnCount, final boolean hierarchical) {
            throw new RuntimeException("Stub!");
        }
        
        public static CollectionInfo obtain(final int rowCount, final int columnCount, final boolean hierarchical, final int selectionMode) {
            throw new RuntimeException("Stub!");
        }
        
        public int getRowCount() {
            throw new RuntimeException("Stub!");
        }
        
        public int getColumnCount() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isHierarchical() {
            throw new RuntimeException("Stub!");
        }
        
        public int getSelectionMode() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class CollectionItemInfo
    {
        CollectionItemInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public static CollectionItemInfo obtain(final int rowIndex, final int rowSpan, final int columnIndex, final int columnSpan, final boolean heading) {
            throw new RuntimeException("Stub!");
        }
        
        public static CollectionItemInfo obtain(final int rowIndex, final int rowSpan, final int columnIndex, final int columnSpan, final boolean heading, final boolean selected) {
            throw new RuntimeException("Stub!");
        }
        
        public int getColumnIndex() {
            throw new RuntimeException("Stub!");
        }
        
        public int getRowIndex() {
            throw new RuntimeException("Stub!");
        }
        
        public int getColumnSpan() {
            throw new RuntimeException("Stub!");
        }
        
        public int getRowSpan() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isHeading() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isSelected() {
            throw new RuntimeException("Stub!");
        }
    }
}
