package com.redlogic.dashboard.customer.execution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.request.WorkOrderRequestModel;
import com.redlogic.dashboard.customer.response.ExecutionListResponse;
import com.redlogic.dashboard.customer.work.WorkOrderDetailsActivity;
import com.redlogic.databinding.ActivityCustomerExecutionListBinding;
import com.redlogic.databinding.ItemCustomerExecutionBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class CustomerExecutionListActivity extends BaseLoaderActivity {
    private ActivityCustomerExecutionListBinding binding;
    private Typeface roboto, robotoMedium;
    private SlimAdapter slimAdapter;
    List<ExecutionListResponse.DataBean> data = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, CustomerExecutionListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_execution_list);
        binding = (ActivityCustomerExecutionListBinding) viewDataBinding;
        setTitle("Execution Listing");
        roboto = ResourcesCompat.getFont(this, R.font.roboto);
        robotoMedium = ResourcesCompat.getFont(this, R.font.roboto_medium);
        setAdapter();
        getList("");
    }

    private void setAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_customer_execution, new SlimInjector<ExecutionListResponse.DataBean>() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onInject(@NonNull final ExecutionListResponse.DataBean data, @NonNull IViewInjector injector) {
                        ItemCustomerExecutionBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvName.setText(data.getOperator_name());
                        mBinding.tvTypeTxt.setText(data.getAsset_details().getType());
                        mBinding.tvType.setText(data.getAsset_details().getName());
                        mBinding.tvRegNo.setText(String.format("Reg No:%s", data.getReg_number()));
                        mBinding.tvRefNo.setText(data.getRef_no());
                        mBinding.tvRelNo.setText(data.getRl_no());
                        if (data.getStatus().equals("ongoing")) {
                            mBinding.viLeft.setBackgroundColor(getColor(R.color.grad_9));
                        } else if (data.getStatus().equals("pending")) {
                            mBinding.viLeft.setBackgroundColor(getColor(R.color.grad_10));
                        }
                        mBinding.liItem.setOnClickListener(v -> {
                            CustomerExecutionDetailsActivity.executionId = data.getExecution_id();
                            CustomerExecutionDetailsActivity.start(CustomerExecutionListActivity.this);
                        });
                    }
                })
                .register(R.layout.item_empty, new SlimInjector<Boolean>() {

                    @Override
                    public void onInject(@NonNull Boolean data, @NonNull IViewInjector injector) {

                    }
                })
                .attachTo(binding.recyclerView);
    }

    private void getList(String type) {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        WorkOrderRequestModel requestModel = new WorkOrderRequestModel();
        requestModel.setWork_order_id(WorkOrderDetailsActivity.data.getWork_order_id());
        Call<ResponseBody> call = apiServiceProvider.apiServices.callCustomerExecutionList(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String response) {
                hideDialog();
                ExecutionListResponse responseModel = new Gson().fromJson(response, ExecutionListResponse.class);
                List<ExecutionListResponse.DataBean> responseData = responseModel.getData();
                data.clear();
                if (type.isEmpty()){
                    data.addAll(responseModel.getData());
                    slimAdapter.updateData(data);
                }else {
                    for (int i = 0; i < responseData.size(); i++) {
                        if (responseData.get(i).getStatus().matches(type)){
                            data.add(responseData.get(i));
                            slimAdapter.updateData(data);
                        }else {
                            slimAdapter.updateData(data);
                        }
                    }
                }

            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                // shivin
                if(errorObject.getSuccess() == false)
                {
                    showToast("Execution list not found");
                }
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    public void onGoing(View view) {
        binding.tvAll.setTypeface(roboto);
        binding.tvCompleted.setTypeface(roboto);
        binding.tvPending.setTypeface(roboto);
        binding.tvOngoing.setTypeface(robotoMedium);
        binding.tvOngoing.setTextSize(18);
        binding.tvAll.setTextSize(16);
        binding.tvCompleted.setTextSize(16);
        binding.tvPending.setTextSize(16);
        binding.tvOngoing.setTextColor(Color.BLACK);
        binding.tvAll.setTextColor(getColor(R.color.gray_131));
        binding.tvCompleted.setTextColor(getColor(R.color.gray_131));
        binding.tvPending.setTextColor(getColor(R.color.gray_131));
        getList("inprogress");
    }

    public void onCompleted(View view) {
        binding.tvAll.setTypeface(roboto);
        binding.tvPending.setTypeface(roboto);
        binding.tvOngoing.setTypeface(roboto);
        binding.tvCompleted.setTypeface(robotoMedium);
        binding.tvOngoing.setTextSize(16);
        binding.tvAll.setTextSize(16);
        binding.tvCompleted.setTextSize(18);
        binding.tvPending.setTextSize(16);
        binding.tvCompleted.setTextColor(Color.BLACK);
        binding.tvAll.setTextColor(getColor(R.color.gray_131));
        binding.tvOngoing.setTextColor(getColor(R.color.gray_131));
        binding.tvPending.setTextColor(getColor(R.color.gray_131));
        getList("completed");
    }

    public void onAll(View view) {
        binding.tvCompleted.setTypeface(roboto);
        binding.tvPending.setTypeface(roboto);
        binding.tvOngoing.setTypeface(roboto);
        binding.tvAll.setTypeface(robotoMedium);
        binding.tvOngoing.setTextSize(16);
        binding.tvAll.setTextSize(18);
        binding.tvCompleted.setTextSize(16);
        binding.tvPending.setTextSize(16);
        binding.tvAll.setTextColor(Color.BLACK);
        binding.tvOngoing.setTextColor(getColor(R.color.gray_131));
        binding.tvCompleted.setTextColor(getColor(R.color.gray_131));
        binding.tvPending.setTextColor(getColor(R.color.gray_131));
        getList("");
    }

    public void onPending(View view) {
        binding.tvAll.setTypeface(roboto);
        binding.tvCompleted.setTypeface(roboto);
        binding.tvOngoing.setTypeface(roboto);
        binding.tvPending.setTypeface(robotoMedium);
        binding.tvOngoing.setTextSize(16);
        binding.tvAll.setTextSize(16);
        binding.tvCompleted.setTextSize(16);
        binding.tvPending.setTextSize(18);
        binding.tvPending.setTextColor(Color.BLACK);
        binding.tvAll.setTextColor(getColor(R.color.gray_131));
        binding.tvCompleted.setTextColor(getColor(R.color.gray_131));
        binding.tvOngoing.setTextColor(getColor(R.color.gray_131));
        getList("pending");
    }
}