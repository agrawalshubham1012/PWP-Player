
package com.player.coachesapp.Model;

import com.google.gson.annotations.SerializedName;

public class SendVideoParameter {

    @SerializedName("coach_id")
    private String coachId;
    @SerializedName("msg_content")
    private String msgContent;
    @SerializedName("msg_file_url")
    private String msgFileUrl;
    @SerializedName("msg_send_by")
    private String msgSendBy;
    @SerializedName("player_id")
    private String playerId;

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

}
