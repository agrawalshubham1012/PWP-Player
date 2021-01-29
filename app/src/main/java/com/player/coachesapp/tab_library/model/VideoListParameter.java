
package com.player.coachesapp.tab_library.model;

import com.google.gson.annotations.SerializedName;


public class VideoListParameter {

    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("sub_category_id")
    private String subCategoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

}
