package com.redlogic.dashboard.driver.damage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.redlogic.BuildConfig;
import com.redlogic.R;
import com.redlogic.dashboard.driver.job.JobActivity;
import com.redlogic.dashboard.driver.request.ReportDamageRequestModel;
import com.redlogic.databinding.ActivityReportDamagesBinding;
import com.redlogic.databinding.ItemFilesBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.FileConversion;
import com.redlogic.utils.image.ImageUtils;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ReportDamagesActivity extends BaseLoaderActivity {
    private ActivityReportDamagesBinding binding;

    public static final int REQUEST_CAMERA = 11;
    private Uri mImageCaptureUri;
    private File destination;
    List<ReportDamageRequestModel.AttachementBean> listAttachment = new ArrayList<>();
    private SlimAdapter slimAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ReportDamagesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_damages);
        binding = (ActivityReportDamagesBinding) viewDataBinding;
        setTitle("Report Damages");
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 0, binding.tvSend);
        ImageUtils.setRoundedBackgroundWithBorder(this, Color.WHITE,
                getColor(R.color.gray_196), 1, 11, binding.edWrite);
        ImageUtils.setRoundedBackgroundWithBorder(this, Color.WHITE,
                getColor(R.color.gray_196), 1, 6, binding.liTake);
        setAdapter();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && permissions[0].equals(Manifest.permission.CAMERA)) {
            onTakeCamera(null);
        }
    }

    public void onSend(View view) {
        if (binding.edWrite.getText().toString().isEmpty()) {
            showToast("Please enter reason");
            return;
        }
        if (listAttachment.size() == 0) {
            showToast("Please take a photo");
            return;
        }
        callReportDamage();
    }

    public void onTakeCamera(View view) {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
            return;
        }
        if (listAttachment.size() == 8) return;
        camera();
    }

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = CoreUtils.createDirectory(this);
        destination = new File(dir, CoreUtils.getCurrentDateTime() + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            mImageCaptureUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", destination);
        else {

            mImageCaptureUri = Uri.fromFile(destination);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (mImageCaptureUri != null) {
                    new Thread(() -> {
                        try {
                            File compressedImageFile = new Compressor(this).compressToFile(destination);
                            runOnUiThread(() -> {
                                ReportDamageRequestModel.AttachementBean attachment = new ReportDamageRequestModel.AttachementBean();
                                attachment.setImage_base64(FileConversion.getStringFile(compressedImageFile));
                                listAttachment.add(attachment);
                                slimAdapter.updateData(listAttachment);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }

            }
        }
    }

    private void setAdapter() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_files, new SlimInjector<ReportDamageRequestModel.AttachementBean>() {

                    @Override
                    public void onInject(@NonNull final ReportDamageRequestModel.AttachementBean data, @NonNull IViewInjector injector) {
                        ItemFilesBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        FileConversion.setImage(data.getImage_base64(), mBinding.imIcon);
                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(listAttachment);
    }

    private void callReportDamage() {
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        ReportDamageRequestModel requestModel = new ReportDamageRequestModel();
        requestModel.setJob_id(JobActivity.data.getJob_id());
        requestModel.setDamage_desc(binding.edWrite.getText().toString());
        requestModel.setAttachement(listAttachment);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callReportDamage(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        showDialog();
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                hideDialog();
                showToast("Damage Reported Successfully");
                finish();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

}
