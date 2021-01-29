package com.player.coachesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationParameter {

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("date_of_birth")
    @Expose
    private String date_of_birth;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("facebook_id")
    @Expose
    private String facebook_id;

    @SerializedName("google_id")
    @Expose
    private String google_id;

    @SerializedName("auth_type")
    @Expose
    private String auth_type;

    @SerializedName("device_type")
    @Expose
    private String device_type;

    @SerializedName("fcm_token")
    @Expose
    private String fcm_token;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    @Override
    public String toString() {
        return "RegistrationParameter{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", facebook_id='" + facebook_id + '\'' +
                ", google_id='" + google_id + '\'' +
                ", auth_type='" + auth_type + '\'' +
                ", device_type='" + device_type + '\'' +
                ", fcm_token='" + fcm_token + '\'' +
                '}';
    }
}
