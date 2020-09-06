package com.redlogic.dashboard.driver.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.response.SchedulesResponseModel;
import com.redlogic.databinding.ActivityScheduleBinding;
import com.redlogic.databinding.ItemNotificationBinding;
import com.redlogic.generic.BaseLoaderActivity;
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

public class ScheduleActivity extends BaseLoaderActivity {

    private ActivityScheduleBinding binding;
    private String TAG="jithin_check";

    public static void start(Context context) {
        Intent intent = new Intent(context, ScheduleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        binding = (ActivityScheduleBinding) viewDataBinding;
        setTitle("Schedule");
        hideSchedules();
        callSchedules();

//        List<Calendar> calendars = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        calendars.add(calendar)
//        binding.calendarView.setHighlightedDays(calendars);
    }


    private void callSchedules() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callSchedules();
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                SchedulesResponseModel responseModel = new Gson().fromJson(responseBodyString, SchedulesResponseModel.class);
                setAdapter(responseModel.getData());

            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setAdapter(List<SchedulesResponseModel.DataBean> dataList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_notification, new SlimInjector<SchedulesResponseModel.DataBean>() {

                    @Override
                    public void onInject(@NonNull final SchedulesResponseModel.DataBean data, @NonNull IViewInjector injector) {
                        ItemNotificationBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvTitle.setText(String.format("%s to %s", data.getJob_details().getFrom_location(), data.getJob_details().getTo_location()));
                        String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
                        mBinding.tvTitleTxt.setText(dateTime);
                        mBinding.liItem.setOnClickListener(v -> {
                        });
                        binding.calenderView.setDate(data.getTimestamp() * 1000);
                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(dataList);
    }
}
