package com.redlogic.dashboard.customer.response;

public class LiveMapResponse {

    /**
     * status : true
     * message : Live map details
     * data : {"start_lat":"-74.044636","start_long":"+40.689060","end_lat":"-74.044636","end_long":"+40.689060","current_lat":"-74.044636","current_long":"+40.689060"}
     */

    private boolean status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * start_lat : -74.044636
         * start_long : +40.689060
         * end_lat : -74.044636
         * end_long : +40.689060
         * current_lat : -74.044636
         * current_long : +40.689060
         */

        private String start_lat;
        private String start_long;
        private String end_lat;
        private String end_long;
        private String current_lat;
        private String current_long;

        public String getStart_lat() {
            return start_lat;
        }

        public void setStart_lat(String start_lat) {
            this.start_lat = start_lat;
        }

        public String getStart_long() {
            return start_long;
        }

        public void setStart_long(String start_long) {
            this.start_long = start_long;
        }

        public String getEnd_lat() {
            return end_lat;
        }

        public void setEnd_lat(String end_lat) {
            this.end_lat = end_lat;
        }

        public String getEnd_long() {
            return end_long;
        }

        public void setEnd_long(String end_long) {
            this.end_long = end_long;
        }

        public String getCurrent_lat() {
            return current_lat;
        }

        public void setCurrent_lat(String current_lat) {
            this.current_lat = current_lat;
        }

        public String getCurrent_long() {
            return current_long;
        }

        public void setCurrent_long(String current_long) {
            this.current_long = current_long;
        }
    }
}
