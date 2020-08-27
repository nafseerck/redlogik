package com.redlogic.utils.distance;

import com.google.gson.Gson;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DistanceMatrix {
    public interface DistanceValue{
         void distanceValue( DistanceResponse mResponses );
    }
    public void getDistance(String origins, String destinations,
                             BaseLoaderActivity baseLoaderActivity,DistanceValue distanceValue) {

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?" +
                "origins=" + origins + "&" +
                "destinations=" + destinations +
                "&key=AIzaSyBV7Qha3GiO1E1NmBQMkEMde5x8KKqFbrs";


        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(baseLoaderActivity);
        Call<ResponseBody> call = apiServiceProvider.apiServices.getUrl(url);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseString) {
                DistanceResponse mResponses = new Gson().fromJson(responseString, DistanceResponse.class);
                distanceValue.distanceValue(mResponses);
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {

            }
        };
        apiServiceProvider.callApi(apiParams);
    }
}
