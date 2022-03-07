package android.view;

import android.graphics.*;
import android.view.autofill.*;
import android.os.*;
import java.util.*;
import android.util.*;

public abstract class ViewStructure
{
    public ViewStructure() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void setId(final int p0, final String p1, final String p2, final String p3);
    
    public abstract void setDimens(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    public abstract void setTransformation(final Matrix p0);
    
    public abstract void setElevation(final float p0);
    
    public abstract void setAlpha(final float p0);
    
    public abstract void setVisibility(final int p0);
    
    public abstract void setEnabled(final boolean p0);
    
    public abstract void setClickable(final boolean p0);
    
    public abstract void setLongClickable(final boolean p0);
    
    public abstract void setContextClickable(final boolean p0);
    
    public abstract void setFocusable(final boolean p0);
    
    public abstract void setFocused(final boolean p0);
    
    public abstract void setAccessibilityFocused(final boolean p0);
    
    public abstract void setCheckable(final boolean p0);
    
    public abstract void setChecked(final boolean p0);
    
    public abstract void setSelected(final boolean p0);
    
    public abstract void setActivated(final boolean p0);
    
    public abstract void setOpaque(final boolean p0);
    
    public abstract void setClassName(final String p0);
    
    public abstract void setContentDescription(final CharSequence p0);
    
    public abstract void setText(final CharSequence p0);
    
    public abstract void setText(final CharSequence p0, final int p1, final int p2);
    
    public abstract void setTextStyle(final float p0, final int p1, final int p2, final int p3);
    
    public abstract void setTextLines(final int[] p0, final int[] p1);
    
    public abstract void setHint(final CharSequence p0);
    
    public abstract CharSequence getText();
    
    public abstract int getTextSelectionStart();
    
    public abstract int getTextSelectionEnd();
    
    public abstract CharSequence getHint();
    
    public abstract Bundle getExtras();
    
    public abstract boolean hasExtras();
    
    public abstract void setChildCount(final int p0);
    
    public abstract int addChildCount(final int p0);
    
    public abstract int getChildCount();
    
    public abstract ViewStructure newChild(final int p0);
    
    public abstract ViewStructure asyncNewChild(final int p0);
    
    public abstract AutofillId getAutofillId();
    
    public abstract void setAutofillId(final AutofillId p0);
    
    public abstract void setAutofillId(final AutofillId p0, final int p1);
    
    public abstract void setAutofillType(final int p0);
    
    public abstract void setAutofillHints(final String[] p0);
    
    public abstract void setAutofillValue(final AutofillValue p0);
    
    public abstract void setAutofillOptions(final CharSequence[] p0);
    
    public abstract void setInputType(final int p0);
    
    public abstract void setDataIsSensitive(final boolean p0);
    
    public abstract void asyncCommit();
    
    public abstract void setWebDomain(final String p0);
    
    public abstract void setLocaleList(final LocaleList p0);
    
    public abstract HtmlInfo.Builder newHtmlInfoBuilder(final String p0);
    
    public abstract void setHtmlInfo(final HtmlInfo p0);
    
    public abstract static class HtmlInfo
    {
        public HtmlInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract String getTag();
        
        public abstract List<Pair<String, String>> getAttributes();
        
        public abstract static class Builder
        {
            public Builder() {
                throw new RuntimeException("Stub!");
            }
            
            public abstract Builder addAttribute(final String p0, final String p1);
            
            public abstract HtmlInfo build();
        }
    }
}
