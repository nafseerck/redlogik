package com.redlogic.dashboard.customer.response;

import java.util.List;

public class WorkOrderDetailsResponse {

    /**
     * status : true
     * message : Work order details
     * data : {"work_order_id":1,"workorder_approval_required":0,"licensee_address":{"name":"Redlogik Logistics Technologies","address_line_1":"Redlogik Logistics Technologies","address_line_2":"Redlogik Logistics Technologies","country":"Redlogik Logistics Technologies","contact_number":"+91 94465 47272"},"customer_address":{"name":"GSAT Tech","address_line_1":"THrissur","address_line_2":"THrissur","country":"Qatar","contact_number":"+91 97467 88517"},"status":"inprogress","rl_no":1,"ref_no":"PO9988","date":"2020-08-14T17:39:57+00:00","jobs":[{"job_id":1,"service_id":1,"job_details":{"from_location":"Abu Dhalouf","to_location":"Abu Nakhla","equipment_type":"20 ft Flatbed Trailer","scheduled_at":"2020-08-14T17:38:00+00:00"},"executions":1,"execution_inprocess":0}]}
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
         * work_order_id : 1
         * workorder_approval_required : 0
         * licensee_address : {"name":"Redlogik Logistics Technologies","address_line_1":"Redlogik Logistics Technologies","address_line_2":"Redlogik Logistics Technologies","country":"Redlogik Logistics Technologies","contact_number":"+91 94465 47272"}
         * customer_address : {"name":"GSAT Tech","address_line_1":"THrissur","address_line_2":"THrissur","country":"Qatar","contact_number":"+91 97467 88517"}
         * status : inprogress
         * rl_no : 1
         * ref_no : PO9988
         * date : 2020-08-14T17:39:57+00:00
         * jobs : [{"job_id":1,"service_id":1,"job_details":{"from_location":"Abu Dhalouf","to_location":"Abu Nakhla","equipment_type":"20 ft Flatbed Trailer","scheduled_at":"2020-08-14T17:38:00+00:00"},"executions":1,"execution_inprocess":0}]
         */

        private int work_order_id;
        private int workorder_approval_required;
        private LicenseeAddressBean licensee_address;
        private CustomerAddressBean customer_address;
        private String status;
        private String rl_no;
        private String ref_no;
        private String date;
        private List<JobsBean> jobs;

        public int getWork_order_id() {
            return work_order_id;
        }

        public void setWork_order_id(int work_order_id) {
            this.work_order_id = work_order_id;
        }

        public int getWorkorder_approval_required() {
            return workorder_approval_required;
        }

        public void setWorkorder_approval_required(int workorder_approval_required) {
            this.workorder_approval_required = workorder_approval_required;
        }

        public LicenseeAddressBean getLicensee_address() {
            return licensee_address;
        }

        public void setLicensee_address(LicenseeAddressBean licensee_address) {
            this.licensee_address = licensee_address;
        }

        public CustomerAddressBean getCustomer_address() {
            return customer_address;
        }

        public void setCustomer_address(CustomerAddressBean customer_address) {
            this.customer_address = customer_address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<JobsBean> getJobs() {
            return jobs;
        }

        public void setJobs(List<JobsBean> jobs) {
            this.jobs = jobs;
        }

        public static class LicenseeAddressBean {
            /**
             * name : Redlogik Logistics Technologies
             * address_line_1 : Redlogik Logistics Technologies
             * address_line_2 : Redlogik Logistics Technologies
             * country : Redlogik Logistics Technologies
             * contact_number : +91 94465 47272
             */

            private String name;
            private String address_line_1;
            private String address_line_2;
            private String country;
            private String contact_number;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress_line_1() {
                return address_line_1;
            }

            public void setAddress_line_1(String address_line_1) {
                this.address_line_1 = address_line_1;
            }

            public String getAddress_line_2() {
                return address_line_2;
            }

            public void setAddress_line_2(String address_line_2) {
                this.address_line_2 = address_line_2;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getContact_number() {
                return contact_number;
            }

            public void setContact_number(String contact_number) {
                this.contact_number = contact_number;
            }
        }

        public static class CustomerAddressBean {
            /**
             * name : GSAT Tech
             * address_line_1 : THrissur
             * address_line_2 : THrissur
             * country : Qatar
             * contact_number : +91 97467 88517
             */

            private String name;
            private String address_line_1;
            private String address_line_2;
            private String country;
            private String contact_number;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress_line_1() {
                return address_line_1;
            }

            public void setAddress_line_1(String address_line_1) {
                this.address_line_1 = address_line_1;
            }

            public String getAddress_line_2() {
                return address_line_2;
            }

            public void setAddress_line_2(String address_line_2) {
                this.address_line_2 = address_line_2;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getContact_number() {
                return contact_number;
            }

            public void setContact_number(String contact_number) {
                this.contact_number = contact_number;
            }
        }

        public static class JobsBean {
            /**
             * job_id : 1
             * service_id : 1
             * job_details : {"from_location":"Abu Dhalouf","to_location":"Abu Nakhla","equipment_type":"20 ft Flatbed Trailer","scheduled_at":"2020-08-14T17:38:00+00:00"}
             * executions : 1
             * execution_inprocess : 0
             */

            private int job_id;
            private int service_id;
            private JobDetailsBean job_details;
            private int executions;
            private int execution_inprocess;

            public int getJob_id() {
                return job_id;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public int getService_id() {
                return service_id;
            }

            public void setService_id(int service_id) {
                this.service_id = service_id;
            }

            public JobDetailsBean getJob_details() {
                return job_details;
            }

            public void setJob_details(JobDetailsBean job_details) {
                this.job_details = job_details;
            }

            public int getExecutions() {
                return executions;
            }

            public void setExecutions(int executions) {
                this.executions = executions;
            }

            public int getExecution_inprocess() {
                return execution_inprocess;
            }

            public void setExecution_inprocess(int execution_inprocess) {
                this.execution_inprocess = execution_inprocess;
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
