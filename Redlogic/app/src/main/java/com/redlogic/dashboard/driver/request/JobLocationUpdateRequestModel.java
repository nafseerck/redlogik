package com.redlogic.dashboard.driver.request;

public class JobLocationUpdateRequestModel {
    /**
     * job_id : 2
     */

    private String job_id;
    private String latitude;
    private String longitude;


    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
