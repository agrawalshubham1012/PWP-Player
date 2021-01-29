
package com.player.coachesapp.Model;

import com.google.gson.annotations.Expose;


public class ProfileUploadResponse {

    @Expose
    private Long code;
    @Expose
    private ProfilePicData data;
    @Expose
    private Long error;
    @Expose
    private String message;
    @Expose
    private String status;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public ProfilePicData getData() {
        return data;
    }

    public void setData(ProfilePicData data) {
        this.data = data;
    }

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
