package com.example.musicplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainService extends Service {
    private MediaPlayer player;
    private List<Integer> playList;
    private int currentTrack;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if(action == null)
            return START_STICKY;

        if (action.equals(Constants.STARTFOREGROUND_ACTION) && !player.isPlaying()) {

            Intent mainNotificationIntent = new Intent(this, MainActivity.class);
            mainNotificationIntent.setAction(Constants.MAIN_ACTION);
            mainNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent mainPandingIntent = PendingIntent.getActivity(this, 0, mainNotificationIntent, 0);


            Intent previouseIntent = new Intent(this, MainService.class);
            previouseIntent.setAction(Constants.PREV_ACTION);
            PendingIntent pendingIntent = PendingIntent.getService(this,
                    0,
                    previouseIntent,
                    0);



            Intent nextIntent = new Intent(this, MainService.class);
            nextIntent.setAction(Constants.NEXT_ACTION);
            PendingIntent pendingIntent3 = PendingIntent.getService(this,
                    0,
                    nextIntent,
                    0);


            Notification notification = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
                    .setAutoCancel(false)
                    .setContentTitle("Awesome title")
                    .setTicker("Awesome ticker")
                    .setContentText("Indicator that the service is running")
                    .setOngoing(true)
                    .setContentIntent(mainPandingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .addAction(R.drawable.ic_launcher_background, "Next", pendingIntent3)
                    .addAction(R.drawable.ic_launcher_background, "previous", pendingIntent)
                    .build();


            startForeground(Constants.FOREGROUND_SERVICE, notification);
            player.start();


        } else if (action.equals(Constants.PREV_ACTION)) {
            player.stop();
            player.release();
            currentTrack--;
            if(currentTrack < 0)
                currentTrack = playList.size()-1;
            player = MediaPlayer.create(this, playList.get(currentTrack));
            player.start();

        }else if (action.equals(Constants.NEXT_ACTION)) {
            player.stop();
            player.release();
            currentTrack++;
            if(currentTrack >= playList.size())
                currentTrack = 0;
            player = MediaPlayer.create(this, playList.get(currentTrack));
            player.start();

        } else if (action.equals(Constants.STOPFOREGROUND_ACTION)) {
            player.pause();
            stopForeground(true);
        }


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        playList = new ArrayList<>();
        playList.add(R.raw.appalachia_freight_train_fiddlin);
        playList.add(R.raw.mtver);
        currentTrack = 0;
        player = MediaPlayer.create(this,playList.get(currentTrack));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currentTrack = 0;
        player.release();
        player = null;
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
