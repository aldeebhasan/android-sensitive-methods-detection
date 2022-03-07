package android.view;

public abstract static class Builder
{
    public Builder() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Builder addAttribute(final String p0, final String p1);
    
    public abstract HtmlInfo build();
}
