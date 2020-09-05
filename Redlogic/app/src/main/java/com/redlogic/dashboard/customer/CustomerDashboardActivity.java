package com.redlogic.dashboard.customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.response.DashboardResponse;
import com.redlogic.dashboard.customer.work.WorkOrderListingActivity;
import com.redlogic.dashboard.driver.DashboardActivity;
import com.redlogic.databinding.ActivityCustomerDashboardBinding;
import com.redlogic.databinding.ItemDashboardBinding;
import com.redlogic.databinding.ItemOnGoingBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.image.ImageUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class CustomerDashboardActivity extends BaseLoaderActivity {

    private ActivityCustomerDashboardBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, CustomerDashboardActivity.class);
        context.startActivity(intent);
    }

    public static void startExit(Context context) {
        Intent intent = new Intent(context, CustomerDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        binding = (ActivityCustomerDashboardBinding) viewDataBinding;
        ImageUtils.setRoundedBackground(this, getColor(R.color.grad_4), 45, binding.tvUserIcon);
        ImageUtils.setRoundedBackgroundWithBorder(this, Color.TRANSPARENT
                , getColor(R.color.gray_222), 1, 0, binding.recyclerViewWorkGoing);
        binding.recyclerViewWorkGoing.setLayoutManager(new LinearLayoutManager(this));

        getDashboard();
        setBurger();

//        String response = Utils.getJsonFromAssets(getApplicationContext(), "dashboard.json");
    }

    private void getDashboard() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callCustomerDashboard();
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String response) {
                hideDialog();
                DashboardResponse responseModel = new Gson().fromJson(response, DashboardResponse.class);
                setDashboardDetails(responseModel);

            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setDashboardDetails(DashboardResponse responseModel) {
        DashboardResponse.DataBean.CustomerDetailsBean customerDetailsBean = responseModel.getData().getCustomer_details();
        String nameFirst = String.valueOf(customerDetailsBean.getName().charAt(0));
        binding.tvUserIcon.setText(nameFirst);
        binding.tvName.setText(customerDetailsBean.getName());
        binding.tvRole.setText(customerDetailsBean.getDesignation());
        setAdapter(responseModel.getData());
    }

    private void setAdapter(DashboardResponse.DataBean resultBean) {
        binding.recyclerViewWork.setLayoutManager(new GridLayoutManager(this, 2));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_dashboard, new SlimInjector<DashboardActivity.ItemDashboardModel>() {

                    @Override
                    public void onInject(@NonNull final DashboardActivity.ItemDashboardModel data, @NonNull IViewInjector injector) {
                        ItemDashboardBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.imIcon.setBackgroundResource(data.icon);
                        mBinding.tvTitle.setText(data.title);
                        mBinding.tvCases.setText(data.cases);
                        if (data.cases.length() == 1) {
                            mBinding.tvCases.setText(String.format("0%s", data.cases));
                        }
                        mBinding.liCard.setOnClickListener(v -> {
                            WorkOrderListingActivity.selectedPosition = data.position;
                            WorkOrderListingActivity.start(CustomerDashboardActivity.this);
                        });
                    }
                })
                .attachTo(binding.recyclerViewWork);
        SlimAdapter slimAdapter1 = SlimAdapter.create()
                .register(R.layout.item_on_going, new SlimInjector<DashboardResponse.DataBean.CustomerDetailsBean.OngoingJobsBean>() {

                    @Override
                    public void onInject(@NonNull DashboardResponse.DataBean.CustomerDetailsBean.OngoingJobsBean data, @NonNull IViewInjector injector) {
                        ItemOnGoingBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvScheduledAt.setText(data.getJob_details().getScheduled_at());
//                        mBinding.tvTitle21.setText(data.getJob_details());
                        mBinding.liItem.setOnClickListener(v -> {
                        });
                    }
                })
                .attachTo(binding.recyclerViewWorkGoing);
        DashboardResponse.DataBean.CustomerDetailsBean data = resultBean.getCustomer_details();
        List<DashboardActivity.ItemDashboardModel> list = new ArrayList<>();
        list.add(DashboardActivity.getItem(getString(R.string.delivery_new), data.getWorkorder_new_count(), R.mipmap.latest, 0));
        list.add(DashboardActivity.getItem(getString(R.string.delivery_in_process), data.getWorkorder_new_inprocess(), R.mipmap.inprocess, 1));
        list.add(DashboardActivity.getItem(getString(R.string.delivery_completed), data.getWorkorder_new_completed(), R.mipmap.completed, 2));
        list.add(DashboardActivity.getItem(getString(R.string.delivery_rejected), data.getWorkorder_new_approvals(), R.mipmap.rejected, 3));
        slimAdapter.updateData(list);
        slimAdapter1.updateData(resultBean.getCustomer_details().getOngoing_jobs());

    }

    @Override
    public void onBack(View view) {
        toggleMenu();
    }

    public void onWork(View view) {
        binding.liGoing.setVisibility(View.GONE);
        binding.liWork.setVisibility(View.VISIBLE);
    }

    public void onGoing(View view) {
        binding.liGoing.setVisibility(View.VISIBLE);
        binding.liWork.setVisibility(View.GONE);
    }
}