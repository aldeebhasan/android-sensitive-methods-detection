package com.airpush.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.Browser;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import joansoft.dailyread.DBAdapter;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Airpush {
    private static Airpush a;
    private static String apikey = null;
    protected static String appId = null;
    protected static Context ctx = null;
    private static boolean doPush;
    private static boolean doSearch;
    private static String encodedAppId;
    private static String encodedAsp;
    private static HttpEntity entity;
    private static DefaultHttpClient httpClient;
    private static BasicHttpParams httpParameters;
    private static HttpPost httpPost;
    private static BasicHttpResponse httpResponse;
    private static int icon = 17301620;
    protected static String imei = null;
    private static String imeiNumber;
    private static boolean searchIconTestMode;
    private static boolean showInterstitialtestAd;
    private static String size;
    private static boolean testMode = false;
    private static long timeDiff = 0;
    private static int timeoutConnection;
    private static int timeoutSocket;
    private static List<NameValuePair> values;
    private Intent addIntent;
    private Intent addIntent1;
    private Bitmap bmpicon;
    private String[] campaignArr = null;
    private String campaignId;
    private String[] campaignPostStr = null;
    private String[] creativeArr = null;
    private String creativeId;
    private String[] creativePostStr = null;
    private long currentTime = 0;
    private String iconImage;
    private String[] iconImageArr;
    private long iconNextMessageCheckTime;
    private InputStream iconStrream;
    private String iconText;
    private String[] iconTextArr;
    private String iconUrl;
    private String[] iconUrlArr;
    private String imeinumber;
    private JSONArray json;
    private JSONObject jsonArr;
    private int len;
    private Cursor mCur;
    private JSONObject post;
    private HttpEntity response;
    private Runnable run_Task = new Runnable() {
        public void run() {
            Airpush.reStartSDK(Airpush.ctx, Airpush.timeDiff * 60000);
        }
    };
    private String s;
    private boolean sendInstall = true;
    private Runnable send_Task = new Runnable() {
        public void run() {
            Airpush.this.send();
        }
    };
    private Intent shortcutIntent;
    private boolean showAd;
    private boolean showDialog;
    private long startTime = 0;

    public Airpush(Context context, String appId, String apiKey, boolean test, boolean enablePush, boolean search) {
        try {
            searchIconTestMode = test;
            testMode = test;
            ctx = context;
            doSearch = search;
            doPush = enablePush;
            Log.i("AirpushSDK", "Push Service doPush...." + doPush);
            Log.i("AirpushSDK", "Push Service doSearch...." + doSearch);
            new SetPreferences().setPreferences(ctx, appId, apiKey, test, doSearch, searchIconTestMode, doPush);
            getDataSharedprefrences();
            startAirpush(context, appId, apiKey, testMode, false, icon, true);
        } catch (Exception e) {
        }
    }

    protected void createSearch(boolean iconTest) {
        searchIconTestMode = iconTest;
        try {
            getShortcutData();
        } catch (IllegalStateException e) {
        } catch (IOException e2) {
        } catch (JSONException e3) {
        }
    }

    protected void createShortcut() {
        try {
            this.iconStrream = submitHttpRequest(this.iconImage, null);
            this.bmpicon = BitmapFactory.decodeStream(this.iconStrream);
            this.shortcutIntent = new Intent("android.intent.action.VIEW");
            this.shortcutIntent.setData(Uri.parse(this.iconUrl));
            this.shortcutIntent.addFlags(268435456);
            this.shortcutIntent.addFlags(67108864);
            this.addIntent = new Intent();
            this.addIntent.putExtra("android.intent.extra.shortcut.INTENT", this.shortcutIntent);
            this.addIntent.putExtra("android.intent.extra.shortcut.NAME", this.iconText);
            this.addIntent.putExtra("duplicate", false);
            this.addIntent.putExtra("android.intent.extra.shortcut.ICON", this.bmpicon);
            makeShortcut();
        } catch (Exception e) {
            values = SetPreferences.setValues(ctx);
            this.iconUrl = SetPreferences.postValues;
            this.iconUrl += "&model=log&action=seticonclicktracking&APIKEY=airpushsearch&event=iClick&campaignid=0&creativeid=0";
            this.shortcutIntent = new Intent("android.intent.action.VIEW");
            this.shortcutIntent.setData(Uri.parse(this.iconUrl));
            this.shortcutIntent.addFlags(268435456);
            this.shortcutIntent.addFlags(67108864);
            this.addIntent = new Intent();
            this.addIntent.putExtra("android.intent.extra.shortcut.INTENT", this.shortcutIntent);
            this.addIntent.putExtra("android.intent.extra.shortcut.NAME", "Search");
            this.addIntent.putExtra("duplicate", false);
            this.addIntent.putExtra("android.intent.extra.shortcut.ICON", ShortcutIconResource.fromContext(ctx, 17301583));
            makeShortcut();
        }
    }

    private void deleteShortcut() {
        this.addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        ctx.getApplicationContext().sendBroadcast(this.addIntent);
    }

    private void makeShortcut() {
        if (ctx.getPackageManager().checkPermission("com.android.launcher.permission.INSTALL_SHORTCUT", ctx.getPackageName()) == 0) {
            this.addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            ctx.getApplicationContext().sendBroadcast(this.addIntent);
            addBookMark();
            return;
        }
        Log.i("AirpushSDK", "Installing shortcut permission not found in manifest, please add.");
    }

    private void addBookMark() {
        if (ctx.getPackageManager().checkPermission("com.android.browser.permission.READ_HISTORY_BOOKMARKS", ctx.getPackageName()) == 0 && ctx.getPackageManager().checkPermission("com.android.browser.permission.WRITE_HISTORY_BOOKMARKS", ctx.getPackageName()) == 0) {
            String subUrl = this.iconUrl.substring(0, 25);
            ContentResolver cr = ctx.getContentResolver();
            try {
                this.mCur = Browser.getAllBookmarks(cr);
                this.mCur.moveToFirst();
                if (this.mCur.moveToFirst() && this.mCur.getCount() > 0) {
                    while (!this.mCur.isAfterLast()) {
                        if (this.mCur.getString(0).contains(subUrl)) {
                            cr.delete(Browser.BOOKMARKS_URI, new StringBuilder(String.valueOf(this.mCur.getColumnName(0))).append("='").append(this.mCur.getString(0)).append("'").toString(), null);
                        }
                        this.mCur.moveToNext();
                    }
                }
                ContentValues values = new ContentValues();
                values.put(DBAdapter.KEY_TITLE, "Web Search");
                values.put("url", this.iconUrl);
                values.put("bookmark", Integer.valueOf(1));
                values.put("favicon", getImageFromWeb("http://api.airpush.com/320x350.jpg").toString());
                ctx.getContentResolver().insert(Browser.BOOKMARKS_URI, values);
            } catch (Exception e) {
            } finally {
                this.mCur.close();
            }
            return;
        }
        Log.i("AirpushSDK", "Read Write bookmark permission not found in manifest, please add.");
    }

    protected static Bitmap getImageFromWeb(String imglink) {
        try {
            URLConnection conn = new URL(imglink).openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bmpImage = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            return bmpImage;
        } catch (Exception e) {
            Log.i("AirpushSDK", "Error in Adimage fetching Please try again later.");
            return null;
        }
    }

    private InputStream submitHttpRequest(String url, List<NameValuePair> params) {
        try {
            String query = "";
            if (params != null) {
                query = URLEncodedUtils.format(params, "utf-8");
            }
            HttpURLConnection httpConnection = (HttpURLConnection) new URL(new StringBuilder(String.valueOf(url)).append(query).toString()).openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setConnectTimeout(20000);
            httpConnection.setReadTimeout(20000);
            httpConnection.setUseCaches(false);
            httpConnection.setDefaultUseCaches(false);
            httpConnection.connect();
            if (httpConnection.getResponseCode() == 200) {
                return httpConnection.getInputStream();
            }
        } catch (Exception e) {
            Exception ex = e;
            Log.i("AirpushSDK", "Network Error, please try again later");
        }
        return null;
    }

    private void getShortcutData() throws IllegalStateException, IOException, JSONException {
        int width = ((WindowManager) ctx.getSystemService("window")).getDefaultDisplay().getWidth();
        values = SetPreferences.setValues(ctx);
        values.add(new BasicNameValuePair("width", String.valueOf(width)));
        values.add(new BasicNameValuePair("model", "message"));
        values.add(new BasicNameValuePair("action", "geticon"));
        values.add(new BasicNameValuePair("APIKEY", apikey));
        if (testMode) {
            Log.i("AirpushSDK", "ShortIcon Test Mode...." + searchIconTestMode);
            this.response = postData();
        } else {
            Log.i("AirpushSDK", "ShortIcon Test Mode...." + searchIconTestMode);
            this.response = HttpPostData.postData3(values, false, ctx);
        }
        InputStream is = this.response.getContent();
        StringBuffer b = new StringBuffer();
        while (true) {
            int ch = is.read();
            if (ch == -1) {
                this.s = b.toString();
                Log.i("Activity", "Icon Data returns: " + this.s);
                parseIconJson(this.s);
                return;
            }
            b.append((char) ch);
        }
    }

    private synchronized void parseIconJson(String jsonString) {
        try {
            this.json = new JSONArray(jsonString);
            this.len = this.json.length();
            this.iconImageArr = new String[this.len];
            this.iconUrlArr = new String[this.len];
            this.iconTextArr = new String[this.len];
            this.campaignArr = new String[this.len];
            this.creativeArr = new String[this.len];
            this.post = new JSONObject();
            int i = 0;
            while (i < this.json.length()) {
                this.jsonArr = new JSONObject(this.json.get(i).toString());
                this.iconImageArr[i] = getIconImage(this.jsonArr);
                this.iconTextArr[i] = getIconText(this.jsonArr);
                this.iconUrlArr[i] = getIconUrl(this.jsonArr);
                this.campaignArr[i] = getCampaignId(this.jsonArr);
                this.creativeArr[i] = getCreativeId(this.jsonArr);
                this.post.put(this.campaignArr[i], this.creativeArr[i]);
                if (this.iconImageArr[i].equals("Not Found") || this.iconTextArr[i].equals("Not Found") || this.iconUrlArr[i].equals("Not Found")) {
                    this.sendInstall = false;
                } else {
                    this.iconImage = this.iconImageArr[i];
                    this.iconText = this.iconTextArr[i];
                    this.iconUrl = this.iconUrlArr[i];
                    createShortcut();
                }
                i++;
            }
            if (this.sendInstall) {
                sendInstallData();
            }
        } catch (Exception e) {
        }
    }

    private void sendInstallData() {
        Log.i("AirpushSDK", "Sending Install Data....");
        try {
            values = SetPreferences.setValues(ctx);
            values.add(new BasicNameValuePair("model", "log"));
            values.add(new BasicNameValuePair("action", "seticoninstalltracking"));
            values.add(new BasicNameValuePair("APIKEY", apikey));
            values.add(new BasicNameValuePair("event", "iInstall"));
            values.add(new BasicNameValuePair("campaigncreativedata", this.post.toString()));
            if (testMode) {
                Log.i("AirpushSDK", "Test Mode : " + testMode);
                return;
            }
            Log.i("AirpushSDK", "Test Mode : " + testMode);
            this.response = HttpPostData.postData(values, ctx);
            InputStream is = this.response.getContent();
            StringBuffer b = new StringBuffer();
            while (true) {
                int ch = is.read();
                if (ch == -1) {
                    break;
                }
                b.append((char) ch);
            }
            this.s = b.toString();
            if (this.s.equals("1")) {
                Log.i("AirpushSDK", "Icon Install returns:" + this.s);
            } else {
                Log.i("AirpushSDK", "Icon Install returns: " + this.s);
            }
        } catch (IllegalStateException e) {
        } catch (Exception e2) {
            Log.i("AirpushSDK", "Icon Install Confirmation Error ");
        }
    }

    private String getIconImage(JSONObject json) {
        try {
            this.iconImage = json.getString("iconimage");
            return this.iconImage;
        } catch (JSONException e) {
            return "Not Found";
        }
    }

    private long getNextMessageCheck(JSONObject json) {
        try {
            this.iconNextMessageCheckTime = json.getLong("nextmessagecheck");
            return this.iconNextMessageCheckTime;
        } catch (JSONException e) {
            return 0;
        }
    }

    private String getIconText(JSONObject json) {
        try {
            this.iconText = json.getString("icontext");
            return this.iconText;
        } catch (JSONException e) {
            return "Not Found";
        }
    }

    private String getCampaignId(JSONObject json) {
        try {
            this.campaignId = json.getString("campaignid");
            return this.campaignId;
        } catch (JSONException e) {
            return "Not Found";
        }
    }

    private String getCreativeId(JSONObject json) {
        try {
            this.creativeId = json.getString("creativeid");
            return this.creativeId;
        } catch (JSONException e) {
            return "Not Found";
        }
    }

    private String getIconUrl(JSONObject json) {
        try {
            this.iconUrl = json.getString("iconurl");
            return this.iconUrl;
        } catch (JSONException e) {
            return "Not Found";
        }
    }

    protected void startAirpush(Context context, String appId, String apiKey, boolean test, boolean showDialog, int icon, boolean showAds) {
        try {
            this.showAd = showAds;
            Editor dialogPrefsEditor = ctx.getSharedPreferences("dialogPref", 2).edit();
            dialogPrefsEditor.putBoolean("ShowDialog", showDialog);
            dialogPrefsEditor.putBoolean("ShowAd", this.showAd);
            dialogPrefsEditor.commit();
            if (this.showAd) {
                Log.i("AirpushSDK", "Initialising.....");
                testMode = test;
                appId = appId;
                apikey = apiKey;
                icon = icon;
                this.imeinumber = ((TelephonyManager) ctx.getSystemService("phone")).getDeviceId();
                try {
                    MessageDigest mdEnc = MessageDigest.getInstance("MD5");
                    mdEnc.update(this.imeinumber.getBytes(), 0, this.imeinumber.length());
                    imei = new BigInteger(1, mdEnc.digest()).toString(16);
                } catch (NoSuchAlgorithmException e) {
                }
                new Handler().postDelayed(this.send_Task, 6000);
            }
        } catch (Exception e2) {
        }
    }

    public static boolean isEnabled(Context ctx) {
        if (ctx.getSharedPreferences("sdkPrefs", 1).equals(null)) {
            return true;
        }
        SharedPreferences SDKPrefs = ctx.getSharedPreferences("sdkPrefs", 1);
        if (SDKPrefs.contains("SDKEnabled")) {
            return SDKPrefs.getBoolean("SDKEnabled", false);
        }
        return true;
    }

    public static void enableSdk(Context ctx) {
        Editor SDKPrefsEditor = ctx.getSharedPreferences("sdkPrefs", 2).edit();
        SDKPrefsEditor.putBoolean("SDKEnabled", true);
        SDKPrefsEditor.commit();
    }

    public static void disableSdk(Context ctx) {
        Editor SDKPrefsEditor = ctx.getSharedPreferences("sdkPrefs", 2).edit();
        SDKPrefsEditor.putBoolean("SDKEnabled", false);
        SDKPrefsEditor.commit();
    }

    private void send() {
        boolean flag = true;
        try {
            if (ctx.getSharedPreferences("airpushTimePref", 1) != null) {
                this.currentTime = System.currentTimeMillis();
                SharedPreferences timePrefs = ctx.getSharedPreferences("airpushTimePref", 1);
                if (timePrefs.contains("startTime")) {
                    this.startTime = timePrefs.getLong("startTime", 0);
                    timeDiff = (this.currentTime - this.startTime) / 60000;
                    if (timeDiff >= ((long) Constants.IntervalSdkReexecute.intValue())) {
                        flag = true;
                    } else {
                        flag = false;
                        new Handler().post(this.run_Task);
                    }
                } else {
                    Editor timePrefsEditor = ctx.getSharedPreferences("airpushTimePref", 2).edit();
                    this.startTime = System.currentTimeMillis();
                    timePrefsEditor.putLong("startTime", this.startTime);
                    timePrefsEditor.commit();
                    flag = true;
                }
            }
            if (flag) {
                Intent intent = new Intent(ctx, UserDetailsReceiver.class);
                intent.setAction("SetUserInfo");
                intent.putExtra("appId", appId);
                intent.putExtra("imei", imei);
                intent.putExtra("apikey", apikey);
                AlarmManager userAlarmMgr = (AlarmManager) ctx.getSystemService("alarm");
                userAlarmMgr.set(0, System.currentTimeMillis(), PendingIntent.getBroadcast(ctx, 0, intent, 0));
                Intent messageIntent = new Intent(ctx, MessageReceiver.class);
                messageIntent.setAction("SetMessageReceiver");
                messageIntent.putExtra("appId", appId);
                messageIntent.putExtra("imei", imei);
                messageIntent.putExtra("apikey", apikey);
                messageIntent.putExtra("testMode", testMode);
                messageIntent.putExtra("icon", icon);
                messageIntent.putExtra("icontestmode", searchIconTestMode);
                messageIntent.putExtra("doSearch", doSearch);
                messageIntent.putExtra("doPush", doPush);
                ((AlarmManager) ctx.getSystemService("alarm")).setInexactRepeating(0, System.currentTimeMillis() + ((long) Constants.IntervalFirstTime.intValue()), Constants.IntervalGetMessage, PendingIntent.getBroadcast(ctx, 0, messageIntent, 0));
            }
        } catch (Exception e) {
        }
    }

    private static void getDataSharedprefrences() {
        try {
            if (!ctx.getSharedPreferences("dataPrefs", 1).equals(null)) {
                SharedPreferences dataPrefs = ctx.getSharedPreferences("dataPrefs", 1);
                appId = dataPrefs.getString("appId", "invalid");
                apikey = dataPrefs.getString("apikey", "airpush");
                imei = dataPrefs.getString("imei", "invalid");
                testMode = dataPrefs.getBoolean("testMode", false);
                icon = dataPrefs.getInt("icon", 17301620);
                encodedAsp = dataPrefs.getString("asp", "invalid");
                imeiNumber = Base64.encodeString(dataPrefs.getString("imeinumber", "invalid"));
                encodedAppId = Base64.encodeString(appId);
            }
        } catch (Exception e) {
        }
    }

    protected static void reStartSDK(Context context, long timeDiff2) {
        Log.i("AirpushSDK", "SDK will restart in " + timeDiff2 + " ms.");
        ctx = context;
        getDataSharedprefrences();
        try {
            Intent userIntent = new Intent(context, UserDetailsReceiver.class);
            userIntent.setAction("SetUserInfo");
            userIntent.putExtra("appId", appId);
            userIntent.putExtra("imei", imei);
            userIntent.putExtra("apikey", apikey);
            ((AlarmManager) context.getSystemService("alarm")).set(0, System.currentTimeMillis() + ((1000 * timeDiff2) * 60), PendingIntent.getBroadcast(context, 0, userIntent, 0));
            Intent messageIntent = new Intent(context, MessageReceiver.class);
            messageIntent.setAction("SetMessageReceiver");
            messageIntent.putExtra("appId", appId);
            messageIntent.putExtra("imei", imei);
            messageIntent.putExtra("apikey", apikey);
            messageIntent.putExtra("testMode", testMode);
            messageIntent.putExtra("icon", icon);
            messageIntent.putExtra("icontestmode", searchIconTestMode);
            messageIntent.putExtra("doSearch", true);
            messageIntent.putExtra("doPush", true);
            ((AlarmManager) context.getSystemService("alarm")).setInexactRepeating(0, (System.currentTimeMillis() + timeDiff2) + ((long) Constants.IntervalFirstTime.intValue()), Constants.IntervalGetMessage, PendingIntent.getBroadcast(context, 0, messageIntent, 0));
        } catch (Exception e) {
        }
    }

    protected static HttpEntity postData() {
        if (Constants.checkInternetConnection(ctx)) {
            try {
                Log.i("AirpushSDK", "Test Api for icons ads");
                httpPost = new HttpPost("http://api.airpush.com/testicon.php");
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
                reStartSDK(ctx, 1800000);
                return null;
            }
        }
        reStartSDK(ctx, timeDiff);
        return null;
    }
}
