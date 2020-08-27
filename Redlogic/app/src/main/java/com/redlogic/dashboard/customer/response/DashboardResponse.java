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
                 * job_id : 1
                 * job_details : 40 ft Trailer
                 Doha - Dukan
                 * scheduled_at : 01 January 2020, 5 PM
                 */

                private int job_id;
                private String job_details;
                private String scheduled_at;

                public int getJob_id() {
                    return job_id;
                }

                public void setJob_id(int job_id) {
                    this.job_id = job_id;
                }

                public String getJob_details() {
                    return job_details;
                }

                public void setJob_details(String job_details) {
                    this.job_details = job_details;
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

