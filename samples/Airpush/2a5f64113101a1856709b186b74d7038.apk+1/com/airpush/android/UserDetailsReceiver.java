package com.airpush.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailsReceiver extends BroadcastReceiver {
    protected static Context ctx = null;
    private static String imei = "invalid";
    private String apikey = "airpush";
    private String appId = "Invalid";
    private int icon;
    private JSONObject json;
    private String jsonstr;
    private String packageName;
    private boolean testMode;
    List<NameValuePair> values = null;

    public void onReceive(Context context, Intent intent) {
        ctx = context;
        if (SetPreferences.isEnabled(ctx)) {
            try {
                if (Constants.checkInternetConnection(ctx)) {
                    if (intent.getAction().equals("SetUserInfo")) {
                        getDataSharedprefrences();
                    }
                    Log.i("AirpushSDK", "Sending User Info....");
                    Constants.doToast(context, "airpushAppid " + this.appId);
                    Intent userServiceIntent = new Intent();
                    userServiceIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                    userServiceIntent.putExtra("appId", this.appId);
                    userServiceIntent.putExtra("type", "userInfo");
                    userServiceIntent.putExtra("apikey", this.apikey);
                    if (userServiceIntent.equals(null)) {
                        getDataSharedprefrences();
                        Airpush airpush = new Airpush(ctx, this.appId, "airpush", false, true, true);
                        return;
                    }
                    ctx.startService(userServiceIntent);
                    return;
                }
                Airpush.reStartSDK(ctx, 1800000);
                return;
            } catch (Exception e) {
                getDataSharedprefrences();
                airpush = new Airpush(ctx, this.appId, "airpush", false, true, true);
                Log.i("AirpushSDK", "Sending User Info failed");
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
                this.appId = getAppId(this.jsonstr);
                this.apikey = getApiKey(this.jsonstr);
                return;
            }
            SharedPreferences dataPrefs = ctx.getSharedPreferences("dataPrefs", 1);
            this.appId = dataPrefs.getString("appId", "invalid");
            this.apikey = dataPrefs.getString("apikey", "airpush");
            imei = dataPrefs.getString("imei", "invalid");
            this.testMode = dataPrefs.getBoolean("testMode", false);
            this.icon = dataPrefs.getInt("icon", 17301620);
        } catch (Exception e) {
            this.packageName = ctx.getPackageName();
            this.jsonstr = HttpPostData.postData2("http://api.airpush.com/model/user/getappinfo.php?packageName=" + this.packageName, "default", "default", ctx);
            this.appId = getAppId(this.jsonstr);
            this.apikey = getApiKey(this.jsonstr);
            Airpush airpush = new Airpush(ctx, this.appId, "airpush", false, true, true);
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
