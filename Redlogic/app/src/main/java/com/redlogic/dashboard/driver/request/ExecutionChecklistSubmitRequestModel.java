package com.redlogic.dashboard.driver.request;

import com.redlogic.dashboard.driver.response.ExecutionChecklistResponseModel;

import java.util.List;

public class ExecutionChecklistSubmitRequestModel {
    private List<ExecutionChecklistResponseModel.DataBeanX.TasksBean.DataBean> data;

    public List<ExecutionChecklistResponseModel.DataBeanX.TasksBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ExecutionChecklistResponseModel.DataBeanX.TasksBean.DataBean> data) {
        this.data = data;
    }

}
