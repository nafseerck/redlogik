package com.redlogic.dashboard.driver.decline;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.redlogic.R;
import com.redlogic.dashboard.driver.DashboardActivity;
import com.redlogic.dashboard.driver.job.JobActivity2;
import com.redlogic.dashboard.driver.request.AcceptOrRejectRequestModel;
import com.redlogic.databinding.ActivityDeclineBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.image.ImageUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DeclineActivity extends BaseLoaderActivity {

    private ActivityDeclineBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, DeclineActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decline);
        binding = (ActivityDeclineBinding) viewDataBinding;
        setTitle("Decline");
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 0, binding.tvSend);

        ImageUtils.setRoundedBackgroundWithBorder(this, Color.WHITE,
                getColor(R.color.gray_196), 1, 11, binding.edWrite);
    }

    public void onSend(View view) {
        if (binding.edWrite.getText().toString().isEmpty()) {
            showToast("Please enter reason");
            return;
        }
        callAcceptOrReject();
    }

    private void callAcceptOrReject() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        AcceptOrRejectRequestModel requestModel = new AcceptOrRejectRequestModel();
        requestModel.setJob_id(JobActivity2.data.getJob_id());
        requestModel.setStatus("reject");
        requestModel.setDescription(binding.edWrite.getText().toString());
        Call<ResponseBody> call = apiServiceProvider.apiServices.callAcceptOrReject(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                showToast("Job Rejected Successfully");
                DashboardActivity.startExit(DeclineActivity.this);
                finish();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

}
