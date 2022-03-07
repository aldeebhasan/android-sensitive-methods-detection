package android.app;

import android.widget.*;

public abstract static class Style
{
    protected Builder mBuilder;
    
    public Style() {
        throw new RuntimeException("Stub!");
    }
    
    protected void internalSetBigContentTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    protected void internalSetSummaryText(final CharSequence cs) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBuilder(final Builder builder) {
        throw new RuntimeException("Stub!");
    }
    
    protected void checkBuilder() {
        throw new RuntimeException("Stub!");
    }
    
    protected RemoteViews getStandardView(final int layoutId) {
        throw new RuntimeException("Stub!");
    }
    
    public Notification build() {
        throw new RuntimeException("Stub!");
    }
}
