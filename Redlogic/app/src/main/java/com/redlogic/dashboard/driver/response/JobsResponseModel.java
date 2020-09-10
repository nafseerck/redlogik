package com.redlogic.dashboard.driver.response;

import java.util.List;

public class JobsResponseModel {
    /**
     * status : true
     * message : jobs data
     * data : [{"job_id":"2","from_location":"Doha","to_location":"Al-Wakrah","timestamp":"1587279575","end_lat":"8.540301","end_long":"76.922104","assigned_by":"ABC Logistics","material":"Rubber","quantity":"10","unit":"CT","assignee_phone":"7979797979","customer":"GAC Qatar","customer_phone":"7979797979","customer_address":"jhsbfsddf jsddfhg"},{"job_id":"3","from_location":"Doha","to_location":"Al-Wakrah","timestamp":"1587279575","end_lat":"8.540301","end_long":"76.922104","assigned_by":"ABC Logistics","material":"Rubber","quantity":"10","unit":"CT","assignee_phone":"7979797979","customer":"GAC Qatar","customer_phone":"7979797979","customer_address":"jhsbfsddf jsddfhg"}]
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
         * job_id : 2
         * from_location : Doha
         * to_location : Al-Wakrah
         * timestamp : 1587279575
         * end_lat : 8.540301
         * end_long : 76.922104
         * assigned_by : ABC Logistics
         * material : Rubber
         * quantity : 10
         * unit : CT
         * assignee_phone : 7979797979
         * customer : GAC Qatar
         * customer_phone : 7979797979
         * customer_address : jhsbfsddf jsddfhg
         */

        private String job_id;
        private String from_location;
        private String to_location;
        private long timestamp;
        private String end_lat;
        private String end_long;
        private String assigned_by;
        private List<CargoDetails> cargo_details;
        private String material;
        private String quantity;
        private String unit;
        private String assignee_phone;
        private String customer;
        private String customer_phone;
        private String customer_address;

        public List<CargoDetails> getCargo_details() {
            return cargo_details;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

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

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
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

        public String getAssigned_by() {
            return assigned_by;
        }

        public void setAssigned_by(String assigned_by) {
            this.assigned_by = assigned_by;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getAssignee_phone() {
            return assignee_phone;
        }

        public void setAssignee_phone(String assignee_phone) {
            this.assignee_phone = assignee_phone;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getCustomer_phone() {
            return customer_phone;
        }

        public void setCustomer_phone(String customer_phone) {
            this.customer_phone = customer_phone;
        }

        public String getCustomer_address() {
            return customer_address;
        }

        public void setCustomer_address(String customer_address) {
            this.customer_address = customer_address;
        }

        private class CargoDetails {
            String material;
            String quantity;
            String weight;
            String unit;
            String dimension;

            public String getMaterial() {
                return material;
            }

            public String getQuantity() {
                return quantity;
            }

            public String getWeight() {
                return weight;
            }

            public String getUnit() {
                return unit;
            }

            public String getDimension() {
                return dimension;
            }
        }
    }
}
