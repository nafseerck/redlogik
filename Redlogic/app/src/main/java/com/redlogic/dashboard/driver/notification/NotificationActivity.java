package com.redlogic.dashboard.driver.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.response.NotificationListResponseModel;
import com.redlogic.databinding.ActivityNotificationBinding;
import com.redlogic.databinding.ItemNotificationBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.AppPrefes;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class NotificationActivity extends BaseLoaderActivity {

    private ActivityNotificationBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        binding = (ActivityNotificationBinding) viewDataBinding;

        appPrefes =  appPrefes = new AppPrefes(this);
        appPrefes.saveBoolData("is_job_id",false);
        appPrefes.saveIntData("job_id",0);

        setTitle("Notifications");
        hideNotification();
        callUpdateLocation();
    }

    private void callUpdateLocation() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callUpdateLocation();
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                NotificationListResponseModel responseModel = new Gson().fromJson(responseBodyString, NotificationListResponseModel.class);
                setAdapter(responseModel.getData());
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setAdapter(List<NotificationListResponseModel.DataBean> dataList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_notification, new SlimInjector<NotificationListResponseModel.DataBean>() {

                    @Override
                    public void onInject(@NonNull final NotificationListResponseModel.DataBean data, @NonNull IViewInjector injector) {
                        ItemNotificationBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvTitle.setText(data.getNotification_title());
                        //String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
                        String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());

                        mBinding.tvTitleTxt.setText(dateTime);
                        mBinding.liItem.setOnClickListener(v -> {
                        });
                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(dataList);
    }
}
