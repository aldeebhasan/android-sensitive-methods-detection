package android.renderscript;

public static class RSErrorHandler implements Runnable
{
    protected String mErrorMessage;
    protected int mErrorNum;
    
    public RSErrorHandler() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void run() {
        throw new RuntimeException("Stub!");
    }
}
