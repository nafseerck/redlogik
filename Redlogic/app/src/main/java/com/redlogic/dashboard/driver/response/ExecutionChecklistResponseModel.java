package com.redlogic.dashboard.driver.response;

import java.util.List;

public class ExecutionChecklistResponseModel {

    /**
     * status : true
     * message : Execution checklist data
     * data : {"job_id":1,"service_master_id":1,"tasks":[{"id":1,"description":"Started from yard","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":1,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":2,"description":"Reported at site","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":2,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":3,"description":"Started loading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":3,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":4,"description":"Loading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":4,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":5,"description":"Started for destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":5,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":6,"description":"Reached destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":6,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":7,"description":"Started unloading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":7,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":8,"description":"Unloading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":8,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":9,"description":"Released vehicle","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":9,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":10,"description":"Started from yard","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":10,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":11,"description":"Reported at site","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":11,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":12,"description":"Started loading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":12,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":13,"description":"Loading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":13,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":14,"description":"Started for destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":14,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":15,"description":"Reached destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":15,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":16,"description":"Started unloading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":16,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":17,"description":"Unloading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":17,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":18,"description":"Released vehicle","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":18,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]}]}
     */

    private boolean status;
    private String message;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * job_id : 1
         * service_master_id : 1
         * tasks : [{"id":1,"description":"Started from yard","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":1,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":2,"description":"Reported at site","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":2,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":3,"description":"Started loading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":3,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":4,"description":"Loading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":4,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":5,"description":"Started for destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":5,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":6,"description":"Reached destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":6,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":7,"description":"Started unloading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":7,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":8,"description":"Unloading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":8,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":9,"description":"Released vehicle","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":9,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]},{"id":10,"description":"Started from yard","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":10,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":11,"description":"Reported at site","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":11,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":12,"description":"Started loading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":12,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":13,"description":"Loading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":13,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":14,"description":"Started for destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":14,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":15,"description":"Reached destination","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":15,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":16,"description":"Started unloading","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":16,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":17,"description":"Unloading finished","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":17,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]},{"id":18,"description":"Released vehicle","is_active":1,"is_completed":0,"completed_on":null,"data":[{"id":18,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:46","updated_by":""}]}]
         */

        private int job_id;
        private int service_master_id;
        private List<TasksBean> tasks;

        public int getJob_id() {
            return job_id;
        }

        public void setJob_id(int job_id) {
            this.job_id = job_id;
        }

        public int getService_master_id() {
            return service_master_id;
        }

        public void setService_master_id(int service_master_id) {
            this.service_master_id = service_master_id;
        }

        public List<TasksBean> getTasks() {
            return tasks;
        }

        public void setTasks(List<TasksBean> tasks) {
            this.tasks = tasks;
        }

        public static class TasksBean {
            /**
             * id : 1
             * description : Started from yard
             * is_active : 1
             * is_completed : 0
             * completed_on : null
             * data : [{"id":1,"field_type_id":3,"label":"Completed","value":null,"items":"[]","updated_on":"2020-08-26 10:44:44","updated_by":""}]
             */

            private int id;
            private String description;
            private int is_active;
            private boolean is_completed;
            private String completed_on;
            private List<DataBean> data;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getIs_active() {
                return is_active;
            }

            public void setIs_active(int is_active) {
                this.is_active = is_active;
            }

            public boolean getIs_completed() {
                return is_completed;
            }

            public void setIs_completed(boolean is_completed) {
                this.is_completed = is_completed;
            }

            public String getCompleted_on() {
                return completed_on;
            }

            public void setCompleted_on(String completed_on) {
                this.completed_on = completed_on;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * id : 1
                 * field_type_id : 3
                 * label : Completed
                 * value : null
                 * items : []
                 * updated_on : 2020-08-26 10:44:44
                 * updated_by :
                 */

                private int id;
                private int field_type_id;
                private String label;
                private String value;
                private String items;
                private String updated_on;
                private String updated_by;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getField_type_id() {
                    return field_type_id;
                }

                public void setField_type_id(int field_type_id) {
                    this.field_type_id = field_type_id;
                }

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getItems() {
                    return items;
                }

                public void setItems(String items) {
                    this.items = items;
                }

                public String getUpdated_on() {
                    return updated_on;
                }

                public void setUpdated_on(String updated_on) {
                    this.updated_on = updated_on;
                }

                public String getUpdated_by() {
                    return updated_by;
                }

                public void setUpdated_by(String updated_by) {
                    this.updated_by = updated_by;
                }
            }
        }
    }
}
