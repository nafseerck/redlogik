package com.redlogic.dashboard.driver.deliveries;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.redlogic.R;
import com.redlogic.dashboard.driver.job.JobActivity;
import com.redlogic.dashboard.driver.request.JobsRequestModel;
import com.redlogic.dashboard.driver.response.JobsResponseModel;
import com.redlogic.databinding.ActivityDeliveriesBinding;
import com.redlogic.databinding.ItemDeliveryTabsBinding;
import com.redlogic.databinding.ItemProcessBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DeliveriesActivity extends BaseLoaderActivity implements TextWatcher {
    public static int selectedPosition;
    public static boolean isnewJob = false;
    public static boolean isCompleted = false;
    private ActivityDeliveriesBinding binding;
    private LinearLayout selectedLiItem;
    private ImageView selectedImIcon;
    private TextView selectedTvTitle;
    private boolean isSearch;
    List<JobsResponseModel.DataBean> data = new ArrayList<>();
    private SlimAdapter slimAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, DeliveriesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries);
        binding = (ActivityDeliveriesBinding) viewDataBinding;
        setTitle("Deliveries");
        addItem(getString(R.string.delivery_new), R.mipmap.latest, 0);
        addItem(getString(R.string.delivery_in_process), R.mipmap.inprocess, 1);
        addItem(getString(R.string.delivery_completed), R.mipmap.completed, 2);
        //addItem(getString(R.string.delivery_rejected), R.mipmap.rejected, 3);
        setAdapter();

        setItems();

        binding.edSearch.addTextChangedListener(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setItems();

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
        int width = size.x / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.liItem.setLayoutParams(layoutParams);
        binding.liItem.addView(injector);
        if (position != 3)
            binding.liItem.addView(LayoutInflater.from(this).inflate(R.layout.item_devider, null, false));
    }

    private void setItems() {
        if (isSearch)
            onSearch(null);
        switch (selectedPosition) {
            case 0:
                isnewJob = true;
                isCompleted = false;
                getDeliveries("new");
                binding.tvText.setText(R.string.delivery_new);
                break;
            case 1:
                isCompleted = false;
                isnewJob = false;
                getDeliveries("inprogress");
                binding.tvText.setText(R.string.delivery_in_process);
                break;
            case 2:
                isnewJob = false;
                isCompleted = true;
                getDeliveries("completed");
                binding.tvText.setText(R.string.delivery_completed);
                break;
            case 3:
                isCompleted = false;
                isnewJob = false;
                getDeliveries("rejected");
                binding.tvText.setText(R.string.delivery_rejected);
                break;
        }
    }

    private void getDeliveries(String type) {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        JobsRequestModel requestModel = new JobsRequestModel();
        requestModel.setType(type);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callJobs(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                JobsResponseModel responseModel = new Gson().fromJson(responseBodyString, JobsResponseModel.class);
                data.clear();
                data.addAll(responseModel.getData());
                slimAdapter.updateData(data);
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
                .register(R.layout.item_process, new SlimInjector<JobsResponseModel.DataBean>() {

                    @Override
                    public void onInject(@NonNull final JobsResponseModel.DataBean data, @NonNull IViewInjector injector) {
                        ItemProcessBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvFrom.setText(data.getFrom_location());
                        mBinding.tvTo.setText(data.getTo_location());
                        String dateTime = CoreUtils.
                                getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
                        mBinding.tvDateTime.setText(dateTime);
                        mBinding.liItem.setOnClickListener(v -> {
                            JobActivity.data = data;
                            JobActivity.start(DeliveriesActivity.this);
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

    public void onSearch(View view) {
        if (isSearch) {
            binding.edSearch.setText(null);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.edSearch.getWindowToken(), 0);
            slimAdapter.updateData(data);
            binding.card.setVisibility(View.GONE);
        } else {
            binding.edSearch.requestFocus();
            binding.card.setVisibility(View.VISIBLE);
            binding.edSearch.postDelayed(() -> {
                        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        keyboard.showSoftInput(binding.edSearch, 0);
                    }
                    , 200);
        }
        isSearch = !isSearch;
    }

    public void onFilter(View view) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String searchKey = binding.edSearch.getText().toString().toLowerCase();
        if (searchKey.isEmpty()) {
            slimAdapter.updateData(data);
            return;
        }
        List<JobsResponseModel.DataBean> tempData = new ArrayList<>();
        for (int j = 0; j < data.size(); j++) {
            JobsResponseModel.DataBean item = data.get(j);
            if (item.getFrom_location().toLowerCase().contains(searchKey) ||
                    item.getTo_location().toLowerCase().contains(searchKey)) {
                tempData.add(item);
            }
        }
        if (tempData.size() == 0) {
            List<Boolean> booleanList = new ArrayList<>();
            booleanList.add(true);
            slimAdapter.updateData(booleanList);
            return;
        }
        slimAdapter.updateData(tempData);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
