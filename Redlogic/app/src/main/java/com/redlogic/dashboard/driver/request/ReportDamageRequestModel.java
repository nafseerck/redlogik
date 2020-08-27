package com.redlogic.dashboard.driver.request;

import java.util.List;

public class ReportDamageRequestModel {
    /**
     * job_id : 2
     * damage_desc : reason
     * attachement : [{"image_base64":"dkjabfukagskdsflsiflsiudyfsyflsfsudi"},{"image_base64":"dkjabfukagskdsflsiflsiudyfsyflsfsudi"},{"image_base64":"dkjabfukagskdsflsiflsiudyfsyflsfsudi"}]
     */

    private String job_id;
    private String damage_desc;
    private List<AttachementBean> attachement;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getDamage_desc() {
        return damage_desc;
    }

    public void setDamage_desc(String damage_desc) {
        this.damage_desc = damage_desc;
    }

    public List<AttachementBean> getAttachement() {
        return attachement;
    }

    public void setAttachement(List<AttachementBean> attachement) {
        this.attachement = attachement;
    }

    public static class AttachementBean {
        /**
         * image_base64 : dkjabfukagskdsflsiflsiudyfsyflsfsudi
         */

        private String image_base64;

        public String getImage_base64() {
            return image_base64;
        }

        public void setImage_base64(String image_base64) {
            this.image_base64 = image_base64;
        }
    }
}
