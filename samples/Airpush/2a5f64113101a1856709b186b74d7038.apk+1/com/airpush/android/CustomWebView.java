package com.airpush.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.webkit.WebView;

public class CustomWebView extends WebView {
    private Context ctx;
    private ProgressDialog pd = null;

    public CustomWebView(Context context) {
        super(context);
        clearCache(true);
        clearFormData();
        clearHistory();
        getSettings().setJavaScriptEnabled(true);
        getSettings().setUserAgentString(null);
        requestFocus(130);
        this.pd = new ProgressDialog(context);
        this.pd.setMessage("Loading...");
        this.ctx = context;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        goBack();
        return true;
    }
}
