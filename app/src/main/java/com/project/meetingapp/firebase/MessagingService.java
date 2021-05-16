package com.project.meetingapp.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.project.meetingapp.R;
import com.project.meetingapp.activities.MainActivity;
import com.project.meetingapp.utilities.Constants;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("jjjjjjjjjjjj", "onNewToken() token: " + token);

        super.onNewToken(token);
    }

    private void sendNotification(RemoteMessage remoteMessage) {


        String REMOTE_MSG_MEETING_TYPE = remoteMessage.getData().get(Constants.REMOTE_MSG_MEETING_TYPE);
        String KEY_FIRST_NAME = remoteMessage.getData().get(Constants.KEY_FIRST_NAME);
        String REMOTE_MSG_INVITER_TOKEN = remoteMessage.getData().get(Constants.REMOTE_MSG_INVITER_TOKEN);
        String REMOTE_MSG_MEETING_ROOM = remoteMessage.getData().get(Constants.REMOTE_MSG_MEETING_ROOM);


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(KEY_FIRST_NAME)
                .setContentText(REMOTE_MSG_MEETING_TYPE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());



    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("recievedmessage","called");

                String type = remoteMessage.getData().get(Constants.REMOTE_MSG_TYPE);

        if (type != null) {
            if (type.equals(Constants.REMOTE_MSG_INVITATION)) {
                sendNotification(remoteMessage);




            }

        }
    }
}
