package com.redlogic.dashboard.customer.response;

import java.util.List;

public class DashboardResponse {
    /**
     * status : true
     * message : Customer dashboard
     * data : {"customer_details":{"name":"Pradeep","designation":"Manager","profile_pic":null,"workorder_new_count":2,"workorder_new_inprocess":1,"workorder_new_approvals":0,"workorder_new_completed":0,"ongoing_jobs":[]}}
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
         * customer_details : {"name":"Pradeep","designation":"Manager","profile_pic":null,"workorder_new_count":2,"workorder_new_inprocess":1,"workorder_new_approvals":0,"workorder_new_completed":0,"ongoing_jobs":[]}
         */

        private CustomerDetailsBean customer_details;

        public CustomerDetailsBean getCustomer_details() {
            return customer_details;
        }

        public void setCustomer_details(CustomerDetailsBean customer_details) {
            this.customer_details = customer_details;
        }

        public static class CustomerDetailsBean {
            /**
             * name : Pradeep
             * designation : Manager
             * profile_pic : null
             * workorder_new_count : 2
             * workorder_new_inprocess : 1
             * workorder_new_approvals : 0
             * workorder_new_completed : 0
             * ongoing_jobs : []
             */

            private String name;
            private String designation;
            private Object profile_pic;
            private String  workorder_new_count;
            private String workorder_new_inprocess;
            private String workorder_new_approvals;
            private String workorder_new_completed;
            private List<OngoingJobsBean> ongoing_jobs;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesignation() {
                return designation;
            }

            public void setDesignation(String designation) {
                this.designation = designation;
            }

            public Object getProfile_pic() {
                return profile_pic;
            }

            public void setProfile_pic(Object profile_pic) {
                this.profile_pic = profile_pic;
            }

            public String getWorkorder_new_count() {
                return workorder_new_count;
            }

            public void setWorkorder_new_count(String workorder_new_count) {
                this.workorder_new_count = workorder_new_count;
            }

            public String getWorkorder_new_inprocess() {
                return workorder_new_inprocess;
            }

            public void setWorkorder_new_inprocess(String workorder_new_inprocess) {
                this.workorder_new_inprocess = workorder_new_inprocess;
            }

            public String getWorkorder_new_approvals() {
                return workorder_new_approvals;
            }

            public void setWorkorder_new_approvals(String workorder_new_approvals) {
                this.workorder_new_approvals = workorder_new_approvals;
            }

            public String getWorkorder_new_completed() {
                return workorder_new_completed;
            }

            public void setWorkorder_new_completed(String workorder_new_completed) {
                this.workorder_new_completed = workorder_new_completed;
            }

            public List<OngoingJobsBean> getOngoing_jobs() {
                return ongoing_jobs;
            }

            public void setOngoing_jobs(List<OngoingJobsBean> ongoing_jobs) {
                this.ongoing_jobs = ongoing_jobs;
            }
            public static class OngoingJobsBean {
                /**
                 * "job_id":3,
                 *                "service_id":1,
                 *                "job_details":{
                 *                   "from_location":"Al Ghuwariyah",
                 *                   "to_location":"Hamad seaport",
                 *                   "equipment_type":"20 ft Flatbed Trailer",
                 *                   "scheduled_at":"2020-09-04T14:38:00+00:00"
                 *                }
                 */

                private int job_id;
                private JobDetailsBean job_details;

                public int getJob_id() {
                    return job_id;
                }

                public void setJob_id(int job_id) {
                    this.job_id = job_id;
                }

                public JobDetailsBean getJob_details() {
                    return job_details;
                }

                public void setJob_details(String JobDetailsBean) {
                    this.job_details = job_details;
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

    }


}

