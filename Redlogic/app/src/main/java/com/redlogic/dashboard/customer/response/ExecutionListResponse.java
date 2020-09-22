package com.redlogic.dashboard.customer.response;

import java.util.List;

public class ExecutionListResponse {


    /**
     * status : true
     * message : Execution listing
     * data : [{"execution_id":1,"operator_name":"John Eric","reg_number":"QT6666","rl_no":"1","ref_no":"PO9988","status":"In progress","asset_details":{"type":"Vehicle","name":"20 ft Flatbed Trailer"}}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;
    private List<String> result;
    private String info;

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
         * execution_id : 1
         * operator_name : John Eric
         * reg_number : QT6666
         * rl_no : 1
         * ref_no : PO9988
         * status : In progress
         * asset_details : {"type":"Vehicle","name":"20 ft Flatbed Trailer"}
         */

        private String execution_id;
        private String operator_name;
        private String reg_number;
        private String rl_no;
        private String ref_no;
        private String status;
        private AssetDetailsBean asset_details;

        public String getExecution_id() {
            return execution_id;
        }

        public void setExecution_id(String execution_id) {
            this.execution_id = execution_id;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }

        public String getReg_number() {
            return reg_number;
        }

        public void setReg_number(String reg_number) {
            this.reg_number = reg_number;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public AssetDetailsBean getAsset_details() {
            return asset_details;
        }

        public void setAsset_details(AssetDetailsBean asset_details) {
            this.asset_details = asset_details;
        }

        public static class AssetDetailsBean {
            /**
             * type : Vehicle
             * name : 20 ft Flatbed Trailer
             */

            private String type;
            private String name;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
