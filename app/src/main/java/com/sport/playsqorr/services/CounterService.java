package com.sport.playsqorr.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CounterService extends IntentService {
    public static final String ACTION = "com.codepath.example.servicesdemo.MyTestService";

    public CounterService() {
        super("test-service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Fetch data passed into the intent on start
        String val = intent.getStringExtra("foo");
        // Construct an Intent tying it to the ACTION (arbitrary event namespace)
        Intent in = new Intent(ACTION);
        // Put extras into the intent as usual
        in.putExtra("resultCode", Activity.RESULT_OK);
        in.putExtra("resultValue", "My Result Value. Passed in: " + val);
        // Fire the broadcast with intent packaged
        LocalBroadcastManager.getInstance(this).sendBroadcast(in);
        // or sendBroadcast(in) for a normal broadcast;
    }
}
