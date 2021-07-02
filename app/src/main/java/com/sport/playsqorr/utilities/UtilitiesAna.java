package com.sport.playsqorr.utilities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.sport.playsqorr.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UtilitiesAna {

    private static final String key = "K8I550EORBPVVUQN6THB";
    private static String trackID;

    public static void setInstance(Context context, String token) {
            token = "K8I550EORBPVVUQN6THB";
        JSONObject jbInfo = new JSONObject();
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            // getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            jbInfo.put("al_lib", "Android");
            jbInfo.put("brand", Build.BRAND);
            jbInfo.put("model", android.os.Build.MODEL);
            jbInfo.put("os", "");
            jbInfo.put("os_version", "");
            jbInfo.put("device", Build.DEVICE);
            jbInfo.put("screen_height", height);
            jbInfo.put("screen_width", width);
            jbInfo.put("user_id", "");
            jbInfo.put("manufacturer", Build.MANUFACTURER);
            jbInfo.put("carrier", "");
            jbInfo.put("city", "");
            jbInfo.put("country", "");
            jbInfo.put("carshed_reason", "");
            jbInfo.put("current_url", "");
            jbInfo.put("device_id", "");
            jbInfo.put("event_name", "");
            jbInfo.put("initial_refferer", "");
            jbInfo.put("initial_referring_domain", "");
            jbInfo.put("lib_version", "");
            jbInfo.put("radio", "");
            jbInfo.put("referrer", "");
            jbInfo.put("referring_domain", "");
            jbInfo.put("region", "");

            Log.e("69---", jbInfo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post("https://api-playsqorr.azurewebsites.net/analytics/api/initialize?token=" + token)
                .addJSONObjectBody(jbInfo) // posting json
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        trackID = response;
                        Log.e("83----", "" + trackID);
                        JSONObject jb = new JSONObject();
                        try {
                            jb.put("sending_from", "Android");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        trackEvent("AddFunds", null);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("88----", "" + anError);

                    }
                });
    }


    public static void trackEvent(String eventName, JSONObject properties) {

        Log.e("117--", eventName + "--" + properties + "---" + trackID);

        JSONObject jbInfoTrack = new JSONObject();
        try {
            jbInfoTrack.put("event_name", eventName);
            jbInfoTrack.put("event_data", properties);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("117--", jbInfoTrack.toString());

        AndroidNetworking.post("https://api-playsqorr.azurewebsites.net/analytics/api/track?_id=" + trackID)
                .addJSONObjectBody(jbInfoTrack) // posting json
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("117--", "response--");
                        Log.e("127--", response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("117--", "error--");
                        Log.e("132--", anError.toString());

                    }
                });


    }

    // Register
    public static void trackEventRegister(String eventName, JSONObject properties) {

        Log.e("117--", eventName + "--" + properties + "---" + trackID);

    //    JSONObject jbInfoTrack = new JSONObject();
        try {

            properties.put("user_id", trackID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("117-- reg", properties.toString());
// https://api-playsqorr.azurewebsites.net/analytics/api/register_once?_id=5ff0c8e17b672a9d1f01fd86
        AndroidNetworking.post("https://api-playsqorr.azurewebsites.net/analytics/api/register_once?_id=" + trackID)
                .addJSONObjectBody(properties) // posting json
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("117-reg-", "response--");
                        Log.e("117-- reg 1", response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("117--reg error", "error--");
                        Log.e("117--reg error", anError.toString());

                    }
                });


    }
}
