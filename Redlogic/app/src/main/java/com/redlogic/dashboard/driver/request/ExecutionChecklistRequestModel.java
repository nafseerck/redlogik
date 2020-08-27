package com.redlogic.dashboard.driver.request;

import com.redlogic.dashboard.driver.response.ExecutionChecklistResponseModel;

import java.util.List;

public class ExecutionChecklistRequestModel {


    private List<ExecutionChecklistResponseModel.DataBeanX> job;

    public List<ExecutionChecklistResponseModel.DataBeanX> getJob() {
        return job;
    }

    public void setJob(List<ExecutionChecklistResponseModel.DataBeanX> job) {
        this.job = job;
    }

}
