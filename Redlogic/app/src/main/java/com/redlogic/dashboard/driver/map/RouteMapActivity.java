package com.redlogic.dashboard.driver.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.SphericalUtil;
import com.jetradarmobile.rxlocationsettings.RxLocationSettings;
import com.redlogic.R;
import com.redlogic.dashboard.customer.request.CustomerExecutionRequestModel;
import com.redlogic.dashboard.customer.response.CustomerLiveMapResponse;
import com.redlogic.dashboard.customer.response.ExecutionDetailsResponse;
import com.redlogic.databinding.ActivityRouteMapBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.redlogic.dashboard.customer.execution.CustomerExecutionDetailsActivity.executionId;

public class RouteMapActivity extends BaseLoaderActivity implements OnMapReadyCallback {

    public static boolean isCustomer;
    public static ExecutionDetailsResponse.DataBean result;
    private GoogleMap mMap;
    private LatLng startLatLng,endLatLng;
    private String TAG="tag_jithin";
    ActivityRouteMapBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, RouteMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        binding = (ActivityRouteMapBinding) viewDataBinding;
        setTitle("Route Map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        showGps();
        getAddress();
        getLiveMap();
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
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
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

    private void getLiveMap () {
        showDialog();

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        CustomerExecutionRequestModel requestModel = new CustomerExecutionRequestModel();
        requestModel.setExecution_id(executionId);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callCustomerLiveMaps(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String response) {
                hideDialog();

                CustomerLiveMapResponse responseModel = new Gson().fromJson(response, CustomerLiveMapResponse.class);

                pinLocations(responseModel);


            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void pinLocations(CustomerLiveMapResponse responseModel) {

        try {

            Double startLat,startLng,endLat,endLng;
            startLat = Double.parseDouble(responseModel.getData().getStart_lat());
            startLng = Double.parseDouble(responseModel.getData().getStart_long());

            endLat = Double.parseDouble(responseModel.getData().getEnd_lat());
            endLng = Double.parseDouble(responseModel.getData().getEnd_long());

            Double distance = SphericalUtil.computeDistanceBetween(startLatLng, endLatLng);
            distance = distance/1000;
            binding.include.titleKm.setVisibility(View.VISIBLE);
            binding.include.titleKm.setText(distance+"  KM");

            startLatLng = new LatLng(startLat,startLng);
            endLatLng = new LatLng(endLat,endLng);

            createMarker(startLatLng,"Source","Start location");
            createMarker(endLatLng,"Destination","End location");

            mMap.moveCamera(CameraUpdateFactory.newLatLng(startLatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(9));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected Marker createMarker(LatLng latLng, String title, String snippet) {

        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(bitmapDescriptorFromVector(RouteMapActivity.this)));
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

    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, R.drawable.ic_map);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}