package android.graphics.pdf;

import android.graphics.*;

public static final class PageInfo
{
    PageInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPageWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPageHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public Rect getContentRect() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPageNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Builder
    {
        public Builder(final int pageWidth, final int pageHeight, final int pageNumber) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setContentRect(final Rect contentRect) {
            throw new RuntimeException("Stub!");
        }
        
        public PageInfo create() {
            throw new RuntimeException("Stub!");
        }
    }
}
