package joansoft.dailyread;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import java.util.Random;

public class BookmarkDisplayActivity extends Activity {
    public static int[] adColors = new int[]{14605278, -526345, 16776669, 15792121, 16381894};
    String details;
    private Random random = new Random();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_display);
        if (savedInstanceState != null) {
            this.details = savedInstanceState.getString("details");
        } else {
            this.details = getIntent().getExtras().getString("details");
        }
        WebView webView = (WebView) findViewById(R.id.webDetView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("http://www.joansoft.com/", this.details, "text/html", "utf-8", null);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("details", this.details);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bk_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back /*2131165210*/:
                finish();
                break;
        }
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
