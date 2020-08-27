package com.redlogic.dashboard.driver.response;

public class GeneratePodResponseModel {
    /**
     * status : true
     * message : POD Generated
     * download_url : http://pdfdownload url
     */

    private boolean status;
    private String message;
    private String download_url;

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

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
