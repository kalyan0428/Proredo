package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.WithdrawResponse;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.GPSTracker;
import com.sport.playsqorr.utilities.LocationTrack;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import static com.sport.playsqorr.utilities.LocationTrack.getLocationFu;
import static com.sport.playsqorr.utilities.LocationTrack.llat;
import static com.sport.playsqorr.utilities.LocationTrack.llong;

public class UserLocation extends AppCompatActivity implements View.OnClickListener {

    Cursor cursor;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    TextView loc_cancel;

    GPSTracker gpsTracker;
    TextView location_btn;
    String city, state, country;
    String sessionToken, NEWTOKEN,playerMode, avatar, amount_cash, amount_token, wins, acc_name;

    ProgressDialog progressDialog;

    public  static  String ppco;

    String pp_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.init_status_bar_color));
        }


        progressDialog = new ProgressDialog(UserLocation.this);
//        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        init();
    }


    private void init() {


        /******************Database Starts************************/
        mydb = new DataBaseHelper(getApplicationContext());
        sqLiteDatabase = mydb.getReadableDatabase();
        /******************Database Ends************************/


//        ROLE = getIntent().getExtras().getString("userrole");
//
//

        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                Log.e("89--", sessionToken);

            }

            cursor.close();

        }

        loc_cancel = findViewById(R.id.loc_cancel);
        loc_cancel.setOnClickListener(this);

        /*if (getIntent().getExtras().getString("sessionToken") != null) {
            sessionToken = getIntent().getExtras().getString("sessionToken");
            playerMode = getIntent().getExtras().getString("userPlayMode");
            avatar = getIntent().getExtras().getString("avatar");
            amount_cash = getIntent().getExtras().getString("amount_cash");
            amount_token = getIntent().getExtras().getString("amount_token");
            wins = getIntent().getExtras().getString("wins");
            acc_name = getIntent().getExtras().getString("acc_name");
//            Utilities.showAlertBox(getApplicationContext(),getcardID);
            Log.e("C sessionToken----", sessionToken);
        }*/


        location_btn = findViewById(R.id.tvEnable);
        location_btn.setOnClickListener(this);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission(getApplicationContext(), UserLocation.this);
//                    getAddress();

        }*/


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(UserLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(UserLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            return;
//            getLocationEnable();
        } else {
            // Write you code here if permission already given.
//            getLocationEnable();
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("place_p")) {
            pp_s = bundle.getString("place_p");

//        applyPromo();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEnable:
                if (progressDialog != null)
                    progressDialog.show();

                getLocationEnableByLocalJson();
//                getLocationEnable();
                break;
            case R.id.loc_cancel:
                ContentValues cv = new ContentValues();

                cv.put(DB_Constants.USER_CITY, "");
                cv.put(DB_Constants.USER_STATE, "");
                cv.put(DB_Constants.USER_COUNTRY, "");

                mydb.updateUser(cv);

                ppco = "t2";

                if(pp_s!=null){
                    Intent in = new Intent(UserLocation.this, CheckoutActivity.class);
                    in.putExtra("place_p", pp_s);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }else{
                    Intent in = new Intent(UserLocation.this, Dashboard.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                }

                break;
        }
    }


    private void getLocationEnableByLocalJson() {
        try {
            Dexter.withContext(UserLocation.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {

                            if (progressDialog != null)
                                progressDialog.dismiss();

                            //  if (Helper.isGPSEnabled(MainActivity.this)) {
                            LocationTrack locationTrack = new LocationTrack(UserLocation.this);
//                            if (locationTrack.canGetLocation) {
//                                double lat = locationTrack.getLatitude();
//                                double lon = locationTrack.getLongitude();
                            getLocationFu(UserLocation.this);
                            double lat = llat;
                            double lon = llong;
                                try {
                                    Geocoder gcd = new Geocoder(UserLocation.this, Locale.getDefault());
                                    List<Address> addresses = gcd.getFromLocation(lat,
                                            lon, 1);

                                    if (addresses.size() > 0) {

                                        state_txt = addresses.get(0).getAdminArea();
                                        city_txt = addresses.get(0).getLocality();
                                        country_txt = addresses.get(0).getCountryName();
                                        state = state_txt;
                                        {

                                            Log.e("Address", city
                                                    + state + country );

                                            JSONObject jsonObj = new JSONObject();

                                            try {
//                                                if(city_txt.equalsIgnoreCase("null")){
//
//                                                    jsonObj.put("city", "");
//                                                }else{
//
//                                                }
                                                jsonObj.put("city", city_txt);
                                                jsonObj.put("stateName", state_txt);
                                                jsonObj.put("stateCode", "");
                                                if(country_txt.equalsIgnoreCase("United States")){
                                                    jsonObj.put("country", "USA");
                                                }else {
                                                    jsonObj.put("country", country_txt);
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                if (progressDialog != null)
                                                    progressDialog.dismiss();

                                            }

                                            if (progressDialog != null)
                                                progressDialog.show();

Log.e("276---",jsonObj.toString() + "----"+ APIs.SIGNUP_LOCATION_USER + " ---------"+NEWTOKEN);
                                            //   obj_list_token(state_txt,city_txt,country_txt);
                                            AndroidNetworking.post(APIs.SIGNUP_LOCATION_USER)
                                                    .addJSONObjectBody(jsonObj) // posting json
//                                                    .addHeaders("sessionToken", sessionToken)
                                                    .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                                                    .setPriority(Priority.MEDIUM)
                                                    .build()
                                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {

                                                            if (progressDialog != null)
                                                                progressDialog.dismiss();
                                                            Log.e("***MA: cash 285:", response.toString());

                                                            try {
                                                             //   WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);
                                                                ContentValues cv = new ContentValues();
                                                                cv.put(DB_Constants.USER_CITY, city_txt);
                                                                cv.put(DB_Constants.USER_STATE, state_txt);
                                                                cv.put(DB_Constants.USER_COUNTRY, country_txt);

//                                                                cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
//                                                                cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
//                                                                cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
//                                                                cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());

                                                                String Usermode = response.getString("userPlayMode");

                                                                if (Usermode.equalsIgnoreCase("cash")) {

                                                                    cv.put(DB_Constants.USER_MODETYPE, response.getString("userPlayMode"));
                                                                    mydb.updateUser(cv);
                                                                    ppco = "t2";


                                                                    if(pp_s!=null){
                                                                        Intent in = new Intent(UserLocation.this, CheckoutActivity.class);
                                                                        in.putExtra("place_p", pp_s);
                                                                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        startActivity(in);
                                                                        finish();
                                                                    }else{
                                                                        Intent in = new Intent(UserLocation.this, Dashboard.class);
                                                                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        startActivity(in);
                                                                    }

                                                                } else {
                                                                    cv.put(DB_Constants.USER_MODETYPE, response.getString("userPlayMode"));
                                                                    mydb.updateUser(cv);
                                                                    showAlertBox(UserLocation.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                                                                }


                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                                if (progressDialog != null)
                                                                    progressDialog.dismiss();
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(ANError anError) {
                                                            Log.e("js", "Login----error------- 329" + anError.getErrorBody());


                                                            ContentValues cv = new ContentValues();
                                                            cv.put(DB_Constants.USER_CITY, city_txt);
                                                            cv.put(DB_Constants.USER_STATE, state_txt);
                                                            cv.put(DB_Constants.USER_COUNTRY, country_txt);
                                                            String Usermode = "cash";

                                                            if (Usermode.equalsIgnoreCase("cash")) {

                                                                cv.put(DB_Constants.USER_MODETYPE, "cash");
                                                                mydb.updateUser(cv);
                                                                ppco = "t2";


                                                                if(pp_s!=null){
                                                                    Intent in = new Intent(UserLocation.this, CheckoutActivity.class);
                                                                    in.putExtra("place_p", pp_s);
                                                                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    startActivity(in);
                                                                    finish();
                                                                }else{
                                                                    Intent in = new Intent(UserLocation.this, Dashboard.class);
                                                                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    startActivity(in);
                                                                }

                                                            } else {
                                                                cv.put(DB_Constants.USER_MODETYPE, "token");
                                                                mydb.updateUser(cv);
                                                                showAlertBox(UserLocation.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                                                            }


                                                            if (progressDialog != null)
                                                                progressDialog.dismiss();

                                                            if (anError.getErrorCode() != 0) {
                                                                Log.d("", "onError errorCode : " + anError.getErrorCode());
                                                                Log.d("", "onError errorBody : " + anError.getErrorBody());
                                                                Log.d("", "onError errorDetail : " + anError.getErrorDetail());


                                                            } else {
                                                                Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                                                            }

                                                        }
                                                    });
                                        }
//
                                    } else {
                                        Log.e("test--", "enable loction");

                                        if (progressDialog != null)
                                            progressDialog.dismiss();

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();

                                    if (progressDialog != null)
                                        progressDialog.dismiss();

                                }
                          //  }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                // navigate user to app settings

                                if (progressDialog != null)
                                    progressDialog.dismiss();

//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                Utilities.showAlertBoxLoc(UserLocation.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest
                                                                               permission, PermissionToken token) {
                            if (progressDialog != null)
                                progressDialog.dismiss();

                            token.continuePermissionRequest();

                        }


                    }).

                    check();


            // finish();
        } catch (Exception e) {
            if (progressDialog != null)
                progressDialog.dismiss();

        }
    }

    public String getJson() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.state_rules);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    private void obj_list_token(String state_txt, String city_txt, String country_txt) {

        try {
            JSONArray array = new JSONArray(getJson());
            for (int k = 0; k < array.length(); k++) {
                JSONObject object = array.getJSONObject(k);
                String State = object.getString("State");
                if (State.equalsIgnoreCase(state_txt)) {
                    String cashValue = object.getString("Cash");
                    System.out.println(cashValue);

                    if (cashValue.equalsIgnoreCase("YES")) {

                        ContentValues cv = new ContentValues();

                        cv.put(DB_Constants.USER_CITY, city_txt);
                        cv.put(DB_Constants.USER_STATE, state_txt);
                        cv.put(DB_Constants.USER_COUNTRY, country_txt);

                        mydb.updateUser(cv);
                        ppco = "t2";
                        Intent in = new Intent(UserLocation.this, Dashboard.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                    } else {
                        showAlertBox(UserLocation.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    String state_txt = "", city_txt = "", country_txt = "";


    //API
    private void getLocationEnable() {
        try {
            Dexter.withContext(UserLocation.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {


                            //  if (Helper.isGPSEnabled(MainActivity.this)) {
                            LocationTrack locationTrack = new LocationTrack(UserLocation.this);
//                            if (locationTrack.canGetLocation) {
//                                double lat = locationTrack.getLatitude();
//                                double lon = locationTrack.getLongitude();
                            getLocationFu(UserLocation.this);
                            double lat = llat;
                            double lon = llong;
                                try {
                                    Geocoder gcd = new Geocoder(UserLocation.this, Locale.getDefault());
                                    List<Address> addresses = gcd.getFromLocation(lat,
                                            lon, 1);

                                    if (addresses.size() > 0) {

                                        state_txt = addresses.get(0).getAdminArea();
                                        city_txt = addresses.get(0).getLocality();
                                        country_txt = addresses.get(0).getCountryName();
                                        state = state_txt;
                                        {

                                            Log.e("Address", city
                                                    + state + country);

                                            JSONObject jsonObj = new JSONObject();

                                            try {
                                                jsonObj.put("city", city_txt);
                                                jsonObj.put("state", state_txt);
                                                if(country_txt.equalsIgnoreCase("United States")){
                                                    jsonObj.put("country", "USA");
                                                }else {
                                                    jsonObj.put("country", country_txt);
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            AndroidNetworking.post(APIs.LOCATION_USER)
                                                    .addJSONObjectBody(jsonObj) // posting json
                                         //           .addHeaders("sessionToken", sessionToken)
                                                    .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                                                    .setPriority(Priority.MEDIUM)
                                                    .build()
                                                    .getAsString(new StringRequestListener() {
                                                        @Override
                                                        public void onResponse(String response) {

                                                            Log.e("***Location: ", response.toString());
                                                            if (response.equalsIgnoreCase("true")) {

                                                                ContentValues cv = new ContentValues();

                                                                cv.put(DB_Constants.USER_CITY, city_txt);
                                                                cv.put(DB_Constants.USER_STATE, state_txt);
                                                                cv.put(DB_Constants.USER_COUNTRY, country_txt);

                                                                mydb.updateUser(cv);
                                                                ppco = "t2";
                                                                Intent in = new Intent(UserLocation.this, Dashboard.class);
//                                                                        in.putExtra("sessionToken", sessionToken);
//                                                                        in.putExtra("userrole", playerMode);
//                                                                        in.putExtra("avatar", avatar);
//                                                                        in.putExtra("amount_cash", amount_cash);
//                                                                        in.putExtra("amount_token", amount_token);
//                                                                        in.putExtra("wins", wins);
//                                                                        in.putExtra("acc_name", acc_name);

                                                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                startActivity(in);

                                                            } else {
                                                                showAlertBox(UserLocation.this, getString(R.string.location_msg) + " " + state, getString(R.string.location_msg));


                                                            }

                                                        }

                                                        @Override
                                                        public void onError(ANError anError) {
                                                            Log.e("js", "user----error-------" + anError);

                                                        }
                                                    });
                                        }
//
                                    } else {
                                        Log.e("test--", "enable loction");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                          //  }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                // navigate user to app settings
//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                Utilities.showAlertBoxLoc(UserLocation.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }


                    }).check();


            // finish();
        } catch (Exception e) {

        }
    }

    private void showAlertBox(final Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts);


        Window window = dialog.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // window.setGravity(Gravity.CENTER);
//        window.setGravity(Gravity.BOTTOM);


        dialog.show();
        dialog.setCancelable(false);


        TextView alert_title = (TextView) dialog.findViewById(R.id.alert_title);
        TextView alert_msg = (TextView) dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = (TextView) dialog.findViewById(R.id.alert_ok);
        alert_ok.setText("OK");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                ((Activity) context).finish();

//                ContentValues cv = new ContentValues();
//                mydb.updateUser(cv);
                ppco = "t2";

                if(pp_s!=null){
                    Intent in = new Intent(UserLocation.this, CheckoutActivity.class);
                    in.putExtra("place_p", pp_s);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }else{

                    Intent in = new Intent(UserLocation.this, Dashboard.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                }




            }
        });

    }


    private void getAddress() {

        gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

//            Log.e("Address", "" + latitude);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                updateLocationAPI(city, state, country);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

//    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                UserLocation.this);
//        alertDialog.setTitle("SETTINGS");
//        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
//        alertDialog.setPositiveButton("Settings",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(
//                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        UserLocation.this.startActivity(intent);
//                    }
//                });
//        alertDialog.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//        alertDialog.show();
//    }
//

    private void updateLocationAPI(String city, final String state, String country) {

        Log.e("Address", city
                + state + country);

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("city", city);
            jsonObj.put("state", state);
            if(country.equalsIgnoreCase("United States")){
                jsonObj.put("country", "USA");
            }else {
                jsonObj.put("country", country);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(APIs.LOCATION_USER)
                .addJSONObjectBody(jsonObj) // posting json
          //      .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("***Location: ", response.toString());
                        if (response.equalsIgnoreCase("true")) {
                            ppco = "t2";

                            if(pp_s!=null){
                                Intent in = new Intent(UserLocation.this, CheckoutActivity.class);
                                in.putExtra("place_p", pp_s);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                finish();
                            }else{
                                Intent in = new Intent(UserLocation.this, Dashboard.class);
//                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                in.putExtra("sessionToken", sessionToken);
                                in.putExtra("userrole", playerMode);
                                in.putExtra("avatar", avatar);
                                in.putExtra("amount_cash", amount_cash);
                                in.putExtra("amount_token", amount_token);
                                in.putExtra("wins", wins);

                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                            }





                        } else {
                            Toast.makeText(getApplicationContext(), " No address associated with hostname", Toast.LENGTH_LONG).show();
                            Utilities.showAlertBox(UserLocation.this, getString(R.string.location_msg) + " " + state, getString(R.string.location_msg));
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "user----error-------" + anError);

                    }
                });
    }


    private void showAlertBoxAU(final Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts);


        Window window = dialog.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // window.setGravity(Gravity.CENTER);
//        window.setGravity(Gravity.BOTTOM);


        dialog.show();
        dialog.setCancelable(false);


        TextView alert_title = (TextView) dialog.findViewById(R.id.alert_title);
        TextView alert_msg = (TextView) dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = (TextView) dialog.findViewById(R.id.alert_ok);
        alert_ok.setText("OK");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                dialog.dismiss();

                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                //mydb.clearTableMobileUser();
                mydb.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(UserLocation.this, OnBoarding.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                // getApplicationContext().deleteDatabase(DataBaseHelper.DATABASE_NAME);


                //    resetDatabase();

              /*  Intent mStartActivity = new Intent(Profile.this, OnBoarding.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(Profile.this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);*/

            }
        });

    }
}
