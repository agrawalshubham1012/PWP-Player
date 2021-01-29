package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlayerProfileModel implements Serializable {

    @SerializedName("player_id")
    private String player_id;

    @SerializedName("pl_first_name")
    private String pl_first_name;

    @SerializedName("pl_last_name")
    private String pl_last_name;

    @SerializedName("pl_email")
    private String pl_email;

    @SerializedName("pl_date_of_birth")
    private String pl_date_of_birth;

    @SerializedName("pl_sport")
    private String pl_sport;

    @SerializedName("pl_position")
    private List<String> pl_position;

    @SerializedName("pl_avatar")
    private String pl_avatar;

    @SerializedName("pl_facebook_id")
    private String pl_facebook_id;

    @SerializedName("pl_google_id")
    private String pl_google_id;

    @SerializedName("pl_access_token")
    private String pl_access_token;

    @SerializedName("pl_created_at")
    private String pl_created_at;


    @SerializedName("pl_updated_at")
    private String pl_updated_at;


    @SerializedName("is_new")
    private String is_new;

    @SerializedName("pl_notification")
    private String plNotification;

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getPl_first_name() {
        return pl_first_name;
    }

    public void setPl_first_name(String pl_first_name) {
        this.pl_first_name = pl_first_name;
    }

    public String getPl_last_name() {
        return pl_last_name;
    }

    public void setPl_last_name(String pl_last_name) {
        this.pl_last_name = pl_last_name;
    }

    public String getPl_email() {
        return pl_email;
    }

    public void setPl_email(String pl_email) {
        this.pl_email = pl_email;
    }

    public String getPl_date_of_birth() {
        return pl_date_of_birth;
    }

    public void setPl_date_of_birth(String pl_date_of_birth) {
        this.pl_date_of_birth = pl_date_of_birth;
    }

    public String getPl_sport() {
        return pl_sport;
    }

    public void setPl_sport(String pl_sport) {
        this.pl_sport = pl_sport;
    }

    public List<String> getPl_position() {
        return pl_position;
    }

    public void setPl_position(List<String> pl_position) {
        this.pl_position = pl_position;
    }

    public String getPl_avatar() {
        return pl_avatar;
    }

    public void setPl_avatar(String pl_avatar) {
        this.pl_avatar = pl_avatar;
    }

    public String getPl_facebook_id() {
        return pl_facebook_id;
    }

    public void setPl_facebook_id(String pl_facebook_id) {
        this.pl_facebook_id = pl_facebook_id;
    }

    public String getPl_google_id() {
        return pl_google_id;
    }

    public void setPl_google_id(String pl_google_id) {
        this.pl_google_id = pl_google_id;
    }

    public String getPl_created_at() {
        return pl_created_at;
    }

    public void setPl_created_at(String pl_created_at) {
        this.pl_created_at = pl_created_at;
    }

    public String getPl_updated_at() {
        return pl_updated_at;
    }

    public void setPl_updated_at(String pl_updated_at) {
        this.pl_updated_at = pl_updated_at;
    }


    public String getAccess_token() {
        return pl_access_token;
    }

    public void setAccess_token(String access_token) {
        this.pl_access_token = access_token;
    }


    public String getPl_access_token() {
        return pl_access_token;
    }

    public void setPl_access_token(String pl_access_token) {
        this.pl_access_token = pl_access_token;
    }


    public String getPlNotification() {
        return plNotification;
    }

    public void setPlNotification(String plNotification) {
        this.plNotification = plNotification;
    }

    @Override
    public String toString() {
        return "PlayerProfileModel{" +
                "player_id='" + player_id + '\'' +
                ", pl_first_name='" + pl_first_name + '\'' +
                ", pl_last_name='" + pl_last_name + '\'' +
                ", pl_email='" + pl_email + '\'' +
                ", pl_date_of_birth='" + pl_date_of_birth + '\'' +
                ", pl_sport='" + pl_sport + '\'' +
                ", pl_position=" + pl_position +
                ", pl_avatar='" + pl_avatar + '\'' +
                ", pl_facebook_id='" + pl_facebook_id + '\'' +
                ", pl_google_id='" + pl_google_id + '\'' +
                ", pl_access_token='" + pl_access_token + '\'' +
                ", pl_created_at='" + pl_created_at + '\'' +
                ", pl_updated_at='" + pl_updated_at + '\'' +
                ", is_new='" + is_new + '\'' +
                '}';
    }
}
