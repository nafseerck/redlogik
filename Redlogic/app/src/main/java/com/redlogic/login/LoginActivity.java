package com.redlogic.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.CustomerDashboardActivity;
import com.redlogic.dashboard.driver.DashboardActivity;
import com.redlogic.databinding.ActivityLoginBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.login.request.LoginRequestModel;
import com.redlogic.login.response.LoginResponseModel;
import com.redlogic.utils.PrefConstants;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.ImageUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class LoginActivity extends BaseLoaderActivity implements TextWatcher {

    private ActivityLoginBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startClear(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = (ActivityLoginBinding) viewDataBinding;
        hideActionBar();
        CoreUtils.enableDisableConfirmButton(this, binding.tvSignIn, false);
        ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_253),
                getColor(R.color.gray_226), 1, 5, binding.edUsername);
        ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_253),
                getColor(R.color.gray_226), 1, 5, binding.edPassword);

        binding.edUsername.addTextChangedListener(this);
        binding.edPassword.addTextChangedListener(this);

//        binding.edUsername.setText("RL62234791");
//        binding.edUsername.setText("RL32282114");
//        binding.edPassword.setText("Asdf@1234");

        binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    binding.edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // hide password
                    binding.edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void onLogin(View view) {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        LoginRequestModel loginModel = new LoginRequestModel();
        loginModel.setDevice_id("aaaduyadjg");
        loginModel.setUsername(binding.edUsername.getText().toString());
        loginModel.setPassword(binding.edPassword.getText().toString());
        loginModel.setLanguage(appPrefes.getBoolData(PrefConstants.isEnglish) ? "EN" : "AR");
        Call<ResponseBody> call = apiServiceProvider.apiServices.callLogin(loginModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                showToast("Login successful");
                LoginResponseModel loginResponseModel = new Gson().fromJson(responseBodyString, LoginResponseModel.class);

                if (loginResponseModel.getData().getUser_type().matches("3")){
                    appPrefes.saveBoolData(PrefConstants.isCustomer, true);
                }

                if (loginResponseModel.getData().getUser_type().matches("2")){
                    appPrefes.saveBoolData(PrefConstants.isCustomer, false);
                }


                appPrefes.saveData(PrefConstants.loginData, responseBodyString);
                appPrefes.saveData(PrefConstants.token, loginResponseModel.getData().getToken());

                if (appPrefes.getBoolData(PrefConstants.isCustomer)) {
                    CustomerDashboardActivity.start(LoginActivity.this);
                } else {
                    DashboardActivity.start(LoginActivity.this);
                }
                finish();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                showToast(errorObject.getMessage());
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        CoreUtils.enableDisableConfirmButton(this, binding.tvSignIn, isValid());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private boolean isValid() {
        return checkEditLength(binding.edUsername, binding.edPassword);
    }

    private boolean checkEditLength(EditText... editText) {
        for (EditText text : editText) {
            if (text.getText().length() == 0)
                return false;
        }
        return true;
    }
}
