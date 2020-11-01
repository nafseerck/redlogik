package com.redlogic.utils.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.model.LatLng;
import com.redlogic.R;
import com.redlogic.utils.image.ImageUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class CoreUtils {
    public static int convertDpToPixels(Resources resources, int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static float convertPixelsToDp(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static boolean checkPermissions(String permission, Activity compatActivity) {
        int permissionState = ActivityCompat.checkSelfPermission(compatActivity,
                permission);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(String[] permission, Activity compatActivity) {
        ActivityCompat.requestPermissions(compatActivity,
                permission,
                1);
    }


    public static void addFragment(FragmentActivity activity, int id, Fragment fragment, int enter, int exit) {
        activity.getSupportFragmentManager().beginTransaction().addToBackStack(activity.getClass().getName())
                .setCustomAnimations(enter, exit)
                .add(id, fragment).commit();
    }

    public static int getColor(Context context, int color) {
        if (context == null) return 0;
        return context.getColor(color);
    }

    public static void setCustomFont(Context context, TextView tvTitle, int fontPath) {
        Typeface typeface = ResourcesCompat.getFont(context, fontPath);
        tvTitle.setTypeface(typeface);
    }

    public static void setWindow(AlertDialog dialog, View dialogLayout) {
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.setView(dialogLayout);
    }

    public static String getParsedDate(String format1, String format2, String date) {
        if (date == null)
            return null;

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
        try {
            return sdf2.format(sdf1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long toTimeStamp(String format, String date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getParsedStamp(String format, long timeStamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(new Date(timeStamp * 1000));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getParsedCurrentDate(String format) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        return sdf.format(new Date());
    }

    public static String getParsedCurrentDate(String format, int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(c.getTime());
    }

    public static String getParsedDateTimeFromApi(String date,String format)
    {
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(date);
            DateFormat targetFormat = new SimpleDateFormat(format);
            String formattedDate = targetFormat.format(date1);
            return formattedDate;
        }catch (Exception e)
        {

        }

        return "";

    }

    public static void enableDisableConfirmButton(Context context, TextView button, boolean enable) {
        button.setEnabled(enable);
        ImageUtils.setRoundedBackground(context, ImageUtils.convertToIntArray(getColor(context, R.color.grad_3), getColor(context, R.color.grad_3))
                , ImageUtils.convertToIntArray(getColor(context, R.color.grad_1_trans), getColor(context, R.color.grad_1_trans)), 5, button);

        if (enable) {
            button.setTextColor(getColor(context, R.color.white));
        } else {
            button.setTextColor(getColor(context, R.color.white_transparent_overlay));
        }
    }

    public static void openMaps(Context context, String latitude, String longitude, String name) {
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude +
                "?q=" + latitude + "," + longitude + "(" + name + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }

    public static void openRoutesMaps(Context context, String name) {
        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + name);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }

    public static void openSourceDestinationRouteMaps(Context context, LatLng sLocation, LatLng dLocation) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+sLocation.latitude+","+sLocation.longitude+"&daddr="+dLocation.latitude+","+dLocation.longitude));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }

      /*  Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + name);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }

       */
    }

    public static File createDirectory(Context mContext) {
        String destPath = mContext.getExternalFilesDir(null).getAbsolutePath();
        File dir = new File(destPath, "temp");
        if (!dir.exists())
            dir.mkdir();
        return dir;
    }

    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.getTimeInMillis());
    }

    public static RequestBody getRequestBody(String body) {
        return RequestBody.create(body, MediaType.parse("text/plain"));
    }
}
