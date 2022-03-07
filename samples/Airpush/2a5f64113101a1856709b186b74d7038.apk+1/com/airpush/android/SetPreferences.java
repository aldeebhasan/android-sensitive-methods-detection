package com.airpush.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class SetPreferences {
    private static String android_id;
    private static String apikey = "0";
    private static String appId = "0";
    private static String carrier = "0";
    private static String city = "unknown";
    private static String connectionType;
    private static String country = "unknown";
    private static Context ctx;
    private static String dte = "00";
    private static int icon;
    protected static String imei = "0";
    private static String ip = "0";
    protected static JSONObject json = null;
    private static String jsonstr;
    private static String lat = "0";
    private static String lon = "0";
    private static String manufacturer = "0";
    private static String networkOperator = "0";
    private static String packageName = "invalid";
    private static String phonemodel = "0";
    protected static String postValues;
    private static String s;
    private static String sdkversion = "4.02";
    private static String state = "unknown";
    private static boolean testMode;
    private static String token = "0";
    private static String user_agent;
    protected static List<NameValuePair> values;
    private static String version = "0";
    private boolean doPush;
    private boolean doSearch;
    private String encodedAsp;
    private String imeinumber = "0";
    private String jsonString = "0";
    private boolean searchIconTestMode;
    private boolean showAd;
    private boolean showDialog;

    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location loc) {
            try {
                SetPreferences.lon = String.valueOf(loc.getLongitude());
                SetPreferences.lat = String.valueOf(loc.getLatitude());
            } catch (Exception e) {
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }
    }

    protected void setPreferences(Context context, String appid, String apikey, boolean testMode, boolean doSearch, boolean searchIconTestMode, boolean doPush) {
        ctx = context;
        appId = appid;
        apikey = apikey;
        this.showDialog = this.showDialog;
        this.doPush = doPush;
        this.doSearch = doSearch;
        this.searchIconTestMode = searchIconTestMode;
        testMode = testMode;
        user_agent = new WebView(ctx).getSettings().getUserAgentString();
        Log.i("User Agent", "User Agent : " + this.doPush);
        connectionType = getConnectionType();
        getLocation(ctx);
        TelephonyManager telephony = (TelephonyManager) ctx.getSystemService("phone");
        this.imeinumber = telephony.getDeviceId();
        try {
            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(this.imeinumber.getBytes(), 0, this.imeinumber.length());
            imei = new BigInteger(1, mdEnc.digest()).toString(16);
            dte = new Date().toString();
            phonemodel = Build.MODEL;
            manufacturer = Build.MANUFACTURER;
            networkOperator = telephony.getNetworkOperatorName();
            carrier = telephony.getSimOperatorName();
            version = VERSION.SDK;
            android_id = Secure.getString(ctx.getContentResolver(), "android_id");
            packageName = ctx.getPackageName();
            token = imei + appId + dte;
            MessageDigest mdEnc2 = MessageDigest.getInstance("MD5");
            mdEnc2.update(token.getBytes(), 0, token.length());
            token = new BigInteger(1, mdEnc2.digest()).toString(16);
            setSharedPreferences();
        } catch (NoSuchAlgorithmException e) {
            Log.i("AirpushSDK", "IMEI conversion Error ");
        }
    }

    protected void setSharedPreferences() {
        try {
            dte = new Date().toString();
            Editor dataPrefsEditor = ctx.getSharedPreferences("dataPrefs", 2).edit();
            dataPrefsEditor.putString("apikey", apikey);
            dataPrefsEditor.putString("appId", appId);
            dataPrefsEditor.putString("imei", imei);
            dataPrefsEditor.putString("connectionType", connectionType);
            dataPrefsEditor.putString("token", token);
            dataPrefsEditor.putString("request_timestamp", dte);
            dataPrefsEditor.putString("packageName", packageName);
            dataPrefsEditor.putString("version", version);
            dataPrefsEditor.putString("carrier", carrier);
            dataPrefsEditor.putString("networkOperator", networkOperator);
            dataPrefsEditor.putString("phoneModel", phonemodel);
            dataPrefsEditor.putString("manufacturer", manufacturer);
            dataPrefsEditor.putString("longitude", lon);
            dataPrefsEditor.putString("latitude", lat);
            dataPrefsEditor.putString("sdkversion", "4.02");
            dataPrefsEditor.putString("android_id", android_id);
            dataPrefsEditor.putBoolean("showDialog", this.showDialog);
            dataPrefsEditor.putBoolean("showAd", this.showAd);
            dataPrefsEditor.putBoolean("testMode", testMode);
            dataPrefsEditor.putBoolean("doPush", this.doPush);
            dataPrefsEditor.putBoolean("doSearch", this.doSearch);
            dataPrefsEditor.putBoolean("searchIconTestMode", this.searchIconTestMode);
            dataPrefsEditor.putInt("icon", icon);
            dataPrefsEditor.putString("useragent", user_agent);
            this.encodedAsp = Base64.encodeString(appId + this.imeinumber + connectionType + token + dte + packageName + version + carrier + networkOperator + phonemodel + manufacturer + lon + lat + user_agent);
            dataPrefsEditor.putString("asp", this.encodedAsp);
            dataPrefsEditor.putString("imeinumber", this.imeinumber);
            dataPrefsEditor.commit();
        } catch (Exception e) {
        }
    }

    private static void getDataSharedprefrences(Context context) {
        try {
            if (context.getSharedPreferences("dataPrefs", 1).equals(null)) {
                packageName = ctx.getPackageName();
                jsonstr = HttpPostData.postData2("http://api.airpush.com/model/user/getappinfo.php?packageName=" + packageName, "default", "default", ctx);
                appId = getAppId(jsonstr);
                apikey = getApiKey(jsonstr);
                return;
            }
            SharedPreferences dataPrefs = context.getSharedPreferences("dataPrefs", 1);
            appId = dataPrefs.getString("appId", "invalid");
            apikey = dataPrefs.getString("apikey", "airpush");
            imei = dataPrefs.getString("imei", "invalid");
            token = dataPrefs.getString("token", "invalid");
            dte = new Date().toString();
            packageName = dataPrefs.getString("packageName", "invalid");
            version = dataPrefs.getString("version", "invalid");
            carrier = dataPrefs.getString("carrier", "invalid");
            networkOperator = dataPrefs.getString("networkOperator", "invalid");
            phonemodel = dataPrefs.getString("phoneModel", "invalid");
            manufacturer = dataPrefs.getString("manufacturer", "invalid");
            lon = dataPrefs.getString("longitude", "invalid");
            lat = dataPrefs.getString("latitude", "invalid");
            sdkversion = dataPrefs.getString("sdkversion", "4.02");
            connectionType = dataPrefs.getString("connectionType", "0");
            testMode = dataPrefs.getBoolean("testMode", false);
            user_agent = dataPrefs.getString("useragent", "Default");
            icon = dataPrefs.getInt("icon", 17301514);
            android_id = dataPrefs.getString("android_id", "Android_id");
        } catch (Exception e) {
        }
    }

    private static String getAppId(String jsonstr) {
        try {
            json = new JSONObject(jsonstr);
            return json.getString("appid");
        } catch (JSONException e) {
            return "invalid Id";
        }
    }

    private static String getApiKey(String jsonstr) {
        try {
            json = new JSONObject(jsonstr);
            return json.getString("authkey");
        } catch (JSONException e) {
            return "invalid key";
        }
    }

    protected static List<NameValuePair> setValues(Context context) {
        try {
            getDataSharedprefrences(context);
            values = new ArrayList();
            values.add(new BasicNameValuePair("apikey", apikey));
            values.add(new BasicNameValuePair("appId", appId));
            values.add(new BasicNameValuePair("imei", imei));
            values.add(new BasicNameValuePair("token", token));
            values.add(new BasicNameValuePair("request_timestamp", dte));
            values.add(new BasicNameValuePair("packageName", packageName));
            values.add(new BasicNameValuePair("version", version));
            values.add(new BasicNameValuePair("carrier", carrier));
            values.add(new BasicNameValuePair("networkOperator", networkOperator));
            values.add(new BasicNameValuePair("phoneModel", phonemodel));
            values.add(new BasicNameValuePair("manufacturer", manufacturer));
            values.add(new BasicNameValuePair("longitude", lon));
            values.add(new BasicNameValuePair("latitude", lat));
            values.add(new BasicNameValuePair("sdkversion", sdkversion));
            values.add(new BasicNameValuePair("wifi", connectionType));
            values.add(new BasicNameValuePair("useragent", user_agent));
            values.add(new BasicNameValuePair("android_id", android_id));
            values.add(new BasicNameValuePair("longitude", lon));
            values.add(new BasicNameValuePair("latitude", lat));
            postValues = "http://api.airpush.com/v2/api.php?apikey=" + apikey + "&appId=" + appId + "&imei=" + imei + "&token=" + token + "&request_timestamp=" + dte + "&packageName=" + packageName + "&version=" + version + "&carrier=" + carrier + "&networkOperator=" + networkOperator + "&phoneModel=" + phonemodel + "&manufacturer=" + manufacturer + "&longitude=" + lon + "&latitude=" + lat + "&sdkversion=" + sdkversion + "&wifi=" + connectionType + "&useragent=" + user_agent;
        } catch (Exception e) {
        }
        return values;
    }

    private String getConnectionType() {
        if (((ConnectivityManager) ctx.getSystemService("connectivity")).getActiveNetworkInfo().getTypeName().equals("WIFI")) {
            return "1";
        }
        return "0";
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

    private void getLocation(Context ctx) {
        if (ctx.getPackageManager().checkPermission("android.permission.ACCESS_COARSE_LOCATION", ctx.getPackageName()) == 0 && ctx.getPackageManager().checkPermission("android.permission.ACCESS_FINE_LOCATION", ctx.getPackageName()) == 0) {
            LocationManager mlocManager = (LocationManager) ctx.getSystemService("location");
            Location loc = mlocManager.getLastKnownLocation("network");
            if (loc == null) {
                mlocManager.requestLocationUpdates("network", 0, 0.0f, new MyLocationListener());
                return;
            }
            lon = String.valueOf(loc.getLongitude());
            lat = String.valueOf(loc.getLatitude());
        }
    }
}
