package com.redlogic.dashboard.customer.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.execution.CustomerExecutionListActivity;
import com.redlogic.dashboard.customer.request.WorkOrderApprovalRequestModel;
import com.redlogic.dashboard.customer.request.WorkOrderRequestModel;
import com.redlogic.dashboard.customer.response.WorkOrderDetailsResponse;
import com.redlogic.dashboard.customer.response.WorkOrderListingResponse;
import com.redlogic.dashboard.driver.request.JobsRequestModel;
import com.redlogic.databinding.ActivityWorkOrderDetailsBinding;
import com.redlogic.databinding.ItemWorkOrderDetailsBinding;
import com.redlogic.databinding.ItemWorkOrderView1Binding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.image.ImageUtils;
import com.redlogic.utils.json.Utils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class WorkOrderDetailsActivity extends BaseLoaderActivity {

    public static WorkOrderListingResponse.DataBean.WorkOrdersBean data;
    private WorkOrderDetailsResponse.DataBean result;
    private SlimAdapter slimAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, WorkOrderDetailsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_details);
        com.redlogic.databinding.ActivityWorkOrderDetailsBinding binding = (ActivityWorkOrderDetailsBinding) viewDataBinding;
        setTitle("Work Order View");
        getList();
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_3),
                getColor(R.color.grad_3)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 6, binding.tvApprove);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (WorkOrderListingActivity.deliveryType.matches("new")){
            binding.tvApprove.setVisibility(View.VISIBLE);
        }else {
            binding.tvApprove.setVisibility(View.GONE);
        }

         slimAdapter = SlimAdapter.create()
                .register(R.layout.item_work_order_details, new SlimInjector<WorkOrderDetailsResponse.DataBean.JobsBean>() {

                    @Override
                    public void onInject(@NonNull WorkOrderDetailsResponse.DataBean.JobsBean data, @NonNull IViewInjector injector) {
                        ItemWorkOrderDetailsBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvExecution.setText(String.format(":%s", data.getExecutions()));
                        mBinding.tvInProcess.setText(String.format(":%s", data.getExecution_inprocess()));
                        if (data.getExecutions() == 1)
                            mBinding.tvExecution.setText(String.format(":0%s", data.getExecutions()));
                        if (data.getExecution_inprocess()== 1)
                            mBinding.tvInProcess.setText(String.format(":0%s", data.getExecution_inprocess()));
                        mBinding.tvTitle21.setText(data.getJob_details().getFrom_location());
                        mBinding.liItem.setOnClickListener(v -> {
                            CustomerExecutionListActivity.start(WorkOrderDetailsActivity.this);
                        });
                    }
                })
                .register(R.layout.item_work_order_view_1, new SlimInjector<WorkOrderDetailsResponse.DataBean>() {

                    @Override
                    public void onInject(@NonNull WorkOrderDetailsResponse.DataBean data, @NonNull IViewInjector injector) {
                        ItemWorkOrderView1Binding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvLicensee.setText(data.getLicensee_address().getAddress_line_1());
                        mBinding.tvAddress.setText(data.getLicensee_address().getAddress_line_2());
                        mBinding.tvStatus.setText(data.getStatus());
                        mBinding.tvDate.setText(data.getDate());
                        mBinding.tvRefNo.setText(data.getRef_no());
                        mBinding.tvRelNo.setText(data.getRl_no());
                        ImageUtils.setRoundedBackground(WorkOrderDetailsActivity.this,
                                getColor(R.color.grad_7), 13, mBinding.tvStatus);
                        ImageUtils.setRoundedBackground(WorkOrderDetailsActivity.this,
                                getColor(R.color.grad_9), 5, mBinding.imCall);
                    }
                })
                .attachTo(binding.recyclerView);
    }

    private void getList() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        WorkOrderRequestModel requestModel = new WorkOrderRequestModel();
        requestModel.setWork_order_id(data.getWork_order_id());
        Call<ResponseBody> call = apiServiceProvider.apiServices.callWorkOrderDetails(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String response) {
                hideDialog();
                WorkOrderDetailsResponse responseModel = new Gson().fromJson(response, WorkOrderDetailsResponse.class);

                result = responseModel.getData();
                List<Object> list = new ArrayList<>();
                list.add(result);
                list.addAll(result.getJobs());
                slimAdapter.updateData(list);
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    public void onCall(View view) {
        call(result.getLicensee_address().getContact_number());
    }

    public void onApprove(View view) {
        showDialog();

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        WorkOrderApprovalRequestModel requestModel = new WorkOrderApprovalRequestModel();
        requestModel.setWork_order_id(data.getWork_order_id());
        requestModel.setStatus("approved");
//        requestModel.setUser_id();
        requestModel.setReason("");

        Call<ResponseBody> call = apiServiceProvider.apiServices.callWorkOrderapproval(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String response) {
                hideDialog();
                finish();

            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
                finish();
            }
        };
        apiServiceProvider.callApi(apiParams);

    }
}