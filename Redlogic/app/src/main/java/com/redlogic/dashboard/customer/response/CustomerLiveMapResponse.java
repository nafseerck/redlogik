package com.redlogic.dashboard.customer.response;

public class CustomerLiveMapResponse {

    /**
     * {
     *     "status": true,
     *     "message": "Notification List",
     *     "data": {
     *         "start_lat": "25.90316",
     *         "start_long": "51.522031",
     *         "end_lat": "25.273056",
     *         "end_long": "51.608056",
     *         "current_lat": "0.00",
     *         "current_long": "0.00"
     *     }
     * }
     */
    private Data data;
    private String message;
    private boolean status;

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean getStatus() {
        return status;
    }

    public static class Data {
        /**
         * "data": {
         *         "start_lat": "25.90316",
         *         "start_long": "51.522031",
         *         "end_lat": "25.273056",
         *         "end_long": "51.608056",
         *         "current_lat": "0.00",
         *         "current_long": "0.00"
         *     }
         */

        private String current_long;
        private String current_lat;
        private String end_long;
        private String end_lat;
        private String start_long;
        private String start_lat;

        public String getCurrent_long() {
            return current_long;
        }

        public String getCurrent_lat() {
            return current_lat;
        }

        public String getEnd_long() {
            return end_long;
        }

        public String getEnd_lat() {
            return end_lat;
        }

        public String getStart_long() {
            return start_long;
        }

        public String getStart_lat() {
            return start_lat;
        }
    }
}
