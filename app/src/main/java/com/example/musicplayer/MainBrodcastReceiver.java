package com.example.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class MainBrodcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            Intent startIntent = new Intent(context,MainService.class);
            startIntent.setAction(Constants.STARTFOREGROUND_ACTION);
            context.startService(startIntent);
        }
    }
}
