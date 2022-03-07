package joansoft.news;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Random;
import joansoft.dailyread.DBAdapter;
import joansoft.dailyread.R;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class NewsSummaryActivity extends ListActivity implements OnItemClickListener, XmlListener {
    static String ADTITLE = "<JS:ADMOB>";
    static int[] adColors = new int[]{14605278, -526345, 16776669, 15792121, 16381894, 15989503, 16777215};
    static Random random = new Random();
    boolean active = true;
    ProgressDialog dialog;
    boolean inItem = false;
    private RefreshHandler mRedrawHandler = new RefreshHandler();
    int mode = 0;
    NewsItem newsItem;
    boolean[] read;
    SimpleAdapter summaryAdapter;
    String type;
    String url = null;

    class RefreshHandler extends Handler {
        RefreshHandler() {
        }

        public void handleMessage(Message msg) {
            if (!NewsSummaryActivity.this.active) {
                return;
            }
            if (NewsSummaryActivity.this.mode == 0) {
                NewsSummaryActivity.this.dialog.show();
                NewsSummaryActivity newsSummaryActivity = NewsSummaryActivity.this;
                newsSummaryActivity.mode++;
                refresh(10);
                return;
            }
            XmlParser.parse(NewsSummaryActivity.this.getContent(NewsSummaryActivity.this.url), NewsSummaryActivity.this);
        }

        public void refresh(long delayMillis) {
            removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    private class SimpleAdapter extends ArrayAdapter<NewsItem> {
        public SimpleAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            NewsItem item = (NewsItem) getItem(position);
            if (!(item == null || item.title == NewsSummaryActivity.ADTITLE)) {
                v = ((LayoutInflater) NewsSummaryActivity.this.getSystemService("layout_inflater")).inflate(R.layout.news_row, null);
                CharSequence title = item.title;
                CharSequence description = item.stripDescription;
                TextView tt = (TextView) v.findViewById(R.id.titleText);
                tt.setText(title);
                if (NewsSummaryActivity.this.read != null && NewsSummaryActivity.this.read.length > position && NewsSummaryActivity.this.read[position]) {
                    tt.setTypeface(Typeface.DEFAULT);
                }
                if (description != null) {
                    ((TextView) v.findViewById(R.id.desText)).setText(description);
                }
            }
            return v;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        this.summaryAdapter = new SimpleAdapter(this, R.layout.news_row);
        setListAdapter(this.summaryAdapter);
        getListView().setOnItemClickListener(this);
        if (savedInstanceState != null) {
            this.url = savedInstanceState.getString("url");
            this.type = savedInstanceState.getString("type");
        } else {
            this.url = getIntent().getExtras().getString("url");
            this.type = getIntent().getExtras().getString("type");
        }
        this.dialog = ProgressDialog.show(this, "", getString(R.string.fetching), true);
        this.mRedrawHandler.refresh(50);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.summaryAdapter.notifyDataSetChanged();
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
    }

    protected void onSaveInstanceState(Bundle outState) {
        this.mRedrawHandler.removeMessages(0);
        if (this.dialog != null) {
            this.dialog.cancel();
            this.dialog = null;
        }
        super.onSaveInstanceState(outState);
        outState.putString("url", this.url);
        outState.putString("type", this.type);
        this.active = false;
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int selection, long arg3) {
        if (selection >= 0 && selection < this.summaryAdapter.getCount()) {
            NewsItem nItem = (NewsItem) this.summaryAdapter.getItem(selection);
            if (nItem.title != ADTITLE) {
                if (this.read != null && this.read.length > selection) {
                    this.read[selection] = true;
                }
                Intent intent = new Intent(this, TopNewsDetailsActivity.class);
                intent.putExtra("url", nItem.url);
                intent.putExtra("type", this.type);
                startActivityForResult(intent, 1);
            }
        }
    }

    public void onEndTag(String name) {
        if (name.equals("item") || name.equals("story")) {
            if (this.newsItem != null) {
                this.summaryAdapter.add(this.newsItem);
            }
            this.inItem = false;
        }
    }

    public void onFinish() {
        if (this.active && this.dialog != null) {
            this.dialog.cancel();
            this.dialog = null;
        }
        this.read = new boolean[this.summaryAdapter.getCount()];
        this.summaryAdapter.notifyDataSetChanged();
    }

    public void onStartTag(String name) {
        if (name.equals("item")) {
            this.inItem = true;
        } else if (name.equals("story")) {
            this.inItem = true;
        }
    }

    public void onText(String text, String name) {
        if (!this.inItem) {
            return;
        }
        if (name.equals(DBAdapter.KEY_TITLE)) {
            this.newsItem = new NewsItem();
            this.newsItem.title = Entities.HTML40.unescape(Entities.XML.unescape(text));
        } else if ((name.equals("link") || name.equals("url")) && this.newsItem != null) {
            this.newsItem.url = Entities.HTML40.unescape(Entities.XML.unescape(text));
        } else if ((name.equals("description") || name.equals("excerpt")) && this.newsItem != null) {
            String allDescription = Entities.HTML40.unescape(Entities.XML.unescape(text));
            this.newsItem.stripDescription = XmlStripper.getText(allDescription);
        }
    }

    private void fixDescription() {
        try {
            int index = this.newsItem.stripDescription.indexOf("--");
            if (index != -1) {
                this.newsItem.stripDescription = this.newsItem.stripDescription.substring(index + 2);
            }
        } catch (Exception e) {
        }
    }

    String fetchPage(String url) throws Exception {
        String responseBody = "";
        return (String) new DefaultHttpClient().execute(new HttpGet(url), new BasicResponseHandler());
    }

    String getContent(String rssUrl) {
        try {
            return fetchPage(rssUrl);
        } catch (Exception e) {
            String data = "";
            try {
                BufferedInputStream in = new BufferedInputStream(new URL(rssUrl).openConnection().getInputStream(), 1024);
                ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
                byte[] b = new byte[512];
                while (true) {
                    int read = in.read(b);
                    if (read == -1) {
                        break;
                    }
                    bout.write(b, 0, read);
                }
                data = bout.toString("UTF-8");
                in.close();
            } catch (Exception e2) {
            }
            return data;
        }
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
        i.putExtra("android.intent.extra.SUBJECT", "Check this out. Daily news, word, joke, quote and horroscope on your android");
        i.putExtra("android.intent.extra.TEXT", "http://www.joansoft.com/dr.htm");
        startActivity(Intent.createChooser(i, "Spread DailyRead"));
    }

    public void onStartTag(String name, String params) {
    }
}
