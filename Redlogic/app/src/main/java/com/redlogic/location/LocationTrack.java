package com.redlogic.location;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.redlogic.dashboard.driver.request.JobLocationUpdateRequestModel;
import com.redlogic.generic.SosResponseModel;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class LocationTrack extends Service implements LocationListener {

    private Context mContext;
    boolean checkGPS = false;
    boolean checkNetwork = false;
    boolean canGetLocation = false;

    public Location loc;
    public double latitude;
    public double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;


    public static long MIN_TIME_BW_UPDATES = 9000;
    protected LocationManager locationManager;
    OnLocationHelperUpdateListener onLocationHelperUpdateListener;

    public LocationTrack(Context mContext, OnLocationHelperUpdateListener onLocationHelperUpdateListener) {
        this.mContext = mContext;
        getLocation();
        this.onLocationHelperUpdateListener = onLocationHelperUpdateListener;

    }

    public LocationTrack() {}

    private Location getLocation() {

        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // get GPS status- false in marshmallow
            checkGPS = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // get network provider status
            checkNetwork = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!checkGPS && !checkNetwork) {
                //Toast.makeText(mContext, "No Service Provider is available", Toast.LENGTH_SHORT).show();

            } else {
                this.canGetLocation = true;

                // if GPS Enabled get lat/long using GPS Services
                if (checkGPS) {

                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    }
                }
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //locationHelperUpdateListener.onLocationUpdate(location);
            loc = location;
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
            Log.d("worker_log", "onLocationChanged: location track "+loc.getLatitude()+","+loc.getLongitude());
            onLocationHelperUpdateListener.onLocationUpdate(location);

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}