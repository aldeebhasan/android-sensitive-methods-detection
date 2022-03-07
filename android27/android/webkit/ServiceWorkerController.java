package android.webkit;

public abstract class ServiceWorkerController
{
    public ServiceWorkerController() {
        throw new RuntimeException("Stub!");
    }
    
    public static ServiceWorkerController getInstance() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract ServiceWorkerWebSettings getServiceWorkerWebSettings();
    
    public abstract void setServiceWorkerClient(final ServiceWorkerClient p0);
}
