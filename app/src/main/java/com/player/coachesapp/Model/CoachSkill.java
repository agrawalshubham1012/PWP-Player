
package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoachSkill implements Serializable {

    @SerializedName("coach_id")
    private String coachId;
    @SerializedName("sk_title")
    private String skTitle;
    @SerializedName("skill_id")
    private String skillId;

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getSkTitle() {
        return skTitle;
    }

    public void setSkTitle(String skTitle) {
        this.skTitle = skTitle;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

}
