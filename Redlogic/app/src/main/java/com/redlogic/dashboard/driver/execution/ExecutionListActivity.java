package com.redlogic.dashboard.driver.execution;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.damage.ReportDamagesActivity;
import com.redlogic.dashboard.driver.declaration.DeclarationActivity;
import com.redlogic.dashboard.driver.deliveries.DeliveriesActivity;
import com.redlogic.dashboard.driver.job.JobActivity;
import com.redlogic.dashboard.driver.request.ExecutionChecklistRequestModel;
import com.redlogic.dashboard.driver.request.InitialChecklistRequestModel;
import com.redlogic.dashboard.driver.response.ExecutionChecklistResponseModel;
import com.redlogic.dashboard.driver.response.JobsResponseModel;
import com.redlogic.dashboard.driver.timesheet.TimeSheetActivity;
import com.redlogic.databinding.ActivityExecutionListBinding;
import com.redlogic.databinding.ItemExecutionBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.ImageUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ExecutionListActivity extends BaseLoaderActivity {
    private ActivityExecutionListBinding binding;
    private JobsResponseModel.DataBean data;
    private String dateTime;
    private ExecutionChecklistResponseModel.DataBeanX listExecute;
    private boolean isSecondTime;
    private SlimAdapter slimAdapter;
    int nextCompleteCheckBoxId=0;
    private String TAG=  "tag_jithin";
    public static String jobId;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExecutionListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_list);
        binding = (ActivityExecutionListBinding) viewDataBinding;
        setTitle("Execution List");
        data = JobActivity.data;
        binding.include.tvTitleTxt1.setText(R.string.customer);
        binding.include.tvTitle1.setText(data.getCustomer());
        binding.include.imCall.setOnClickListener(v -> call(data.getCustomer_phone()));
        dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
        binding.include.tvTitleTxt2.setText(dateTime);
        binding.include.tvTitle21.setText(data.getFrom_location());
        binding.include.tvTitle22.setText(data.getTo_location());
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 0, binding.tvComplete);
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_6),
                getColor(R.color.grad_6)), ImageUtils.convertToIntArray(getColor(R.color.grad_6_trans),
                getColor(R.color.grad_6_trans)), 20, binding.tvReport);
        callInitialChecklist();

        if (DeliveriesActivity.isCompleted){
            binding.tvComplete.setVisibility(View.GONE);
        }
    }

    private void callInitialChecklist() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        InitialChecklistRequestModel requestModel = new InitialChecklistRequestModel();
        requestModel.setJob_id(data.getJob_id());


        Call<ResponseBody> call = apiServiceProvider.apiServices.callExecutionChecklist(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                ExecutionChecklistResponseModel responseModel = new Gson().fromJson(responseBodyString, ExecutionChecklistResponseModel.class);
                listExecute = responseModel.getData();

                setAdapter();
                hideDialog();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_execution, new SlimInjector<ExecutionChecklistResponseModel.DataBeanX.TasksBean>() {

                    @Override
                    public void onInject(@NonNull final ExecutionChecklistResponseModel.DataBeanX.TasksBean data, @NonNull IViewInjector injector) {

                        ItemExecutionBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvTitle.setText(data.getDescription());

                        mBinding.liItem.setOnClickListener(v -> {
                        });
                        if (isSecondTime) {
                            mBinding.imShape.setVisibility(View.GONE);
                        }
                        mBinding.imShape.setOnClickListener(v -> {
                            TimeSheetActivity.start(ExecutionListActivity.this);
                        });
                        mBinding.imCheck.setOnCheckedChangeListener((compoundButton, b) -> {
                            String mydate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                            mBinding.tvTitleTxt.setText(mydate);
//                            data.setValue(b ? "checked" : "unchecked");
                            if (mBinding.imCheck.isChecked()) {
                                if (data.getId() == listExecute.getTasks().get(nextCompleteCheckBoxId).getId()) {

                                    data.setIs_completed(true);
                                    data.setCompleted_on(getCurrentTime());
                                    executionChecklistSubmit();

                                } else {
                                    showToast("Complete Tasks orderly");
                                    if (mBinding.imCheck.isChecked()) {
                                        mBinding.imCheck.setChecked(false);
                                    }
                                }

                            }

                        });

                        if (!JobActivity.isDateValid()) {
                            mBinding.imCheck.setEnabled(false);
                            mBinding.imCheck.setClickable(false);
                        }

                        if (data.getIs_completed()) {
                            mBinding.imCheck.setVisibility(View.GONE);
                            mBinding.imTic.setVisibility(View.VISIBLE);
                        } else {
                            mBinding.imCheck.setVisibility(View.VISIBLE);
                            mBinding.imTic.setVisibility(View.GONE);
                        }
                        /*if (data.getIs_active() == 0) {
                            mBinding.imCheck.setEnabled(false);
                        } else {
                            mBinding.imCheck.setEnabled(true);
                        }*/
                    }
                })
                .attachTo(binding.recyclerView);
//        try {
//            for (int i = 0; i < listExecute.getTasks().get(0).getData().size(); i++) {
//                listExecute.getTasks().get(0).getData().get(i).setValue(listExecute.getTasks().get(0).getData().get(i).getValue().toString().equals("checked"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        slimAdapter.updateData(listExecute.getTasks());
        updateCompleteJobBtn();
        getNextCheckboxToComplete();
    }

    private String getCurrentTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = df.format(new Date());
        return gmtTime;
    }

    private void getNextCheckboxToComplete() {
        for (int i=0;i<listExecute.getTasks().size();i++){
            if (!listExecute.getTasks().get(i).getIs_completed()){
                nextCompleteCheckBoxId = i;
                break;
            }
        }
    }

    private void updateCompleteJobBtn() {
        int lastTask = listExecute.getTasks().size();
        if (listExecute.getTasks().get(lastTask-1).getIs_completed()){
            binding.tvComplete.setEnabled(true);
        }else {
            binding.tvComplete.setEnabled(false);
        }
    }

    private void updateCompleteJobBtnLastExecution() {
        int lastTask = listExecute.getTasks().size();
        if (listExecute.getTasks().get(lastTask-2).getIs_completed()){
            binding.tvComplete.setEnabled(true);
        }else {
            binding.tvComplete.setEnabled(false);
        }
    }

    private void executionChecklistSubmit() {
//        showToastDialog();
        showDialog();
        addCompletedOnAndValueFields();

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        ExecutionChecklistRequestModel requestModel = new ExecutionChecklistRequestModel();
        List<ExecutionChecklistResponseModel.DataBeanX> listJob = new ArrayList<>();
        ExecutionChecklistResponseModel.DataBeanX item = new ExecutionChecklistResponseModel.DataBeanX();
        item.setJob_id(listExecute.getJob_id());
        item.setService_master_id(listExecute.getService_master_id());
        item.setTasks(listExecute.getTasks());
        listJob.add(item);
        requestModel.setJob(listJob);

        Call<ResponseBody> call = apiServiceProvider.apiServices.callExecutionChecklistSubmit(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {

                hideDialog();
                updateCompleteJobBtnLastExecution();
                getNextCheckboxToComplete();
                slimAdapter.updateData(listExecute.getTasks());
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);

    }

    private void addCompletedOnAndValueFields() {
        for (int i=0;i<listExecute.getTasks().size();i++){
            if (listExecute.getTasks().get(i).getCompleted_on() == null){
                listExecute.getTasks().get(i).setCompleted_on("");
            }
            if (listExecute.getTasks().get(i).getData().get(0).getValue() == null){
                listExecute.getTasks().get(i).getData().get(0).setValue("");
            }
        }
    }

    public void onComplete(View view) {
        callCompleteJob();
    }

    private void callCompleteJob() {
        showToast("Job completed Successfully");
        jobId = String.valueOf(listExecute.getJob_id());
        DeclarationActivity.isPod = true;
        DeclarationActivity.start(ExecutionListActivity.this);
        finish();

        /*InitialChecklistRequestModel requestModel = new InitialChecklistRequestModel();
        requestModel.setJob_id(String.valueOf(listExecute.getJob_id()));

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callCloseJob(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                showToast("Job completed Successfully");
                DeclarationActivity.isPod = new Random().nextBoolean();

            }

            @Override
            public void onResponseError(ErrorObject errorObject) {

            }
        };
        apiServiceProvider.callApi(apiParams);*/

    }

    /*private void callExecutionChecklistSubmit() {
        showDialog();
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        ExecutionChecklistRequestModel requestModel = new ExecutionChecklistRequestModel();
        List<ExecutionChecklistResponseModel.DataBeanX> listJob = new ArrayList<>();
        ExecutionChecklistResponseModel.DataBeanX item = new ExecutionChecklistResponseModel.DataBeanX();

//        for (int i = 0; i < listExecute.getTasks().size(); i++) {
//            listExecute.getTasks().get(i).setIs_completed(true);
//        }
        item.setJob_id(listExecute.getJob_id());
        item.setService_master_id(listExecute.getService_master_id());
        item.setTasks(listExecute.getTasks());
        listJob.add(item);
        requestModel.setJob(listJob);

        Call<ResponseBody> call = apiServiceProvider.apiServices.callExecutionChecklistSubmit(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                showToast("Execution checklist submitted");
                DeclarationActivity.isPod = new Random().nextBoolean();
                DeclarationActivity.start(ExecutionListActivity.this);
                finish();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }*/

//    private void callInitialChecklistSubmit() {
//        showDialog();
//        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
//        ExecutionChecklistSubmitRequestModel requestModel = new ExecutionChecklistSubmitRequestModel();
//        requestModel.setData(listExecute.getTasks().get(0).getData());
//        Call<ResponseBody> call = apiServiceProvider.apiServices.callInitialChecklistSubmit(requestModel);
//        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
//        apiParams.call = call;
//        apiParams.retrofitListener = new RetrofitListener() {
//            @Override
//            public void onResponseSuccess(String responseBodyString) {
//                hideDialog();
//                isSecondTime = true;
//                showToast("Initial execution submitted");
//                slimAdapter.updateData(listExecute.getTasks().get(0).getData());
//            }
//
//            @Override
//            public void onResponseError(ErrorObject errorObject) {
//                hideDialog();
//            }
//        };
//        apiServiceProvider.callApi(apiParams);
//    }

    public void onReport(View view) {
        ReportDamagesActivity.start(this);
    }
}
