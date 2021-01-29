package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetVideo implements Serializable {


    @SerializedName("category_id")
    private String category_id ;

    @SerializedName("cat_title")
    private String cat_title ;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }


}
