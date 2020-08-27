package com.redlogic.utils.api;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.listeners.RetrofitListener;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiServiceProvider extends RetrofitBase {
    private static ApiServiceProvider apiServiceProvider;
    public ApiServices apiServices;

    private ApiServiceProvider(BaseLoaderActivity context) {
        super(context, true);
        apiServices = retrofit.create(ApiServices.class);
    }

    public static ApiServiceProvider getInstance(BaseLoaderActivity context) {
        return new ApiServiceProvider(context);
    }

    public static class ApiParams {
        public Call<ResponseBody> call;
        public RetrofitListener retrofitListener;
    }

    public void callApi(ApiParams apiParams) {
        Call<ResponseBody> call = apiParams.call;
        RetrofitListener retrofitListener = apiParams.retrofitListener;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (retrofitListener != null)
                        validateResponse(response, retrofitListener);
                } catch (Exception e) {
                    errorResponse("Something went wrong. Please try again later.");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                serverDown();
            }
        });
    }

    private void serverDown() {
        errorResponse("No Response. Please check your internet connection");
    }

    private void errorResponse(String message) {
        context.hideDialog();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
