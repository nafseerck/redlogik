package com.redlogic.dashboard.driver.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jetradarmobile.rxlocationsettings.RxLocationSettings;
import com.redlogic.R;
import com.redlogic.dashboard.customer.response.ExecutionDetailsResponse;
import com.redlogic.databinding.ActivityRouteMapBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.core.CoreUtils;

public class RouteMapActivity extends BaseLoaderActivity implements OnMapReadyCallback {

    public static boolean isCustomer;
    public static ExecutionDetailsResponse.DataBean result;
    private GoogleMap mMap;

    public static void start(Context context) {
        Intent intent = new Intent(context, RouteMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        ActivityRouteMapBinding binding = (ActivityRouteMapBinding) viewDataBinding;
        setTitle("Route Map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        showGps();
        getAddress();
        if (!isCustomer) {
            binding.include.liItem.setVisibility(View.GONE);
        }else {
            binding.include.liMap.setVisibility(View.GONE);
//            binding.include.tvTitleTxt2.setText(result.getJob_details().getScheduled_at());
//            binding.include.tvTitle21.setText(result.getJob_details().getJob_details());

            String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", result.getJob_details().getJob_details().getScheduled_at());
            binding.include.tvTitleTxt2.setText(dateTime);
            binding.include.tvTitle21.setText(result.getJob_details().getJob_details().getFrom_location());
        }
    }

    private void showGps() {
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY))
                .build();
        RxLocationSettings.with(this).ensure(locationSettingsRequest)
                .doOnSuccess(aBoolean -> locationOperation())
                .subscribe();
    }

    @SuppressLint("MissingPermission")
    private void locationOperation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                })
                .addOnFailureListener(this, e -> {
                });
        mFusedLocationClient.getLocationAvailability().addOnSuccessListener(locationAvailability -> {
            if (locationAvailability.isLocationAvailable()) {
                //showGps();
            }
        });
    }

    private void getAddress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions();
            }
        }
        locationOperation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCustomer = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void requestPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAddress();
        }
    }
}