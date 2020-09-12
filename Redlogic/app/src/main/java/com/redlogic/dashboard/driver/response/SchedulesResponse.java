package com.redlogic.dashboard.driver.response;

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
         * fromlocation : Doha - Dukan
         * 40 ft Trailer
         * tolocation : Doha - Dukan
         * 40 ft Trailer
         * timestamp : 1587279575
         * job_id : 2
         */

        private String fromlocation;
        private String tolocation;
        private long timestamp;
        private String job_id;

        public String getFromlocation() {
            return fromlocation;
        }

        public String getTolocation() {
            return tolocation;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getJob_id() {
            return job_id;
        }
    }
}
