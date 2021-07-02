package com.sport.playsqorr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorRestarterBroadcastReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("sree", "Broadcast Received");
        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Restarting Service");
        context.startService(new Intent(context, SensorService.class));
    }
}
