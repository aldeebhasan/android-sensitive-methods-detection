package android.graphics.drawable;

import android.graphics.*;

public abstract static class ShaderFactory
{
    public ShaderFactory() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract Shader resize(final int p0, final int p1);
}
