package android.media;

public static class SimpleCallback extends Callback
{
    public SimpleCallback() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteSelected(final MediaRouter router, final int type, final RouteInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteUnselected(final MediaRouter router, final int type, final RouteInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteAdded(final MediaRouter router, final RouteInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteRemoved(final MediaRouter router, final RouteInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteChanged(final MediaRouter router, final RouteInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteGrouped(final MediaRouter router, final RouteInfo info, final RouteGroup group, final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteUngrouped(final MediaRouter router, final RouteInfo info, final RouteGroup group) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRouteVolumeChanged(final MediaRouter router, final RouteInfo info) {
        throw new RuntimeException("Stub!");
    }
}
