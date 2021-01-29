package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoInput implements Serializable {


    @SerializedName("video_id")
    private String video_id ;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    @Override
    public String toString() {
        return "VideoInput{" +
                "video_id='" + video_id + '\'' +
                '}';
    }
}
