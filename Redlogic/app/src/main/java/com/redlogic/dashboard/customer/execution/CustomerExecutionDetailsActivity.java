package com.redlogic.dashboard.customer.execution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.request.CustomerExecutionRequestModel;
import com.redlogic.dashboard.customer.response.ExecutionDetailsResponse;
import com.redlogic.dashboard.driver.map.RouteMapActivity;
import com.redlogic.databinding.ActivityCustomerExecutionDetailsBinding;
import com.redlogic.databinding.ItemExecutionDetailsBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.FileConversion;
import com.redlogic.utils.image.ImageUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class CustomerExecutionDetailsActivity extends BaseLoaderActivity {
    public static String executionId;
    private ActivityCustomerExecutionDetailsBinding binding;
    private ExecutionDetailsResponse.DataBean result;
    String pdf= new String("");

    public static void start(Context context) {
        Intent intent = new Intent(context, CustomerExecutionDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_execution_details);
        binding = (ActivityCustomerExecutionDetailsBinding) viewDataBinding;
        setTitle("Execution Details");
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 7, binding.liDownload);
        getList();
//        binding.include.tvTitle22.setText(data.getTo_location());
    }

    private void setAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_execution_details, new SlimInjector<ExecutionDetailsResponse.DataBean.MilestoneDetailsBean>() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onInject(@NonNull final ExecutionDetailsResponse.DataBean.MilestoneDetailsBean data, @NonNull IViewInjector injector) {
                        ItemExecutionDetailsBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvTitle.setText(data.getLabel());
                        mBinding.tvTitleTxt.setText(data.getCompleted_on());
                        if (data.getStatus() != 1) {
                            mBinding.imIcon.setVisibility(View.GONE);
                            mBinding.progress.setVisibility(View.VISIBLE);
                            mBinding.tvTitleTxt.setVisibility(View.GONE);
                        } else {
                            mBinding.imIcon.setVisibility(View.VISIBLE);
                            mBinding.tvTitleTxt.setVisibility(View.VISIBLE);
                            mBinding.progress.setVisibility(View.GONE);
                        }
                        mBinding.liItem.setOnClickListener(v -> {
                        });
                    }
                })
                .register(R.layout.item_empty, new SlimInjector<Boolean>() {

                    @Override
                    public void onInject(@NonNull Boolean data, @NonNull IViewInjector injector) {

                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(result.getMilestone_details());
    }

    public void onMapClick(View view) {
        RouteMapActivity.isCustomer = true;
        RouteMapActivity.result = result;
        RouteMapActivity.start(this);
    }

    private void getList() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        CustomerExecutionRequestModel requestModel = new CustomerExecutionRequestModel();
        requestModel.setExecution_id(executionId);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callCustomerExecution(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String response) {
                hideDialog();
                ExecutionDetailsResponse responseModel = new Gson().fromJson(response, ExecutionDetailsResponse.class);
                result = responseModel.getData();
                String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", result.getJob_details().getJob_details().getScheduled_at());
                binding.include.tvTitleTxt2.setText(dateTime);
                binding.include.tvTitle21.setText(result.getJob_details().getJob_details().getFrom_location());
                pdf = result.getDownload_document().getUrl();
                setAdapter();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }


    public void onDownload(View view) {

        try {
            if (pdf != null) {
                FileConversion.downloadPdf(CustomerExecutionDetailsActivity.this, pdf);
                showToast("Downloading...");
                showToast("Please check you downloads folder");
            } else {
                showToast("POD not available");

            }
        }catch (Exception e){

        }
    }
}