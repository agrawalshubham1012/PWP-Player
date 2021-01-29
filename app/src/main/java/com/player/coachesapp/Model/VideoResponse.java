package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoResponse implements Serializable {

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private int error;

    @SerializedName("data")
    private List<VideoModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<VideoModel> getData() {
        return data;
    }

    public void setData(List<VideoModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VideoResponse{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", error=" + error +
                ", data=" + data +
                '}';
    }
}
