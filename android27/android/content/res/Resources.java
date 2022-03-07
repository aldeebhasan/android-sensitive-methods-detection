package android.content.res;

import android.graphics.drawable.*;
import android.graphics.*;
import android.util.*;
import android.os.*;
import org.xmlpull.v1.*;
import java.io.*;

public class Resources
{
    public Resources(final AssetManager assets, final DisplayMetrics metrics, final Configuration config) {
        throw new RuntimeException("Stub!");
    }
    
    public static Resources getSystem() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getText(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public Typeface getFont(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getQuantityText(final int id, final int quantity) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String getString(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String getString(final int id, final Object... formatArgs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String getQuantityString(final int id, final int quantity, final Object... formatArgs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String getQuantityString(final int id, final int quantity) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getText(final int id, final CharSequence def) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence[] getTextArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getStringArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getIntArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public TypedArray obtainTypedArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public float getDimension(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public int getDimensionPixelOffset(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public int getDimensionPixelSize(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public float getFraction(final int id, final int base, final int pbase) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Drawable getDrawable(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getDrawable(final int id, final Theme theme) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Drawable getDrawableForDensity(final int id, final int density) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getDrawableForDensity(final int id, final int density, final Theme theme) {
        throw new RuntimeException("Stub!");
    }
    
    public Movie getMovie(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getColor(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public int getColor(final int id, final Theme theme) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public ColorStateList getColorStateList(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList getColorStateList(final int id, final Theme theme) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getBoolean(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public int getInteger(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public XmlResourceParser getLayout(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public XmlResourceParser getAnimation(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public XmlResourceParser getXml(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public InputStream openRawResource(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public InputStream openRawResource(final int id, final TypedValue value) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public AssetFileDescriptor openRawResourceFd(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public void getValue(final int id, final TypedValue outValue, final boolean resolveRefs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public void getValueForDensity(final int id, final int density, final TypedValue outValue, final boolean resolveRefs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public void getValue(final String name, final TypedValue outValue, final boolean resolveRefs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public final Theme newTheme() {
        throw new RuntimeException("Stub!");
    }
    
    public TypedArray obtainAttributes(final AttributeSet set, final int[] attrs) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void updateConfiguration(final Configuration config, final DisplayMetrics metrics) {
        throw new RuntimeException("Stub!");
    }
    
    public DisplayMetrics getDisplayMetrics() {
        throw new RuntimeException("Stub!");
    }
    
    public Configuration getConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    public int getIdentifier(final String name, final String defType, final String defPackage) {
        throw new RuntimeException("Stub!");
    }
    
    public String getResourceName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String getResourcePackageName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String getResourceTypeName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public String getResourceEntryName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public void parseBundleExtras(final XmlResourceParser parser, final Bundle outBundle) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void parseBundleExtra(final String tagName, final AttributeSet attrs, final Bundle outBundle) throws XmlPullParserException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetManager getAssets() {
        throw new RuntimeException("Stub!");
    }
    
    public final void flushLayoutCache() {
        throw new RuntimeException("Stub!");
    }
    
    public final void finishPreloading() {
        throw new RuntimeException("Stub!");
    }
    
    public static class NotFoundException extends RuntimeException
    {
        public NotFoundException() {
            throw new RuntimeException("Stub!");
        }
        
        public NotFoundException(final String name) {
            throw new RuntimeException("Stub!");
        }
        
        public NotFoundException(final String name, final Exception cause) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public final class Theme
    {
        Theme() {
            throw new RuntimeException("Stub!");
        }
        
        public void applyStyle(final int resId, final boolean force) {
            throw new RuntimeException("Stub!");
        }
        
        public void setTo(final Theme other) {
            throw new RuntimeException("Stub!");
        }
        
        public TypedArray obtainStyledAttributes(final int[] attrs) {
            throw new RuntimeException("Stub!");
        }
        
        public TypedArray obtainStyledAttributes(final int resId, final int[] attrs) throws NotFoundException {
            throw new RuntimeException("Stub!");
        }
        
        public TypedArray obtainStyledAttributes(final AttributeSet set, final int[] attrs, final int defStyleAttr, final int defStyleRes) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean resolveAttribute(final int resid, final TypedValue outValue, final boolean resolveRefs) {
            throw new RuntimeException("Stub!");
        }
        
        public Resources getResources() {
            throw new RuntimeException("Stub!");
        }
        
        public Drawable getDrawable(final int id) throws NotFoundException {
            throw new RuntimeException("Stub!");
        }
        
        public int getChangingConfigurations() {
            throw new RuntimeException("Stub!");
        }
        
        public void dump(final int priority, final String tag, final String prefix) {
            throw new RuntimeException("Stub!");
        }
    }
}
