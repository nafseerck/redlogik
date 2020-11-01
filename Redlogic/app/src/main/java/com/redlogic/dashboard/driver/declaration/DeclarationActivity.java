package com.redlogic.dashboard.driver.declaration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.redlogic.R;
import com.redlogic.dashboard.driver.DashboardActivity;
import com.redlogic.dashboard.driver.execution.ExecutionListActivity;
import com.redlogic.dashboard.driver.job.JobActivity;
import com.redlogic.dashboard.driver.request.GeneratePodRequestModel;
import com.redlogic.dashboard.driver.request.InitialChecklistRequestModel;
import com.redlogic.dashboard.driver.response.JobsResponseModel;
import com.redlogic.databinding.ActivityDeclarationBinding;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.AppPrefes;
import com.redlogic.utils.api.ApiServiceProvider;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.core.CoreUtils;
import com.redlogic.utils.image.FileConversion;
import com.redlogic.utils.image.ImageUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DeclarationActivity extends BaseLoaderActivity {

    public static boolean isPod;
    private ActivityDeclarationBinding binding;
    private JobsResponseModel.DataBean data;
    static final int REQUEST_IMAGE_GET = 1;
    boolean isUpload;
    private String base64;
    private boolean isSecond;
    String pdf;

    public static void start(Context context) {
        Intent intent = new Intent(context, DeclarationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration);
        binding = (ActivityDeclarationBinding) viewDataBinding;
        setTitle("Declaration");
        data = JobActivity.data;

        appPrefes =  appPrefes = new AppPrefes(this);
        appPrefes.saveBoolData("is_job_id",true);
        appPrefes.saveIntData("job_id",Integer.parseInt(data.getJob_id()));

        binding.include.tvTitleTxt1.setText(R.string.customer);
        binding.include.tvTitle1.setText(data.getCustomer());
        binding.include.imCall.setOnClickListener(v -> call(data.getCustomer_phone()));
        String dateTime = CoreUtils.getParsedStamp("dd-MMM-yyyy,hh:mm aa", data.getTimestamp());
        binding.include.tvTitleTxt2.setText(dateTime);
        binding.include.tvTitle21.setText(data.getFrom_location());
        binding.include.tvTitle22.setText(data.getTo_location());
        ImageUtils.setRoundedBackground(this, ImageUtils.convertToIntArray(getColor(R.color.grad_1),
                getColor(R.color.grad_1)), ImageUtils.convertToIntArray(getColor(R.color.grad_1_trans),
                getColor(R.color.grad_1_trans)), 0, binding.tvGenerate);
        binding.tvGenerate.setText(isPod ? "Generate POD" : "Generate Timesheet");

        ImageUtils.setRoundedBackgroundWithBorder(this, Color.WHITE,
                getColor(R.color.gray_232), 1, 19, binding.liDraw);

        if(data.isPod_status()) {
            binding.liDraw.setVisibility(View.GONE);
            binding.tvGenerate.setText("Close Job");
            callGetPod();
        }
        if(data.getJob_status().equals("completed"))
            binding.tvGenerate.setVisibility(View.GONE);

    }

    public void onGenerate(View view) {
        if (isSecond) {
            if (binding.tvGenerate.getText().toString().matches("Close Job")){
                callCloseJob();
            }
            return;

        }
        if (isUpload) {
            if (base64 == null) {
                showToast("Please pick signature");
                return;
            }
        } else if (binding.signatureView.isBitmapEmpty()) {
            showToast("Please draw signature");
            return;
        }
        callGeneratePod();
    }

    public void onDraw(View view) {
        isUpload = false;
        binding.viDraw.setVisibility(View.VISIBLE);
        binding.signatureView.setVisibility(View.VISIBLE);
        binding.viUpload.setVisibility(View.GONE);
        binding.imSig.setVisibility(View.GONE);
    }

    public void onUpload(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    private void callGeneratePod() {
        showDialog();
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        GeneratePodRequestModel requestModel = new GeneratePodRequestModel();
        requestModel.setJob_id(data.getJob_id());
        if(!data.isPod_status())
            requestModel.setDocument_base64(isUpload ? base64 : FileConversion.getBase64(binding.signatureView.getSignatureBitmap()));
        else
            requestModel.setDocument_base64("");

        Call<ResponseBody> call = apiServiceProvider.apiServices.callGeneratePod(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                isSecond = true;
                setTitle("Download");
                binding.tvGenerate.setText("Close Job");
                binding.tvTitle.setText(isPod ?
                        "Generated\nPOD Successfully" :
                        "Generated\nTimesheet Successfully");
                binding.liDraw.setVisibility(View.GONE);
                binding.liGenerated.setVisibility(View.VISIBLE);

                try {

                    JSONObject obj = new JSONObject(responseBodyString);
                    pdf=obj.getString("download_url");


                } catch (Throwable t) {
                    // shivin :
                    showToast("POD is pending");
                }
                hideDialog();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }

    private void callGetPod() {
        showDialog();
        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        GeneratePodRequestModel requestModel = new GeneratePodRequestModel();
        requestModel.setJob_id(data.getJob_id());


        Call<ResponseBody> call = apiServiceProvider.apiServices.callGetPod(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                isSecond = true;
                setTitle("Download");
                binding.tvGenerate.setText("Close Job");
              /*  binding.tvTitle.setText(isPod ?
                        "Generated\nPOD Successfully" :
                        "Generated\nTimesheet Successfully");*/
                binding.tvTitle.setText("Download POD");
                binding.liDraw.setVisibility(View.GONE);
                binding.liGenerated.setVisibility(View.VISIBLE);


                try {

                    JSONObject obj = new JSONObject(responseBodyString);
                    JSONObject  JSONResult =new JSONObject(obj.getString("result"));

                    pdf=JSONResult.getString("download_url");

                } catch (Throwable t) {
                    // shivin :
                    showToast("POD is pending");
                }
                hideDialog();
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {
                hideDialog();
            }
        };
        apiServiceProvider.callApi(apiParams);
    }


    private void callCloseJob() {
        InitialChecklistRequestModel requestModel = new InitialChecklistRequestModel();
        requestModel.setJob_id(ExecutionListActivity.jobId);

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        Call<ResponseBody> call = apiServiceProvider.apiServices.callCloseJob(requestModel);
        ApiServiceProvider.ApiParams apiParams = new ApiServiceProvider.ApiParams();
        apiParams.call = call;
        apiParams.retrofitListener = new RetrofitListener() {
            @Override
            public void onResponseSuccess(String responseBodyString) {
                showToast("Job Closed");
                DashboardActivity.startExit(DeclarationActivity.this);
            }

            @Override
            public void onResponseError(ErrorObject errorObject) {

            }
        };
        apiServiceProvider.callApi(apiParams);

    }

    @Override
    @SuppressLint("StaticFieldLeak")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    try {
                        if (Build.VERSION.SDK_INT >= 29) {
                            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), fullPhotoUri));
                        } else {
                            return MediaStore.Images.Media.getBitmap(getContentResolver(), fullPhotoUri);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    Bitmap bmp = ThumbnailUtils.extractThumbnail(bitmap, 250, 250);
                    binding.imSig.setImageBitmap(bmp);
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            base64 = FileConversion.getBase64(bmp);
                            return null;
                        }
                    }.execute();
                }
            }.execute();
            binding.viDraw.setVisibility(View.GONE);
            binding.signatureView.setVisibility(View.GONE);
            binding.viUpload.setVisibility(View.VISIBLE);
            binding.imSig.setVisibility(View.VISIBLE);
            isUpload = true;
        }
    }

    public void onDownload(View view) {
        if(pdf != null) {
            //FileConversion.downloadPdf(this, "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/"+pdf);
            FileConversion.downloadPdf(this, pdf);
            showToast("Downloading...");
            showToast("Please check you downloads folder");
        }else {
            showToast("POD is pending");

        }
    }

    public void onClear(View view) {
        binding.signatureView.clearCanvas();
    }
}
