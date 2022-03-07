package android.media;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onRouteSelected(final MediaRouter p0, final int p1, final RouteInfo p2);
    
    public abstract void onRouteUnselected(final MediaRouter p0, final int p1, final RouteInfo p2);
    
    public abstract void onRouteAdded(final MediaRouter p0, final RouteInfo p1);
    
    public abstract void onRouteRemoved(final MediaRouter p0, final RouteInfo p1);
    
    public abstract void onRouteChanged(final MediaRouter p0, final RouteInfo p1);
    
    public abstract void onRouteGrouped(final MediaRouter p0, final RouteInfo p1, final RouteGroup p2, final int p3);
    
    public abstract void onRouteUngrouped(final MediaRouter p0, final RouteInfo p1, final RouteGroup p2);
    
    public abstract void onRouteVolumeChanged(final MediaRouter p0, final RouteInfo p1);
    
    public void onRoutePresentationDisplayChanged(final MediaRouter router, final RouteInfo info) {
        throw new RuntimeException("Stub!");
    }
}
