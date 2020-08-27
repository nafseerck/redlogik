package com.redlogic.dashboard.customer.response;

import java.util.List;

public class NotificationsResponse {

    /**
     * status : true
     * message : Notification List
     * data : [{"id":"1","notification_title":"notification 1","timestamp":"1587279575"},{"id":"2","notification_title":"notification 2","timestamp":"1587279575"},{"id":"3","notification_title":"notification 3","timestamp":"1587279575"}]
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
         * notification_title : notification 1
         * timestamp : 1587279575
         */

        private String id;
        private String notification_title;
        private String timestamp;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNotification_title() {
            return notification_title;
        }

        public void setNotification_title(String notification_title) {
            this.notification_title = notification_title;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
