package android.webkit;

public abstract class RenderProcessGoneDetail
{
    public RenderProcessGoneDetail() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean didCrash();
    
    public abstract int rendererPriorityAtExit();
}
