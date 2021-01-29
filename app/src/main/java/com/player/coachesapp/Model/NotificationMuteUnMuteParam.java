
package com.player.coachesapp.Model;

import com.google.gson.annotations.Expose;

public class NotificationMuteUnMuteParam {

    @Expose
    private String notification;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}
