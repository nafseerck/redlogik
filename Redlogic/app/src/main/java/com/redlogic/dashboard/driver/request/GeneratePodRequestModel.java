package com.redlogic.dashboard.driver.request;

public class GeneratePodRequestModel {
    /**
     * job_id : 2
     * document_base64 : sfsfgegegggdgdg
     */

    private String job_id;
    private String document_base64;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getDocument_base64() {
        return document_base64;
    }

    public void setDocument_base64(String document_base64) {
        this.document_base64 = document_base64;
    }
}
