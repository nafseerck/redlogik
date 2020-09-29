package com.redlogic.generic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telecom.TelecomManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.roundcornerprogressbar.CenteredRoundCornerProgressBar;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.Schedule.ScheduleActivity;
import com.redlogic.dashboard.driver.job.JobActivity;
import com.redlogic.dashboard.driver.notification.NotificationActivity;
import com.redlogic.dashboard.driver.request.GatePassRequestModel;
import com.redlogic.dashboard.driver.request.JobLocationUpdateRequestModel;
import com.redlogic.dashboard.driver.response.GatePassListResponseModel;
import com.redlogic.dashboard.driver.response.JobsLocationUpdateResponseModel;
import com.redlogic.dashboard.driver.schedule.DriverScheduleActivity;
import com.redlogic.databinding.ItemMenuItemBinding;
import com.redlogic.language.LanguageActivity;
import com.redlogic.language.request.SettingsRequestModel;
import com.redlogic.location.LocationTrack;
import com.redlogic.login.LoginActivity;
import com.redlogic.utils.PrefConstants;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.popup.Popup;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by Sarath on 9/12/18.
 */
public class BaseLoaderActivity extends BaseActivity {

    protected ViewDataBinding viewDataBinding;
    private View dialogContent;
    private ViewGroup subActivityContent;
    private View toolbar, menu;
    private ImageView imBack;
    private TextView tvTitle;
    private View imNotification;
    private View imCal;
    private RecyclerView recyclerView;
    private View relContent;
    public static String sosMobileNumber = "";
    private String TAG="tag_jithin";
    LocationTrack locationTrack;
    String NO_LOCATION_MSG = "No location Found";
    Location currentLocation;

    LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQ_CODE_LOCATION = 700;


    @Override
    public void setContentView(int view) {
        ViewGroup fullLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base_relative, null);
        subActivityContent = fullLayout.findViewById(R.id.contentFrame);
        relContent = fullLayout.findViewById(R.id.relContent);
        dialogContent = fullLayout.findViewById(R.id.contentLoading);
        toolbar = fullLayout.findViewById(R.id.include);
        recyclerView = fullLayout.findViewById(R.id.recyclerViewMenu);
        menu = fullLayout.findViewById(R.id.include2);
        imBack = fullLayout.findViewById(R.id.imBack);
        imNotification = fullLayout.findViewById(R.id.imNotification);
        imCal = fullLayout.findViewById(R.id.imCal);
        tvTitle = fullLayout.findViewById(R.id.tvTitle);
        getLayoutInflater().inflate(view, subActivityContent, true);
        viewDataBinding = DataBindingUtil.bind(subActivityContent.getChildAt(0));
        setAdapter();
        locationTrack = initLocationTrack(BaseLoaderActivity.this);

        if(hasLocationPermission()) {
            initializeLocationManager();
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }else {
            requestLocatopnPermission();
        }

        callSettings();
        super.setContentView(fullLayout);
    }


    public void hideActionBar() {
        toolbar.setVisibility(View.GONE);
    }

    public void hideBack() {
        imBack.setVisibility(View.GONE);
    }

    public void setBurger() {
        imBack.setImageResource(R.mipmap.hambergmenu);
    }

    public void hideNotification() {
        imNotification.setVisibility(View.GONE);
    }

    public void hideSchedules() {
        imCal.setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void showDialog() {
        dialogContent.setVisibility(View.VISIBLE);
        relContent.setVisibility(View.GONE);
        timer.start();
    }

    public void hideDialog() {
        dialogContent.setVisibility(View.GONE);
        relContent.setVisibility(View.VISIBLE);
        timer.cancel();
    }

    public void toggleMenu() {
        if (menu.getVisibility() == View.VISIBLE) {
            menu.setVisibility(View.GONE);
        } else {
            menu.setVisibility(View.VISIBLE);
        }
    }

    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    private void logout() {
        Popup.showPopup(this, "Would you like to logout?"
                , "Logout", isPress -> {
                    appPrefes.saveData(PrefConstants.token, "");
                    LoginActivity.startClear(this);
                });
    }

    public void onSos(View view) {

        if(currentLocation != null ) {

            callSosApi();
        }else {
            getFusedLocation();
        }
    }
    public void callSosNumber(){
        if (!sosMobileNumber.isEmpty()) {
//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sosMobileNumber, null));
//            startActivity(intent);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            }else {
                TelecomManager telecomManager = (TelecomManager) getSystemService(TELECOM_SERVICE);
                Uri uri = Uri.fromParts("tel", sosMobileNumber, null);
                Bundle extras = new Bundle();
                extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, false);
                telecomManager.placeCall(uri, extras);
            }

        }else {
            showToast("SOS phone number not loaded");
        }
    }

    public void onCalender(View view) {
        if (appPrefes.getBoolData(PrefConstants.isCustomer)){
            ScheduleActivity.start(this);
        }else {
            DriverScheduleActivity.start(this);
        }

    }

    public void onNotification(View view) {
        NotificationActivity.start(this);
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_menu_item, new SlimInjector<String>() {

                    @Override
                    public void onInject(@NonNull final String data, @NonNull IViewInjector injector) {
                        ItemMenuItemBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvName.setText(data);
                        if (data.equals("Logout")) {
                            mBinding.viewBottom1.setVisibility(View.GONE);
                            mBinding.viewBottom2.setVisibility(View.VISIBLE);
                            mBinding.viewTop.setVisibility(View.VISIBLE);
                        }
                        mBinding.liItem.setOnClickListener(v -> {
                            toggleMenu();
                            switch (data) {
                                case "Profiles":

                                    break;
                                case "Logout":
                                    logout();
                                    break;
                                    case "Dashboard":
                                    dashBoard();
                                    break;

                                    case "Schedules":
                                        schedue();
                                    break;

                                    case "Settings":
//                                        language();
                                    break;
                            }
                        });
                    }
                })
                .attachTo(recyclerView);
        List<String> list = new ArrayList<>();
        list.add("Profiles");
        list.add("Dashboard");
        list.add("Settings");
        list.add("Schedules");
        list.add("Logout");
        slimAdapter.updateData(list);
    }

    private void language() {
        LanguageActivity.start(this);
    }

    private void schedue() {
        if (appPrefes.getBoolData(PrefConstants.isCustomer)){
            ScheduleActivity.start(this);
        }else {
            DriverScheduleActivity.start(this);
        }
    }

    private void dashBoard() {
//        DashboardActivity.start(this);
    }

    public void goToLogin() {
        appPrefes.saveData(PrefConstants.token, "");
        LoginActivity.startClear(this);
    }

    private CountDownTimer timer = new CountDownTimer(1000, 80) {
        @Override
        public void onTick(long l) {
            CenteredRoundCornerProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(progressBar.getProgress() + 5);
        }

        @Override
        public void onFinish() {

        }
    };



    public void callSosApi() {

        SosRequestModel requestModel = new SosRequestModel();

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        if(appPrefes.getBoolData("is_job_id")) {
            requestModel.setJob_id(String.valueOf(appPrefes.getIntData("job_id")));
        }else {
            requestModel.setJob_id("");
        }
        requestModel.setLocation_details(getAddressFromLatLng(currentLocation.getLatitude(),currentLocation.getLongitude()));
        requestModel.setLat(String.valueOf(currentLocation.getLatitude()));
        requestModel.setLong_(String.valueOf(currentLocation.getLongitude()));

        Call<ResponseBody> call = apiServiceProvider.apiServices.calSos(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                try {

                    SosResponseModel responseModel = new Gson().fromJson(responseBodyString, SosResponseModel.class);

                    if (responseModel.isStatus()) {
                        showToast(responseModel.getMessage());
                    }else
                    {
                        showToast("Sos not send");
                    }
                }catch (Exception e){

                }
                callSosNumber();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
                showToast("Sos not send");
                callSosNumber();
            }
        };
        apiServiceProvider.callApi(apiParams);


    }

    private void initializeLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private String getAddressFromLatLng(double Inlatitude, double Inlongitude) {
        Geocoder gCoder = new Geocoder(BaseLoaderActivity.this);
        List<Address> addresses = null;
        try {
            addresses = gCoder.getFromLocation(Inlatitude, Inlongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            return addresses.get(0).getAddressLine(0);
        } else {
            return NO_LOCATION_MSG;
        }
    }

    @SuppressLint("MissingPermission")
    private void getFusedLocation() {
        if (currentLocation != null) {
            //location fetched already
            callSosApi();
        } else {
            //if location not fetched
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    currentLocation = location;
                }
                getFusedLocation();
            });
        }
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
            initializeLocationManager();
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }else {
            requestLocatopnPermission();
        }
    }

    private void callSettings() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        SettingsRequestModel settingsRequestModel = new SettingsRequestModel();
        settingsRequestModel.setDevice_id("aaaduyadjg");
        Call<ResponseBody> call = apiServiceProvider.apiServices.callSettings(settingsRequestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
       // showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
               // hideDialog();
                JobsLocationUpdateResponseModel responseModel = new Gson().fromJson(responseBodyString, JobsLocationUpdateResponseModel.class);

                if(responseModel.isStatus()) {
                    appPrefes.saveIntData("location_distance", responseModel.getLat_lon_frequency());
                }
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
             //   hideDialog();
                appPrefes.saveIntData("location_distance", 2000);

            }
        };
        apiServiceProvider.callApi(apiParams);
    }
}
