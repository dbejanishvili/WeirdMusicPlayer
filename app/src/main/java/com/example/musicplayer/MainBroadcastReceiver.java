package com.example.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class MainBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action == null)
            return;

        if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            Intent startIntent = new Intent(context,MainService.class);
            startIntent.setAction(Constants.STARTFOREGROUND_ACTION);
            context.startService(startIntent);
        }
    }
}
