package com.redlogic.utils.api.utils;

public class Constants {

    public interface TimeOut {
        int IMAGE_UPLOAD_CONNECTION_TIMEOUT = 120;
        int IMAGE_UPLOAD_SOCKET_TIMEOUT = 120;
        int SOCKET_TIME_OUT = 60;
        int CONNECTION_TIME_OUT = 60;
    }

    public interface ErrorClass {
        String CODE = "code";
        String STATUS = "status";
    }
}
