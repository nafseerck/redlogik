package com.redlogic.dashboard.driver.response;

import java.util.List;

public class InitialChecklistResponseModel {
    /**
     * status : true
     * message : initial checklist data
     * data : [{"id":"1","title":"Entry Pass","type":"checkbox"},{"id":"2","title":"HSC check","type":"checkbox"},{"id":"3","title":"Additional Lashing","type":"checkbox"},{"id":"4","title":"Upload Document","type":"upload"}]
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
         * id : 1
         * title : Entry Pass
         * type : checkbox
         */

        private String id;
        private String title;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
