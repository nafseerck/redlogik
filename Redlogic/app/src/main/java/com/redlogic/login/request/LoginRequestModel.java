package com.redlogic.login.request;

public class LoginRequestModel {
    /**
     * device_id : aaaduyadjg
     * username : arjun
     * password : 123456
     * language : EN
     */

    private String device_id;
    private String username;
    private String password;
    private String language;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
