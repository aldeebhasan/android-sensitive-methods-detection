package android.content.pm;

import java.util.*;
import android.content.*;

public class ShortcutManager
{
    ShortcutManager() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setDynamicShortcuts(final List<ShortcutInfo> shortcutInfoList) {
        throw new RuntimeException("Stub!");
    }
    
    public List<ShortcutInfo> getDynamicShortcuts() {
        throw new RuntimeException("Stub!");
    }
    
    public List<ShortcutInfo> getManifestShortcuts() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean addDynamicShortcuts(final List<ShortcutInfo> shortcutInfoList) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeDynamicShortcuts(final List<String> shortcutIds) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeAllDynamicShortcuts() {
        throw new RuntimeException("Stub!");
    }
    
    public List<ShortcutInfo> getPinnedShortcuts() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean updateShortcuts(final List<ShortcutInfo> shortcutInfoList) {
        throw new RuntimeException("Stub!");
    }
    
    public void disableShortcuts(final List<String> shortcutIds) {
        throw new RuntimeException("Stub!");
    }
    
    public void disableShortcuts(final List<String> shortcutIds, final CharSequence disabledMessage) {
        throw new RuntimeException("Stub!");
    }
    
    public void enableShortcuts(final List<String> shortcutIds) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxShortcutCountPerActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRateLimitingActive() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIconMaxWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIconMaxHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public void reportShortcutUsed(final String shortcutId) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRequestPinShortcutSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean requestPinShortcut(final ShortcutInfo shortcut, final IntentSender resultIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public Intent createShortcutResultIntent(final ShortcutInfo shortcut) {
        throw new RuntimeException("Stub!");
    }
}
