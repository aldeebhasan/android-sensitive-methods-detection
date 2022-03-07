package android.view.textclassifier;

import android.graphics.drawable.*;
import android.content.*;
import android.view.*;

public final class TextClassification
{
    TextClassification() {
        throw new RuntimeException("Stub!");
    }
    
    public String getText() {
        throw new RuntimeException("Stub!");
    }
    
    public int getEntityCount() {
        throw new RuntimeException("Stub!");
    }
    
    public String getEntity(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public float getConfidenceScore(final String entity) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent getIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public View.OnClickListener getOnClickListener() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setText(final String text) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setEntityType(final String type, final float confidenceScore) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIcon(final Drawable icon) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setLabel(final String label) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIntent(final Intent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setOnClickListener(final View.OnClickListener onClickListener) {
            throw new RuntimeException("Stub!");
        }
        
        public TextClassification build() {
            throw new RuntimeException("Stub!");
        }
    }
}
