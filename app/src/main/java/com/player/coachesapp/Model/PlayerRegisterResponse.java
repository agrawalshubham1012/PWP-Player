package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayerRegisterResponse implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String error;

    @SerializedName("data")
    private PlayerProfileModel data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public PlayerProfileModel getData() {
        return data;
    }

    public void setData(PlayerProfileModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PlayerRegisterResponse{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
