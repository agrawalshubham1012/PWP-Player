package com.player.coachesapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.player.coachesapp.R;
import com.player.coachesapp.SplashActivity;
import com.player.coachesapp.Utils.Constant;


/**
 * Created by Ritu Patidar  on 03/24/2020.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Context ct = this;


    @Override
    public void onNewToken( String s) {
        super.onNewToken(s);


    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        String reference_id ="", type ="";
        try {


            // String message = remoteMessage.getData().get("message");
            String message = remoteMessage.getData().get("message");
            String title = remoteMessage.getData().get("title");

            reference_id = remoteMessage.getData().get("reference_id");
            type = remoteMessage.getData().get("type");
           sendNotification(title, message, reference_id, type);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification( String title , String messageBody,String reference_id, String type) {
        try {





            Intent intent = null;
            intent = new Intent(this, SplashActivity.class)
            .putExtra(Constant.REFERENCE_ID, reference_id)
            .putExtra(Constant.NOTIFY_TYPE,type);


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);




            String CHANNEL_ID = "driver_channel_01";

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this);


            Notification notification = mBuilder.setSmallIcon(getNotificationIcon(mBuilder)).setTicker("LickR").setWhen(0)
                    .setAutoCancel(true)
                    .setSmallIcon(getNotificationIcon(mBuilder))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setChannelId(CHANNEL_ID)
                    .setContentText(messageBody).build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int time = (int) System.currentTimeMillis();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // The id of the channel.
                CharSequence name = "OrangeCart_Driver";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(time, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return R.drawable.logo;

        }
        return R.drawable.logo;
    }


}