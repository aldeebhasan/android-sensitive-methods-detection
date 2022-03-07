package joansoft.news;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TopNewsWebView extends WebViewClient {
    Activity act;

    public TopNewsWebView(Activity act) {
        this.act = act;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public synchronized void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
