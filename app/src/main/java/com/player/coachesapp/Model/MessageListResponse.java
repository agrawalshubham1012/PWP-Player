
package com.player.coachesapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;

public class MessageListResponse implements Serializable {

    @Expose
    private Long code;
    @Expose
    private ArrayList<CoachDetailData> data;
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

    public ArrayList<CoachDetailData> getData() {
        return data;
    }

    public void setData(ArrayList<CoachDetailData> data) {
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
