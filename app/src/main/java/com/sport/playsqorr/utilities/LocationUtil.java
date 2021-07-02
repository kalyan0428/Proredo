package com.sport.playsqorr.utilities;

import android.content.Context;
import android.location.LocationManager;

public class LocationUtil {

    public static String userid="user";

    public static boolean isLocationServiceEnabled(Context context){
        try {
            LocationManager locationManager = null;
            boolean gps_enabled = false, network_enabled = false;

            LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }catch (Exception ex){}
            try{
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }catch (Exception ex){}

            if (locationManager == null)
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            try {
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
                //do nothing...
            }

            try {
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
                //do nothing...
            }



            return gps_enabled || network_enabled;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
