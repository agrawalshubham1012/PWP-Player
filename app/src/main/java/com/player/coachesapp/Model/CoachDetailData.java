
package com.player.coachesapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoachDetailData implements Serializable {

    @SerializedName("co_access_token")
    private String coAccessToken;
    @SerializedName("co_avatar")
    private String coAvatar;
    @SerializedName("co_banner")
    private String coBanner;
    @SerializedName("co_banner_type")
    private String coBannerType;
    @SerializedName("co_coach_type")
    private String coCoachType;
    @SerializedName("co_coach_type_icon")
    private String coCoachTypeIcon;
    @SerializedName("co_created_at")
    private String coCreatedAt;
    @SerializedName("co_date_of_birth")
    private String coDateOfBirth;
    @SerializedName("co_device_type")
    private Long coDeviceType;
    @SerializedName("co_email")
    private String coEmail;
    @SerializedName("co_facebook_id")
    private String coFacebookId;
    @SerializedName("co_fcm_token")
    private String coFcmToken;
    @SerializedName("co_google_id")
    private String coGoogleId;
    @SerializedName("co_mlb_seasons")
    private String coMlbSeasons;
    @SerializedName("co_mobile")
    private String coMobile;
    @SerializedName("co_moments")
    private String coMoments;
    @SerializedName("co_name")
    private String coName;
    @SerializedName("co_organization")
    private String coOrganization;
    @SerializedName("co_position")
    private List<String> coPosition;
    @SerializedName("co_seasons")
    private Long coSeasons;
    @SerializedName("co_status")
    private String coStatus;
    @SerializedName("co_updated_at")
    private String coUpdatedAt;
    @SerializedName("coach_id")
    private String coachId;
    @SerializedName("is_favorite")
    private String isFavorite;
    @SerializedName("co_about")
    private Object coAbout;
    @Expose
    private ArrayList<CoachSkill> skills;

    public Object getCoAbout() {
        return coAbout;
    }

    public void setCoAbout(Object coAbout) {
        this.coAbout = coAbout;
    }

    public String getCoAccessToken() {
        return coAccessToken;
    }

    public void setCoAccessToken(String coAccessToken) {
        this.coAccessToken = coAccessToken;
    }

    public String getCoAvatar() {
        return coAvatar;
    }

    public void setCoAvatar(String coAvatar) {
        this.coAvatar = coAvatar;
    }

    public String getCoBanner() {
        return coBanner;
    }

    public void setCoBanner(String coBanner) {
        this.coBanner = coBanner;
    }

    public String getCoBannerType() {
        return coBannerType;
    }

    public void setCoBannerType(String coBannerType) {
        this.coBannerType = coBannerType;
    }

    public String getCoCoachType() {
        return coCoachType;
    }

    public void setCoCoachType(String coCoachType) {
        this.coCoachType = coCoachType;
    }

    public String getCoCoachTypeIcon() {
        return coCoachTypeIcon;
    }

    public void setCoCoachTypeIcon(String coCoachTypeIcon) {
        this.coCoachTypeIcon = coCoachTypeIcon;
    }

    public String getCoCreatedAt() {
        return coCreatedAt;
    }

    public void setCoCreatedAt(String coCreatedAt) {
        this.coCreatedAt = coCreatedAt;
    }

    public String getCoDateOfBirth() {
        return coDateOfBirth;
    }

    public void setCoDateOfBirth(String coDateOfBirth) {
        this.coDateOfBirth = coDateOfBirth;
    }

    public Long getCoDeviceType() {
        return coDeviceType;
    }

    public void setCoDeviceType(Long coDeviceType) {
        this.coDeviceType = coDeviceType;
    }

    public String getCoEmail() {
        return coEmail;
    }

    public void setCoEmail(String coEmail) {
        this.coEmail = coEmail;
    }

    public String getCoFacebookId() {
        return coFacebookId;
    }

    public void setCoFacebookId(String coFacebookId) {
        this.coFacebookId = coFacebookId;
    }

    public String getCoFcmToken() {
        return coFcmToken;
    }

    public void setCoFcmToken(String coFcmToken) {
        this.coFcmToken = coFcmToken;
    }

    public String getCoGoogleId() {
        return coGoogleId;
    }

    public void setCoGoogleId(String coGoogleId) {
        this.coGoogleId = coGoogleId;
    }

    public String getCoMlbSeasons() {
        return coMlbSeasons;
    }

    public void setCoMlbSeasons(String coMlbSeasons) {
        this.coMlbSeasons = coMlbSeasons;
    }

    public String getCoMobile() {
        return coMobile;
    }

    public void setCoMobile(String coMobile) {
        this.coMobile = coMobile;
    }

    public String getCoMoments() {
        return coMoments;
    }

    public void setCoMoments(String coMoments) {
        this.coMoments = coMoments;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getCoOrganization() {
        return coOrganization;
    }

    public void setCoOrganization(String coOrganization) {
        this.coOrganization = coOrganization;
    }

    public List<String> getCoPosition() {
        return coPosition;
    }

    public void setCoPosition(List<String> coPosition) {
        this.coPosition = coPosition;
    }

    public Long getCoSeasons() {
        return coSeasons;
    }

    public void setCoSeasons(Long coSeasons) {
        this.coSeasons = coSeasons;
    }

    public String getCoStatus() {
        return coStatus;
    }

    public void setCoStatus(String coStatus) {
        this.coStatus = coStatus;
    }

    public String getCoUpdatedAt() {
        return coUpdatedAt;
    }

    public void setCoUpdatedAt(String coUpdatedAt) {
        this.coUpdatedAt = coUpdatedAt;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public ArrayList<CoachSkill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<CoachSkill> skills) {
        this.skills = skills;
    }

}
