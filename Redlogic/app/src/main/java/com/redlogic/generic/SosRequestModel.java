package com.redlogic.generic;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class SosRequestModel {
    /**
     * job_id : 2
     */

    private String job_id;
    private String location_details;
    private String longt;
    private String lat;


    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getLocation_details() {
        return location_details;
    }

    public void setLocation_details(String location_details) {
        this.location_details = location_details;
    }

    public String getLong_() {
        return longt;
    }

    public void setLong_(String longt) {
        this.longt = longt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }



}
