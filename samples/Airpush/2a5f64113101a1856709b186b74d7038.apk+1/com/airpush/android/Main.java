package com.airpush.android;

import android.app.Activity;
import android.os.Bundle;
import joansoft.dailyread.R;

public class Main extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Airpush airpush = new Airpush(this, "10256", "airpush", true, true, true);
        setContentView(R.layout.bookmark_display);
    }
}
