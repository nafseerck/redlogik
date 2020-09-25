package com.redlogic.dashboard.driver.response;

import java.util.List;

public class GatePassListResponseModel {
    /**
     * status : true
     * message : Notification List
     * data : [{"id":"1","notification_title":"notification 1","timestamp":"1587279575"},{"id":"2","notification_title":"notification 2","timestamp":"1587279575"},{"id":"3","notification_title":"notification 3","timestamp":"1587279575"}]
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

        List<GatePasses> gate_passes;

        public List<GatePasses> getGate_passes() {
            return gate_passes;
        }

        public void setGate_passes(List<GatePasses> gate_passes) {
            this.gate_passes = gate_passes;
        }

        public static class GatePasses{
            /**
             * id : 1
             * notification_title : notification 1
             * timestamp : 1587279575
             */

            private int id;
            private String name;
            private List<String> download_url;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getDownload_url() {
                return download_url;
            }

            public void setDownload_url(List<String> download_url) {
                this.download_url = download_url;
            }
        }
    }
}
