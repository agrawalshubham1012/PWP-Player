package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterInputModel implements Serializable {

    @SerializedName("pl_google_id")
    private String pl_google_id;


    public String getPl_google_id() {
        return pl_google_id;
    }

    public void setPl_google_id(String pl_google_id) {
        this.pl_google_id = pl_google_id;
    }

    @Override
    public String toString() {
        return "RegisterInputModel{" +
                "pl_google_id='" + pl_google_id + '\'' +
                '}';
    }
}
