package com.sport.playsqorr.utilities;

//
//import android.Manifest;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.location.LocationListener;
//
//public class LocationTrack extends Service implements LocationListener {

//    private final Context mContext;
//
//
//    boolean checkGPS = false;
//
//
//    boolean checkNetwork = false;
//
//    public boolean canGetLocation = false;
//
//    Location loc;
//    double latitude;
//    double longitude;
//
//
//    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
//
//
//    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
//    protected LocationManager locationManager;
//
//    public LocationTrack(Context mContext) {
//        this.mContext = mContext;
//        getLocation();
//    }
//
//    private Location getLocation() {
//
//        try {
//            locationManager = (LocationManager) mContext
//                    .getSystemService(LOCATION_SERVICE);
//
//            // get GPS status
//            checkGPS = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            // get network provider status
//            checkNetwork = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (!checkGPS && !checkNetwork) {
//                Toast.makeText(mContext, "No Service Provider is available", Toast.LENGTH_SHORT).show();
//            } else {
//                this.canGetLocation = true;
//
//                // if GPS Enabled get lat/long using GPS Services
//                if (checkGPS) {
//
//                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                    }
//                    locationManager.requestLocationUpdates(
//                            LocationManager.GPS_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
//                    if (locationManager != null) {
//                        loc = locationManager
//                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                        if (loc != null) {
//                            latitude = loc.getLatitude();
//                            longitude = loc.getLongitude();
//                        }
//                    }
//
//
//                }
//
//
//
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return loc;
//    }
//
//
//    public double getLongitude() {
//        if (loc != null) {
//            longitude = loc.getLongitude();
//        }
//        return longitude;
//    }
//
//    public double getLatitude() {
//        if (loc != null) {
//            latitude = loc.getLatitude();
//        }
//        return latitude;
//    }
//
//    public boolean canGetLocation() {
//        return this.canGetLocation;
//    }
//
//   /* public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//
//
//        alertDialog.setTitle("GPS is not Enabled!");
//
//        alertDialog.setMessage("Do you want to turn on GPS?");
//
//
//        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                mContext.startActivity(intent);
//            }
//        });
//
//
//        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//
//        alertDialog.show();
//    }*/
//
//
//    public void stopListener() {
//        if (locationManager != null) {
//
//            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            locationManager.removeUpdates((android.location.LocationListener) LocationTrack.this);
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//    }
//
//

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.Objects;
import java.util.Random;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationTrack extends Service implements LocationListener {
    ///--------
// Constants
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    // Location classes
    static FusedLocationProviderClient mFusedLocationClient;
    static Location mLastLocation;

    ///--------------
    private final Context mContext;


    // boolean checkGPS = false;


    //  boolean checkNetwork = false;

    public boolean canGetLocation = false;

    Location loc;
    double latitude;
    double longitude;

    String provider;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;


    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;



//     LocationRequest mLocationRequest;
//
//    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
//    private long FASTEST_INTERVAL = 2000; /* 2 sec */


  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocationFu();
                } else {
                    Toast.makeText(this,
                            "Not allowed",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
*/

    public static LatLng getLocation(double lon, double lat, int radius) {
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(lat);

        double foundLongitude = new_x + lon;
        double foundLatitude = y + lat;
        System.out.println("Longitude: " + foundLongitude + "  Latitude: "
                + foundLatitude);

        return new LatLng(foundLatitude, foundLongitude);

    }

    public static double llat;
    public static double llong;



    public static void getLocationFu(Activity activity_txt) {
        if (ActivityCompat.checkSelfPermission(activity_txt,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity_txt, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d("TAG", "getLocation: permissions granted");
        }
      //  mFusedLocationClient = getFusedLocationProviderClient(activity_txt);

         long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
        long FASTEST_INTERVAL = 2000; /* 2 sec */

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        getFusedLocationProviderClient(activity_txt).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChangedFused(locationResult.getLastLocation());


                    }

                    private void onLocationChangedFused(Location lastLocation) {

                        // You can now create a LatLng Object for use with maps
                      //  LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        llat = lastLocation.getLatitude();
                        llong = lastLocation.getLongitude();
                    }
                },
                Looper.myLooper());

       /* mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mLastLocation = location;
                    location.getAccuracy();


                    Double.toString(location.getLatitude());
                    Double.toString(location.getLongitude());

                    llat = location.getLatitude();
                    llong = location.getLongitude();

                    //   setAddress(location);
                } else {
                    //          Toast.makeText(activity_txt,"permissions denied", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    public LocationTrack(Context mContext) {
        this.mContext = mContext;
        getLocation();
//       getLocationFu(act);
    }

    protected LocationManager locationManager;


    private Location getLocation() {

        try {
            locationManager = (LocationManager) Objects.requireNonNull(mContext).getSystemService(Context.LOCATION_SERVICE);

//            locationManager = (LocationManager) mContext
//                    .getSystemService(LOCATION_SERVICE);


//            // get GPS status
//            checkGPS = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            // get network provider status
//            checkNetwork = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            boolean isGPSLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkLocation = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSLocation) {
                provider = LocationManager.GPS_PROVIDER;

            } else if (isNetworkLocation) {
                provider = LocationManager.NETWORK_PROVIDER;

            }

            if (provider.equalsIgnoreCase("false")) {
                Toast.makeText(mContext, "No Service Provider is available", Toast.LENGTH_SHORT).show();
            } else {
                this.canGetLocation = true;

                // if GPS Enabled get lat/long using GPS Services
                //if (checkGPS) {

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {

//                        loc = locationManager
//                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
                    Criteria crit = new Criteria();
                    crit.setAccuracy(Criteria.ACCURACY_MEDIUM);
                    String provider = locationManager.getBestProvider(crit, true);
                    Location loc = locationManager.getLastKnownLocation(provider);
                    if (loc != null) {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                    }
                } else {
//                        Location location = locationManager
//                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        boolean isGPSLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//                        boolean isNetworkLocation = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//                        if (isGPSLocation) {
//                            provider = LocationManager.GPS_PROVIDER;
//
//                        } else if (isNetworkLocation) {
//                            provider = LocationManager.NETWORK_PROVIDER;
//
//                        }
                }


                //     }


                /*if (checkNetwork) {


                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }

                    if (loc != null) {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                    }
                }*/

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return loc;
    }

    public double getLongitude() {
        if (loc != null) {
            longitude = loc.getLongitude();
        }
        return longitude;
    }

    public double getLatitude() {
        if (loc != null) {
            latitude = loc.getLatitude();
        }
        return latitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);


        alertDialog.setTitle("GPS is not Enabled!");

        alertDialog.setMessage("Do you want to turn on GPS?");


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }


    public void stopListener() {
        if (locationManager != null) {

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(LocationTrack.this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}

