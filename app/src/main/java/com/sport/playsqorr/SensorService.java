package com.sport.playsqorr;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import static com.sport.playsqorr.views.MatchupScreen.time;


public class SensorService extends Service {
    public static int counter=0;
    public static final String TAG = SensorService.class.getSimpleName();

    public SensorService(Context applicationContext) {
        super();
        Log.i(TAG, "here I am!");
    }

    public SensorService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        // send new broadcast when service is destroyed.
        // this broadcast restarts the service.
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    public static TimerTask timerTask;
    long oldTime=0;


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();
        time();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 5000, 15000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public  void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
