package android.renderscript;

public static class RSMessageHandler implements Runnable
{
    protected int[] mData;
    protected int mID;
    protected int mLength;
    
    public RSMessageHandler() {
        this.mData = null;
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void run() {
        throw new RuntimeException("Stub!");
    }
}
