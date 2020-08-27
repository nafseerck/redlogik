package com.redlogic.utils.api.models;

import java.io.Serializable;

public class ErrorObjectFalse implements Serializable {


    /**
     * Success : false
     * Message : Mobile number already exists
     */

    private boolean Success;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

}