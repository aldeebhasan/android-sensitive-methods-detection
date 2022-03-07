package android.webkit;

import android.widget.*;
import android.content.*;
import android.net.http.*;
import android.print.*;
import java.util.*;
import android.net.*;
import android.view.textclassifier.*;
import android.view.accessibility.*;
import android.util.*;
import android.view.autofill.*;
import android.content.res.*;
import android.view.inputmethod.*;
import android.view.*;
import android.graphics.*;
import android.os.*;
import android.content.pm.*;

public class WebView extends AbsoluteLayout implements ViewTreeObserver.OnGlobalFocusChangeListener, OnHierarchyChangeListener
{
    public static final int RENDERER_PRIORITY_BOUND = 1;
    public static final int RENDERER_PRIORITY_IMPORTANT = 2;
    public static final int RENDERER_PRIORITY_WAIVED = 0;
    public static final String SCHEME_GEO = "geo:0,0?q=";
    public static final String SCHEME_MAILTO = "mailto:";
    public static final String SCHEME_TEL = "tel:";
    
    public WebView(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public WebView(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public WebView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public WebView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public WebView(final Context context, final AttributeSet attrs, final int defStyleAttr, final boolean privateBrowsing) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setHorizontalScrollbarOverlay(final boolean overlay) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setVerticalScrollbarOverlay(final boolean overlay) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean overlayHorizontalScrollbar() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean overlayVerticalScrollbar() {
        throw new RuntimeException("Stub!");
    }
    
    public SslCertificate getCertificate() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setCertificate(final SslCertificate certificate) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void savePassword(final String host, final String username, final String password) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setHttpAuthUsernamePassword(final String host, final String realm, final String username, final String password) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String[] getHttpAuthUsernamePassword(final String host, final String realm) {
        throw new RuntimeException("Stub!");
    }
    
    public void destroy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setNetworkAvailable(final boolean networkUp) {
        throw new RuntimeException("Stub!");
    }
    
    public WebBackForwardList saveState(final Bundle outState) {
        throw new RuntimeException("Stub!");
    }
    
    public WebBackForwardList restoreState(final Bundle inState) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadUrl(final String url, final Map<String, String> additionalHttpHeaders) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadUrl(final String url) {
        throw new RuntimeException("Stub!");
    }
    
    public void postUrl(final String url, final byte[] postData) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadData(final String data, final String mimeType, final String encoding) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadDataWithBaseURL(final String baseUrl, final String data, final String mimeType, final String encoding, final String historyUrl) {
        throw new RuntimeException("Stub!");
    }
    
    public void evaluateJavascript(final String script, final ValueCallback<String> resultCallback) {
        throw new RuntimeException("Stub!");
    }
    
    public void saveWebArchive(final String filename) {
        throw new RuntimeException("Stub!");
    }
    
    public void saveWebArchive(final String basename, final boolean autoname, final ValueCallback<String> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void stopLoading() {
        throw new RuntimeException("Stub!");
    }
    
    public void reload() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canGoBack() {
        throw new RuntimeException("Stub!");
    }
    
    public void goBack() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canGoForward() {
        throw new RuntimeException("Stub!");
    }
    
    public void goForward() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canGoBackOrForward(final int steps) {
        throw new RuntimeException("Stub!");
    }
    
    public void goBackOrForward(final int steps) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPrivateBrowsingEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean pageUp(final boolean top) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean pageDown(final boolean bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public void postVisualStateCallback(final long requestId, final VisualStateCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void clearView() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Picture capturePicture() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public PrintDocumentAdapter createPrintDocumentAdapter() {
        throw new RuntimeException("Stub!");
    }
    
    public PrintDocumentAdapter createPrintDocumentAdapter(final String documentName) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @ViewDebug.ExportedProperty(category = "webview")
    public float getScale() {
        throw new RuntimeException("Stub!");
    }
    
    public void setInitialScale(final int scaleInPercent) {
        throw new RuntimeException("Stub!");
    }
    
    public void invokeZoomPicker() {
        throw new RuntimeException("Stub!");
    }
    
    public HitTestResult getHitTestResult() {
        throw new RuntimeException("Stub!");
    }
    
    public void requestFocusNodeHref(final Message hrefMsg) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestImageRef(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "webview")
    public String getUrl() {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "webview")
    public String getOriginalUrl() {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "webview")
    public String getTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getFavicon() {
        throw new RuntimeException("Stub!");
    }
    
    public int getProgress() {
        throw new RuntimeException("Stub!");
    }
    
    @ViewDebug.ExportedProperty(category = "webview")
    public int getContentHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public void pauseTimers() {
        throw new RuntimeException("Stub!");
    }
    
    public void resumeTimers() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPause() {
        throw new RuntimeException("Stub!");
    }
    
    public void onResume() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void freeMemory() {
        throw new RuntimeException("Stub!");
    }
    
    public void clearCache(final boolean includeDiskFiles) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearFormData() {
        throw new RuntimeException("Stub!");
    }
    
    public void clearHistory() {
        throw new RuntimeException("Stub!");
    }
    
    public void clearSslPreferences() {
        throw new RuntimeException("Stub!");
    }
    
    public static void clearClientCertPreferences(final Runnable onCleared) {
        throw new RuntimeException("Stub!");
    }
    
    public static void startSafeBrowsing(final Context context, final ValueCallback<Boolean> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public static void setSafeBrowsingWhitelist(final List<String> urls, final ValueCallback<Boolean> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getSafeBrowsingPrivacyPolicyUrl() {
        throw new RuntimeException("Stub!");
    }
    
    public WebBackForwardList copyBackForwardList() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFindListener(final FindListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void findNext(final boolean forward) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int findAll(final String find) {
        throw new RuntimeException("Stub!");
    }
    
    public void findAllAsync(final String find) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean showFindDialog(final String text, final boolean showIme) {
        throw new RuntimeException("Stub!");
    }
    
    public static String findAddress(final String addr) {
        throw new RuntimeException("Stub!");
    }
    
    public static void enableSlowWholeDocumentDraw() {
        throw new RuntimeException("Stub!");
    }
    
    public void clearMatches() {
        throw new RuntimeException("Stub!");
    }
    
    public void documentHasImages(final Message response) {
        throw new RuntimeException("Stub!");
    }
    
    public void setWebViewClient(final WebViewClient client) {
        throw new RuntimeException("Stub!");
    }
    
    public WebViewClient getWebViewClient() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDownloadListener(final DownloadListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setWebChromeClient(final WebChromeClient client) {
        throw new RuntimeException("Stub!");
    }
    
    public WebChromeClient getWebChromeClient() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setPictureListener(final PictureListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addJavascriptInterface(final Object object, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeJavascriptInterface(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public WebMessagePort[] createWebMessageChannel() {
        throw new RuntimeException("Stub!");
    }
    
    public void postWebMessage(final WebMessage message, final Uri targetOrigin) {
        throw new RuntimeException("Stub!");
    }
    
    public WebSettings getSettings() {
        throw new RuntimeException("Stub!");
    }
    
    public static void setWebContentsDebuggingEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public void onChildViewAdded(final View parent, final View child) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public void onChildViewRemoved(final View p, final View child) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public void onGlobalFocusChanged(final View oldFocus, final View newFocus) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setMapTrackballToArrowKeys(final boolean setMap) {
        throw new RuntimeException("Stub!");
    }
    
    public void flingScroll(final int vx, final int vy) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean canZoomIn() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean canZoomOut() {
        throw new RuntimeException("Stub!");
    }
    
    public void zoomBy(final float zoomFactor) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean zoomIn() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean zoomOut() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRendererPriorityPolicy(final int rendererRequestedPriority, final boolean waivedWhenNotVisible) {
        throw new RuntimeException("Stub!");
    }
    
    public int getRendererRequestedPriority() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getRendererPriorityWaivedWhenNotVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTextClassifier(final TextClassifier textClassifier) {
        throw new RuntimeException("Stub!");
    }
    
    public TextClassifier getTextClassifier() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToWindow() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setLayoutParams(final ViewGroup.LayoutParams params) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setOverScrollMode(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setScrollBarStyle(final int style) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeHorizontalScrollRange() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeHorizontalScrollOffset() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeVerticalScrollRange() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeVerticalScrollOffset() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected int computeVerticalScrollExtent() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void computeScroll() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onHoverEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onGenericMotionEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onTrackballEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyMultiple(final int keyCode, final int repeatCount, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    @Override
    public boolean shouldDelayChildPressedState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onProvideVirtualStructure(final ViewStructure structure) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onProvideAutofillVirtualStructure(final ViewStructure structure, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void autofill(final SparseArray<AutofillValue> values) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onOverScrolled(final int scrollX, final int scrollY, final boolean clampedX, final boolean clampedY) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onWindowVisibilityChanged(final int visibility) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean performLongClick() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public InputConnection onCreateInputConnection(final EditorInfo outAttrs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onDragEvent(final DragEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onVisibilityChanged(final View changedView, final int visibility) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onWindowFocusChanged(final boolean hasWindowFocus) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onFocusChanged(final boolean focused, final int direction, final Rect previouslyFocusedRect) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onSizeChanged(final int w, final int h, final int ow, final int oh) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean requestFocus(final int direction, final Rect previouslyFocusedRect) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean requestChildRectangleOnScreen(final View child, final Rect rect, final boolean immediate) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setBackgroundColor(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setLayerType(final int layerType, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dispatchDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onStartTemporaryDetach() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onFinishTemporaryDetach() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Handler getHandler() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View findFocus() {
        throw new RuntimeException("Stub!");
    }
    
    public static PackageInfo getCurrentWebViewPackage() {
        throw new RuntimeException("Stub!");
    }
    
    public class WebViewTransport
    {
        public WebViewTransport() {
            throw new RuntimeException("Stub!");
        }
        
        public synchronized void setWebView(final WebView webview) {
            throw new RuntimeException("Stub!");
        }
        
        public synchronized WebView getWebView() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public abstract static class VisualStateCallback
    {
        public VisualStateCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onComplete(final long p0);
    }
    
    public static class HitTestResult
    {
        @Deprecated
        public static final int ANCHOR_TYPE = 1;
        public static final int EDIT_TEXT_TYPE = 9;
        public static final int EMAIL_TYPE = 4;
        public static final int GEO_TYPE = 3;
        @Deprecated
        public static final int IMAGE_ANCHOR_TYPE = 6;
        public static final int IMAGE_TYPE = 5;
        public static final int PHONE_TYPE = 2;
        public static final int SRC_ANCHOR_TYPE = 7;
        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
        public static final int UNKNOWN_TYPE = 0;
        
        HitTestResult() {
            throw new RuntimeException("Stub!");
        }
        
        public int getType() {
            throw new RuntimeException("Stub!");
        }
        
        public String getExtra() {
            throw new RuntimeException("Stub!");
        }
    }
    
    @Deprecated
    public interface PictureListener
    {
        @Deprecated
        void onNewPicture(final WebView p0, final Picture p1);
    }
    
    public interface FindListener
    {
        void onFindResultReceived(final int p0, final int p1, final boolean p2);
    }
}
