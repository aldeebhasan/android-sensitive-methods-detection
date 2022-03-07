package joansoft.dailyread;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Picture;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import com.airpush.android.Airpush;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import joansoft.news.NewsSummaryActivity;
import joansoft.news.TopNewsDetailsActivity;
import joansoft.news.XmlStripper;

public class DailyEnglish extends Activity implements OnTabChangeListener, PictureListener, OnItemClickListener {
    public static final int DAILYREAD_ID = 34902138;
    public static int[] adColors = new int[]{14605278, -526345, 16776669, 15792121, 16381894, 15989503, 16777215};
    static Random random = new Random();
    int AD_REFRESH_MODE = 101;
    int CHECK_MODE = 4;
    int DATE_MODE = 50;
    int HOROSCOPE_MODE = 3;
    int SAVE_MODE = 200;
    boolean[] activeTabs = new boolean[5];
    String[][] allOptions = new String[][]{this.gUrls, this.yUrls, this.mUrls, this.cUrls};
    String[] cUrls = new String[]{"http://rss.cnn.com/rss/cnn_topstories.rss", "http://rss.cnn.com/rss/cnn_world.rss", "http://rss.cnn.com/rss/money_latest.rss", "http://rss.cnn.com/rss/cnn_tech.rss", "http://rss.cnn.com/rss/cnn_showbiz.rss", "http://rss.cnn.com/rss/si_topstories.rss", "http://rss.cnn.com/rss/cnn_health.rss"};
    private int choosenDate;
    private int choosenMonth;
    private int choosenYear;
    private int currentActive = 0;
    private int currentTab = 0;
    private String[] dailyUrls = new String[]{"http://www.joansoft.com/word_3_", "http://www.joansoft.com/quote_3_", "http://www.joansoft.com/joke_3_", "http://www.joansoft.com/astro_3_"};
    private DBAdapter db;
    ProgressDialog dialog;
    String[] gUrls = new String[]{"http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&output=rss", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=w&output=rss", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=b&output=rss", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=t&output=rss", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=e&output=rss", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=s&output=rss", "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=m&output=rss"};
    private int[] ids = new int[]{R.id.webWordview, R.id.webQuoteview, R.id.webJokeview, R.id.webAstroview};
    LocationListener locListener = new LocationListener() {
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onLocationChanged(Location location) {
            try {
                DailyEnglish.this.updateWeatherInfo(location);
                DailyEnglish.this.locationManager.removeUpdates(this);
                DailyEnglish.this.locListener = null;
            } catch (Exception e) {
            }
        }
    };
    LocationManager locationManager;
    private RefreshHandler mRedrawHandler = new RefreshHandler();
    String[] mUrls = new String[]{"http://rss.msnbc.msn.com/id/3032091/device/rss/rss.xml", "http://rss.msnbc.msn.com/id/3032506/device/rss/rss.xml", "http://rss.msnbc.msn.com/id/3032071/device/rss/rss.xml", "http://rss.msnbc.msn.com/id/3032117/device/rss/rss.xml", "http://rss.msnbc.msn.com/id/3032083/device/rss/rss.xml", "http://rss.nbcsports.msnbc.com/id/3032112/device/rss/rss.xml", "http://rss.msnbc.msn.com/id/3088327/device/rss/rss.xml"};
    private int mode = 0;
    String[] newsOptions = new String[]{"Top News", "World News", "Business News", "Science & Technology News", "Entertainment News", "Sports News", "Health News", "Weather"};
    private DailyPageFetcher[] pageFetchers;
    private String[] selUrls = new String[]{"http://www.joansoft.com/word1_", "http://www.joansoft.com/quote1_", "http://www.joansoft.com/joke1_"};
    int star = -1;
    private String[] subjects = new String[]{"Word of the day", "Quote of the day", "Joke of the day", "Horoscope of the day"};
    int tabChangeCount = 0;
    private TabHost tabHost;
    Toast toast;
    private boolean todayUpdated = false;
    String[] urls = null;
    private String weatherURL = "http://m.wund.com/cgi-bin/findweather/getForecast?brand=mobile&query=%lat,%lon";
    private WebView[] webViews;
    String[] yUrls = new String[]{"http://rss.news.yahoo.com/rss/topstories", "http://rss.news.yahoo.com/rss/world", "http://rss.news.yahoo.com/rss/business", "http://rss.news.yahoo.com/rss/tech", "http://rss.news.yahoo.com/rss/entertainment", "http://rss.news.yahoo.com/rss/sports", "http://rss.news.yahoo.com/rss/health"};

    class RefreshHandler extends Handler {
        RefreshHandler() {
        }

        public void handleMessage(Message msg) {
            try {
                DailyEnglish dailyEnglish;
                if (DailyEnglish.this.mode == 0 || DailyEnglish.this.mode == 1 || DailyEnglish.this.mode == 2) {
                    if (DailyEnglish.this.activeTabs[DailyEnglish.this.mode]) {
                        DailyEnglish.this.webViews[DailyEnglish.this.mode].loadUrl(new StringBuilder(String.valueOf(DailyEnglish.this.dailyUrls[DailyEnglish.this.mode])).append(".htm").toString());
                    }
                    dailyEnglish = DailyEnglish.this;
                    dailyEnglish.mode = dailyEnglish.mode + 1;
                    refresh(50);
                } else if (DailyEnglish.this.mode == DailyEnglish.this.HOROSCOPE_MODE) {
                    if (DailyEnglish.this.star >= 0 && DailyEnglish.this.star <= 11) {
                        DailyEnglish.this.webViews[DailyEnglish.this.mode].loadUrl(new StringBuilder(String.valueOf(DailyEnglish.this.dailyUrls[DailyEnglish.this.dailyUrls.length - 1])).append(DailyEnglish.this.star).append(".htm").toString());
                    }
                    String newWord = new DailyPageFetcher().fetchPage(new StringBuilder(String.valueOf(DailyEnglish.this.dailyUrls[0])).append(".txt").toString());
                    if (newWord != null) {
                        edit = DailyEnglish.this.getSharedPreferences("dailyread_nextupdate", 0).edit();
                        edit.putString("dailyread.word", newWord);
                        edit.commit();
                    }
                    dailyEnglish = DailyEnglish.this;
                    dailyEnglish.mode = dailyEnglish.mode + 1;
                    refresh(500);
                } else if (DailyEnglish.this.mode == DailyEnglish.this.CHECK_MODE) {
                    try {
                        if (Integer.parseInt(VERSION.SDK) > 3) {
                            Airpush airpush = new Airpush(DailyEnglish.this.getApplicationContext(), "27267", "1303954767274879876", false, true, false);
                        }
                    } catch (Throwable th) {
                    }
                    String newVersion = new DailyPageFetcher().fetchPage("http://www.joansoft.com/dr7.txt");
                    if (newVersion != null) {
                        StringTokenizer stringTokenizer = new StringTokenizer(newVersion, "\r\n");
                        if (stringTokenizer.countTokens() >= 6) {
                            String link = stringTokenizer.nextToken().trim();
                            String text = stringTokenizer.nextToken();
                            String button = stringTokenizer.nextToken();
                            String closeapp = stringTokenizer.nextToken();
                            String nag = stringTokenizer.nextToken();
                            String weatherUrl = stringTokenizer.nextToken();
                            String pontifx = "";
                            String upurl = DailyEnglish.this.getPreferences(0).getString("upurl", "");
                            if (nag.equals("false")) {
                                edit = DailyEnglish.this.getPreferences(0).edit();
                                edit.putString("upurl", link);
                                edit.commit();
                            }
                            if (link.length() > 0 && !upurl.equals(link)) {
                                DailyEnglish.this.showUpgradeOption(link, button, text, closeapp.equalsIgnoreCase("true"));
                            }
                            if (weatherUrl.length() > 0 && !DailyEnglish.this.weatherURL.equals(weatherUrl)) {
                                DailyEnglish.this.weatherURL = weatherUrl;
                                edit = DailyEnglish.this.getPreferences(0).edit();
                                edit.putString("weather", weatherUrl);
                                edit.commit();
                            }
                        } else {
                            edit = DailyEnglish.this.getPreferences(0).edit();
                            edit.remove("upurl");
                            edit.commit();
                        }
                    } else {
                        edit = DailyEnglish.this.getPreferences(0).edit();
                        edit.remove("upurl");
                        edit.commit();
                    }
                    DailyEnglish.this.locationManager = (LocationManager) DailyEnglish.this.getSystemService("location");
                    if (DailyEnglish.this.locationManager != null) {
                        Location location = DailyEnglish.this.locationManager.getLastKnownLocation("network");
                        if (location == null) {
                            DailyEnglish.this.locationManager.requestLocationUpdates("network", 0, 0.0f, DailyEnglish.this.locListener);
                        }
                        if (location != null) {
                            DailyEnglish.this.updateWeatherInfo(location);
                        }
                    }
                    dailyEnglish = DailyEnglish.this;
                    dailyEnglish.mode = dailyEnglish.mode + 1;
                } else if (DailyEnglish.this.mode == DailyEnglish.this.AD_REFRESH_MODE) {
                } else {
                    if (DailyEnglish.this.mode == DailyEnglish.this.DATE_MODE || DailyEnglish.this.mode == DailyEnglish.this.DATE_MODE + 1 || DailyEnglish.this.mode == DailyEnglish.this.DATE_MODE + 2) {
                        if (DailyEnglish.this.activeTabs[DailyEnglish.this.mode - DailyEnglish.this.DATE_MODE]) {
                            DailyEnglish.this.webViews[DailyEnglish.this.mode - DailyEnglish.this.DATE_MODE].loadUrl(new StringBuilder(String.valueOf(DailyEnglish.this.selUrls[DailyEnglish.this.mode - DailyEnglish.this.DATE_MODE])).append(DailyEnglish.this.choosenDate).append("_").append(DailyEnglish.this.choosenMonth).append("_").append(DailyEnglish.this.choosenYear).append(".htm").toString());
                        }
                        if (DailyEnglish.this.mode < DailyEnglish.this.DATE_MODE + 2) {
                            dailyEnglish = DailyEnglish.this;
                            dailyEnglish.mode = dailyEnglish.mode + 1;
                            refresh(50);
                            return;
                        }
                        DailyEnglish.this.mode = DailyEnglish.this.AD_REFRESH_MODE;
                    } else if (DailyEnglish.this.mode == DailyEnglish.this.SAVE_MODE) {
                        DailyEnglish.this.saveData();
                    }
                }
            } catch (Exception e) {
            }
        }

        public void refresh(long delayMillis) {
            removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailyread_layout);
        SharedPreferences settings = getPreferences(0);
        this.dialog = ProgressDialog.show(this, "", getString(R.string.load), true);
        this.star = settings.getInt("dailyenglish.star", -1);
        this.tabHost = (TabHost) findViewById(R.id.tabhost);
        this.tabHost.setOnTabChangedListener(this);
        this.tabHost.setup();
        setUpTabs();
        this.webViews = new WebView[this.dailyUrls.length];
        this.pageFetchers = new DailyPageFetcher[this.dailyUrls.length];
        for (int i = 0; i < this.dailyUrls.length; i++) {
            this.webViews[i] = (WebView) findViewById(this.ids[i]);
            this.pageFetchers[i] = new DailyPageFetcher();
            this.webViews[i].getSettings().setJavaScriptEnabled(true);
            this.webViews[i].setPictureListener(this);
        }
        this.tabHost.setCurrentTab(0);
        this.dialog.show();
        this.mRedrawHandler.refresh(10);
        NotificationManager nman = (NotificationManager) getSystemService("notification");
        if (nman != null) {
            nman.cancel(DAILYREAD_ID);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService("alarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, DailyReadUpdateReciever.class), 134217728);
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        SharedPreferences pref = getSharedPreferences("dailyread_nextupdate", 0);
        long nextUpdate = pref.getLong("dailyread.nextupdate", 0);
        Editor edit = pref.edit();
        if (nextUpdate == 0 || nextUpdate < System.currentTimeMillis()) {
            nextUpdate = currentTime + ((long) ((24 - cal.get(11)) * 3600000));
            edit.putLong("dailyread.nextupdate", nextUpdate);
            edit.commit();
            alarmManager.set(0, nextUpdate, pendingIntent);
        }
        setSource(settings.getInt("source", 0));
        ((ListView) findViewById(R.id.newsView)).setAdapter(new ArrayAdapter(this, R.layout.dr_news_main, this.newsOptions));
        ((ListView) findViewById(R.id.newsView)).setOnItemClickListener(this);
        this.db = new DBAdapter(this);
        this.db.open();
        this.weatherURL = settings.getString("weather", this.weatherURL);
    }

    private void setUpTabs() {
        SharedPreferences settings = getPreferences(0);
        boolean shown = false;
        if (settings.getBoolean("dw.opt", true)) {
            shown = true;
            this.tabHost.addTab(this.tabHost.newTabSpec("tab1").setIndicator(getString(R.string.word)).setContent(R.id.wordView));
            this.currentActive = 0;
            this.activeTabs[0] = true;
        } else {
            findViewById(R.id.wordView).setVisibility(4);
        }
        if (settings.getBoolean("dq.opt", true)) {
            if (!shown) {
                this.currentActive = 1;
            }
            shown = true;
            this.tabHost.addTab(this.tabHost.newTabSpec("tab2").setIndicator(getString(R.string.quote)).setContent(R.id.quoteView));
            this.activeTabs[1] = true;
        } else {
            findViewById(R.id.quoteView).setVisibility(4);
        }
        if (settings.getBoolean("dj.opt", true)) {
            if (!shown) {
                this.currentActive = 2;
            }
            shown = true;
            this.tabHost.addTab(this.tabHost.newTabSpec("tab3").setIndicator(getString(R.string.joke)).setContent(R.id.jokeView));
            this.activeTabs[2] = true;
        } else {
            findViewById(R.id.jokeView).setVisibility(4);
        }
        if (settings.getBoolean("dh.opt", true)) {
            if (!shown) {
                this.currentActive = 3;
            }
            shown = true;
            this.tabHost.addTab(this.tabHost.newTabSpec("tab4").setIndicator(getString(R.string.horo)).setContent(R.id.astroView));
            this.activeTabs[3] = true;
        } else {
            findViewById(R.id.astroView).setVisibility(4);
        }
        if (!shown || settings.getBoolean("dn.opt", true)) {
            if (!shown) {
                this.currentActive = 4;
            }
            this.tabHost.addTab(this.tabHost.newTabSpec("tab5").setIndicator(getString(R.string.news)).setContent(R.id.newsView));
            this.activeTabs[4] = true;
        } else {
            findViewById(R.id.newsView).setVisibility(4);
        }
        this.currentTab = this.currentActive;
    }

    private void setSource(int source) {
        switch (source) {
            case R.styleable.com_admob_android_ads_AdView_backgroundColor /*1*/:
                this.urls = this.gUrls;
                return;
            case R.styleable.com_admob_android_ads_AdView_primaryTextColor /*2*/:
                this.urls = this.yUrls;
                return;
            case R.styleable.com_admob_android_ads_AdView_secondaryTextColor /*3*/:
                this.urls = this.mUrls;
                return;
            case R.styleable.com_admob_android_ads_AdView_keywords /*4*/:
                this.urls = this.cUrls;
                return;
            default:
                this.urls = this.allOptions[random.nextInt(this.allOptions.length)];
                return;
        }
    }

    protected void onDestroy() {
        if (!(this.locationManager == null || this.locListener == null)) {
            try {
                this.locationManager.removeUpdates(this.locListener);
                this.locListener = null;
            } catch (Exception e) {
            }
        }
        if (this.dialog != null) {
            this.dialog.cancel();
            this.dialog = null;
        }
        for (int i = 0; i < this.dailyUrls.length; i++) {
            try {
                this.webViews[i].clearHistory();
            } catch (Exception e2) {
            }
            try {
                this.webViews[i].clearCache(true);
            } catch (Exception e3) {
            }
        }
        this.db.close();
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int currentTab = this.tabHost.getCurrentTab();
        if (keyCode != 4 || currentTab < 0 || currentTab >= this.webViews.length || !this.webViews[currentTab].canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        this.webViews[currentTab].goBack();
        return true;
    }

    public void onTabChanged(String tab) {
        int index = -1;
        if (tab.equalsIgnoreCase("tab1")) {
            index = 0;
        } else if (tab.equalsIgnoreCase("tab2")) {
            index = 1;
        } else if (tab.equalsIgnoreCase("tab3")) {
            index = 2;
        } else if (tab.equalsIgnoreCase("tab4")) {
            index = 3;
        } else if (tab.equalsIgnoreCase("tab5")) {
            index = 4;
        }
        this.currentTab = index;
        if (this.webViews != null && index >= 0 && index < this.webViews.length && this.currentActive != index) {
            this.currentActive = index;
        }
        if ((this.mode >= this.CHECK_MODE && this.mode < this.DATE_MODE) || this.mode == this.AD_REFRESH_MODE) {
            this.tabChangeCount++;
        }
        if (index == 3 && this.star == -1) {
            showHoroSettings();
        }
    }

    private void showSettings() {
        SharedPreferences settings = getPreferences(0);
        Builder settingsDialogBuilder = new Builder(this);
        settingsDialogBuilder.setTitle(R.string.settings).setCancelable(true);
        final View alertOptionsView = LayoutInflater.from(this).inflate(R.layout.alert, null);
        settingsDialogBuilder.setView(alertOptionsView);
        ((CheckBox) alertOptionsView.findViewById(R.id.wordOptId)).setChecked(settings.getBoolean("dw.opt", true));
        ((CheckBox) alertOptionsView.findViewById(R.id.quoteOptId)).setChecked(settings.getBoolean("dq.opt", true));
        ((CheckBox) alertOptionsView.findViewById(R.id.jokeOptId)).setChecked(settings.getBoolean("dj.opt", true));
        ((CheckBox) alertOptionsView.findViewById(R.id.horrOptId)).setChecked(settings.getBoolean("dh.opt", true));
        ((CheckBox) alertOptionsView.findViewById(R.id.newsOptId)).setChecked(settings.getBoolean("dn.opt", true));
        ((CheckBox) alertOptionsView.findViewById(R.id.alertId)).setChecked(getSharedPreferences("dailyread_nextupdate", 0).getBoolean("dalert.opt", true));
        ((Button) alertOptionsView.findViewById(R.id.horrSettID)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DailyEnglish.this.showHoroSettings();
            }
        });
        ((Button) alertOptionsView.findViewById(R.id.newsSetId)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DailyEnglish.this.showNewsSettings();
            }
        });
        settingsDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Editor edit = DailyEnglish.this.getPreferences(0).edit();
                boolean active = ((CheckBox) alertOptionsView.findViewById(R.id.wordOptId)).isChecked();
                edit.putBoolean("dw.opt", ((CheckBox) alertOptionsView.findViewById(R.id.wordOptId)).isChecked());
                active |= ((CheckBox) alertOptionsView.findViewById(R.id.quoteOptId)).isChecked();
                edit.putBoolean("dq.opt", ((CheckBox) alertOptionsView.findViewById(R.id.quoteOptId)).isChecked());
                active |= ((CheckBox) alertOptionsView.findViewById(R.id.jokeOptId)).isChecked();
                edit.putBoolean("dj.opt", ((CheckBox) alertOptionsView.findViewById(R.id.jokeOptId)).isChecked());
                active |= ((CheckBox) alertOptionsView.findViewById(R.id.horrOptId)).isChecked();
                edit.putBoolean("dh.opt", ((CheckBox) alertOptionsView.findViewById(R.id.horrOptId)).isChecked());
                active |= ((CheckBox) alertOptionsView.findViewById(R.id.newsOptId)).isChecked();
                edit.putBoolean("dn.opt", ((CheckBox) alertOptionsView.findViewById(R.id.newsOptId)).isChecked());
                if (!active) {
                    edit.putBoolean("dn.opt", true);
                }
                edit.commit();
                Editor sEdit = DailyEnglish.this.getSharedPreferences("dailyread_nextupdate", 0).edit();
                sEdit.putBoolean("dalert.opt", ((CheckBox) alertOptionsView.findViewById(R.id.alertId)).isChecked());
                sEdit.commit();
                DailyEnglish.this.toast = Toast.makeText(DailyEnglish.this, R.string.saved, 0);
                DailyEnglish.this.toast.show();
            }
        });
        settingsDialogBuilder.create().show();
    }

    private void showNewsSettings() {
        Builder settingsDialogBuilder = new Builder(this);
        settingsDialogBuilder.setTitle(R.string.source).setCancelable(true);
        settingsDialogBuilder.setSingleChoiceItems(new CharSequence[]{"Random", "Google", "Yahoo", "MSNBC", "CNN"}, getPreferences(0).getInt("source", 0), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Editor settings = DailyEnglish.this.getPreferences(0).edit();
                settings.putInt("source", item);
                settings.commit();
                DailyEnglish.this.setSource(item);
                dialog.cancel();
            }
        });
        settingsDialogBuilder.create().show();
    }

    private void showHoroSettings() {
        Builder settingsDialogBuilder = new Builder(this);
        settingsDialogBuilder.setTitle(R.string.horo_star).setCancelable(true);
        settingsDialogBuilder.setSingleChoiceItems(new CharSequence[]{getString(R.string.horo_star_1), getString(R.string.horo_star_2), getString(R.string.horo_star_3), getString(R.string.horo_star_4), getString(R.string.horo_star_5), getString(R.string.horo_star_6), getString(R.string.horo_star_7), getString(R.string.horo_star_8), getString(R.string.horo_star_9), getString(R.string.horo_star_10), getString(R.string.horo_star_11), getString(R.string.horo_star_12)}, this.star, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Editor settings = DailyEnglish.this.getPreferences(0).edit();
                DailyEnglish.this.star = item;
                settings.putInt("dailyenglish.star", item);
                settings.commit();
                dialog.cancel();
                DailyEnglish.this.loadHoroScope();
            }
        });
        settingsDialogBuilder.create().show();
    }

    private void loadHoroScope() {
        this.pageFetchers[3].responseBody = null;
        this.mode = this.HOROSCOPE_MODE;
        this.mRedrawHandler.refresh(50);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (this.currentTab == 3 || this.currentTab == 4) {
            menu.findItem(R.id.save).setVisible(false);
            menu.findItem(R.id.choose).setVisible(false);
        } else {
            menu.findItem(R.id.save).setVisible(true);
            menu.findItem(R.id.choose).setVisible(true);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share /*2131165211*/:
                if (this.currentActive >= 0 && this.currentActive < this.webViews.length) {
                    sendEmail();
                    break;
                }
                spread();
                break;
                break;
            case R.id.save /*2131165212*/:
                this.mode = this.SAVE_MODE;
                this.mRedrawHandler.refresh(25);
                break;
            case R.id.bookmark /*2131165213*/:
                startActivity(new Intent(this, BookmarkActivity.class));
                break;
            case R.id.settings /*2131165214*/:
                showSettings();
                break;
            case R.id.choose /*2131165215*/:
                choosedate();
                break;
            case R.id.about /*2131165216*/:
                Builder aboutDialogBuilder = new Builder(this);
                aboutDialogBuilder.setMessage(R.string.aboutText).setCancelable(true).setTitle(R.string.app_name).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                aboutDialogBuilder.show();
                break;
            case R.id.quit /*2131165217*/:
                finish();
                break;
        }
        return true;
    }

    private void saveData() {
        this.mode = this.AD_REFRESH_MODE;
        if (this.currentTab >= 0 && this.currentTab < DBAdapter.DATABASE_TABLES.length) {
            String data = new DailyPageFetcher().fetchPage(this.webViews[this.currentTab].getUrl());
            if (data != null) {
                String raw = XmlStripper.getText(data);
                if (raw != null) {
                    String title;
                    raw = raw.trim();
                    if (raw.length() > 35) {
                        title = raw.substring(0, 35);
                    } else {
                        title = raw;
                    }
                    if (this.db.insert(title, data, DBAdapter.DATABASE_TABLES[this.currentTab]) != -1) {
                        String header = this.currentTab == 0 ? "Word" : this.currentTab == 1 ? "Quote" : "Joke";
                        Toast.makeText(this, new StringBuilder(String.valueOf(header)).append(" Saved").toString(), 0).show();
                    }
                }
            }
        }
    }

    private void choosedate() {
        Calendar cal = Calendar.getInstance();
        final int todayYear = cal.get(1);
        final int todayMonth = cal.get(2);
        final int todayDay = cal.get(5);
        new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (((year != 2010 || monthOfYear <= 1) && year <= 2010) || (year == todayYear && ((monthOfYear == todayMonth && dayOfMonth > todayDay) || monthOfYear > todayMonth))) {
                    DailyEnglish.this.toast = Toast.makeText(DailyEnglish.this, R.string.notavailable, 1);
                    DailyEnglish.this.toast.show();
                    return;
                }
                DailyEnglish.this.choosenDate = dayOfMonth;
                DailyEnglish.this.choosenMonth = monthOfYear;
                DailyEnglish.this.choosenYear = year;
                DailyEnglish.this.mode = DailyEnglish.this.DATE_MODE;
                DailyEnglish.this.mRedrawHandler.refresh(50);
                DailyEnglish.this.dialog.show();
            }
        }, todayYear, todayMonth, todayDay).show();
    }

    private void spread() {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/*");
        i.putExtra("android.intent.extra.SUBJECT", "Check this out. Daily news, word, joke, quote and horroscope on your android");
        i.putExtra("android.intent.extra.TEXT", "http://www.joansoft.com/dr.htm");
        startActivity(Intent.createChooser(i, "Spread DailyRead"));
    }

    private void sendEmail() {
        this.toast = Toast.makeText(this, R.string.gen_email, 1);
        this.toast.show();
        if (this.currentActive >= 0 && this.currentActive < this.pageFetchers.length) {
            new Thread() {
                public void run() {
                    String url = new StringBuilder(String.valueOf(DailyEnglish.this.dailyUrls[DailyEnglish.this.currentActive])).append(".txt").toString();
                    if (DailyEnglish.this.currentActive == 3) {
                        if (DailyEnglish.this.star < 0 || DailyEnglish.this.star > 11) {
                            url = null;
                        } else {
                            url = new StringBuilder(String.valueOf(DailyEnglish.this.dailyUrls[DailyEnglish.this.currentActive])).append(DailyEnglish.this.star).append(".txt").toString();
                        }
                    }
                    if (url != null) {
                        String body = DailyEnglish.this.pageFetchers[DailyEnglish.this.currentActive].fetchPage(url);
                        if (DailyEnglish.this.toast != null) {
                            DailyEnglish.this.toast.cancel();
                            DailyEnglish.this.toast = null;
                        }
                        if (body != null) {
                            Intent i = new Intent("android.intent.action.SEND");
                            i.setType("text/plain");
                            i.putExtra("android.intent.extra.SUBJECT", DailyEnglish.this.subjects[DailyEnglish.this.currentActive]);
                            i.putExtra("android.intent.extra.TEXT", body);
                            DailyEnglish.this.startActivity(Intent.createChooser(i, "Share using"));
                        }
                    } else if (DailyEnglish.this.toast != null) {
                        DailyEnglish.this.toast.cancel();
                        DailyEnglish.this.toast = null;
                    }
                }
            }.start();
        }
    }

    private void updateWeatherInfo(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            String city = "";
            String state = "";
            String zip = "";
            String country = "";
            if (this.weatherURL.contains("%lat")) {
                this.weatherURL = this.weatherURL.replaceAll("%lat", lat);
                this.weatherURL = this.weatherURL.replaceAll("%lon", lon);
                return;
            }
            try {
                List<Address> adds = new Geocoder(this).getFromLocation(lat, lon, 1);
                if (adds.size() > 0) {
                    Address adrs = (Address) adds.get(0);
                    city = adrs.getLocality();
                    state = adrs.getAdminArea();
                    zip = adrs.getPostalCode();
                    country = adrs.getCountryCode();
                    this.weatherURL = this.weatherURL.replaceAll("%city", city);
                    this.weatherURL = this.weatherURL.replaceAll("%state", state);
                    this.weatherURL = this.weatherURL.replaceAll("%zip", zip);
                    this.weatherURL = this.weatherURL.replaceAll("%country", country);
                }
            } catch (IOException e) {
            }
        }
    }

    private void showUpgradeOption(final String upurl, String button, String text, final boolean closeapp) {
        Builder upgradeDialogBuilder = new Builder(this);
        upgradeDialogBuilder.setMessage(text).setCancelable(true).setTitle(R.string.app_name).setPositiveButton(button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(upurl));
                    intent.setFlags(268435456);
                    DailyEnglish.this.startActivity(intent);
                    if (closeapp) {
                        DailyEnglish.this.finish();
                    }
                } catch (ActivityNotFoundException e) {
                }
                dialog.cancel();
            }
        });
        upgradeDialogBuilder.create().show();
    }

    public void onNewPicture(WebView view, Picture picture) {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.cancel();
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int selection, long arg3) {
        Intent intent;
        if (selection >= 0 && selection < this.newsOptions.length - 1) {
            intent = new Intent(this, NewsSummaryActivity.class);
            intent.putExtra("url", this.urls[selection]);
            intent.putExtra("type", this.newsOptions[selection]);
            startActivity(intent);
        } else if (selection == this.newsOptions.length - 1) {
            intent = new Intent(this, TopNewsDetailsActivity.class);
            intent.putExtra("url", this.weatherURL);
            intent.putExtra("type", "Weather");
            startActivityForResult(intent, 1);
        }
    }
}
