package com.redlogic.dashboard.customer.request;

public class WorkOrderApprovalRequestModel {

    private String reason;
    private String status;
    private String user_id;
    private String work_order_id;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setWork_order_id(String work_order_id) {
        this.work_order_id = work_order_id;
    }
}
