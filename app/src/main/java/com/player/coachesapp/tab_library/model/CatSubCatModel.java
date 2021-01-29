
package com.player.coachesapp.tab_library.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatSubCatModel {

    @SerializedName("cat_image")
    private String catImage;
    @SerializedName("cat_title")
    private String catTitle;
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("parent_id")
    private String parentId;
    @Expose
    private String media;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
