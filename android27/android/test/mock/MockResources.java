package android.test.mock;

import android.graphics.drawable.*;
import android.graphics.*;
import java.io.*;
import android.content.res.*;
import android.util.*;

@Deprecated
public class MockResources extends Resources
{
    public MockResources() {
        super(null, null, null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void updateConfiguration(final Configuration config, final DisplayMetrics metrics) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getText(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getQuantityText(final int id, final int quantity) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getString(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getString(final int id, final Object... formatArgs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getQuantityString(final int id, final int quantity, final Object... formatArgs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getQuantityString(final int id, final int quantity) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getText(final int id, final CharSequence def) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence[] getTextArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] getStringArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int[] getIntArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public TypedArray obtainTypedArray(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public float getDimension(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getDimensionPixelOffset(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getDimensionPixelSize(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Drawable getDrawable(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Movie getMovie(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getColor(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public ColorStateList getColorStateList(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getInteger(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public XmlResourceParser getLayout(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public XmlResourceParser getAnimation(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public XmlResourceParser getXml(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public InputStream openRawResource(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AssetFileDescriptor openRawResourceFd(final int id) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void getValue(final int id, final TypedValue outValue, final boolean resolveRefs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void getValue(final String name, final TypedValue outValue, final boolean resolveRefs) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public TypedArray obtainAttributes(final AttributeSet set, final int[] attrs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public DisplayMetrics getDisplayMetrics() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Configuration getConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getIdentifier(final String name, final String defType, final String defPackage) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getResourceName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getResourcePackageName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getResourceTypeName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String getResourceEntryName(final int resid) throws NotFoundException {
        throw new RuntimeException("Stub!");
    }
}
