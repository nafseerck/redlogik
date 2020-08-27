package com.redlogic.generic;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.redlogic.R;
import com.redlogic.utils.AppPrefes;


public class BaseActivity extends AppCompatActivity {

    public AppPrefes appPrefes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPrefes = new AppPrefes(this);
        disableAutoFill();
    }


    private void disableAutoFill() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Is internet available boolean.
     *
     * @return the boolean
     */
    public boolean isInternetAvailable() {
//        if (Reachability.isNetworkingAvailable(this)) {
//            return false;
//        }
//        popup.showPopupNormal(this,
//                "Check your connectivity"
//                , null);

        return true;
    }

    public void onBack(View view) {
        finish();
    }


}
