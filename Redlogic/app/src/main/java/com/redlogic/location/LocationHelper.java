package com.redlogic.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.redlogic.utils.AppPrefes;

public class LocationHelper implements LocationListener {

    private LocationManager locationManager;
    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    OnLocationHelperUpdateListener onLocationHelperUpdateListener;

    boolean isLocationAvailable = false;
    public AppPrefes appPrefes;

    Location loc;
    double latitude;
    double longitude;

    public LocationHelper(LocationManager locationManager, Context context, OnLocationHelperUpdateListener onLocationHelperUpdateListener) {
        this.locationManager = locationManager;
        this.onLocationHelperUpdateListener = onLocationHelperUpdateListener;
        appPrefes = new AppPrefes(context);

        context = context;
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, Looper.getMainLooper());
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,appPrefes.getIntData("location_distance"),0,this);
           // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,3000,0,this);

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null && location.hasAccuracy()) {
                    loc = location;
                    isLocationAvailable = true;                }
            });

            Log.d("worker_log", "LocationHelper: Permission already granted");
        } else {
            // No permission
            Log.d("worker_log", "LocationHelper: No permission");
        }


    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //locationHelperUpdateListener.onLocationUpdate(location);
            loc = location;
            isLocationAvailable = true;
            Log.d("worker_log", "onLocationChanged: "+loc.getLatitude()+","+loc.getLongitude());
            onLocationHelperUpdateListener.onLocationUpdate(location);
        }
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
