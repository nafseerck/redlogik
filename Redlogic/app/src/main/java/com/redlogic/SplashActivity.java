package com.redlogic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.akexorcist.roundcornerprogressbar.CenteredRoundCornerProgressBar;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;
import com.redlogic.dashboard.customer.CustomerDashboardActivity;
import com.redlogic.dashboard.driver.DashboardActivity;
import com.redlogic.generic.BaseActivity;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.language.LanguageActivity;
import com.redlogic.language.request.SettingsRequestModel;
import com.redlogic.location.LocationTrack;
import com.redlogic.login.LoginActivity;
import com.redlogic.utils.PrefConstants;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class SplashActivity extends BaseActivity {

    private static final int REQ_CODE_LOCATION = 700;
    CenteredRoundCornerProgressBar progress;
    LocationTrack locationTrack;
    private IntentFilter gpsStatusIntent;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int PERMISSION_REQUEST_CODE = 5;
    private static final int REQUEST_GPS_TURN_ON = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.progress);

        // GPS Status change intent
        gpsStatusIntent = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        gpsStatusIntent.addAction(Intent.ACTION_PROVIDER_CHANGED);


        if (hasLocationPermission()) {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                requestTurnOnGps();}
            else {
                handleSplashScreen();
            }
        } else {
            requestLocatopnPermission();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Getting location
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requestTurnOnGps();
        }

    }

    private void handleSplashScreen()
    {
        locationTrack = initLocationTrack(SplashActivity.this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        new CountDownTimer(2500, 80) {
            @Override
            public void onTick(long l) {
                progress.setProgress(progress.getProgress() + 5);
            }

            @Override
            public void onFinish() {

            }
        }.start();
        //hideActionBar();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!appPrefes.getData(PrefConstants.token).isEmpty()) {
                    if (appPrefes.getBoolData(PrefConstants.isCustomer))
                        CustomerDashboardActivity.start(SplashActivity.this);
                    else
                        DashboardActivity.start(SplashActivity.this);
                    finish();
                    return;
                }
                if (appPrefes.getBoolData(PrefConstants.isLanguage)) {
                    LoginActivity.start(SplashActivity.this);
                    finish();
                    return;
                }
                LanguageActivity.start(SplashActivity.this);
                finish();
            }
        }, 2500);
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestLocatopnPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_CODE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_CODE_LOCATION) {
            handleSplashScreen();
        }else {
            requestLocatopnPermission();
        }
    }

    private void requestTurnOnGps() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        Task<LocationSettingsResponse> task =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);

            } catch (ApiException e) {
                e.printStackTrace();

                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.

                        try {
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    SplashActivity.this,
                                    REQUEST_GPS_TURN_ON);

                        } catch (IntentSender.SendIntentException e1) {
                            // Ignore the error.

                        } catch (ClassCastException e2) {
                            // Ignore, should be an impossible error.

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        break;
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GPS_TURN_ON) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // All required changes were successfully made
                    handleSplashScreen();
                    break;
                case Activity.RESULT_CANCELED:
                    finish();
                    break;
                default:

                    break;
            }
        }


    }
}
