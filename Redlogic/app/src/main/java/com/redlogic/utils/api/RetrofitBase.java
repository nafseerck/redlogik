package com.redlogic.utils.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redlogic.BuildConfig;
import com.redlogic.generic.BaseLoaderActivity;
import com.redlogic.utils.AppPrefes;
import com.redlogic.utils.PrefConstants;
import com.redlogic.utils.api.listeners.RetrofitListener;
import com.redlogic.utils.api.models.ErrorObject;
import com.redlogic.utils.api.utils.Constants;
import com.redlogic.utils.api.utils.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {
    Retrofit retrofit;
    BaseLoaderActivity context;
    private Logger logger;

    private AppPrefes appPrefes;

    RetrofitBase(BaseLoaderActivity context, boolean addTimeout) {
        this.context = context;

        appPrefes = new AppPrefes(context);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder().addInterceptor(interceptor);
        if (addTimeout) {
            httpClientBuilder.readTimeout(Constants.TimeOut.SOCKET_TIME_OUT, TimeUnit.SECONDS);
            httpClientBuilder.connectTimeout(Constants.TimeOut.CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        } else {
            httpClientBuilder.readTimeout(Constants.TimeOut.IMAGE_UPLOAD_SOCKET_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.connectTimeout(Constants.TimeOut.IMAGE_UPLOAD_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        }

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            httpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            httpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        addVersioningHeaders(httpClientBuilder, context);
        OkHttpClient httpClient = httpClientBuilder.build();

        logger = new Logger(RetrofitBase.class.getSimpleName());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)

                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    private void addVersioningHeaders(OkHttpClient.Builder builder, Context context) {
        builder.interceptors().add(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + appPrefes.getData(PrefConstants.token))
                    .build();
            return chain.proceed(request);
        });
    }


    void validateResponse(Response response, RetrofitListener retrofitListener) throws IOException {
        if (response.code() == 200) {
            ResponseBody responseBody = (ResponseBody) response.body();
            if (responseBody != null) {
                String responses = responseBody.string();
                retrofitListener.onResponseSuccess(responses);
            }
        } else {
            ResponseBody responseBody = (ResponseBody) response.errorBody();
            if (responseBody != null) {
                String responses = responseBody.string();
                if (responses.isEmpty()) {
                    ErrorObject errorPojo = new ErrorObject();
                    errorPojo.setMessage("Some error occurred");
                    retrofitListener.onResponseError(errorPojo);
                    return;
                }
                ErrorObject errorPojo = new Gson().fromJson(responses, ErrorObject.class);
                if (errorPojo.getMessage().equals("Invalid token")) {
                    context.goToLogin();
                } else {
                    retrofitListener.onResponseError(errorPojo);
                }
            }
        }
    }

}
