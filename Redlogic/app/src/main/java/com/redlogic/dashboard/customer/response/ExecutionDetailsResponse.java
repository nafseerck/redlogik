package com.redlogic.dashboard.customer.response;

import java.util.List;

public class ExecutionDetailsResponse {


    /**
     * status : true
     * message : Execution details
     * data : {"job_details":{"job_id":1,"service_id":1,"job_details":{"from_location":"Abu Dhalouf","to_location":"Abu Nakhla","equipment_type":"20 ft Flatbed Trailer","scheduled_at":1597426680}},"milestone_details":[{"label":"Started from yard","status":1,"completed_on":null},{"label":"Reported at site","status":0,"completed_on":null},{"label":"Started loading","status":0,"completed_on":null},{"label":"Loading finished","status":0,"completed_on":null},{"label":"Started for destination","status":0,"completed_on":null},{"label":"Reached destination","status":0,"completed_on":null},{"label":"Started unloading","status":0,"completed_on":null},{"label":"Unloading finished","status":0,"completed_on":null},{"label":"Released vehicle","status":0,"completed_on":null}],"download_document":{"label":"Download POD","url":"1597551289_5f38b2b9e49d5.pdf"}}
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
         * job_details : {"job_id":1,"service_id":1,"job_details":{"from_location":"Abu Dhalouf","to_location":"Abu Nakhla","equipment_type":"20 ft Flatbed Trailer","scheduled_at":1597426680}}
         * milestone_details : [{"label":"Started from yard","status":1,"completed_on":null},{"label":"Reported at site","status":0,"completed_on":null},{"label":"Started loading","status":0,"completed_on":null},{"label":"Loading finished","status":0,"completed_on":null},{"label":"Started for destination","status":0,"completed_on":null},{"label":"Reached destination","status":0,"completed_on":null},{"label":"Started unloading","status":0,"completed_on":null},{"label":"Unloading finished","status":0,"completed_on":null},{"label":"Released vehicle","status":0,"completed_on":null}]
         * download_document : {"label":"Download POD","url":"1597551289_5f38b2b9e49d5.pdf"}
         */

        private JobDetailsBeanX job_details;
        private DownloadDocumentBean download_document;
        private List<MilestoneDetailsBean> milestone_details;

        public JobDetailsBeanX getJob_details() {
            return job_details;
        }

        public void setJob_details(JobDetailsBeanX job_details) {
            this.job_details = job_details;
        }

        public DownloadDocumentBean getDownload_document() {
            return download_document;
        }

        public void setDownload_document(DownloadDocumentBean download_document) {
            this.download_document = download_document;
        }

        public List<MilestoneDetailsBean> getMilestone_details() {
            return milestone_details;
        }

        public void setMilestone_details(List<MilestoneDetailsBean> milestone_details) {
            this.milestone_details = milestone_details;
        }

        public static class JobDetailsBeanX {
            /**
             * job_id : 1
             * service_id : 1
             * job_details : {"from_location":"Abu Dhalouf","to_location":"Abu Nakhla","equipment_type":"20 ft Flatbed Trailer","scheduled_at":1597426680}
             */

            private int job_id;
            private int service_id;
            private JobDetailsBean job_details;

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

            public static class JobDetailsBean {
                /**
                 * from_location : Abu Dhalouf
                 * to_location : Abu Nakhla
                 * equipment_type : 20 ft Flatbed Trailer
                 * scheduled_at : 1597426680
                 */

                private String from_location;
                private String to_location;
                private String equipment_type;
                private long scheduled_at;

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

                public long getScheduled_at() {
                    return scheduled_at;
                }

                public void setScheduled_at(int scheduled_at) {
                    this.scheduled_at = scheduled_at;
                }
            }
        }

        public static class DownloadDocumentBean {
            /**
             * label : Download POD
             * url : 1597551289_5f38b2b9e49d5.pdf
             */

            private String label;
            private String url;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class MilestoneDetailsBean {
            /**
             * label : Started from yard
             * status : 1
             * completed_on : null
             */

            private String label;
            private int status;
            private String completed_on;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCompleted_on() {
                return completed_on;
            }

            public void setCompleted_on(String completed_on) {
                this.completed_on = completed_on;
            }
        }
    }
}
