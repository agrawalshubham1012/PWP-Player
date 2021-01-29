package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FavoriteInput implements Serializable {

    @SerializedName("fc_coach_id")
    private String fc_coach_id;

    public String getFc_coach_id() {
        return fc_coach_id;
    }

    public void setFc_coach_id(String fc_coach_id) {
        this.fc_coach_id = fc_coach_id;
    }

    @Override
    public String toString() {
        return "FavoriteInput{" +
                "fc_coach_id='" + fc_coach_id + '\'' +
                '}';
    }
}
