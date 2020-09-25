package com.redlogic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.akexorcist.roundcornerprogressbar.CenteredRoundCornerProgressBar;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.progress);


        if (hasLocationPermission()) {
            handleSplashScreen();
        } else {
            requestLocatopnPermission();
        }



//        Intent intent = new Intent(this, LocationServiece.class);
//        startService(intent);

    }

    private void handleSplashScreen()
    {
        locationTrack = initLocationTrack(SplashActivity.this);

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

}
