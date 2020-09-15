package com.redlogic;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.akexorcist.roundcornerprogressbar.CenteredRoundCornerProgressBar;
import com.redlogic.dashboard.customer.CustomerDashboardActivity;
import com.redlogic.dashboard.driver.DashboardActivity;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.language.LanguageActivity;
import com.redlogic.login.LoginActivity;
import com.redlogic.utils.PrefConstants;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseLoaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CenteredRoundCornerProgressBar progress = findViewById(R.id.progress);
        new CountDownTimer(2500, 80) {
            @Override
            public void onTick(long l) {
                progress.setProgress(progress.getProgress() + 5);
            }

            @Override
            public void onFinish() {

            }
        }.start();
        hideActionBar();
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

//        Intent intent = new Intent(this, LocationServiece.class);
//        startService(intent);

    }
}
