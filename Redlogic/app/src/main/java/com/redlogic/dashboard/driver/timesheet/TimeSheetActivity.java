package com.redlogic.dashboard.driver.timesheet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.redlogic.R;
import com.redlogic.dashboard.driver.request.GenerateTimeSheetRequestModel;
import com.redlogic.databinding.ActivityTimeSheetBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.ImageUtils;

import java.util.Calendar;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class TimeSheetActivity extends BaseLoaderActivity {

    private ActivityTimeSheetBinding binding;
    private boolean isStart;

    public static void start(Context context) {
        Intent intent = new Intent(context, TimeSheetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);
        binding = (ActivityTimeSheetBinding) viewDataBinding;
        hideActionBar();
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_6),
                getColor(R.color.grad_6)), ImageUtils.convertToIntArray(getColor(R.color.grad_6_trans),
                getColor(R.color.grad_6_trans)), 20, binding.tvSave);

        ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_250),
                getColor(R.color.gray_219), 1, 4, binding.edNote);

        ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_250_trans),
                getColor(R.color.gray_219_trans), 1, 4, binding.liFrom);
        ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_250_trans),
                getColor(R.color.gray_219_trans), 1, 4, binding.liTo);

        isStart = new Random().nextBoolean();
        if (isStart) {
            ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_250),
                    getColor(R.color.gray_219), 1, 4, binding.liFrom);
        } else {
            ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_250),
                    getColor(R.color.gray_219), 1, 4, binding.liTo);
        }
        binding.tvTitle.setText(isStart ? "Loading Started" : "Start for Delivery");
    }

    public void onSave(View view) {
        if (isStart)
            if (binding.tvFrom.getText().toString().isEmpty()) {
                showToast("Please enter from date");
                return;
            }
        if (!isStart)
            if (binding.tvTo.getText().toString().isEmpty()) {
                showToast("Please enter to date");
                return;
            }
        if (binding.edNote.getText().toString().isEmpty()) {
            showToast("Please enter notes");
            return;
        }
        showToast("TimeSheet Saved Successfully");
        finish();
    }

    private void callGenerateTimeSheet() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        GenerateTimeSheetRequestModel requestModel = new GenerateTimeSheetRequestModel();

        Call<ResponseBody> call = apiServiceProvider.apiServices.callGenerateTimeSheet(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    public void onFrom(View view) {
        if (isStart)
            setDateTime(true);
    }

    public void onTo(View view) {
        if (!isStart)
            setDateTime(false);
    }

    private void setDateTime(boolean isFrom) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + " " + (month + 1) + " " + year;
            String outDate = CoreUtils.getParsedDate("dd MM yyyy", "dd-MMM-yyyy,", selectedDate);
            TimePickerDialog tpd = new TimePickerDialog(this, (view2, hourOfDay, minute) -> {
                String selectedTime = hourOfDay + ":" + minute;
                String outTime;
                if (hourOfDay == 12) {
                    outTime = CoreUtils.getParsedDate("hh:mm",
                            "hh:mm", selectedTime) + " PM";
                } else {
                    outTime = CoreUtils.getParsedDate("hh:mm",
                            "hh:mm aa", selectedTime);
                }
                if (isFrom) {
                    ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_250),
                            getColor(R.color.gray_219), 1, 4, binding.liFrom);
                    binding.tvFrom.setText(String.format("%s%s", outDate, outTime));
                } else {
                    ImageUtils.setRoundedBackgroundWithBorder(this, getColor(R.color.gray_250),
                            getColor(R.color.gray_219), 1, 4, binding.liTo);
                    binding.tvTo.setText(String.format("%s%s", outDate, outTime));
                }
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
            tpd.show();
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        dpd.show();
    }
}
