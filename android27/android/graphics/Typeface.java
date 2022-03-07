package android.graphics;

import android.content.res.*;
import java.io.*;
import android.graphics.fonts.*;

public class Typeface
{
    public static final int BOLD = 1;
    public static final int BOLD_ITALIC = 3;
    public static final Typeface DEFAULT;
    public static final Typeface DEFAULT_BOLD;
    public static final int ITALIC = 2;
    public static final Typeface MONOSPACE;
    public static final int NORMAL = 0;
    public static final Typeface SANS_SERIF;
    public static final Typeface SERIF;
    
    Typeface() {
        throw new RuntimeException("Stub!");
    }
    
    public int getStyle() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isBold() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isItalic() {
        throw new RuntimeException("Stub!");
    }
    
    public static Typeface create(final String familyName, final int style) {
        throw new RuntimeException("Stub!");
    }
    
    public static Typeface create(final Typeface family, final int style) {
        throw new RuntimeException("Stub!");
    }
    
    public static Typeface defaultFromStyle(final int style) {
        throw new RuntimeException("Stub!");
    }
    
    public static Typeface createFromAsset(final AssetManager mgr, final String path) {
        throw new RuntimeException("Stub!");
    }
    
    public static Typeface createFromFile(final File path) {
        throw new RuntimeException("Stub!");
    }
    
    public static Typeface createFromFile(final String path) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        DEFAULT = null;
        DEFAULT_BOLD = null;
        MONOSPACE = null;
        SANS_SERIF = null;
        SERIF = null;
    }
    
    public static final class Builder
    {
        public Builder(final File path) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final FileDescriptor fd) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final String path) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final AssetManager assetManager, final String path) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setWeight(final int weight) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setItalic(final boolean italic) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setTtcIndex(final int ttcIndex) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setFontVariationSettings(final String variationSettings) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setFontVariationSettings(final FontVariationAxis[] axes) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setFallback(final String familyName) {
            throw new RuntimeException("Stub!");
        }
        
        public Typeface build() {
            throw new RuntimeException("Stub!");
        }
    }
}
