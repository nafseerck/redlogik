package com.redlogic.dashboard.driver.job;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.decline.DeclineActivity;
import com.redlogic.dashboard.driver.deliveries.DeliveriesActivity;
import com.redlogic.dashboard.driver.execution.ExecutionListActivity;
import com.redlogic.dashboard.driver.map.RouteMapActivity;
import com.redlogic.dashboard.driver.request.AcceptOrRejectRequestModel;
import com.redlogic.dashboard.driver.response.JobsResponseModel;
import com.redlogic.databinding.ActivityJobBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.ImageUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class JobActivity extends BaseLoaderActivity {

    public static JobsResponseModel.DataBean data;
    private ActivityJobBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, JobActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        binding = (ActivityJobBinding) viewDataBinding;
        setTitle("Job");
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 0, binding.tvAccept);
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.black),
                getColor(R.color.black)), ImageUtils.convertToIntArray(getColor(R.color.black_trans),
                getColor(R.color.black_trans)), 0, binding.tvReject);
        binding.tvRoute.setText("Route - 20.9 kilometers");
        binding.tvTime.setText("23 Minutes");
        binding.include1.tvTitleTxt1.setText("Assigned by");
        binding.include1.tvTitle1.setText(data.getAssigned_by());
        binding.include2.tvTitleTxt1.setText(R.string.customer);
        binding.include2.tvTitle1.setText(data.getCustomer());
        String dateTime = CoreUtils.
                getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
        binding.include1.tvTitleTxt2.setText(dateTime);
        binding.include1.tvTitle21.setText(data.getFrom_location());
        binding.include1.tvTitle22.setText(data.getTo_location());
        binding.include1.tvTitleTxt3.setText("Marterial & Quantity");
        binding.include1.tvTitle3.setText(data.getMaterial());
        binding.include2.tvTitleTxt3.setText(R.string.address);
        binding.include2.tvTitle3.setText(data.getCustomer_address());
        binding.include2.li2.setVisibility(View.GONE);

        binding.include1.imCall.setOnClickListener(v -> call(data.getAssignee_phone()));
        binding.include2.imCall.setOnClickListener(v -> call(data.getCustomer_phone()));
        binding.include1.card.setOnClickListener(v -> {
            if (!DeliveriesActivity.isnewJob) {
                callAcceptOrReject();
            }
        });
        if (DeliveriesActivity.selectedPosition == 2 || DeliveriesActivity.selectedPosition == 3 || DeliveriesActivity.selectedPosition == 1) {
            binding.tvAccept.setVisibility(View.GONE);
            binding.tvReject.setVisibility(View.GONE);
        }

//        String origin = "11.715602, 77.040184";
//        String destination = "12.360282, 78.380516";
//        new DistanceMatrix().getDistance(origin, destination, this, value -> {
//            String distance = value.getRows().get(0)
//                    .getElements().get(0).getDistance().getText().replace("km", "kilometers");
//            binding.tvRoute.setText(String.format("Route - %s", distance));
//            binding.tvTime.setText(String.format("%s", value.getRows().get(0)
//                    .getElements().get(0).getDuration().getText()));
//        });
    }

    public void onAccept(View view) {
        callAcceptOrReject();
    }

    public void onReject(View view) {
        DeclineActivity.start(JobActivity.this);
    }

    public void onMap(View view) {
        //RouteMapActivity.start(this);
        CoreUtils.openRoutesMaps(this, data.getTo_location());
    }


    private void callAcceptOrReject() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        AcceptOrRejectRequestModel requestModel = new AcceptOrRejectRequestModel();
        requestModel.setJob_id(data.getJob_id());
        requestModel.setStatus("accept");
        requestModel.setDescription("");
        Call<ResponseBody> call = apiServiceProvider.apiServices.callAcceptOrReject(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                //showToast("Job Accepted Successfully");
                ExecutionListActivity.start(JobActivity.this);
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
