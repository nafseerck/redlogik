package com.redlogic.dashboard.driver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.deliveries.DeliveriesActivity;
import com.redlogic.dashboard.driver.response.DashboardResponseModel;
import com.redlogic.databinding.ActivityDashboardBinding;
import com.redlogic.databinding.ItemDashboardBinding;
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

public class DashboardActivity extends BaseLoaderActivity {

    private ActivityDashboardBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, DashboardActivity.class);
        context.startActivity(intent);
    }

    public static void startExit(Context context) {
        Intent intent = new Intent(context, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        binding = (ActivityDashboardBinding) viewDataBinding;
        ImageUtils.setRoundedBackground(this, getColor(R.color.grad_4), 45, binding.tvUserIcon);
        ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_14),
                getColor(R.color.gray_151), 1, 18, binding.tvRegNo);
        setBurger();
//        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "bezkoder.json");
//        Log.i("data", jsonFileString);
        getDashboard();
//        TimeSheetActivity.start(this);
    }

    private void getDashboard() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callDashboard();
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                DashboardResponseModel responseModel = new Gson().fromJson(responseBodyString, DashboardResponseModel.class);
                setDashboardDetails(responseModel);
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setDashboardDetails(DashboardResponseModel responseModel) {
        String nameFirst = String.valueOf(responseModel.getData().getName().charAt(0));
        binding.tvUserIcon.setText(nameFirst);
        binding.tvName.setText(responseModel.getData().getName());
        binding.tvRole.setText(responseModel.getData().getRole());
        binding.tvRegNo.setText(String.format("Reg No:%s", responseModel.getData().getReg_no()));
        binding.tvVehicleType.setText(responseModel.getData().getVehicle_type());
        setAdapter(responseModel.getData());
    }

    private void setAdapter(DashboardResponseModel.DataBean data) {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_dashboard, new SlimInjector<ItemDashboardModel>() {

                    @Override
                    public void onInject(@NonNull final ItemDashboardModel data, @NonNull IViewInjector injector) {
                        ItemDashboardBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.imIcon.setBackgroundResource(data.icon);
                        mBinding.tvTitle.setText(data.title);
                        mBinding.tvCases.setText(data.cases);
                        if (data.cases.length() == 1) {
                            mBinding.tvCases.setText(String.format("0%s", data.cases));
                        }
                        mBinding.liCard.setOnClickListener(v -> {
                            DeliveriesActivity.selectedPosition = data.position;
                            DeliveriesActivity.start(DashboardActivity.this);
                        });
                    }
                })
                .attachTo(binding.recyclerView);
        List<ItemDashboardModel> list = new ArrayList<>();
        list.add(getItem(getString(R.string.delivery_new), data.getNew_work_count(), R.mipmap.latest, 0));
        list.add(getItem(getString(R.string.delivery_in_process), data.getIn_progress_count(), R.mipmap.inprocess, 1));
        list.add(getItem(getString(R.string.delivery_completed), data.getCompleted_count(), R.mipmap.completed, 2));
       // list.add(getItem(getString(R.string.delivery_rejected), data.getRejected(), R.mipmap.rejected, 3));
        slimAdapter.updateData(list);

    }

    public static ItemDashboardModel getItem(String title, String cases, int icon, int position) {
        ItemDashboardModel itemDashboardModel = new ItemDashboardModel();
        itemDashboardModel.title = title;
        itemDashboardModel.icon = icon;
        itemDashboardModel.cases = cases;
        itemDashboardModel.position = position;
        return itemDashboardModel;
    }

    public static class ItemDashboardModel {
        public String title;
        public int icon;
        public int position;
        public String cases;
    }

    @Override
    public void onBack(View view) {
        toggleMenu();
    }
}
