package com.redlogic.dashboard.customer.response;

import java.util.List;

public class WorkOrderListingResponse {

    /**
     * status : true
     * message : Customer work orders
     * data : {"work_orders":[{"work_order_id":1,"rl_no":1,"ref_no":"9087POIU","services":[{"service_name":"Inland Trucking"}],"jobs":1,"jobs_inprocess":0,"executions":2,"execution_inprocess":0}]}
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
        private List<WorkOrdersBean> work_orders;

        public List<WorkOrdersBean> getWork_orders() {
            return work_orders;
        }

        public void setWork_orders(List<WorkOrdersBean> work_orders) {
            this.work_orders = work_orders;
        }

        public static class WorkOrdersBean {
            /**
             * work_order_id : 1
             * rl_no : 1
             * ref_no : 9087POIU
             * services : [{"service_name":"Inland Trucking"}]
             * jobs : 1
             * jobs_inprocess : 0
             * executions : 2
             * execution_inprocess : 0
             */

            private String work_order_id;
            private String rl_no;
            private String ref_no;
            private String jobs;
            private String jobs_inprocess;
            private String executions;
            private String execution_inprocess;
            private List<ServicesBean> services;

            public String getWork_order_id() {
                return work_order_id;
            }

            public void setWork_order_id(String work_order_id) {
                this.work_order_id = work_order_id;
            }

            public String getRl_no() {
                return rl_no;
            }

            public void setRl_no(String rl_no) {
                this.rl_no = rl_no;
            }

            public String getRef_no() {
                return ref_no;
            }

            public void setRef_no(String ref_no) {
                this.ref_no = ref_no;
            }

            public String getJobs() {
                return jobs;
            }

            public void setJobs(String jobs) {
                this.jobs = jobs;
            }

            public String getJobs_inprocess() {
                return jobs_inprocess;
            }

            public void setJobs_inprocess(String jobs_inprocess) {
                this.jobs_inprocess = jobs_inprocess;
            }

            public String getExecutions() {
                return executions;
            }

            public void setExecutions(String executions) {
                this.executions = executions;
            }

            public String getExecution_inprocess() {
                return execution_inprocess;
            }

            public void setExecution_inprocess(String execution_inprocess) {
                this.execution_inprocess = execution_inprocess;
            }

            public List<ServicesBean> getServices() {
                return services;
            }

            public void setServices(List<ServicesBean> services) {
                this.services = services;
            }

            public static class ServicesBean {
                /**
                 * service_name : Inland Trucking
                 */

                private String service_name;

                public String getService_name() {
                    return service_name;
                }

                public void setService_name(String service_name) {
                    this.service_name = service_name;
                }
            }
        }
    }
}
