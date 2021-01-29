
package com.player.coachesapp.tab_library.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class VideoListResponse {
    @Expose
    private String code;
    @Expose
    private ArrayList<LibraryVideoModel> data;
    @Expose
    private Long error;
    @Expose
    private String message;
    @Expose
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<LibraryVideoModel> getData() {
        return data;
    }

    public void setData(ArrayList<LibraryVideoModel> data) {
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
