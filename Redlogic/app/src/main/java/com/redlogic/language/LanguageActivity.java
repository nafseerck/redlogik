package com.redlogic.language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.redlogic.R;
import com.redlogic.databinding.ActivityLanguageBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.language.request.SettingsRequestModel;
import com.redlogic.login.LoginActivity;
import com.redlogic.utils.PrefConstants;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.image.ImageUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class LanguageActivity extends BaseLoaderActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        hideActionBar();
        ActivityLanguageBinding binding = (ActivityLanguageBinding) viewDataBinding;
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 10, binding.tvEnglish);
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_2),
                getColor(R.color.grad_2)), ImageUtils.convertToIntArray(getColor(R.color.grad_2_trans),
                getColor(R.color.grad_2_trans)), 10, binding.tvArabic);

    }

    public void onEnglish(View view) {
        setLanguage(true);
    }

    public void onArabic(View view) {
        setLanguage(false);
    }


    private void callSettings() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        SettingsRequestModel settingsRequestModel = new SettingsRequestModel();
        settingsRequestModel.setDevice_id("aaaduyadjg");
        Call<ResponseBody> call = apiServiceProvider.apiServices.callSettings(settingsRequestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
//                showToast("Login successful");
//                setLanguage(b);
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setLanguage(boolean b) {
        appPrefes.saveBoolData(PrefConstants.isEnglish, b);
        appPrefes.saveBoolData(PrefConstants.isLanguage, true);
        LoginActivity.start(this);
        finish();
    }
}
