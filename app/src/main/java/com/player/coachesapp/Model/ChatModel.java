
package com.player.coachesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatModel {

    @Expose
    private String avtar;
    @SerializedName("co_avatar")
    private String coAvatar;
    @SerializedName("coach_id")
    private Long coachId;
    @SerializedName("message_id")
    private Long messageId;
    @SerializedName("msg_content")
    private String msgContent;
    @SerializedName("msg_created_at")
    private String msgCreatedAt;
    @SerializedName("msg_file_url")
    private String msgFileUrl;
    @SerializedName("msg_send_by")
    private String msgSendBy;
    @SerializedName("msg_updated_at")
    private String msgUpdatedAt;
    @Expose
    private String name;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("thumbimage")
    private String thumbimage;

    public String getThumbimage() {
        return thumbimage;
    }

    public void setThumbimage(String thumbimage) {
        this.thumbimage = thumbimage;
    }

    public String getAvtar() {
        return avtar;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }

    public String getCoAvatar() {
        return coAvatar;
    }

    public void setCoAvatar(String coAvatar) {
        this.coAvatar = coAvatar;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgCreatedAt() {
        return msgCreatedAt;
    }

    public void setMsgCreatedAt(String msgCreatedAt) {
        this.msgCreatedAt = msgCreatedAt;
    }

    public String getMsgFileUrl() {
        return msgFileUrl;
    }

    public void setMsgFileUrl(String msgFileUrl) {
        this.msgFileUrl = msgFileUrl;
    }

    public String getMsgSendBy() {
        return msgSendBy;
    }

    public void setMsgSendBy(String msgSendBy) {
        this.msgSendBy = msgSendBy;
    }

    public String getMsgUpdatedAt() {
        return msgUpdatedAt;
    }

    public void setMsgUpdatedAt(String msgUpdatedAt) {
        this.msgUpdatedAt = msgUpdatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

}
