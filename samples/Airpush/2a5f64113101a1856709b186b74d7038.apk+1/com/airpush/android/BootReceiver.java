package com.airpush.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context arg0, Intent arg1) {
        Airpush airpush = new Airpush(arg0, "10256", "airpush", false, true, true);
    }
}
