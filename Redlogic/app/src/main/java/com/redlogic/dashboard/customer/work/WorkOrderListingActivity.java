package com.redlogic.dashboard.customer.work;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.customer.response.WorkOrderListingResponse;
import com.redlogic.dashboard.driver.request.JobsRequestModel;
import com.redlogic.databinding.ActivityWorkOrderListingBinding;
import com.redlogic.databinding.ItemDeliveryTabsBinding;
import com.redlogic.databinding.ItemWorkOrderBinding;
import com.redlogic.databinding.ItemWorkServiceNameBinding;
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

public class WorkOrderListingActivity extends BaseLoaderActivity {
    public static int selectedPosition;
    private ActivityWorkOrderListingBinding binding;
    private LinearLayout selectedLiItem;
    private ImageView selectedImIcon;
    private TextView selectedTvTitle;
    List<WorkOrderListingResponse.DataBean.WorkOrdersBean> data = new ArrayList<>();
    private SlimAdapter slimAdapter;
    public static String deliveryType;

    public static void start(Context context) {
        Intent intent = new Intent(context, WorkOrderListingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_listing);
        binding = (ActivityWorkOrderListingBinding) viewDataBinding;
        setAdapter();
        setTitle("Work Order Listing");
        addItem(getString(R.string.delivery_new), R.mipmap.latest, 0);
        addItem(getString(R.string.delivery_in_process), R.mipmap.inprocess, 1);
        addItem(getString(R.string.delivery_completed), R.mipmap.completed, 2);
        addItem(getString(R.string.delivery_rejected), R.mipmap.rejected, 3);
        setItems();
    }

    private void setAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_work_order, new SlimInjector<WorkOrderListingResponse.DataBean.WorkOrdersBean>() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onInject(@NonNull final WorkOrderListingResponse.DataBean.WorkOrdersBean data, @NonNull IViewInjector injector) {
                        ItemWorkOrderBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvRefNo.setText(data.getRef_no());
                        mBinding.tvRelNo.setText(data.getRl_no());
                        mBinding.tvJobs.setText(data.getJobs());
                        mBinding.tvJobsInprocess.setText(data.getJobs_inprocess());
                        mBinding.tvExecution.setText(data.getExecutions());
                        mBinding.tvExecutionInprocess.setText(data.getExecution_inprocess());
                        ViewGroup viewGroup = null;
                        mBinding.liServiceItems.removeAllViews();
                        for (int i = 0; i < data.getServices().size(); i++) {
                            if (viewGroup != null) {
                                ItemWorkServiceNameBinding mServiceBinding = DataBindingUtil.bind(viewGroup.findViewById(R.id.liItem));
                                mServiceBinding.tvItem2.setText(data.getServices().get(i).getService_name());
                                ImageUtils.setRoundedBackground(WorkOrderListingActivity.this,
                                        getColor(R.color.gray_216), 13, mServiceBinding.tvItem2);
                                mServiceBinding.tvItem2.setVisibility(View.VISIBLE);
                                viewGroup = null;
                            } else {
                                ItemWorkServiceNameBinding mServiceBinding = addServiceItem();
                                mServiceBinding.tvItem1.setText(data.getServices().get(i).getService_name());
                                ImageUtils.setRoundedBackground(WorkOrderListingActivity.this,
                                        getColor(R.color.gray_216), 13, mServiceBinding.tvItem1);
                                viewGroup = mServiceBinding.liItem;
                                mServiceBinding.tvItem2.setVisibility(View.GONE);
                                mBinding.liServiceItems.addView(viewGroup);
                            }

                        }
                        mBinding.liItem.setOnClickListener(v -> {
                            WorkOrderDetailsActivity.data = data;
                            WorkOrderDetailsActivity.start(WorkOrderListingActivity.this);
                        });
                    }
                })
                .register(R.layout.item_empty, new SlimInjector<Boolean>() {

                    @Override
                    public void onInject(@NonNull Boolean data, @NonNull IViewInjector injector) {

                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(data);

    }

    private ItemWorkServiceNameBinding addServiceItem() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View injector = inflater.inflate(R.layout.item_work_service_name, null, false);
        return DataBindingUtil.bind(injector.findViewById(R.id.liItem));
    }

    private void addItem(String text, int icon, int position) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View injector = inflater.inflate(R.layout.item_delivery_tabs, null, false);
        ItemDeliveryTabsBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
        if (mBinding == null) return;
        if (position == selectedPosition) {
            mBinding.liItem.setBackgroundColor(getColor(R.color.grad_3));
            mBinding.imIcon.setColorFilter(Color.WHITE);
            mBinding.tvTitle.setTextColor(Color.WHITE);
            selectedLiItem = mBinding.liItem;
            selectedImIcon = mBinding.imIcon;
            selectedTvTitle = mBinding.tvTitle;
        }
        mBinding.tvTitle.setText(text);
        mBinding.imIcon.setImageResource(icon);
        mBinding.liItem.setOnClickListener(v -> {
            if (position == selectedPosition) return;
            selectedLiItem.setBackgroundColor(getColor(R.color.white));
            selectedImIcon.setColorFilter(getColor(R.color.gray_153));
            selectedTvTitle.setTextColor(getColor(R.color.grad_5));

            mBinding.liItem.setBackgroundColor(getColor(R.color.grad_3));
            mBinding.imIcon.setColorFilter(Color.WHITE);
            mBinding.tvTitle.setTextColor(Color.WHITE);


            selectedLiItem = mBinding.liItem;
            selectedImIcon = mBinding.imIcon;
            selectedTvTitle = mBinding.tvTitle;
            selectedPosition = position;
            setItems();
        });
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        int width = size.x / 4;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.liItem.setLayoutParams(layoutParams);
        binding.liItem.addView(injector);
        if (position != 3)
            binding.liItem.addView(LayoutInflater.from(this).inflate(R.layout.item_devider, null, false));
    }

    private void setItems() {
        switch (selectedPosition) {
            case 0:
                deliveryType = "new";
                getDeliveries("new");
                break;
            case 1:
                deliveryType = "inprocess";
                getDeliveries("inprocess");
                break;
            case 2:
                deliveryType = "completed";
                getDeliveries("completed");
                break;
            case 3:
                deliveryType = "rejected";
                getDeliveries("rejected");
                break;
        }
    }

    private void getDeliveries(String type) {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        JobsRequestModel requestModel = new JobsRequestModel();
        requestModel.setType(type);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callWorkOrders(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                WorkOrderListingResponse responseModel = new Gson().fromJson(responseBodyString, WorkOrderListingResponse.class);
                data.clear();
                data.addAll(responseModel.getData().getWork_orders());
                slimAdapter.updateData(data);
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

}