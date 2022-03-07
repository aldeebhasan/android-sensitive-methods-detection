package android.view;

import android.graphics.drawable.*;
import android.content.res.*;
import android.graphics.*;
import android.content.*;

public interface MenuItem
{
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
    
    int getItemId();
    
    int getGroupId();
    
    int getOrder();
    
    MenuItem setTitle(final CharSequence p0);
    
    MenuItem setTitle(final int p0);
    
    CharSequence getTitle();
    
    MenuItem setTitleCondensed(final CharSequence p0);
    
    CharSequence getTitleCondensed();
    
    MenuItem setIcon(final Drawable p0);
    
    MenuItem setIcon(final int p0);
    
    Drawable getIcon();
    
    default MenuItem setIconTintList(final ColorStateList tint) {
        throw new RuntimeException("Stub!");
    }
    
    default ColorStateList getIconTintList() {
        throw new RuntimeException("Stub!");
    }
    
    default MenuItem setIconTintMode(final PorterDuff.Mode tintMode) {
        throw new RuntimeException("Stub!");
    }
    
    default PorterDuff.Mode getIconTintMode() {
        throw new RuntimeException("Stub!");
    }
    
    MenuItem setIntent(final Intent p0);
    
    Intent getIntent();
    
    MenuItem setShortcut(final char p0, final char p1);
    
    default MenuItem setShortcut(final char numericChar, final char alphaChar, final int numericModifiers, final int alphaModifiers) {
        throw new RuntimeException("Stub!");
    }
    
    MenuItem setNumericShortcut(final char p0);
    
    default MenuItem setNumericShortcut(final char numericChar, final int numericModifiers) {
        throw new RuntimeException("Stub!");
    }
    
    char getNumericShortcut();
    
    default int getNumericModifiers() {
        throw new RuntimeException("Stub!");
    }
    
    MenuItem setAlphabeticShortcut(final char p0);
    
    default MenuItem setAlphabeticShortcut(final char alphaChar, final int alphaModifiers) {
        throw new RuntimeException("Stub!");
    }
    
    char getAlphabeticShortcut();
    
    default int getAlphabeticModifiers() {
        throw new RuntimeException("Stub!");
    }
    
    MenuItem setCheckable(final boolean p0);
    
    boolean isCheckable();
    
    MenuItem setChecked(final boolean p0);
    
    boolean isChecked();
    
    MenuItem setVisible(final boolean p0);
    
    boolean isVisible();
    
    MenuItem setEnabled(final boolean p0);
    
    boolean isEnabled();
    
    boolean hasSubMenu();
    
    SubMenu getSubMenu();
    
    MenuItem setOnMenuItemClickListener(final OnMenuItemClickListener p0);
    
    ContextMenu.ContextMenuInfo getMenuInfo();
    
    void setShowAsAction(final int p0);
    
    MenuItem setShowAsActionFlags(final int p0);
    
    MenuItem setActionView(final View p0);
    
    MenuItem setActionView(final int p0);
    
    View getActionView();
    
    MenuItem setActionProvider(final ActionProvider p0);
    
    ActionProvider getActionProvider();
    
    boolean expandActionView();
    
    boolean collapseActionView();
    
    boolean isActionViewExpanded();
    
    MenuItem setOnActionExpandListener(final OnActionExpandListener p0);
    
    default MenuItem setContentDescription(final CharSequence contentDescription) {
        throw new RuntimeException("Stub!");
    }
    
    default CharSequence getContentDescription() {
        throw new RuntimeException("Stub!");
    }
    
    default MenuItem setTooltipText(final CharSequence tooltipText) {
        throw new RuntimeException("Stub!");
    }
    
    default CharSequence getTooltipText() {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnActionExpandListener
    {
        boolean onMenuItemActionExpand(final MenuItem p0);
        
        boolean onMenuItemActionCollapse(final MenuItem p0);
    }
    
    public interface OnMenuItemClickListener
    {
        boolean onMenuItemClick(final MenuItem p0);
    }
}
