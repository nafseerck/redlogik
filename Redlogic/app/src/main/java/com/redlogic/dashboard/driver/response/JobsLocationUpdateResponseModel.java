package com.redlogic.dashboard.driver.response;

import java.util.List;

public class JobsLocationUpdateResponseModel {
    /**
     * status : true
     * message : jobs data
     */

    private boolean status;
    private int lat_lon_frequency;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getLat_lon_frequency() {
        return lat_lon_frequency;
    }

    public void setLat_lon_frequency(int lat_lon_frequency) {
        this.lat_lon_frequency = lat_lon_frequency;
    }
}
