package com.redlogic.dashboard.driver.request;

public class AcceptOrRejectRequestModel {
    /**
     * job_id : 2
     * status : accept
     * description :
     */

    private String job_id;
    private String status;
    private String description;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
