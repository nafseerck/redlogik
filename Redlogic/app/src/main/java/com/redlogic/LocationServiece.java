package com.redlogic;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LocationServiece extends Service {
    private String TAG="tag_location_service";

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand:started service ");

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    private final class ServiceHandler extends Handler{
        public ServiceHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                Thread.sleep(5000);

            }catch (Exception e){
                Thread.currentThread().interrupt();
            }

            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {


        /*HandlerThread thread = new HandlerThread("serviceStartArgs", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);*/


    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: service stopped");
    }

}
