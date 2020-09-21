package com.redlogic.dashboard.customer.response;

import java.util.List;

public class SchedulesCustomerResponseModel {
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
         * "execution_id": 1,
         *             "service_id": 1,
         *             "job_details": {
         *                 "from_location": "Ras Laffan - RLIC QP",
         *                 "to_location": "Doha International Airport",
         *                 "equipment_type": "20 ft Flatbed Trailer",
         *                 "scheduled_at": "2020-09-04T14:20:00+00:00"
         *             },
         *             "timestamp": 1599229200
         */

        private String timestamp;
        private JobDetailsBean job_details;
        private int execution_id;
        private int service_id;

        public JobDetailsBean getJob_details() {
            return job_details;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public void setJob_details(JobDetailsBean job_details) {
            this.job_details = job_details;
        }

        public int getExecution_id() {
            return execution_id;
        }

        public void setExecution_id(int execution_id) {
            this.execution_id = execution_id;
        }

        public int getService_id() {
            return service_id;
        }

        public void setService_id(int service_id) {
            this.service_id = service_id;
        }

        public static class JobDetailsBean {
            /**
             * from_location : Abu Dhalouf
             * to_location : Abu Nakhla
             * equipment_type : 20 ft Flatbed Trailer
             * scheduled_at : 2020-08-14T17:38:00+00:00
             */

            private String from_location;
            private String to_location;
            private String equipment_type;
            private String scheduled_at;

            public String getFrom_location() {
                return from_location;
            }

            public void setFrom_location(String from_location) {
                this.from_location = from_location;
            }

            public String getTo_location() {
                return to_location;
            }

            public void setTo_location(String to_location) {
                this.to_location = to_location;
            }

            public String getEquipment_type() {
                return equipment_type;
            }

            public void setEquipment_type(String equipment_type) {
                this.equipment_type = equipment_type;
            }

            public String getScheduled_at() {
                return scheduled_at;
            }

            public void setScheduled_at(String scheduled_at) {
                this.scheduled_at = scheduled_at;
            }
        }
    }
}
