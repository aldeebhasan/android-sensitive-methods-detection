package android.appwidget;

import android.content.*;
import android.app.*;
import android.os.*;

public class AppWidgetHost
{
    public AppWidgetHost(final Context context, final int hostId) {
        throw new RuntimeException("Stub!");
    }
    
    public void startListening() {
        throw new RuntimeException("Stub!");
    }
    
    public void stopListening() {
        throw new RuntimeException("Stub!");
    }
    
    public int allocateAppWidgetId() {
        throw new RuntimeException("Stub!");
    }
    
    public final void startAppWidgetConfigureActivityForResult(final Activity activity, final int appWidgetId, final int intentFlags, final int requestCode, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getAppWidgetIds() {
        throw new RuntimeException("Stub!");
    }
    
    public void deleteAppWidgetId(final int appWidgetId) {
        throw new RuntimeException("Stub!");
    }
    
    public void deleteHost() {
        throw new RuntimeException("Stub!");
    }
    
    public static void deleteAllHosts() {
        throw new RuntimeException("Stub!");
    }
    
    public final AppWidgetHostView createView(final Context context, final int appWidgetId, final AppWidgetProviderInfo appWidget) {
        throw new RuntimeException("Stub!");
    }
    
    protected AppWidgetHostView onCreateView(final Context context, final int appWidgetId, final AppWidgetProviderInfo appWidget) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onProviderChanged(final int appWidgetId, final AppWidgetProviderInfo appWidget) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onProvidersChanged() {
        throw new RuntimeException("Stub!");
    }
    
    protected void clearViews() {
        throw new RuntimeException("Stub!");
    }
}
