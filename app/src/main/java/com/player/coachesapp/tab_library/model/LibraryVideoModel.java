
package com.player.coachesapp.tab_library.model;

import com.google.gson.annotations.SerializedName;

public class LibraryVideoModel {

    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("is_favorite")
    private String isFavorite;
    @SerializedName("sub_cat_id")
    private String subCatId;
    @SerializedName("vi_created_at")
    private String viCreatedAt;
    @SerializedName("vi_file_name")
    private String viFileName;
    @SerializedName("vi_is_paid")
    private String viIsPaid;
    @SerializedName("vi_price")
    private String viPrice;
    @SerializedName("vi_title")
    private String viTitle;
    @SerializedName("vi_updated_at")
    private String viUpdatedAt;
    @SerializedName("video_id")
    private String videoId;
    @SerializedName("vi_file_image")
    private String viFileImage ;

    public String getViFileImage() {
        return viFileImage;
    }

    public void setViFileImage(String viFileImage) {
        this.viFileImage = viFileImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getViCreatedAt() {
        return viCreatedAt;
    }

    public void setViCreatedAt(String viCreatedAt) {
        this.viCreatedAt = viCreatedAt;
    }

    public String getViFileName() {
        return viFileName;
    }

    public void setViFileName(String viFileName) {
        this.viFileName = viFileName;
    }

    public String getViIsPaid() {
        return viIsPaid;
    }

    public void setViIsPaid(String viIsPaid) {
        this.viIsPaid = viIsPaid;
    }

    public String getViPrice() {
        return viPrice;
    }

    public void setViPrice(String viPrice) {
        this.viPrice = viPrice;
    }

    public String getViTitle() {
        return viTitle;
    }

    public void setViTitle(String viTitle) {
        this.viTitle = viTitle;
    }

    public String getViUpdatedAt() {
        return viUpdatedAt;
    }

    public void setViUpdatedAt(String viUpdatedAt) {
        this.viUpdatedAt = viUpdatedAt;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
