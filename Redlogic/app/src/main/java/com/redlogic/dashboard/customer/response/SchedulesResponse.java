package com.redlogic.dashboard.customer.response;

import java.util.List;

public class SchedulesResponse {

    /**
     * status : true
     * message : Schedule List
     * data : [{"execution_id":"1","execution_details":"Doha - Dukan\n40 ft Trailer","timestamp":"1587279575"},{"job_id":"2","execution_details":"Doha \n20T forkliftr","timestamp":"1587279575"},{"job_id":"3","execution_details":"Doha - Dukan\n40 ft Trailer","timestamp":"1587279575"}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * execution_id : 1
         * execution_details : Doha - Dukan
         40 ft Trailer
         * timestamp : 1587279575
         * job_id : 2
         */

        private String execution_id;
        private String execution_details;
        private String timestamp;
        private String job_id;

        public String getExecution_id() {
            return execution_id;
        }

        public void setExecution_id(String execution_id) {
            this.execution_id = execution_id;
        }

        public String getExecution_details() {
            return execution_details;
        }

        public void setExecution_details(String execution_details) {
            this.execution_details = execution_details;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }
    }
}
