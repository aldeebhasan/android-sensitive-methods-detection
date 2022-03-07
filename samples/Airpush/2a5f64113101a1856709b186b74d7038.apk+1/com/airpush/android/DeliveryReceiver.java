package com.airpush.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.List;
import joansoft.dailyread.DBAdapter;
import org.apache.http.NameValuePair;

public class DeliveryReceiver extends BroadcastReceiver {
    protected static Context ctx = null;
    private String action = null;
    private String apikey = null;
    private String appId = null;
    private String campId = null;
    private String creativeId = null;
    private String event = null;
    private Long expiry_time;
    private String header;
    private String imageurl;
    private String imei = null;
    private String link = null;
    private String message = null;
    private String number;
    private String sms;
    private String text = null;
    private String title;
    private String tray = null;
    private String uri = "http://api.airpush.com/redirect.php?market=";
    List<NameValuePair> values = null;

    public void onReceive(Context context, Intent intent) {
        ctx = context;
        try {
            Intent deliveryServiceIntent = new Intent();
            if (Constants.checkInternetConnection(ctx)) {
                Log.i("AirpushSDK", "Delivering Message");
                if (intent.getAction().equals("setDeliveryReceiverPhone")) {
                    this.apikey = intent.getStringExtra("apikey");
                    this.appId = new String(intent.getStringExtra("appId"));
                    this.imei = intent.getStringExtra("imei");
                    this.number = new String(intent.getStringExtra("number"));
                    this.title = new String(intent.getStringExtra(DBAdapter.KEY_TITLE));
                    this.text = new String(intent.getStringExtra("text"));
                    this.imageurl = new String(intent.getStringExtra("imageurl"));
                    this.expiry_time = Long.valueOf(intent.getLongExtra("expiry_time", 60));
                    this.campId = intent.getStringExtra("campId");
                    this.creativeId = intent.getStringExtra("creativeId");
                    deliveryServiceIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                    deliveryServiceIntent.putExtra("adType", "CC");
                    deliveryServiceIntent.putExtra("appId", this.appId);
                    deliveryServiceIntent.putExtra("type", "delivery");
                    deliveryServiceIntent.putExtra("number", this.number);
                    deliveryServiceIntent.putExtra(DBAdapter.KEY_TITLE, this.title);
                    deliveryServiceIntent.putExtra("text", this.text);
                    deliveryServiceIntent.putExtra("apikey", this.apikey);
                    deliveryServiceIntent.putExtra("imageurl", this.imageurl);
                    deliveryServiceIntent.putExtra("expiry_time", this.expiry_time);
                } else if (intent.getAction().equals("setDeliveryReceiverSMS")) {
                    this.apikey = intent.getStringExtra("apikey");
                    this.appId = new String(intent.getStringExtra("appId"));
                    this.imei = intent.getStringExtra("imei");
                    this.number = new String(intent.getStringExtra("number"));
                    this.sms = new String(intent.getStringExtra("sms"));
                    this.title = new String(intent.getStringExtra(DBAdapter.KEY_TITLE));
                    this.text = new String(intent.getStringExtra("text"));
                    this.imageurl = new String(intent.getStringExtra("imageurl"));
                    this.expiry_time = Long.valueOf(intent.getLongExtra("expiry_time", 60));
                    this.campId = intent.getStringExtra("campId");
                    this.creativeId = intent.getStringExtra("creativeId");
                    deliveryServiceIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                    deliveryServiceIntent.putExtra("adType", "CM");
                    deliveryServiceIntent.putExtra("appId", this.appId);
                    deliveryServiceIntent.putExtra("type", "delivery");
                    deliveryServiceIntent.putExtra("number", this.number);
                    deliveryServiceIntent.putExtra(DBAdapter.KEY_TITLE, this.title);
                    deliveryServiceIntent.putExtra("text", this.text);
                    deliveryServiceIntent.putExtra("sms", this.sms);
                    deliveryServiceIntent.putExtra("apikey", this.apikey);
                    deliveryServiceIntent.putExtra("imageurl", this.imageurl);
                    deliveryServiceIntent.putExtra("expiry_time", this.expiry_time);
                    deliveryServiceIntent.putExtra("campId", this.campId);
                    deliveryServiceIntent.putExtra("creativeId", this.creativeId);
                } else if (intent.getAction().equals("setDeliveryReceiverWEB")) {
                    this.apikey = intent.getStringExtra("apikey");
                    this.appId = new String(intent.getStringExtra("appId"));
                    this.imei = intent.getStringExtra("imei");
                    this.link = new String(intent.getStringExtra("url"));
                    this.title = new String(intent.getStringExtra(DBAdapter.KEY_TITLE));
                    this.text = new String(intent.getStringExtra("text"));
                    this.imageurl = new String(intent.getStringExtra("imageurl"));
                    this.header = new String(intent.getStringExtra("header"));
                    this.expiry_time = Long.valueOf(intent.getLongExtra("expiry_time", 60));
                    this.campId = intent.getStringExtra("campId");
                    this.creativeId = intent.getStringExtra("creativeId");
                    deliveryServiceIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                    deliveryServiceIntent.putExtra("adType", "W");
                    deliveryServiceIntent.putExtra("appId", this.appId);
                    deliveryServiceIntent.putExtra("type", "delivery");
                    deliveryServiceIntent.putExtra("link", this.link);
                    deliveryServiceIntent.putExtra("header", this.header);
                    deliveryServiceIntent.putExtra(DBAdapter.KEY_TITLE, this.title);
                    deliveryServiceIntent.putExtra("text", this.text);
                    deliveryServiceIntent.putExtra("apikey", this.apikey);
                    deliveryServiceIntent.putExtra("imageurl", this.imageurl);
                    deliveryServiceIntent.putExtra("expiry_time", this.expiry_time);
                    deliveryServiceIntent.putExtra("campId", this.campId);
                    deliveryServiceIntent.putExtra("creativeId", this.creativeId);
                } else if (intent.getAction().equals("setDeliveryReceiverMARKET")) {
                    this.apikey = intent.getStringExtra("apikey");
                    this.appId = new String(intent.getStringExtra("appId"));
                    this.imei = intent.getStringExtra("imei");
                    this.link = new String(intent.getStringExtra("url"));
                    this.title = new String(intent.getStringExtra(DBAdapter.KEY_TITLE));
                    this.text = new String(intent.getStringExtra("text"));
                    this.imageurl = new String(intent.getStringExtra("imageurl"));
                    this.expiry_time = Long.valueOf(intent.getLongExtra("expiry_time", 60));
                    this.campId = intent.getStringExtra("campId");
                    this.creativeId = intent.getStringExtra("creativeId");
                    deliveryServiceIntent.setAction("com.airpush.android.PushServiceStart" + this.appId);
                    deliveryServiceIntent.putExtra("adType", "A");
                    deliveryServiceIntent.putExtra("appId", this.appId);
                    deliveryServiceIntent.putExtra("type", "delivery");
                    deliveryServiceIntent.putExtra("link", this.link);
                    deliveryServiceIntent.putExtra(DBAdapter.KEY_TITLE, this.title);
                    deliveryServiceIntent.putExtra("text", this.text);
                    deliveryServiceIntent.putExtra("apikey", this.apikey);
                    deliveryServiceIntent.putExtra("imageurl", this.imageurl);
                    deliveryServiceIntent.putExtra("expiry_time", this.expiry_time);
                    deliveryServiceIntent.putExtra("campId", this.campId);
                    deliveryServiceIntent.putExtra("creativeId", this.creativeId);
                } else if (intent.getAction().equals("SetIconReceiver")) {
                    this.apikey = intent.getStringExtra("apikey");
                    this.appId = new String(intent.getStringExtra("appId"));
                    this.imei = intent.getStringExtra("imei");
                    this.link = new String(intent.getStringExtra("url"));
                    this.title = new String(intent.getStringExtra(DBAdapter.KEY_TITLE));
                    this.text = new String(intent.getStringExtra("text"));
                    this.imageurl = new String(intent.getStringExtra("imageurl"));
                    this.expiry_time = Long.valueOf(intent.getLongExtra("expiry_time", 60));
                    this.campId = intent.getStringExtra("campId");
                    this.creativeId = intent.getStringExtra("creativeId");
                }
                context.startService(deliveryServiceIntent);
            }
        } catch (Exception e) {
            Log.e("AirpushSDK", "Delivering Message Failed");
        }
    }
}
