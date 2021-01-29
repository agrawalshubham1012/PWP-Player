package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoachInput implements Serializable {

    @SerializedName("coach_type")
    private String coach_type;

    public String getCoach_type() {
        return coach_type;
    }

    public void setCoach_type(String coach_type) {
        this.coach_type = coach_type;
    }

    @Override
    public String toString() {
        return "CoachInput{" +
                "coach_type='" + coach_type + '\'' +
                '}';
    }
}
