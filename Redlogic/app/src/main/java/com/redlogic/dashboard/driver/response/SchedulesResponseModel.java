package com.redlogic.dashboard.driver.response;

import java.util.List;

public class SchedulesResponseModel {
    /**
     * status : true
     * message : Schedule List
     * data : [{"job_id":"1","fromlocation":"Doha","tolocation":"Al-Wakrah","timestamp":"1587279575"},{"job_id":"2","fromlocation":"Doha","tolocation":"Al-Wakrah","timestamp":"1587279575"},{"job_id":"3","fromlocation":"Doha","tolocation":"Al-Wakrah","timestamp":"1587279575"}]
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
         * job_id : 1
         * fromlocation : Doha
         * tolocation : Al-Wakrah
         * timestamp : 1587279575
         */

        private String job_id;
        private String fromlocation;
        private String tolocation;
        private long timestamp;

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getFromlocation() {
            return fromlocation;
        }

        public void setFromlocation(String fromlocation) {
            this.fromlocation = fromlocation;
        }

        public String getTolocation() {
            return tolocation;
        }

        public void setTolocation(String tolocation) {
            this.tolocation = tolocation;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
