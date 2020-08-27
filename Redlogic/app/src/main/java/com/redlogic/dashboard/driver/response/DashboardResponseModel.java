package com.redlogic.dashboard.driver.response;

public class DashboardResponseModel {
    /**
     * status : true
     * message : dashboard data
     * data : {"userid":"2","name":"Rajeev Bhatia","role":"Driver","vehicle_type":"53 feet composite dry van trailer","reg_no":"QA20304899","new_work_count":"24","in_progress_count":"18","completed_count":"27","rejected":"7"}
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
         * userid : 2
         * name : Rajeev Bhatia
         * role : Driver
         * vehicle_type : 53 feet composite dry van trailer
         * reg_no : QA20304899
         * new_work_count : 24
         * in_progress_count : 18
         * completed_count : 27
         * rejected : 7
         */

        private String userid;
        private String name;
        private String role;
        private String vehicle_type;
        private String reg_no;
        private String new_work_count;
        private String in_progress_count;
        private String completed_count;
        private String rejected;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getVehicle_type() {
            return vehicle_type;
        }

        public void setVehicle_type(String vehicle_type) {
            this.vehicle_type = vehicle_type;
        }

        public String getReg_no() {
            return reg_no;
        }

        public void setReg_no(String reg_no) {
            this.reg_no = reg_no;
        }

        public String getNew_work_count() {
            return new_work_count;
        }

        public void setNew_work_count(String new_work_count) {
            this.new_work_count = new_work_count;
        }

        public String getIn_progress_count() {
            return in_progress_count;
        }

        public void setIn_progress_count(String in_progress_count) {
            this.in_progress_count = in_progress_count;
        }

        public String getCompleted_count() {
            return completed_count;
        }

        public void setCompleted_count(String completed_count) {
            this.completed_count = completed_count;
        }

        public String getRejected() {
            return rejected;
        }

        public void setRejected(String rejected) {
            this.rejected = rejected;
        }
    }
}
