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
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
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

import com.sport.playsqorr.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class Utilities {



    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    static String provider;
    static double latitude;
    static double longitude;
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void showNoInternetAlert(final Context context) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.no_internet);

        Window window = dialog.getWindow();
        // window.setGravity(Gravity.CENTER);
//        window.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams param = window.getAttributes();
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        param.y = 140;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.setCancelable(true);

        TextView declineButton = dialog.findViewById(R.id.settings);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.android.settings",
                        "com.android.settings.Settings"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

    }

    public static void showAlertBox(final Context context, String title, String message) {

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



            }
        });

    }

    public static void showAlertBoxTrans(final Context context, String title, String message) {

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



            }
        });

    }

    public static void showAlertBoxLoc(final Context context, String title, String message) {

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
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.android.settings",
                        "com.android.settings.Settings"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);


            }
        });

    }



    public static void showAlertBoxTwo(Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts_two);


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
        alert_ok.setText("Add Funds");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                Intent aa = new Intent()


            }
        });

        TextView alert_cancel = (TextView) dialog.findViewById(R.id.alert_cancel);
        // if decline button is clicked, close the custom dialog
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });


    }



    public static Path resizePath(Path path, float width, float height) {
        RectF bounds = new RectF(0, 0, width, height);
        Path resizedPath = new Path(path);
        RectF src = new RectF();
        resizedPath.computeBounds(src, true);

        Matrix resizeMatrix = new Matrix();
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER);
        resizedPath.transform(resizeMatrix);

        return resizedPath;
    }

    public static Bitmap convertTParellelogram(Bitmap src, String type, Context context) {
        Bitmap typex;
        if (type.equals("pare")) {
            typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src,type, context),type,context);
        } else {
            typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type, context),type,context);
        }
        return typex;
    }


    public static Path getParellelogramPath(Bitmap src, String type, Context context) {
        Path path = null;
        //  Context context;
        if (type.equals("pare")) {
            path = resizePath(PathParser.createPathFromPathData(context.getString(R.string.pare)),
                    src.getWidth(), src.getHeight());
        } else {
            path = resizePath(PathParser.createPathFromPathData(context.getString(R.string.square)),
                    src.getWidth(), src.getHeight());
        }

        return path;
    }


    public static String getMonthName(int month) {

        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                break;
        }
        return null;
    }


    public static void showToast(Context applicationContext, String msg) {
        try {
            // Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show();

            Toast toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            //  Typeface typeface = Typeface.createFromAsset(applicationContext.getAssets(),"fonts/exo_regular.ttf");
            //  toastTV.setTypeface(typeface);
            toastTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, applicationContext.getResources().getDimension(R.dimen.normal_size));
            toast.show();
        } catch (Exception e) {
            Log.e("Globals", e.toString());
        }
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    /*public static boolean checkLocationPermission(Context applicationContext, UserLocation userLocation) {
        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(userLocation, android.Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(userLocation, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                ActivityCompat.requestPermissions(userLocation, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(userLocation, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }*/

  /*  public static int  getColorCodes(String gameName){
        int colorCode = 0;

        switch (gameName){
            case "soccer":
                colorCode=R.color.acc_hint_color;
                break;
            default:break;
        }
        return colorCode;

    }
*/

//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static boolean checkLocationPermission(Context applicationContext, Activity atc) {
        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(atc, android.Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(atc, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                ActivityCompat.requestPermissions(atc, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(atc, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(windowmanager!=null)
            windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static Integer getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageInt;
    }

    public static int getAge(String dobString) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss"; //1949-12-31T00:00:00

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Date date = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            date = sdf.parse(dobString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }

    public static  void getLocInf(Context  cxt){


        LocationManager locationManager = (LocationManager) Objects.requireNonNull(cxt).getSystemService(Context.LOCATION_SERVICE);
        try {
            if (locationManager != null) {
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                boolean isGPSLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkLocation = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (isGPSLocation) {
                    provider = LocationManager.GPS_PROVIDER;

                } else if (isNetworkLocation) {
                    provider = LocationManager.NETWORK_PROVIDER;

                }


                Log.e("gps, network", String.valueOf(isGPSLocation + "," + isNetworkLocation));

                if (location != null) {
                    Log.e("activity", "LOC by Network");
                    latitude = location.getLatitude();
                   longitude = location.getLongitude();

           //         currentAddress = Utility.getCompleteAddressString(getActivity(), latitude, longitude);
             //       Log.e(TAG, "getViewId: location manager " + latitude + " " + longitude + " \n address" + currentAddress);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}
