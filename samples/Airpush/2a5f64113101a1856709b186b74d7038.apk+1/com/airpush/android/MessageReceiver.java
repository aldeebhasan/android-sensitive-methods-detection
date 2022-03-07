package com.airpush.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageReceiver extends BroadcastReceiver {
    protected static String appId = "Invalid";
    protected static Context ctx;
    protected static String imei = null;
    private String apikey = null;
    private boolean doPush;
    private boolean doSearch;
    private Runnable getData_Task = new Runnable() {
        public void run() {
            MessageReceiver.this.getDataSharedprefrences();
        }
    };
    private int icon;
    private JSONObject json;
    private String jsonstr;
    private String packageName;
    private boolean searchIconTestMode;
    private boolean testMode;
    private List<NameValuePair> values = null;

    public void onReceive(Context context, Intent intent) {
        ctx = context;
        if (SetPreferences.isEnabled(ctx)) {
            try {
                if (Constants.checkInternetConnection(ctx)) {
                    Log.i("AirpushSDK", "Receiving Message.....");
                    if (intent.getAction().equals("SetMessageReceiver")) {
                        getDataSharedprefrences();
                    }
                    Intent messageServiceIntent = new Intent();
                    messageServiceIntent.setAction("com.airpush.android.PushServiceStart" + appId);
                    messageServiceIntent.putExtra("appId", appId);
                    messageServiceIntent.putExtra("type", "message");
                    messageServiceIntent.putExtra("apikey", this.apikey);
                    messageServiceIntent.putExtra("testMode", this.testMode);
                    messageServiceIntent.putExtra("icon", this.icon);
                    messageServiceIntent.putExtra("icontestmode", this.searchIconTestMode);
                    messageServiceIntent.putExtra("doSearch", this.doSearch);
                    messageServiceIntent.putExtra("doPush", this.doPush);
                    if (messageServiceIntent.equals(null)) {
                        getDataSharedprefrences();
                        if (appId.equals("invalid") || appId.equals(null)) {
                            new Handler().postDelayed(this.getData_Task, 1800000);
                        }
                        Airpush airpush = new Airpush(ctx, appId, "airpush", false, true, true);
                        return;
                    }
                    context.startService(messageServiceIntent);
                    return;
                }
                return;
            } catch (Exception e) {
                getDataSharedprefrences();
                airpush = new Airpush(ctx, appId, "airpush", false, true, true);
                return;
            }
        }
        Log.i("AirpushSDK", "SDK is disabled, please enable to receive Ads !");
    }

    private void getDataSharedprefrences() {
        try {
            if (ctx.getSharedPreferences("dataPrefs", 1).equals(null)) {
                this.packageName = ctx.getPackageName();
                this.jsonstr = HttpPostData.postData2("http://api.airpush.com/model/user/getappinfo.php?packageName=" + this.packageName, "default", "default", ctx);
                appId = getAppId(this.jsonstr);
                this.apikey = getApiKey(this.jsonstr);
                return;
            }
            SharedPreferences dataPrefs = ctx.getSharedPreferences("dataPrefs", 1);
            appId = dataPrefs.getString("appId", "invalid");
            this.apikey = dataPrefs.getString("apikey", "airpush");
            imei = dataPrefs.getString("imei", "invalid");
            this.testMode = dataPrefs.getBoolean("testMode", false);
            this.doPush = dataPrefs.getBoolean("doPush", true);
            this.doSearch = dataPrefs.getBoolean("doSearch", true);
            this.searchIconTestMode = dataPrefs.getBoolean("searchIconTestMode", false);
            this.icon = dataPrefs.getInt("icon", 17301620);
        } catch (Exception e) {
            this.packageName = ctx.getPackageName();
            this.jsonstr = HttpPostData.postData2("http://api.airpush.com/model/user/getappinfo.php?packageName=" + this.packageName, "default", "default", ctx);
            appId = getAppId(this.jsonstr);
            this.apikey = getApiKey(this.jsonstr);
            Airpush airpush = new Airpush(ctx, appId, "airpush", false, true, true);
        }
    }

    private String getAppId(String jsonstr) {
        try {
            this.json = new JSONObject(jsonstr);
            return this.json.getString("appid");
        } catch (JSONException e) {
            return "invalid Id";
        }
    }

    private String getApiKey(String jsonstr) {
        try {
            this.json = new JSONObject(jsonstr);
            return this.json.getString("authkey");
        } catch (JSONException e) {
            return "invalid key";
        }
    }
}
