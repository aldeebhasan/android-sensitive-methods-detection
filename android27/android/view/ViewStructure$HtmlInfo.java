package android.view;

import java.util.*;
import android.util.*;

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
