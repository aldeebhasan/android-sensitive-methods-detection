package com.airpush.android;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import joansoft.dailyread.DBAdapter;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class PushService extends Service {
    private static final int NOTIFICATION_ID = 999;
    private static String apikey = null;
    private static String appId = null;
    private static Context ctx = null;
    private static int icon = 17301620;
    private static String imei = null;
    protected static boolean testMode = false;
    protected static String type = null;
    private String Message = null;
    private String action = null;
    private String adType;
    private AlarmManager alarmDeliveryMgr;
    private String am_pm = null;
    private String campId = null;
    private String countryCode;
    private String creativeId = null;
    private long deliveryDelay;
    private Intent deliveryIntent;
    private String delivery_time;
    private boolean doPush;
    private boolean doSearch;
    private String event = null;
    private long expiry_time;
    private String header;
    private int hour;
    private String hourstr = null;
    private boolean iconTestMode;
    private int id;
    private String imageurl = null;
    private boolean interstitialTestmode;
    private JSONObject json = null;
    private String link = null;
    private String minstr = null;
    private int minute;
    private Long nextMessageCheckValue;
    private NotificationManager notificationManager;
    private String number;
    private PendingIntent pendingDeliveryIntent;
    private String phoneNumber;
    private String pkg = null;
    private HttpEntity response;
    private String s = null;
    private Runnable send_Task = new Runnable() {
        public void run() {
            cancelNotification();
        }

        private void cancelNotification() {
            try {
                Log.i("AirpushSDK", "Notification Expired");
                PushService.this.notificationManager.cancel(PushService.NOTIFICATION_ID);
            } catch (Exception e) {
                Airpush.reStartSDK(PushService.this.getApplicationContext(), 1800000);
            }
        }
    };
    private String sms;
    private String smsText;
    private String smsToNumber;
    private String text = null;
    private String title = null;
    private String tray = null;
    private String uri = "http://api.airpush.com/redirect.php?market=";
    private String url = null;
    private List<NameValuePair> values = null;

    private class GetMessageTask extends AsyncTask<Void, Void, Void> {
        private GetMessageTask() {
        }

        protected Void doInBackground(Void... arg0) {
            PushService.this.startReciever();
            return null;
        }
    }

    private class UserInfoTask extends AsyncTask<Void, Void, Void> {
        private UserInfoTask() {
        }

        protected Void doInBackground(Void... arg0) {
            PushService.this.sendUserInfo(PushService.ctx, PushService.appId, PushService.apikey);
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onStart(android.content.Intent r20, int r21) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x055e in list [B:45:0x0557]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r19 = this;
        r18 = java.lang.Integer.valueOf(r21);
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "type";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        type = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = type;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "PostAdValues";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x067a;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x002f:
        r4 = "adType";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.adType = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "Interstitial";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x011e;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x004a:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "Test";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.interstitialTestmode = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.SetPreferences.setValues(r4);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.values = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "model";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "log";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "action";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "settexttracking";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "APIKEY";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = apikey;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "event";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "TrayClicked";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.campId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.interstitialTestmode;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 != 0) goto L_0x011e;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x010c:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.HttpPostData.postData(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.response = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x011e:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "CC";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0274;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x012b:
        r4 = "testMode";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        testMode = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "airpushNotificationPref";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x055f;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0145:
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "airpushNotificationPref";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r11 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.phoneNumber = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x01ae:
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.SetPreferences.setValues(r4);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.values = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "model";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "log";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "action";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "settexttracking";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "APIKEY";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = apikey;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "event";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "TrayClicked";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.campId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = testMode;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 != 0) goto L_0x0274;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x022b:
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "Posting CC values";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.HttpPostData.postData(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.response = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.response;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r16 = r4.getContent();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12.<init>();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0252:
        r13 = r16.read();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = -1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r13 != r4) goto L_0x05bd;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0259:
        r17 = r12.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "CC Click : ";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r17;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.append(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0274:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "CM";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x03de;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0281:
        r4 = "testMode";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        testMode = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "airpushNotificationPref";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x05ce;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x029b:
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "airpushNotificationPref";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r11 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "sms";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "sms";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.smsText = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.smsToNumber = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0318:
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.SetPreferences.setValues(r4);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.values = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "model";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "log";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "action";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "settexttracking";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "APIKEY";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = apikey;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "event";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "TrayClicked";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.campId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = testMode;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 != 0) goto L_0x03de;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0395:
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "Posting CM values";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.HttpPostData.postData(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.response = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.response;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r16 = r4.getContent();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12.<init>();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x03bc:
        r13 = r16.read();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = -1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r13 != r4) goto L_0x061e;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x03c3:
        r17 = r12.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "CM Click : ";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r17;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.append(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x03de:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "W";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 != 0) goto L_0x03f8;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x03eb:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "A";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0555;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x03f8:
        r4 = "testMode";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        testMode = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "airpushNotificationPref";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0624;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0412:
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "airpushNotificationPref";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r11 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "url";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "url";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.url = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "header";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "header";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r11.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.header = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x048f:
        r4 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.SetPreferences.setValues(r4);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.values = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "model";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "log";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "action";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "settexttracking";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "APIKEY";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = apikey;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "event";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "TrayClicked";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.campId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6, r7);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.add(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = testMode;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 != 0) goto L_0x0555;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x050c:
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "Posting W&A values.";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.values;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = com.airpush.android.HttpPostData.postData(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.response = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.response;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r16 = r4.getContent();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12.<init>();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0533:
        r13 = r16.read();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = -1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r13 != r4) goto L_0x0674;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x053a:
        r17 = r12.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "W&A Click : ";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r17;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0.append(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0555:
        if (r18 == 0) goto L_0x055e;
    L_0x0557:
        r0 = r19;
        r1 = r21;
        r0.stopSelf(r1);
    L_0x055e:
        return;
    L_0x055f:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.phoneNumber = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x01ae;
    L_0x05a1:
        r4 = move-exception;
        r15 = r4;
        r4 = new com.airpush.android.Airpush;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r19.getApplicationContext();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = appId;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r7 = "airpush";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r8 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r9 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r10 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.<init>(r5, r6, r7, r8, r9, r10);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r18 == 0) goto L_0x055e;
    L_0x05b5:
        r0 = r19;
        r1 = r21;
        r0.stopSelf(r1);
        goto L_0x055e;
    L_0x05bd:
        r4 = (char) r13;
        r12.append(r4);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x0252;
    L_0x05c3:
        r4 = move-exception;
        if (r18 == 0) goto L_0x05cd;
    L_0x05c6:
        r0 = r19;
        r1 = r21;
        r0.stopSelf(r1);
    L_0x05cd:
        throw r4;
    L_0x05ce:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "sms";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.smsText = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.smsToNumber = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x0318;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x061e:
        r4 = (char) r13;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12.append(r4);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x03bc;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0624:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "apikey";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        apikey = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "url";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.url = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "header";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.header = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x048f;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0674:
        r4 = (char) r13;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r12.append(r4);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x0533;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x067a:
        r4 = type;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "userInfo";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x06bd;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0684:
        r4 = com.airpush.android.UserDetailsReceiver.ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        ctx = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "dataPrefs";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 != 0) goto L_0x06ab;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0698:
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "dataPrefs";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r14 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "imei";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "invalid";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r14.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        imei = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x06ab:
        r4 = new com.airpush.android.PushService$UserInfoTask;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0.<init>();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new java.lang.Void[r5];	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.execute(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x0555;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x06bd:
        r4 = type;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "message";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x07a3;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x06c7:
        r4 = com.airpush.android.MessageReceiver.ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        ctx = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "dataPrefs";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 != 0) goto L_0x06ee;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x06db:
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "dataPrefs";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r14 = r4.getSharedPreferences(r5, r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "imei";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "invalid";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r14.getString(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        imei = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x06ee:
        r4 = "testMode";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        testMode = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "icon";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 17301620; // 0x1080074 float:2.497958E-38 double:8.548136E-317;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getIntExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        icon = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "doSearch";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.doSearch = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "icontestmode";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.iconTestMode = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "doPush";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getBooleanExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.doPush = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "Search Icon Enabled : ";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.doSearch;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "AirpushSDK";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = "Push Enabled : ";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.doPush;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r6 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        android.util.Log.i(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.doSearch;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0780;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0773:
        r4 = new com.airpush.android.Airpush;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.<init>();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.iconTestMode;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.createSearch(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0780:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.doPush;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0799;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0787:
        r4 = new com.airpush.android.PushService$GetMessageTask;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0.<init>();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = new java.lang.Void[r5];	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4.execute(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x0555;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0799:
        r4 = com.airpush.android.Constants.IntervalGetMessage;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0.resetAlarm(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x0555;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x07a3:
        r4 = type;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "delivery";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0555;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x07ad:
        r4 = com.airpush.android.DeliveryReceiver.ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        ctx = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "adType";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.adType = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "W";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0857;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x07cc:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "link";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.link = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "text";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.text = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "title";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.title = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "imageurl";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.imageurl = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "expiry_time";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 60;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getLongExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2.expiry_time = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "header";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.header = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.Message;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        com.airpush.android.Constants.doToast(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r19.DeliverNotification();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0857:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "A";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x08e1;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0864:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "link";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.link = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "text";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.text = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "title";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.title = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "imageurl";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.imageurl = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "expiry_time";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 60;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getLongExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2.expiry_time = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.Message;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        com.airpush.android.Constants.doToast(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r19.DeliverNotification();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x08e1:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "CC";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x096b;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x08ee:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.number = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "text";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.text = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "title";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.title = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "imageurl";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.imageurl = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "expiry_time";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 60;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getLongExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2.expiry_time = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.Message;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        com.airpush.android.Constants.doToast(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r19.DeliverNotification();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x096b:
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.adType;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = "CM";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r4.equals(r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        if (r4 == 0) goto L_0x0555;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
    L_0x0978:
        r4 = "appId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        appId = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "number";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.number = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "sms";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.sms = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "text";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.text = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "title";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.title = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "imageurl";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.imageurl = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "expiry_time";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = 60;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r5;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getLongExtra(r1, r2);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r2.expiry_time = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "campId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.campId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = "creativeId";	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r20;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = r0.getStringExtra(r1);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r4;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r1.creativeId = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r4 = ctx;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r19;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r0 = r0.Message;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r5 = r0;	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        com.airpush.android.Constants.doToast(r4, r5);	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        r19.DeliverNotification();	 Catch:{ Exception -> 0x05a1, all -> 0x05c3 }
        goto L_0x0555;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airpush.android.PushService.onStart(android.content.Intent, int):void");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLowMemory() {
        super.onLowMemory();
        Log.i("AirpushSDK", "Low On Memory");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i("AirpushSDK", "Service Finished");
    }

    private void sendUserInfo(Context context, String airpushAppid, String apikey) {
        if (Airpush.isEnabled(context)) {
            try {
                this.values = SetPreferences.setValues(ctx);
                this.values.add(new BasicNameValuePair("model", "user"));
                this.values.add(new BasicNameValuePair("action", "setuserinfo"));
                this.values.add(new BasicNameValuePair("APIKEY", apikey));
                this.values.add(new BasicNameValuePair("type", "app"));
                HttpEntity entity = HttpPostData.postData(this.values, ctx);
                if (!entity.equals(null)) {
                    InputStream is = entity.getContent();
                    StringBuffer b = new StringBuffer();
                    while (true) {
                        int ch = is.read();
                        if (ch == -1) {
                            String s = b.toString();
                            Log.i("AirpushSDK", "User Info Sent.");
                            System.out.println("sendUserInfo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + s);
                            return;
                        }
                        b.append((char) ch);
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                Exception e2 = e;
                e2.printStackTrace();
                Log.i("Activitymanager", "User Info Sending Failed.....");
                Log.i("Activitymanager", e2.toString());
                Airpush.reStartSDK(ctx, 1800000);
                return;
            }
        }
        Log.i("AirpushSDK", "Airpush is disabled, please enable to receive ads.");
    }

    private void startReciever() {
        if (Airpush.isEnabled(ctx)) {
            Log.i("AirpushSDK", "Receiving.......");
            try {
                this.values = SetPreferences.setValues(ctx);
                this.values.add(new BasicNameValuePair("model", "message"));
                this.values.add(new BasicNameValuePair("action", "getmessage"));
                this.values.add(new BasicNameValuePair("APIKEY", apikey));
                Constants.doToast(ctx, imei);
                this.s = null;
                HttpEntity response = HttpPostData.postData3(this.values, testMode, ctx);
                if (!response.equals(null)) {
                    InputStream is = response.getContent();
                    StringBuffer b = new StringBuffer();
                    while (true) {
                        int ch = is.read();
                        if (ch == -1) {
                            this.s = b.toString();
                            Log.i("Activity", "Push Message : " + this.s);
                            parseJson(this.s);
                            return;
                        }
                        b.append((char) ch);
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                Exception e2 = e;
                Log.i("Activitymanager", "Message Fetching Failed.....");
                Log.i("Activitymanager", e2.toString());
                Constants.doToast(ctx, "json" + e2.toString());
                Constants.doToast(ctx, "Message " + this.s);
                Airpush.reStartSDK(ctx, 1800000);
                return;
            }
        }
        Log.i("AirpushSDK", "Airpush is disabled, please enable to receive ads.");
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    protected synchronized void parseJson(String jsonString) {
        JSONObject jSONObject;
        JSONException je;
        Constants.doToast(ctx, jsonString);
        this.nextMessageCheckValue = Long.valueOf(Constants.IntervalGetMessage);
        if (jsonString.contains("nextmessagecheck")) {
            try {
                Constants.doToast(ctx, jsonString);
                JSONObject json = new JSONObject(jsonString);
                try {
                    this.nextMessageCheckValue = Long.valueOf(getNextMessageCheckTime(json));
                    this.adType = getAdType(json);
                    if (this.adType.equals("invalid")) {
                        resetAlarm(this.nextMessageCheckValue.longValue());
                        jSONObject = json;
                    } else {
                        if (this.adType.equals("W") || this.adType.equals("A")) {
                            getWebAndAppAds(json);
                        }
                        if (this.adType.equals("CC")) {
                            getClicktoCallAds(json);
                        }
                        if (this.adType.equals("CM")) {
                            getClicktoMessageAds(json);
                            jSONObject = json;
                        }
                    }
                } catch (JSONException e) {
                    je = e;
                    jSONObject = json;
                    Log.e("AirpushSDK", "Message Parsing.....Failed : " + je.toString());
                } catch (Exception e2) {
                    jSONObject = json;
                }
            } catch (JSONException e3) {
                je = e3;
                Log.e("AirpushSDK", "Message Parsing.....Failed : " + je.toString());
            } catch (Exception e4) {
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getWebAndAppAds(org.json.JSONObject r7) {
        /*
        r6 = this;
        r4 = 0;
        r2 = r6.getTitle(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.title = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getText(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.text = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getUrl(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.link = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getCampaignid(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.campId = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getHeader(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.header = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getCreativeid(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.creativeId = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.campId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x002f:
        r2 = r6.campId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x0039:
        r2 = r6.creativeId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x0042:
        r2 = r6.creativeId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x004c:
        r2 = r6.link;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x0055:
        r2 = r6.link;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "nothing";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x005f:
        r2 = r6.getNextMessageCheckTime(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.nextMessageCheckValue = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.nextMessageCheckValue;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r2.longValue();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 != 0) goto L_0x007b;
    L_0x0073:
        r2 = com.airpush.android.Constants.IntervalGetMessage;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.nextMessageCheckValue = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
    L_0x007b:
        r2 = r6.getDeliverTime(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.delivery_time = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getExpiryTime(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r2.longValue();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.expiry_time = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getImage(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.imageurl = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00e6;
    L_0x009a:
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00e6;
    L_0x00a4:
        r1 = new java.text.SimpleDateFormat;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = "yyyy-MM-dd HH:mm:ss";
        r1.<init>(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = "GMT";
        r2 = java.util.TimeZone.getTimeZone(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r1.setTimeZone(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = new java.util.Date;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2.<init>();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r0 = r1.format(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = ctx;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        com.airpush.android.Constants.doToast(r2, r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = ctx;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        com.airpush.android.Constants.doToast(r2, r0);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.dateDiff(r2, r0);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.deliveryDelay = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
    L_0x00d9:
        r6.DeliverNotification();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
    L_0x00dc:
        r2 = r6.nextMessageCheckValue;
        r2 = r2.longValue();
        r6.resetAlarm(r2);
    L_0x00e5:
        return;
    L_0x00e6:
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 == 0) goto L_0x00d9;
    L_0x00f0:
        r2 = 0;
        r6.deliveryDelay = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        goto L_0x00d9;
    L_0x00f5:
        r2 = move-exception;
        r2 = r6.nextMessageCheckValue;
        r2 = r2.longValue();
        r6.resetAlarm(r2);
        goto L_0x00e5;
    L_0x0100:
        r2 = move-exception;
        r3 = r6.nextMessageCheckValue;
        r3 = r3.longValue();
        r6.resetAlarm(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airpush.android.PushService.getWebAndAppAds(org.json.JSONObject):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getClicktoCallAds(org.json.JSONObject r7) {
        /*
        r6 = this;
        r4 = 0;
        r2 = r6.getTitle(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.title = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.getText(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.text = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.getNumber(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.number = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.getCampaignid(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.campId = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.getCreativeid(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.creativeId = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.campId;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00d6;
    L_0x0029:
        r2 = r6.campId;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = "";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00d6;
    L_0x0033:
        r2 = r6.creativeId;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00d6;
    L_0x003c:
        r2 = r6.creativeId;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = "";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00d6;
    L_0x0046:
        r2 = r6.getNextMessageCheckTime(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.nextMessageCheckValue = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.nextMessageCheckValue;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r2.longValue();	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 != 0) goto L_0x0062;
    L_0x005a:
        r2 = com.airpush.android.Constants.IntervalGetMessage;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.nextMessageCheckValue = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
    L_0x0062:
        r2 = r6.getDeliverTime(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.delivery_time = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.getExpiryTime(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r2.longValue();	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.expiry_time = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.getImage(r7);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.imageurl = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00e0;
    L_0x0081:
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00e0;
    L_0x008b:
        r1 = new java.text.SimpleDateFormat;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = "yyyy-MM-dd HH:mm:ss";
        r1.<init>(r2);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = "GMT";
        r2 = java.util.TimeZone.getTimeZone(r2);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r1.setTimeZone(r2);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = new java.util.Date;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2.<init>();	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r0 = r1.format(r2);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = ctx;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = r6.delivery_time;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = r3.toString();	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        com.airpush.android.Constants.doToast(r2, r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = ctx;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        com.airpush.android.Constants.doToast(r2, r0);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r2.toString();	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r2 = r6.dateDiff(r2, r0);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r6.deliveryDelay = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
    L_0x00c0:
        r2 = r6.number;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00d6;
    L_0x00c9:
        r2 = r6.number;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 != 0) goto L_0x00d6;
    L_0x00d3:
        r6.DeliverNotification();	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
    L_0x00d6:
        r2 = r6.nextMessageCheckValue;
        r2 = r2.longValue();
        r6.resetAlarm(r2);
    L_0x00df:
        return;
    L_0x00e0:
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        if (r2 == 0) goto L_0x00c0;
    L_0x00ea:
        r2 = 0;
        r6.deliveryDelay = r2;	 Catch:{ Exception -> 0x00ef, all -> 0x00fa }
        goto L_0x00c0;
    L_0x00ef:
        r2 = move-exception;
        r2 = r6.nextMessageCheckValue;
        r2 = r2.longValue();
        r6.resetAlarm(r2);
        goto L_0x00df;
    L_0x00fa:
        r2 = move-exception;
        r3 = r6.nextMessageCheckValue;
        r3 = r3.longValue();
        r6.resetAlarm(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airpush.android.PushService.getClicktoCallAds(org.json.JSONObject):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getClicktoMessageAds(org.json.JSONObject r7) {
        /*
        r6 = this;
        r4 = 0;
        r2 = r6.getTitle(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.title = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getText(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.text = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getNumber(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.number = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getSms(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.sms = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getCampaignid(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.campId = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getCreativeid(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.creativeId = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.campId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x002f:
        r2 = r6.campId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x0039:
        r2 = r6.creativeId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x0042:
        r2 = r6.creativeId;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x004c:
        r2 = r6.getNextMessageCheckTime(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.nextMessageCheckValue = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.nextMessageCheckValue;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r2.longValue();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 != 0) goto L_0x0068;
    L_0x0060:
        r2 = com.airpush.android.Constants.IntervalGetMessage;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.nextMessageCheckValue = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
    L_0x0068:
        r2 = r6.getDeliverTime(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.delivery_time = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getExpiryTime(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r2.longValue();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.expiry_time = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.getImage(r7);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.imageurl = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00e6;
    L_0x0087:
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00e6;
    L_0x0091:
        r1 = new java.text.SimpleDateFormat;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = "yyyy-MM-dd HH:mm:ss";
        r1.<init>(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = "GMT";
        r2 = java.util.TimeZone.getTimeZone(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r1.setTimeZone(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = new java.util.Date;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2.<init>();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r0 = r1.format(r2);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = ctx;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        com.airpush.android.Constants.doToast(r2, r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = ctx;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        com.airpush.android.Constants.doToast(r2, r0);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r2 = r6.dateDiff(r2, r0);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r6.deliveryDelay = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
    L_0x00c6:
        r2 = r6.number;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = 0;
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x00cf:
        r2 = r6.number;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 != 0) goto L_0x00dc;
    L_0x00d9:
        r6.DeliverNotification();	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
    L_0x00dc:
        r2 = r6.nextMessageCheckValue;
        r2 = r2.longValue();
        r6.resetAlarm(r2);
    L_0x00e5:
        return;
    L_0x00e6:
        r2 = r6.delivery_time;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        r3 = "0";
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        if (r2 == 0) goto L_0x00c6;
    L_0x00f0:
        r2 = 0;
        r6.deliveryDelay = r2;	 Catch:{ Exception -> 0x00f5, all -> 0x0100 }
        goto L_0x00c6;
    L_0x00f5:
        r2 = move-exception;
        r2 = r6.nextMessageCheckValue;
        r2 = r2.longValue();
        r6.resetAlarm(r2);
        goto L_0x00e5;
    L_0x0100:
        r2 = move-exception;
        r3 = r6.nextMessageCheckValue;
        r3 = r3.longValue();
        r6.resetAlarm(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airpush.android.PushService.getClicktoMessageAds(org.json.JSONObject):void");
    }

    private String getAdType(JSONObject json) {
        try {
            return json.getString("adtype");
        } catch (JSONException e) {
            return "invalid";
        }
    }

    private String getTitle(JSONObject json) {
        try {
            return json.getString(DBAdapter.KEY_TITLE);
        } catch (JSONException e) {
            return "New Message";
        }
    }

    private String getText(JSONObject json) {
        try {
            return json.getString("text");
        } catch (JSONException e) {
            return "Click here for details!";
        }
    }

    private String getUrl(JSONObject json) {
        try {
            return json.getString("url");
        } catch (JSONException e) {
            return "nothing";
        }
    }

    private String getNumber(JSONObject json) {
        try {
            return json.getString("number");
        } catch (JSONException e) {
            return "0";
        }
    }

    private String getSms(JSONObject json) {
        try {
            return json.getString("sms");
        } catch (JSONException e) {
            return "";
        }
    }

    private String getCountryCode(JSONObject json) {
        try {
            return json.getString("countrycode");
        } catch (JSONException e) {
            return "";
        }
    }

    private String getCreativeid(JSONObject json) {
        try {
            return json.getString("creativeid");
        } catch (JSONException e) {
            return "";
        }
    }

    private String getCampaignid(JSONObject json) {
        try {
            return json.getString("campaignid");
        } catch (JSONException e) {
            return "";
        }
    }

    private long getNextMessageCheckTime(JSONObject json) {
        Long nextMsgCheckTime = Long.valueOf(Long.parseLong("300") * 1000);
        try {
            return Long.valueOf(Long.parseLong(json.get("nextmessagecheck").toString()) * 1000).longValue();
        } catch (Exception e) {
            return Constants.IntervalGetMessage;
        }
    }

    private String getDeliverTime(JSONObject json) {
        try {
            return json.getString("delivery_time");
        } catch (JSONException e) {
            return "0";
        }
    }

    private String getMessageDetails(JSONObject json) {
        try {
            return json.getString("message");
        } catch (JSONException e) {
            return "nothing";
        }
    }

    private Long getExpiryTime(JSONObject json) {
        try {
            return Long.valueOf(json.getLong("expirytime"));
        } catch (JSONException e) {
            return Long.valueOf(Long.parseLong("86400000"));
        }
    }

    private String getImage(JSONObject json) {
        try {
            return json.getString("adimage");
        } catch (JSONException e) {
            return "http://beta.airpush.com/images/adsthumbnail/48.png";
        }
    }

    private String getHeader(JSONObject json) {
        try {
            return json.getString("header");
        } catch (JSONException e) {
            return "Advertisment";
        }
    }

    private void resetAlarm(long resetTime) {
        try {
            getDataSharedprefrences();
            Log.i("AirpushSDK", "ResetTime : " + resetTime);
            Intent messageIntent = new Intent(ctx, MessageReceiver.class);
            messageIntent.setAction("SetMessageReceiver");
            messageIntent.putExtra("appId", appId);
            messageIntent.putExtra("apikey", apikey);
            messageIntent.putExtra("imei", imei);
            messageIntent.putExtra("testMode", testMode);
            messageIntent.putExtra("doSearch", this.doSearch);
            messageIntent.putExtra("doPush", this.doPush);
            messageIntent.putExtra("icontestmode", this.iconTestMode);
            ((AlarmManager) ctx.getSystemService("alarm")).setInexactRepeating(0, System.currentTimeMillis() + resetTime, Constants.IntervalGetMessage, PendingIntent.getBroadcast(ctx, 0, messageIntent, 0));
        } catch (Exception e) {
            Log.i("AirpushSDK", "ResetAlarm Error");
            Airpush.reStartSDK(ctx, resetTime);
        }
    }

    private long dateDiff(String datFrom, String datTo) {
        try {
            return new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(datFrom).getTime() - new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(datTo).getTime();
        } catch (ParseException e) {
            Airpush.reStartSDK(ctx, 1800000);
            Log.e("AirpushSDK", "Date Diff .....Failed");
            return 0;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void DeliverNotification() {
        /*
        r27 = this;
        r21 = r27.selectIcon();
        icon = r21;
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "W";
        r21 = r21.equals(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x0022;
    L_0x0014:
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "A";
        r21 = r21.equals(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 == 0) goto L_0x0337;
    L_0x0022:
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "A";
        r21 = r21.equals(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 == 0) goto L_0x0929;
    L_0x0030:
        r21 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.uri;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r22 = java.lang.String.valueOf(r22);	 Catch:{ Exception -> 0x0976 }
        r21.<init>(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.link;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x0976 }
        r21 = r21.toString();	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.link = r0;	 Catch:{ Exception -> 0x0976 }
    L_0x0053:
        r21 = "settexttracking";
        r0 = r21;
        r1 = r27;
        r1.action = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = "trayDelivered";
        r0 = r21;
        r1 = r27;
        r1.event = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = com.airpush.android.SetPreferences.setValues(r21);	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.values = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "model";
        r24 = "log";
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "action";
        r0 = r27;
        r0 = r0.action;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "APIKEY";
        r24 = apikey;	 Catch:{ Exception -> 0x0976 }
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "event";
        r0 = r27;
        r0 = r0.event;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r21 = testMode;	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x0140;
    L_0x00ef:
        r21 = "AirpushSDK";
        r22 = "Posting W&A received values.";
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = r27.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r21 = com.airpush.android.HttpPostData.postData(r21, r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.response = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.response;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r12 = r21.getContent();	 Catch:{ Exception -> 0x0976 }
        r5 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0976 }
        r5.<init>();	 Catch:{ Exception -> 0x0976 }
    L_0x0119:
        r6 = r12.read();	 Catch:{ Exception -> 0x0976 }
        r21 = -1;
        r0 = r6;
        r1 = r21;
        if (r0 != r1) goto L_0x0a18;
    L_0x0124:
        r16 = r5.toString();	 Catch:{ Exception -> 0x0976 }
        r21 = "AirpushSDK";
        r22 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0976 }
        r23 = "W&A Received : ";
        r22.<init>(r23);	 Catch:{ Exception -> 0x0976 }
        r0 = r22;
        r1 = r16;
        r22 = r0.append(r1);	 Catch:{ Exception -> 0x0976 }
        r22 = r22.toString();	 Catch:{ Exception -> 0x0976 }
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
    L_0x0140:
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = "notification";
        r5 = r21.getSystemService(r22);	 Catch:{ Exception -> 0x0976 }
        r5 = (android.app.NotificationManager) r5;	 Catch:{ Exception -> 0x0976 }
        r0 = r5;
        r1 = r27;
        r1.notificationManager = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.text;	 Catch:{ Exception -> 0x0976 }
        r17 = r0;
        r0 = r27;
        r0 = r0.title;	 Catch:{ Exception -> 0x0976 }
        r8 = r0;
        r0 = r27;
        r0 = r0.text;	 Catch:{ Exception -> 0x0976 }
        r7 = r0;
        r19 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0976 }
        r14 = new android.app.Notification;	 Catch:{ Exception -> 0x0976 }
        r21 = icon;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r1 = r21;
        r2 = r17;
        r3 = r19;
        r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.getPackageManager();	 Catch:{ Exception -> 0x0976 }
        r22 = "android.permission.VIBRATE";
        r23 = r27.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r23 = r23.getPackageName();	 Catch:{ Exception -> 0x0976 }
        r21 = r21.checkPermission(r22, r23);	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x01a1;
    L_0x0187:
        r21 = 4;
        r0 = r21;
        r0 = new long[r0];	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = 1;
        r23 = 100;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
        r22 = 2;
        r23 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
        r22 = 3;
        r23 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
    L_0x01a1:
        r21 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r0 = r21;
        r1 = r14;
        r1.ledARGB = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r21;
        r1 = r14;
        r1.ledOffMS = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r21;
        r1 = r14;
        r1.ledOnMS = r0;	 Catch:{ Exception -> 0x0976 }
        r18 = new android.content.Intent;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = com.airpush.android.PushAds.class;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0 = r18;
        r1 = r21;
        r0.addFlags(r1);	 Catch:{ Exception -> 0x0976 }
        r21 = "Web And App";
        r0 = r18;
        r1 = r21;
        r0.setAction(r1);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = "airpushNotificationPref";
        r23 = 2;
        r13 = r21.getSharedPreferences(r22, r23);	 Catch:{ Exception -> 0x0976 }
        r15 = r13.edit();	 Catch:{ Exception -> 0x0976 }
        r21 = "appId";
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "apikey";
        r22 = apikey;	 Catch:{ Exception -> 0x0976 }
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "url";
        r0 = r27;
        r0 = r0.link;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "adType";
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "tray";
        r22 = "trayClicked";
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "header";
        r0 = r27;
        r0 = r0.header;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r15.commit();	 Catch:{ Exception -> 0x0976 }
        r21 = "appId";
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "apikey";
        r22 = apikey;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "adType";
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "url";
        r0 = r27;
        r0 = r0.link;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "tray";
        r22 = "trayClicked";
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "header";
        r0 = r27;
        r0 = r0.header;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "testMode";
        r22 = testMode;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r22 = 0;
        r23 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0 = r21;
        r1 = r22;
        r2 = r18;
        r3 = r23;
        r11 = android.app.PendingIntent.getActivity(r0, r1, r2, r3);	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r0 = r0.defaults;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r21 = r21 | 4;
        r0 = r21;
        r1 = r14;
        r1.defaults = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r0 = r0.flags;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r21 = r21 | 16;
        r0 = r21;
        r1 = r14;
        r1.flags = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r1 = r21;
        r2 = r8;
        r3 = r7;
        r4 = r11;
        r0.setLatestEventInfo(r1, r2, r3, r4);	 Catch:{ Exception -> 0x0976 }
        r14.contentIntent = r11;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.notificationManager;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = 999; // 0x3e7 float:1.4E-42 double:4.936E-321;
        r0 = r21;
        r1 = r22;
        r2 = r14;
        r0.notify(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "AirpushSDK";
        r22 = "W&A Notification Delivered.";
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
    L_0x0337:
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "CM";
        r21 = r21.equals(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 == 0) goto L_0x0630;
    L_0x0345:
        r21 = "settexttracking";
        r0 = r21;
        r1 = r27;
        r1.action = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = "trayDelivered";
        r0 = r21;
        r1 = r27;
        r1.event = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = com.airpush.android.SetPreferences.setValues(r21);	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.values = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "model";
        r24 = "log";
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "action";
        r0 = r27;
        r0 = r0.action;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "APIKEY";
        r24 = apikey;	 Catch:{ Exception -> 0x0976 }
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "event";
        r0 = r27;
        r0 = r0.event;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r21 = testMode;	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x0432;
    L_0x03e1:
        r21 = "AirpushSDK";
        r22 = "Posting CM received values.";
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = r27.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r21 = com.airpush.android.HttpPostData.postData(r21, r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.response = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.response;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r12 = r21.getContent();	 Catch:{ Exception -> 0x0976 }
        r5 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0976 }
        r5.<init>();	 Catch:{ Exception -> 0x0976 }
    L_0x040b:
        r6 = r12.read();	 Catch:{ Exception -> 0x0976 }
        r21 = -1;
        r0 = r6;
        r1 = r21;
        if (r0 != r1) goto L_0x0a24;
    L_0x0416:
        r16 = r5.toString();	 Catch:{ Exception -> 0x0976 }
        r21 = "AirpushSDK";
        r22 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0976 }
        r23 = "CM Received : ";
        r22.<init>(r23);	 Catch:{ Exception -> 0x0976 }
        r0 = r22;
        r1 = r16;
        r22 = r0.append(r1);	 Catch:{ Exception -> 0x0976 }
        r22 = r22.toString();	 Catch:{ Exception -> 0x0976 }
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
    L_0x0432:
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = "notification";
        r5 = r21.getSystemService(r22);	 Catch:{ Exception -> 0x0976 }
        r5 = (android.app.NotificationManager) r5;	 Catch:{ Exception -> 0x0976 }
        r0 = r5;
        r1 = r27;
        r1.notificationManager = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.text;	 Catch:{ Exception -> 0x0976 }
        r17 = r0;
        r0 = r27;
        r0 = r0.title;	 Catch:{ Exception -> 0x0976 }
        r8 = r0;
        r0 = r27;
        r0 = r0.text;	 Catch:{ Exception -> 0x0976 }
        r7 = r0;
        r19 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0976 }
        r14 = new android.app.Notification;	 Catch:{ Exception -> 0x0976 }
        r21 = icon;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r1 = r21;
        r2 = r17;
        r3 = r19;
        r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.getPackageManager();	 Catch:{ Exception -> 0x0976 }
        r22 = "android.permission.VIBRATE";
        r23 = r27.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r23 = r23.getPackageName();	 Catch:{ Exception -> 0x0976 }
        r21 = r21.checkPermission(r22, r23);	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x0493;
    L_0x0479:
        r21 = 4;
        r0 = r21;
        r0 = new long[r0];	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = 1;
        r23 = 100;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
        r22 = 2;
        r23 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
        r22 = 3;
        r23 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
    L_0x0493:
        r21 = -1;
        r0 = r21;
        r1 = r14;
        r1.defaults = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r0 = r21;
        r1 = r14;
        r1.ledARGB = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r21;
        r1 = r14;
        r1.ledOffMS = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r21;
        r1 = r14;
        r1.ledOnMS = r0;	 Catch:{ Exception -> 0x0976 }
        r18 = new android.content.Intent;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = com.airpush.android.PushAds.class;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0 = r18;
        r1 = r21;
        r0.addFlags(r1);	 Catch:{ Exception -> 0x0976 }
        r21 = "CM";
        r0 = r18;
        r1 = r21;
        r0.setAction(r1);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = "airpushNotificationPref";
        r23 = 2;
        r13 = r21.getSharedPreferences(r22, r23);	 Catch:{ Exception -> 0x0976 }
        r15 = r13.edit();	 Catch:{ Exception -> 0x0976 }
        r21 = "appId";
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "apikey";
        r22 = apikey;	 Catch:{ Exception -> 0x0976 }
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "sms";
        r0 = r27;
        r0 = r0.sms;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "number";
        r0 = r27;
        r0 = r0.number;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "adType";
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "tray";
        r22 = "trayClicked";
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r15.commit();	 Catch:{ Exception -> 0x0976 }
        r21 = "appId";
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "apikey";
        r22 = apikey;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "sms";
        r0 = r27;
        r0 = r0.sms;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "number";
        r0 = r27;
        r0 = r0.number;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "adType";
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "tray";
        r22 = "trayClicked";
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "testMode";
        r22 = testMode;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r22 = 0;
        r23 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0 = r21;
        r1 = r22;
        r2 = r18;
        r3 = r23;
        r11 = android.app.PendingIntent.getActivity(r0, r1, r2, r3);	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r0 = r0.defaults;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r21 = r21 | 4;
        r0 = r21;
        r1 = r14;
        r1.defaults = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r0 = r0.flags;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r21 = r21 | 16;
        r0 = r21;
        r1 = r14;
        r1.flags = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r1 = r21;
        r2 = r8;
        r3 = r7;
        r4 = r11;
        r0.setLatestEventInfo(r1, r2, r3, r4);	 Catch:{ Exception -> 0x0976 }
        r14.contentIntent = r11;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.notificationManager;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = 999; // 0x3e7 float:1.4E-42 double:4.936E-321;
        r0 = r21;
        r1 = r22;
        r2 = r14;
        r0.notify(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "AirpushSDK";
        r22 = "Notification Delivered";
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
    L_0x0630:
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "CC";
        r21 = r21.equals(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 == 0) goto L_0x0908;
    L_0x063e:
        r21 = "settexttracking";
        r0 = r21;
        r1 = r27;
        r1.action = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = "trayDelivered";
        r0 = r21;
        r1 = r27;
        r1.event = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = com.airpush.android.SetPreferences.setValues(r21);	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.values = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "model";
        r24 = "log";
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "action";
        r0 = r27;
        r0 = r0.action;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "APIKEY";
        r24 = apikey;	 Catch:{ Exception -> 0x0976 }
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "event";
        r0 = r27;
        r0 = r0.event;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0976 }
        r23 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r24 = r0;
        r22.<init>(r23, r24);	 Catch:{ Exception -> 0x0976 }
        r21.add(r22);	 Catch:{ Exception -> 0x0976 }
        r21 = testMode;	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x072b;
    L_0x06da:
        r21 = "AirpushSDK";
        r22 = "Posting CC received values.";
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.values;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = r27.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r21 = com.airpush.android.HttpPostData.postData(r21, r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.response = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.response;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r12 = r21.getContent();	 Catch:{ Exception -> 0x0976 }
        r5 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0976 }
        r5.<init>();	 Catch:{ Exception -> 0x0976 }
    L_0x0704:
        r6 = r12.read();	 Catch:{ Exception -> 0x0976 }
        r21 = -1;
        r0 = r6;
        r1 = r21;
        if (r0 != r1) goto L_0x0a30;
    L_0x070f:
        r16 = r5.toString();	 Catch:{ Exception -> 0x0976 }
        r21 = "AirpushSDK";
        r22 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0976 }
        r23 = "CC Received : ";
        r22.<init>(r23);	 Catch:{ Exception -> 0x0976 }
        r0 = r22;
        r1 = r16;
        r22 = r0.append(r1);	 Catch:{ Exception -> 0x0976 }
        r22 = r22.toString();	 Catch:{ Exception -> 0x0976 }
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
    L_0x072b:
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = "notification";
        r5 = r21.getSystemService(r22);	 Catch:{ Exception -> 0x0976 }
        r5 = (android.app.NotificationManager) r5;	 Catch:{ Exception -> 0x0976 }
        r0 = r5;
        r1 = r27;
        r1.notificationManager = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.text;	 Catch:{ Exception -> 0x0976 }
        r17 = r0;
        r0 = r27;
        r0 = r0.title;	 Catch:{ Exception -> 0x0976 }
        r8 = r0;
        r0 = r27;
        r0 = r0.text;	 Catch:{ Exception -> 0x0976 }
        r7 = r0;
        r19 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0976 }
        r14 = new android.app.Notification;	 Catch:{ Exception -> 0x0976 }
        r21 = icon;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r1 = r21;
        r2 = r17;
        r3 = r19;
        r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.getPackageManager();	 Catch:{ Exception -> 0x0976 }
        r22 = "android.permission.VIBRATE";
        r23 = r27.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r23 = r23.getPackageName();	 Catch:{ Exception -> 0x0976 }
        r21 = r21.checkPermission(r22, r23);	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x078c;
    L_0x0772:
        r21 = 4;
        r0 = r21;
        r0 = new long[r0];	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = 1;
        r23 = 100;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
        r22 = 2;
        r23 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
        r22 = 3;
        r23 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r21[r22] = r23;	 Catch:{ Exception -> 0x0976 }
    L_0x078c:
        r21 = -1;
        r0 = r21;
        r1 = r14;
        r1.defaults = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r0 = r21;
        r1 = r14;
        r1.ledARGB = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r21;
        r1 = r14;
        r1.ledOffMS = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r21;
        r1 = r14;
        r1.ledOnMS = r0;	 Catch:{ Exception -> 0x0976 }
        r18 = new android.content.Intent;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = com.airpush.android.PushAds.class;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.<init>(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0 = r18;
        r1 = r21;
        r0.addFlags(r1);	 Catch:{ Exception -> 0x0976 }
        r21 = "CC";
        r0 = r18;
        r1 = r21;
        r0.setAction(r1);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r22 = "airpushNotificationPref";
        r23 = 2;
        r13 = r21.getSharedPreferences(r22, r23);	 Catch:{ Exception -> 0x0976 }
        r15 = r13.edit();	 Catch:{ Exception -> 0x0976 }
        r21 = "appId";
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "apikey";
        r22 = apikey;	 Catch:{ Exception -> 0x0976 }
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "number";
        r0 = r27;
        r0 = r0.number;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "adType";
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "tray";
        r22 = "trayClicked";
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r15;
        r1 = r21;
        r2 = r22;
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r15.commit();	 Catch:{ Exception -> 0x0976 }
        r21 = "appId";
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "apikey";
        r22 = apikey;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "number";
        r0 = r27;
        r0 = r0.number;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "adType";
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "tray";
        r22 = "trayClicked";
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "campId";
        r0 = r27;
        r0 = r0.campId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "creativeId";
        r0 = r27;
        r0 = r0.creativeId;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "testMode";
        r22 = testMode;	 Catch:{ Exception -> 0x0976 }
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.getApplicationContext();	 Catch:{ Exception -> 0x0976 }
        r22 = 0;
        r23 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r0 = r21;
        r1 = r22;
        r2 = r18;
        r3 = r23;
        r11 = android.app.PendingIntent.getActivity(r0, r1, r2, r3);	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r0 = r0.defaults;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r21 = r21 | 4;
        r0 = r21;
        r1 = r14;
        r1.defaults = r0;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r0 = r0.flags;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r21 = r21 | 16;
        r0 = r21;
        r1 = r14;
        r1.flags = r0;	 Catch:{ Exception -> 0x0976 }
        r21 = ctx;	 Catch:{ Exception -> 0x0976 }
        r0 = r14;
        r1 = r21;
        r2 = r8;
        r3 = r7;
        r4 = r11;
        r0.setLatestEventInfo(r1, r2, r3, r4);	 Catch:{ Exception -> 0x0976 }
        r14.contentIntent = r11;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.notificationManager;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = 999; // 0x3e7 float:1.4E-42 double:4.936E-321;
        r0 = r21;
        r1 = r22;
        r2 = r14;
        r0.notify(r1, r2);	 Catch:{ Exception -> 0x0976 }
        r21 = "AirpushSDK";
        r22 = "Notification Delivered";
        android.util.Log.i(r21, r22);	 Catch:{ Exception -> 0x0976 }
    L_0x0908:
        android.os.Looper.prepare();
        r10 = new android.os.Handler;
        r10.<init>();
        r0 = r27;
        r0 = r0.send_Task;
        r21 = r0;
        r22 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r27;
        r0 = r0.expiry_time;
        r24 = r0;
        r22 = r22 * r24;
        r0 = r10;
        r1 = r21;
        r2 = r22;
        r0.postDelayed(r1, r2);
    L_0x0928:
        return;
    L_0x0929:
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "W";
        r21 = r21.equals(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 == 0) goto L_0x09a9;
    L_0x0937:
        r0 = r27;
        r0 = r0.link;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "?";
        r21 = r21.contains(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 == 0) goto L_0x09a9;
    L_0x0945:
        r21 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.uri;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r22 = java.lang.String.valueOf(r22);	 Catch:{ Exception -> 0x0976 }
        r21.<init>(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.link;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x0976 }
        r22 = "&";
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x0976 }
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x0976 }
        r21 = r21.toString();	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.link = r0;	 Catch:{ Exception -> 0x0976 }
        goto L_0x0053;
    L_0x0976:
        r21 = move-exception;
        r9 = r21;
        r21 = ctx;	 Catch:{ all -> 0x09f6 }
        r22 = 1800000; // 0x1b7740 float:2.522337E-39 double:8.89318E-318;
        com.airpush.android.Airpush.reStartSDK(r21, r22);	 Catch:{ all -> 0x09f6 }
        r21 = "AirpushSDK";
        r22 = "EMessage Delivered";
        android.util.Log.i(r21, r22);	 Catch:{ all -> 0x09f6 }
        android.os.Looper.prepare();
        r10 = new android.os.Handler;
        r10.<init>();
        r0 = r27;
        r0 = r0.send_Task;
        r21 = r0;
        r22 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r27;
        r0 = r0.expiry_time;
        r24 = r0;
        r22 = r22 * r24;
        r0 = r10;
        r1 = r21;
        r2 = r22;
        r0.postDelayed(r1, r2);
        goto L_0x0928;
    L_0x09a9:
        r0 = r27;
        r0 = r0.adType;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "W";
        r21 = r21.equals(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 == 0) goto L_0x0053;
    L_0x09b7:
        r0 = r27;
        r0 = r0.link;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r22 = "?";
        r21 = r21.contains(r22);	 Catch:{ Exception -> 0x0976 }
        if (r21 != 0) goto L_0x0053;
    L_0x09c5:
        r21 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.uri;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r22 = java.lang.String.valueOf(r22);	 Catch:{ Exception -> 0x0976 }
        r21.<init>(r22);	 Catch:{ Exception -> 0x0976 }
        r0 = r27;
        r0 = r0.link;	 Catch:{ Exception -> 0x0976 }
        r22 = r0;
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x0976 }
        r22 = "?";
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x0976 }
        r22 = appId;	 Catch:{ Exception -> 0x0976 }
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x0976 }
        r21 = r21.toString();	 Catch:{ Exception -> 0x0976 }
        r0 = r21;
        r1 = r27;
        r1.link = r0;	 Catch:{ Exception -> 0x0976 }
        goto L_0x0053;
    L_0x09f6:
        r21 = move-exception;
        android.os.Looper.prepare();
        r10 = new android.os.Handler;
        r10.<init>();
        r0 = r27;
        r0 = r0.send_Task;
        r22 = r0;
        r23 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r27;
        r0 = r0.expiry_time;
        r25 = r0;
        r23 = r23 * r25;
        r0 = r10;
        r1 = r22;
        r2 = r23;
        r0.postDelayed(r1, r2);
        throw r21;
    L_0x0a18:
        r0 = r6;
        r0 = (char) r0;
        r21 = r0;
        r0 = r5;
        r1 = r21;
        r0.append(r1);	 Catch:{ Exception -> 0x0976 }
        goto L_0x0119;
    L_0x0a24:
        r0 = r6;
        r0 = (char) r0;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r0 = r5;
        r1 = r21;
        r0.append(r1);	 Catch:{ Exception -> 0x0976 }
        goto L_0x040b;
    L_0x0a30:
        r0 = r6;
        r0 = (char) r0;	 Catch:{ Exception -> 0x0976 }
        r21 = r0;
        r0 = r5;
        r1 = r21;
        r0.append(r1);	 Catch:{ Exception -> 0x0976 }
        goto L_0x0704;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airpush.android.PushService.DeliverNotification():void");
    }

    private int selectIcon() {
        int[] icons = Constants.icons;
        return icons[new Random().nextInt(icons.length - 1)];
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
            }
        } catch (Exception e) {
        }
    }
}
