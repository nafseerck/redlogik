package com.redlogic.utils.api.models;

public class ErrorObject {

    /**
     * login_status : false
     * status : false
     * message : Access token not matching with official id.Please com.redlogic.login.
     * data : []
     */

    private boolean login_status;
    private boolean status;
    private String message;

    public boolean isLogin_status() {
        return login_status;
    }

    public void setLogin_status(boolean login_status) {
        this.login_status = login_status;
    }

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

}