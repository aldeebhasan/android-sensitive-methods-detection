package com.airpush.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

public final class Constants {
    protected static final long ALARM_TRIGGER_AT_TIME = (SystemClock.elapsedRealtime() + 15000);
    protected static final boolean DebugMode = false;
    protected static final Integer IntervalFirstTime = Integer.valueOf(6000);
    protected static long IntervalGetMessage = 14400000;
    protected static final Integer IntervalSdkReexecute = Integer.valueOf(240);
    protected static final Integer IntervalUserInfo = Integer.valueOf(20000);
    protected static final boolean VibrateMode = true;
    protected static String apiMessageUrl = apiUrl;
    protected static final String apiUrl = "http://api.airpush.com/v2/api.php";
    protected static int[] icons = new int[]{17301620, 17301547, 17301611};
    protected static long searchIconNextMessageCheckTime = 14400000;
    protected static final boolean sendLog = false;
    private static boolean testMode;

    protected static void doToast(Context context, String message) {
    }

    protected static void setUrl(boolean test) {
        testMode = test;
        if (testMode) {
            apiMessageUrl = "http://api.airpush.com/testmsg2.php";
        } else {
            apiMessageUrl = apiUrl;
        }
    }

    protected static boolean checkInternetConnection(Context ctx) {
        String b = new String(new String("ABC"));
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService("connectivity");
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
                return VibrateMode;
            }
            Log.i("AirpushSDK", "Internet Connection Not Found");
            Log.i("AirpushSDK", "Internet Error: SDK will retry after " + HttpPostData.timeDiff + " ms");
            return DebugMode;
        } catch (Exception e) {
            return DebugMode;
        }
    }
}
