package com.redlogic.dashboard.driver.job;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.decline.DeclineActivity;
import com.redlogic.dashboard.driver.deliveries.DeliveriesActivity;
import com.redlogic.dashboard.driver.execution.ExecutionListActivity;
import com.redlogic.dashboard.driver.request.AcceptOrRejectRequestModel;
import com.redlogic.dashboard.driver.request.GatePassRequestModel;
import com.redlogic.dashboard.driver.response.GatePassListResponseModel;
import com.redlogic.dashboard.driver.response.JobsResponseModel;
import com.redlogic.databinding.ActivityJobBinding;
import com.redlogic.databinding.ItemCargoDetailsBinding;
import com.redlogic.databinding.ItemGatePassesBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.AppPrefes;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.FileConversion;
import com.redlogic.utils.image.ImageUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class JobActivity extends BaseLoaderActivity {

    public static JobsResponseModel.DataBean data;
    private ActivityJobBinding binding;
    private String TAG = "tag_jithin";
    public static ArrayList<String> acceptedJobs = new ArrayList<>();
    Dialog alertDialog;




    public static void start(Context context) {
        Intent intent = new Intent(context, JobActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        binding = (ActivityJobBinding) viewDataBinding;

        appPrefes =  appPrefes = new AppPrefes(this);
        appPrefes.saveBoolData("is_job_id",true);
        appPrefes.saveIntData("job_id",Integer.parseInt(data.getJob_id()));

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
        if (data.getCargo_details() == null || data.getCargo_details().isEmpty()) {

        } else {
            binding.tvTitleTxt31.setText("Material & Quantity");
            setCargoAdapter(data.getCargo_details());
        }

        binding.tvTitle31.setText(data.getMaterial());
        binding.tvTitleTxt32.setText(R.string.address);
        binding.tvTitle32.setText(data.getCustomer_address());
        binding.li22.setVisibility(View.GONE);

        binding.imCall1.setOnClickListener(v -> call(data.getAssignee_phone()));
        binding.imCall2.setOnClickListener(v -> call(data.getCustomer_phone()));
        binding.card1.setOnClickListener(v -> {

            if (isDateValid()){
                callAcceptOrReject("accept");
            }

        });
        if (DeliveriesActivity.selectedPosition == 2 || DeliveriesActivity.selectedPosition == 3 || DeliveriesActivity.selectedPosition == 1) {
            binding.tvAccept.setVisibility(View.GONE);
            binding.tvReject.setVisibility(View.GONE);

            binding.bottomButtonLayout.setVisibility(View.GONE);

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

        //isAcceptedJob-temporary
        for (int i = 0; i < acceptedJobs.size(); i++) {
            if (acceptedJobs.get(i).matches(data.getJob_id())) {
                binding.bottomButtonLayout.setVisibility(View.GONE);
            }
        }


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
                        cargoDetailsBinding.unit.setText(data.getUnit());
                        cargoDetailsBinding.dimension.setText(data.getDimension());

                    }
                })
                .attachTo(binding.recyclerVewCargo);
        slimAdapter.updateData(cargo_details);
        binding.recyclerVewCargo.setNestedScrollingEnabled(false);
    }

    public void onAccept(View view) {
        if (isDateValid()) {
            callAcceptOrReject("accept");
            Log.d(TAG, "onAccept: ");
        } else {
            Log.d(TAG, "onNotAccept: ");
            // shivin : date validation
            showToast("Execution : "+CoreUtils.getParsedStamp("dd-MM-yyyy", data.getTimestamp()));
        }
    }

    public void onReject(View view) {
        if (isDateValid()) {
            callAcceptOrReject("reject");
            Log.d(TAG, "onAccept: ");
        }else{
            // shivin : date validation
            showToast("Execution : "+CoreUtils.getParsedStamp("dd-MM-yyyy", data.getTimestamp()));

        }

    }

    public static boolean isDateValid() {
        String dateTime = CoreUtils.getParsedStamp("dd-MM-yyyy", data.getTimestamp());
        String currentDate = CoreUtils.getParsedCurrentDate("dd-MM-yyyy");
        if (dateTime.matches(currentDate)) {
            return true;
        } else {
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
                acceptedJobs.add(data.getJob_id());
                if (status.matches("accept")) {
                    ExecutionListActivity.start(JobActivity.this);
                    finish();
                } else {
                    DeclineActivity.start(JobActivity.this);
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

    public void onGatePassClick(View view) {

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        GatePassRequestModel requestModel = new GatePassRequestModel();
        requestModel.setJob_id(Integer.parseInt(data.getJob_id()));
        Call<ResponseBody> call = apiServiceProvider.apiServices.callGatePass(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
               try {

                   GatePassListResponseModel responseModel = new Gson().fromJson(responseBodyString, GatePassListResponseModel.class);

                   if (responseModel.getMessage().equals("No data found")) {
                       showToast("Gate Passes not Found");
                   }else if (responseModel.getData().getGate_passes().size() == 0)
                   {
                       showToast("Gate Passes not Found");
                   }else {
                       setGatePassAdapter(responseModel.getData().getGate_passes());
                   }
               }catch (Exception e){

               }
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void setGatePassAdapter(List<GatePassListResponseModel.DataBean.GatePasses> gate_pass_list) {
        binding.recyclerViewGatePass.setVisibility(View.VISIBLE);
        binding.recyclerViewGatePass.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_gate_passes, new SlimInjector<GatePassListResponseModel.DataBean.GatePasses>() {
                    @Override
                    public void onInject(GatePassListResponseModel.DataBean.GatePasses data, IViewInjector injector) {
                        ItemGatePassesBinding cargoDetailsBinding = DataBindingUtil.bind(injector.findViewById(R.id.gate_pass_layout));
                        if (cargoDetailsBinding == null) return;

                        cargoDetailsBinding.tvPassName.setText(data.getName());

                        cargoDetailsBinding.ivImageDownload.setOnClickListener(v->{
                            createDialogue(data.getDownload_url().get(0));
                        });
                        cargoDetailsBinding.ivImageView.setOnClickListener(v->{
                            downloadGatePass(data.getDownload_url().get(0));

                        });

                    }
                })
                .attachTo(binding.recyclerVewCargo);
        slimAdapter.updateData(gate_pass_list);
        binding.recyclerVewCargo.setNestedScrollingEnabled(false);
    }

    public void downloadGatePass(String url)
    {
        if(url != null) {
            //FileConversion.downloadPdf(this, "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/"+pdf);
            FileConversion.downloadPdf(this, url);
            showToast("Downloading...");
            showToast("Please check you downloads folder");
        }else {
            showToast("Pass not available");

        }
    }

    Dialog createDialogue(String url) {
        try {
            alertDialog = new Dialog(this, 0);
            alertDialog.setContentView(R.layout.gate_pass_view_layout);
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            alertDialog.setCancelable(true);
            ImageView close = alertDialog.findViewById(R.id.close);
            ImageView ItemImg = alertDialog.findViewById(R.id.iv_item);

            RequestOptions options = new RequestOptions();
            options.centerInside().placeholder(this.getResources().getDrawable(R.drawable.logo));
            Glide
                    .with(this)
                    .load(url)
                    .apply(options)
                    .into(ItemImg);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            return alertDialog;
        } catch (Exception e) {

        }
        return null;
    }

}
