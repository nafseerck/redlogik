package com.redlogic.dashboard.customer.Schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.response.SchedulesCustomerResponseModel;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ScheduleActivity extends BaseLoaderActivity {

    private ActivityScheduleBinding binding;
    private String TAG="jithin_check";
    List<SchedulesCustomerResponseModel.DataBean> list;

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
        List<SchedulesCustomerResponseModel.DataBean> filteredList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            if(!list.get(i).getTimestamp().isEmpty() && !list.get(i).getTimestamp().equals("")) {
                try{
                String date = CoreUtils.getParsedStamp("dd-MM-yyyy", Long.parseLong(list.get(i).getTimestamp()));
                if (selectedDate.matches(date)) {
                    filteredList.add(list.get(i));
                }
                }catch (Exception e)
                {
                    showToast("Invalid dates in backend");
                }
            }
        }
        setAdapter(filteredList);
    }


    private void callSchedules() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callCustomerSchedules();
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                SchedulesCustomerResponseModel responseModel = new Gson().fromJson(responseBodyString, SchedulesCustomerResponseModel.class);
//                setAdapter(responseModel.getData());
                list = responseModel.getData();
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

    private void setAdapter(List<SchedulesCustomerResponseModel.DataBean> dataList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_notification, new SlimInjector<SchedulesCustomerResponseModel.DataBean>() {

                    @Override
                    public void onInject(@NonNull final SchedulesCustomerResponseModel.DataBean data, @NonNull IViewInjector injector) {
                        ItemNotificationBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvTitle.setText(String.format("%s to %s", data.getJob_details().getFrom_location(), data.getJob_details().getTo_location()));
                        String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", Long.parseLong(data.getTimestamp()));
                        mBinding.tvTitleTxt.setText(dateTime);
                        mBinding.liItem.setOnClickListener(v -> {
                        });
                        binding.calenderView.setDate(Long.parseLong(data.getTimestamp()) * 1000);
                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(dataList);
    }
}
