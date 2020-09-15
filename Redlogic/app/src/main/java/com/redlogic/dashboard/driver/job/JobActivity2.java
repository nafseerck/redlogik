package com.redlogic.dashboard.driver.job;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.redlogic.R;
import com.redlogic.dashboard.driver.decline.DeclineActivity;
import com.redlogic.dashboard.driver.deliveries.DeliveriesActivity;
import com.redlogic.dashboard.driver.execution.ExecutionListActivity;
import com.redlogic.dashboard.driver.request.AcceptOrRejectRequestModel;
import com.redlogic.dashboard.driver.response.JobsResponseModel;
import com.redlogic.databinding.ActivityJob2Binding;
import com.redlogic.databinding.ItemCargoDetailsBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.ImageUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class JobActivity2 extends BaseLoaderActivity {

    public static JobsResponseModel.DataBean data;
    private ActivityJob2Binding binding;
    private String TAG="tag_jithin";

    public static void start(Context context) {
        Intent intent = new Intent(context, JobActivity2.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job2);
        binding = (ActivityJob2Binding) viewDataBinding;
        setTitle("Job");
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 0, binding.tvAccept);
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.black),
                getColor(R.color.black)), ImageUtils.convertToIntArray(getColor(R.color.black_trans),
                getColor(R.color.black_trans)), 0, binding.tvReject);
        binding.tvRoute.setText("Route - 20.9 kilometers");
        binding.tvTime.setText("23 Minutes");
        binding.tvTitleTxt11.setText("Assigned by");
        binding.tvTitle11.setText(data.getAssigned_by());
        binding.tvTitleTxt12.setText(R.string.customer);
        binding.tvTitle12.setText(data.getCustomer());
        String dateTime = CoreUtils.
                getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
        binding.tvTitleTxt21.setText(dateTime);
        binding.tvTitle211.setText(data.getFrom_location());
        binding.tvTitle221.setText(data.getTo_location());
        if (data.getCargo_details() == null || data.getCargo_details().isEmpty()){
            binding.tvTitleTxt31.setText("Marterial & Quantity");
        }else {
            setCargoAdapter(data.getCargo_details());
        }

        binding.tvTitle31.setText(data.getMaterial());
        binding.tvTitleTxt32.setText(R.string.address);
        binding.tvTitle32.setText(data.getCustomer_address());
        binding.li22.setVisibility(View.GONE);

        binding.imCall1.setOnClickListener(v -> call(data.getAssignee_phone()));
        binding.imCall2.setOnClickListener(v -> call(data.getCustomer_phone()));
        binding.card1.setOnClickListener(v -> {
            if (!DeliveriesActivity.isnewJob) {
                callAcceptOrReject("accept");
            }
        });
        if (DeliveriesActivity.selectedPosition == 2 || DeliveriesActivity.selectedPosition == 3 || DeliveriesActivity.selectedPosition == 1) {
            binding.tvAccept.setVisibility(View.GONE);
            binding.tvReject.setVisibility(View.GONE);
        }

//        String origin = "11.715602, 77.040184";
//        String destination = "12.360282, 78.380516";
//        new DistanceMatrix().getDistance(origin, destination, this, value -> {
//            String distance = value.getRows().get(0)
//                    .getElements().get(0).getDistance().getText().replace("km", "kilometers");
//            binding.tvRoute.setText(String.format("Route - %s", distance));
//            binding.tvTime.setText(String.format("%s", value.getRows().get(0)
//                    .getElements().get(0).getDuration().getText()));
//        });
    }

    private void setCargoAdapter(List<JobsResponseModel.DataBean.CargoDetails> cargo_details) {
        binding.recyclerVewCargo.setVisibility(View.VISIBLE);
        binding.recyclerVewCargo.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_cargo_details, new SlimInjector<JobsResponseModel.DataBean.CargoDetails>() {
                    @Override
                    public void onInject(JobsResponseModel.DataBean.CargoDetails data, IViewInjector injector) {
                        ItemCargoDetailsBinding cargoDetailsBinding = DataBindingUtil.bind(injector.findViewById(R.id.cargo_layout));
                        if (cargoDetailsBinding == null) return;

                        cargoDetailsBinding.material.setText(data.getMaterial());
                        cargoDetailsBinding.quantity.setText(data.getQuantity());
                        cargoDetailsBinding.weight.setText(data.getWeight());

                    }
                })
                .attachTo(binding.recyclerVewCargo);
        slimAdapter.updateData(cargo_details);
        binding.recyclerVewCargo.setNestedScrollingEnabled(false);
    }

    public void onAccept(View view) {
        if (isDateValid()){
            callAcceptOrReject("accept");
            Log.d(TAG, "onAccept: ");
        }else {
            Log.d(TAG, "onNotAccept: ");
            showToast("Date expired");
        }
    }

    public void onReject(View view) {
        if (isDateValid()){
            callAcceptOrReject("reject");
            Log.d(TAG, "onAccept: ");
        }else {
            showToast("Date expired");
        }

    }

    public static boolean isDateValid(){
        String dateTime = CoreUtils.getParsedStamp("dd-MM-yyyy", data.getTimestamp());
        String currentDate = CoreUtils.getParsedCurrentDate("dd-MM-yyyy");
        if (dateTime.matches(currentDate)){
            return true;
        }else {
            return false;
        }
    }

    public void onMap(View view) {
        //RouteMapActivity.start(this);
        CoreUtils.openRoutesMaps(this, data.getTo_location());
    }


    private void callAcceptOrReject(String status) {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        AcceptOrRejectRequestModel requestModel = new AcceptOrRejectRequestModel();
        requestModel.setJob_id(data.getJob_id());
        requestModel.setStatus(status);
        requestModel.setDescription("");
        Call<ResponseBody> call = apiServiceProvider.apiServices.callAcceptOrReject(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                //showToast("Job Accepted Successfully");
                if (status.matches("accept")){
                    ExecutionListActivity.start(JobActivity2.this);
                    finish();
                }else {
                    DeclineActivity.start(JobActivity2.this);
                    finish();
                }
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }
}
