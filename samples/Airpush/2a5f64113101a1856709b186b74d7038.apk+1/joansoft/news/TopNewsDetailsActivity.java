package joansoft.news;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import java.util.Random;
import joansoft.dailyread.R;

public class TopNewsDetailsActivity extends Activity implements PictureListener {
    static int[] adColors = new int[]{14605278, -526345, 16776669, 15792121, 16381894, 15989503, 16777215};
    static Random random = new Random();
    ProgressDialog dialog;
    private RefreshHandler mRedrawHandler = new RefreshHandler();
    WebView searchView;
    String url;

    class RefreshHandler extends Handler {
        RefreshHandler() {
        }

        public void handleMessage(Message msg) {
            TopNewsDetailsActivity.this.searchView.loadUrl(TopNewsDetailsActivity.this.url);
        }

        public void refresh(long delayMillis) {
            removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        this.searchView = (WebView) findViewById(R.id.webView);
        this.searchView.setWebViewClient(new TopNewsWebView(this));
        this.searchView.getSettings().setJavaScriptEnabled(true);
        this.searchView.setScrollBarStyle(0);
        this.searchView.setPictureListener(this);
        if (savedInstanceState != null) {
            this.url = savedInstanceState.getString("url");
        } else {
            this.url = getIntent().getExtras().getString("url");
        }
        this.dialog = ProgressDialog.show(this, "", getString(R.string.loading), true);
        this.dialog.show();
        this.mRedrawHandler.refresh(10);
    }

    protected void onDestroy() {
        if (this.searchView != null) {
            this.searchView.clearCache(true);
            this.searchView.clearHistory();
        }
        if (this.dialog != null) {
            this.dialog.cancel();
            this.dialog = null;
        }
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("url", this.url);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_summ_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back /*2131165210*/:
                finish();
                return true;
            case R.id.share /*2131165211*/:
                spread();
                return true;
            default:
                return false;
        }
    }

    private void spread() {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/*");
        i.putExtra("android.intent.extra.SUBJECT", "Check this news .. by DailyRead Android App");
        i.putExtra("android.intent.extra.TEXT", this.url);
        startActivity(Intent.createChooser(i, "Share this news"));
    }

    public void onNewPicture(WebView arg0, Picture arg1) {
        if (this.dialog != null) {
            this.dialog.cancel();
        }
    }
}
