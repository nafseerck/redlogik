package com.redlogic.dashboard.driver.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.response.SchedulesResponse;
import com.redlogic.databinding.ActivityDriverScheduleBinding;
import com.redlogic.databinding.ItemNotificationBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DriverScheduleActivity extends BaseLoaderActivity {


    private ActivityDriverScheduleBinding binding;
    private String TAG="jithin_check";
    List<SchedulesResponse.DataBean> list;

    public static void start(Context context) {
        Intent intent = new Intent(context, DriverScheduleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_schedule);
        binding = (ActivityDriverScheduleBinding) viewDataBinding;
        setTitle("Schedule");
        hideSchedules();
        callSchedules();

        binding.calenderView.setOnDateChangeListener((calendarView, year, month, day) -> {
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(day+"-"+(month+1)+"-"+year);
                String selectedDate = sdf.format(date);
                if (list != null && !list.isEmpty()){
                    setFilteredList(selectedDate);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        });

    }

    private void setFilteredList(String selectedDate) {
        List<SchedulesResponse.DataBean> filteredList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            String date = CoreUtils.getParsedStamp("dd-MM-yyyy",list.get(i).getTimestamp());
            if (selectedDate.matches(date)){
                filteredList.add(list.get(i));
            }
        }
        setAdapter(filteredList);
    }

    private void callSchedules() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callDriverSchedules();
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                SchedulesResponse responseModel = new Gson().fromJson(responseBodyString, SchedulesResponse.class);
                list = responseModel.getData();
//                setAdapter(responseModel.getData());
                String today = CoreUtils.getParsedCurrentDate("dd-MM-yyyy");
                setFilteredList(today);

            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setAdapter(List<SchedulesResponse.DataBean> dataList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_notification, new SlimInjector<SchedulesResponse.DataBean>() {

                    @Override
                    public void onInject(@NonNull final SchedulesResponse.DataBean data, @NonNull IViewInjector injector) {
                        ItemNotificationBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvTitle.setText(String.format("%s to %s", data.getFromlocation(), data.getTolocation()));
                        String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
                        mBinding.tvTitleTxt.setText(dateTime);
                        mBinding.liItem.setOnClickListener(v -> {
                        });
//                        binding.calenderView.setDate(data.getTimestamp() * 1000);
                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(dataList);
    }
}