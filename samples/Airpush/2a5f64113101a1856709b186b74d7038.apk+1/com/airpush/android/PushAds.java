package com.airpush.android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import java.io.InputStream;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class PushAds extends Activity implements OnClickListener {
    private static Context context;
    private static HttpEntity entity;
    private static DefaultHttpClient httpClient;
    private static BasicHttpParams httpParameters;
    private static HttpPost httpPost;
    private static BasicHttpResponse httpResponse;
    private static String size = "1";
    private static int timeoutConnection;
    private static int timeoutSocket;
    private static String url = null;
    private static List<NameValuePair> values = null;
    private String action;
    private OnClickListener adOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            try {
                PushAds.this.displayAd(PushAds.this.clickUrl);
                PushAds.values = SetPreferences.setValues(PushAds.this.getApplicationContext());
                PushAds.values.add(new BasicNameValuePair("model", "log"));
                PushAds.values.add(new BasicNameValuePair("action", "setfptracking"));
                PushAds.values.add(new BasicNameValuePair("APIKEY", PushAds.this.apikey));
                PushAds.values.add(new BasicNameValuePair("event", "fclick"));
                PushAds.values.add(new BasicNameValuePair("campId", PushAds.this.campaignId));
                PushAds.values.add(new BasicNameValuePair("creativeId", PushAds.this.creativeId));
                new Handler().postDelayed(PushAds.this.sendAdValues_Task, 5000);
            } catch (Exception e) {
                Log.i("AirpushSDK", "Display Ad Network Error, please try again later. ");
            }
        }
    };
    private String adSize;
    private String adType;
    private String apikey = null;
    private String appId = null;
    private BitmapDrawable bd;
    private int btn1Height;
    private float btn1Padding;
    private int btn2Height;
    private float btn2Padding;
    private int buttonMargin;
    private String campId = null;
    private String campaignId;
    private String clickUrl;
    private String creativeId = null;
    private int h;
    private String header;
    private int icon = 17301620;
    private float imagePadding;
    private String imageUrl;
    private Intent intent;
    private boolean more = true;
    private String moreButtonColor = "#000000";
    private String moreButtonText;
    private String moreButtonTextColor = "#FFFFFF";
    private String moreInfoClickUrl;
    private OnClickListener moreOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            try {
                PushAds.this.displayAd(PushAds.this.clickUrl);
                PushAds.values = SetPreferences.setValues(PushAds.this.getApplicationContext());
                PushAds.values.add(new BasicNameValuePair("model", "log"));
                PushAds.values.add(new BasicNameValuePair("action", "setfptracking"));
                PushAds.values.add(new BasicNameValuePair("APIKEY", PushAds.this.apikey));
                PushAds.values.add(new BasicNameValuePair("event", "fclick"));
                PushAds.values.add(new BasicNameValuePair("campId", PushAds.this.campaignId));
                PushAds.values.add(new BasicNameValuePair("creativeId", PushAds.this.creativeId));
                new Handler().postDelayed(PushAds.this.sendAdValues_Task, 3000);
            } catch (Exception e) {
                Log.i("AirpushSDK", "Display Ad Network Error, please try again later. ");
            }
        }
    };
    private String phoneNumber;
    private Runnable postData = new Runnable() {
        public void run() {
            PushAds.this.postValues();
        }
    };
    private HttpEntity response;
    private Runnable sendAdValues_Task = new Runnable() {
        public void run() {
            if (!PushAds.this.showInterstitialtestAd) {
                PushAds.this.sendClickData();
            }
        }
    };
    private Runnable sendImpression_Task = new Runnable() {
        public void run() {
            if (!PushAds.this.showInterstitialtestAd) {
                PushAds.this.sendImpressionData();
            }
        }
    };
    private boolean showAd = true;
    private boolean showDialog = true;
    private boolean showInterstitialtestAd;
    private String skipButtonColor = "#000000";
    private String skipButtonText;
    private String skipButtonTextColor = "#FFFFFF";
    private OnClickListener skipOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            PushAds.this.finish();
        }
    };
    private String smsText;
    private String smsToNumber;
    private boolean test = false;
    private int textSize;
    String url2 = "http://api.airpush.com/api.php";
    private int w;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        this.intent = getIntent();
        this.action = this.intent.getAction();
        getWindowManager().getDefaultDisplay();
        this.h = 320;
        this.w = 350;
        this.adType = this.intent.getStringExtra("adType");
        if (this.adType.equals("searchad")) {
            Log.i("AirpushSDK", "Search Clicked");
            return;
        }
        if (this.adType.equals("ShoWDialog")) {
            this.appId = this.intent.getStringExtra("appId");
            this.apikey = this.intent.getStringExtra("apikey");
            this.test = this.intent.getBooleanExtra("test", false);
            this.icon = this.intent.getIntExtra("icon", 17301620);
            showDialog();
        }
        SharedPreferences NotificationPrefs;
        Intent postAdValuesIntent;
        if (this.action.equals("CC")) {
            if (this.adType.equals("CC")) {
                Log.i("AirpushSDK", "Pushing Ads.....");
                if (context.getSharedPreferences("airpushNotificationPref", 1) != null) {
                    NotificationPrefs = context.getSharedPreferences("airpushNotificationPref", 1);
                    this.appId = NotificationPrefs.getString("appId", this.intent.getStringExtra("appId"));
                    this.apikey = NotificationPrefs.getString("apikey", this.intent.getStringExtra("apikey"));
                    this.phoneNumber = NotificationPrefs.getString("number", this.intent.getStringExtra("number"));
                    this.campId = NotificationPrefs.getString("campId", this.intent.getStringExtra("campId"));
                    this.creativeId = NotificationPrefs.getString("creativeId", this.intent.getStringExtra("creativeId"));
                } else {
                    this.appId = this.intent.getStringExtra("appId");
                    this.apikey = this.intent.getStringExtra("apikey");
                    this.campId = this.intent.getStringExtra("campId");
                    this.creativeId = this.intent.getStringExtra("creativeId");
                    this.phoneNumber = this.intent.getStringExtra("number");
                }
                postAdValuesIntent = new Intent();
                postAdValuesIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                postAdValuesIntent.putExtra("type", "PostAdValues");
                postAdValuesIntent.putExtras(this.intent);
                startService(postAdValuesIntent);
                callNumber();
            }
        } else if (this.action.equals("CM")) {
            if (this.adType.equals("CM")) {
                Log.i("AirpushSDK", "Pushing Ads.....");
                if (context.getSharedPreferences("airpushNotificationPref", 1) != null) {
                    NotificationPrefs = context.getSharedPreferences("airpushNotificationPref", 1);
                    this.appId = NotificationPrefs.getString("appId", this.intent.getStringExtra("appId"));
                    this.apikey = NotificationPrefs.getString("apikey", this.intent.getStringExtra("apikey"));
                    this.smsText = NotificationPrefs.getString("sms", this.intent.getStringExtra("sms"));
                    this.campId = NotificationPrefs.getString("campId", this.intent.getStringExtra("campId"));
                    this.creativeId = NotificationPrefs.getString("creativeId", this.intent.getStringExtra("creativeId"));
                    this.smsToNumber = NotificationPrefs.getString("number", this.intent.getStringExtra("number"));
                } else {
                    this.appId = this.intent.getStringExtra("appId");
                    this.apikey = this.intent.getStringExtra("apikey");
                    this.campId = this.intent.getStringExtra("campId");
                    this.creativeId = this.intent.getStringExtra("creativeId");
                    this.smsText = this.intent.getStringExtra("sms");
                    this.smsToNumber = this.intent.getStringExtra("number");
                }
                postAdValuesIntent = new Intent();
                postAdValuesIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                postAdValuesIntent.putExtra("type", "PostAdValues");
                postAdValuesIntent.putExtras(this.intent);
                startService(postAdValuesIntent);
                sendSms();
            }
        } else if (!this.action.equals("Web And App")) {
        } else {
            if (this.adType.equals("W") || this.adType.equals("A")) {
                Log.i("AirpushSDK", "Pushing Ads.....");
                if (context.getSharedPreferences("airpushNotificationPref", 1) != null) {
                    NotificationPrefs = context.getSharedPreferences("airpushNotificationPref", 1);
                    this.appId = NotificationPrefs.getString("appId", this.intent.getStringExtra("appId"));
                    this.apikey = NotificationPrefs.getString("apikey", this.intent.getStringExtra("apikey"));
                    url = NotificationPrefs.getString("url", this.intent.getStringExtra("url"));
                    this.campId = NotificationPrefs.getString("campId", this.intent.getStringExtra("campId"));
                    this.creativeId = NotificationPrefs.getString("creativeId", this.intent.getStringExtra("creativeId"));
                    this.header = NotificationPrefs.getString("header", this.intent.getStringExtra("header"));
                } else {
                    this.appId = this.intent.getStringExtra("appId");
                    this.apikey = this.intent.getStringExtra("apikey");
                    this.campId = this.intent.getStringExtra("campId");
                    this.creativeId = this.intent.getStringExtra("creativeId");
                    url = this.intent.getStringExtra("url");
                    this.header = this.intent.getStringExtra("header");
                }
                postAdValuesIntent = new Intent();
                postAdValuesIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                postAdValuesIntent.putExtra("type", "PostAdValues");
                postAdValuesIntent.putExtras(this.intent);
                startService(postAdValuesIntent);
                setTitle(this.header);
                display(url);
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    private void callNumber() {
        Log.i("AirpushSDK", "Pushing CC Ads.....");
        startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + this.phoneNumber)));
    }

    private void sendSms() {
        Log.i("AirpushSDK", "Pushing CM Ads.....");
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + this.smsToNumber));
        intent.putExtra("sms_body", this.smsText);
        startActivity(intent);
    }

    private void display(String url) {
        Log.i("AirpushSDK", "Pushing Web and App Ads.....");
        CustomWebView webView = new CustomWebView(this);
        webView.loadUrl(url);
        setContentView(webView);
    }

    public void show(WebView webView) {
        setContentView(webView);
    }

    protected void showDialog() {
        try {
            Builder builder = new Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Support the App developer by enabling ads in the notification tray, limited to 1 per day.");
            builder.setPositiveButton("I Agree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PushAds.this.showDialog = false;
                    PushAds.this.showAd = true;
                    PushAds.this.startAirpush();
                    PushAds.this.finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PushAds.this.showDialog = false;
                    PushAds.this.showAd = false;
                    PushAds.this.startAirpush();
                    PushAds.this.finish();
                }
            });
            builder.create();
            builder.show();
        } catch (Exception e) {
        }
    }

    private void startAirpush() {
        new Airpush().startAirpush(context, this.appId, this.apikey, this.test, this.showDialog, this.icon, this.showAd);
    }

    private void postValues() {
        this.response = HttpPostData.postData(values, getApplicationContext());
    }

    protected void onPause() {
        super.onPause();
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void displayAds() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        new Handler().postDelayed(this.sendImpression_Task, 5000);
        try {
            float scale = getResources().getDisplayMetrics().density;
            RelativeLayout relativeLayout;
            ImageView iv;
            Button skip;
            if (this.more) {
                LinearLayout rl = new LinearLayout(this);
                rl.setOrientation(1);
                relativeLayout = new RelativeLayout(this);
                this.h = (int) (((float) this.h) * scale);
                this.w = (int) (((float) this.w) * scale);
                this.imagePadding = 10.0f * scale;
                this.btn1Padding = 10.0f * scale;
                this.btn2Padding = 5.0f * scale;
                this.btn1Height = (int) (50.0f * scale);
                this.btn2Height = (int) (50.0f * scale);
                iv = new ImageView(this);
                LayoutParams lp0 = new LayoutParams(70, 70);
                lp0.width = (int) (320.0f * scale);
                lp0.gravity = 1;
                LayoutParams lp1 = new LayoutParams(70, 70);
                lp1.gravity = 17;
                lp1.topMargin = (int) (5.0f * scale);
                LayoutParams btn1LP = new LayoutParams(70, 70);
                btn1LP.topMargin = (int) this.btn1Padding;
                btn1LP.width = (int) (320.0f * scale);
                btn1LP.gravity = 1;
                LayoutParams btn2LP = new LayoutParams(70, 70);
                btn2LP.topMargin = (int) this.btn2Padding;
                btn2LP.width = (int) (320.0f * scale);
                btn2LP.gravity = 1;
                lp1.height = this.h;
                lp1.width = this.w;
                lp0.height = (int) (((float) Integer.parseInt(this.adSize)) * scale);
                Log.i("AirpushSDK", "height : " + lp0.height);
                if (this.bd != null) {
                    iv.setBackgroundDrawable(this.bd);
                    skip = new Button(this);
                    skip.setText(this.skipButtonText);
                    skip.setHeight(this.btn1Height);
                    skip.setBackgroundColor(Color.parseColor(this.skipButtonColor));
                    skip.setTextColor(Color.parseColor(this.skipButtonTextColor));
                    skip.setTextSize((float) this.textSize);
                    skip.setTypeface(Typeface.DEFAULT_BOLD);
                    lp0.topMargin = this.buttonMargin;
                    Button moreInfo = new Button(this);
                    moreInfo.setBackgroundColor(Color.parseColor(this.moreButtonColor));
                    moreInfo.setTextColor(Color.parseColor(this.moreButtonTextColor));
                    moreInfo.setTextSize((float) this.textSize);
                    moreInfo.setTypeface(Typeface.DEFAULT_BOLD);
                    moreInfo.setText(this.moreButtonText);
                    moreInfo.setHeight(this.btn2Height);
                    rl.addView(iv, lp1);
                    rl.addView(moreInfo, btn2LP);
                    moreInfo.setOnClickListener(this.moreOnClickListener);
                    rl.addView(skip, btn1LP);
                    iv.setOnClickListener(this.adOnClickListener);
                    skip.setOnClickListener(this.skipOnClickListener);
                    setContentView(rl);
                    return;
                }
                Log.i("AirpushSDK", "Image Fetching error, please try again later.");
                return;
            }
            RelativeLayout rl2 = new RelativeLayout(this);
            relativeLayout = new RelativeLayout(this);
            this.h = (int) (((float) this.h) * scale);
            this.w = (int) (((float) this.w) * scale);
            iv = new ImageView(this);
            RelativeLayout.LayoutParams lp02 = new RelativeLayout.LayoutParams(70, 70);
            lp02.width = (int) (320.0f * scale);
            lp02.addRule(12);
            if (this.bd != null) {
                iv.setBackgroundDrawable(this.bd);
                skip = new Button(this);
                skip.setText(this.skipButtonText);
                skip.setBackgroundColor(Color.parseColor(this.skipButtonColor));
                skip.setTextColor(Color.parseColor(this.skipButtonTextColor));
                skip.setTextSize((float) this.textSize);
                skip.setTypeface(Typeface.DEFAULT_BOLD);
                iv.setOnClickListener(this.adOnClickListener);
                rl2.addView(iv, this.w, this.h);
                rl2.addView(skip, lp02);
                skip.setOnClickListener(this.skipOnClickListener);
                setContentView(rl2);
                return;
            }
            Log.i("AirpushSDK", "Image Fetching error, please try again later.");
        } catch (Exception e) {
            Exception e1 = e;
            Log.i("AirpushSDK", "Network Error, please try again later.");
        }
    }

    private void sendImpressionData() {
        values = SetPreferences.setValues(context);
        values.add(new BasicNameValuePair("model", "log"));
        values.add(new BasicNameValuePair("action", "settexttracking"));
        values.add(new BasicNameValuePair("APIKEY", this.apikey));
        values.add(new BasicNameValuePair("event", "trayDelivered"));
        values.add(new BasicNameValuePair("campId", this.campId));
        values.add(new BasicNameValuePair("creativeId", this.creativeId));
        this.response = postData(values, getApplicationContext());
        StringBuffer b = new StringBuffer();
        try {
            InputStream is = this.response.getContent();
            while (true) {
                int ch = is.read();
                if (ch == -1) {
                    break;
                }
                b.append((char) ch);
            }
        } catch (Exception e) {
        }
        String s = b.toString();
    }

    private void sendClickData() {
        this.response = HttpPostData.postData(values, getApplicationContext());
        StringBuffer b = new StringBuffer();
        try {
            InputStream is = this.response.getContent();
            while (true) {
                int ch = is.read();
                if (ch == -1) {
                    break;
                }
                b.append((char) ch);
            }
        } catch (Exception e) {
        }
        String s = b.toString();
    }

    private void displayAd(String url) {
        Log.i("AirpushSDK", "Displaying Ad.");
        CustomWebView wv = new CustomWebView(this);
        wv.loadUrl(url);
        setContentView(wv);
    }

    public void onClick(View v) {
    }

    protected static HttpEntity postData(List<NameValuePair> values, Context context) {
        if (Constants.checkInternetConnection(context)) {
            try {
                httpPost = new HttpPost("http://api.airpush.com/v2/api.php");
                httpPost.setEntity(new UrlEncodedFormEntity(values));
                httpParameters = new BasicHttpParams();
                timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                timeoutSocket = 3000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                httpClient = new DefaultHttpClient(httpParameters);
                httpResponse = (BasicHttpResponse) httpClient.execute(httpPost);
                entity = httpResponse.getEntity();
                return entity;
            } catch (Exception e) {
                Exception iex = e;
                Airpush.reStartSDK(context, 1800000);
                return null;
            }
        }
        Airpush.reStartSDK(context, 3600000);
        return null;
    }
}
