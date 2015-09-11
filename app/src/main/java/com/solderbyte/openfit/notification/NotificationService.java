package com.solderbyte.openfit.notification;

import java.util.ArrayList;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {
    private static final String TAG = "NotificationService";

    private ArrayList<String> listeningListPackageNames = new ArrayList<String>();
    private Context context;

    @Override
    public void onCreate() {
        Log.d( TAG, "Created NotificationService");
        this.registerReceiver(stopServiceReceiver, new IntentFilter("stopOpenFitService"));
        this.registerReceiver(appsReceiver, new IntentFilter("listeningApps"));
        context = getApplicationContext();

        Intent msg = new Intent("NotificationService");
        context.sendBroadcast(msg);
        super.onCreate();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        String ticker = null;
        String message = null;
        String submessage = null;
        String summary = null;
        String info = null;
        String title = null;
        try {
            ticker = (String) sbn.getNotification().tickerText;
        }
        catch(Exception e) {
            // nothing
        }
        String tag = sbn.getTag();
        long time = sbn.getPostTime();
        int id = sbn.getId();

        // API v19
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        //String category = notification.category; API v21

        if(extras.containsKey("android.title") ) {
            title = extras.getString("android.title");
        }
        if(extras.containsKey("android.text")) {
            message = extras.getCharSequence("android.text").toString();
        }
        if(extras.containsKey("android.subText")) {
            submessage = extras.getCharSequence("android.subText").toString();
        }
        if(extras.containsKey("android.summaryText")) {
            summary = extras.getCharSequence("android.summaryText").toString();
        }
        if(extras.containsKey("android.infoText")) {
            info = extras.getCharSequence("android.infoText").toString();
        }

        Log.d( TAG, "Captured notification message: " + message + " from source:" + packageName);

        if(listeningListPackageNames.contains(packageName)) {
            Log.d( TAG, "ticker: " + ticker);
            Log.d( TAG, "title: " + title);
            Log.d( TAG, "message: " + message);
            Log.d( TAG, "tag: " + tag);
            Log.d( TAG, "time: " + time);
            Log.d( TAG, "id: " + id);
            Log.d( TAG, "submessage: " + submessage);
            Log.d( TAG, "summary: " + summary);
            Log.d( TAG, "info: " + info);
            //Log.d(TAG, "category: " + category);

            Intent intent = new Intent("notification");
            intent.putExtra( "packageName", packageName );
            intent.putExtra( "ticker", ticker );
            intent.putExtra( "title", title );
            intent.putExtra( "message", message );
            intent.putExtra( "time", time );
            intent.putExtra( "id", id );
            if(submessage != null) {
                intent.putExtra( "submessage", submessage );
            }

            //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            context.sendBroadcast(intent);
            Log.d( TAG, "Sending notification message: " + message + " from source:" + packageName);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        String shortMsg = "";
        try {
            shortMsg = (String) sbn.getNotification().tickerText;
        }
        catch(Exception e) {

        }
        Log.d( TAG, "Removed notification message: " + shortMsg + " from source:" + packageName);
    }

    public void setListeningPackageNames(ArrayList<String> packageNames) {
        listeningListPackageNames = packageNames;
    }

    private BroadcastReceiver stopServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d( TAG, "Stopping Service");
            unregisterReceiver(appsReceiver);
            unregisterReceiver(stopServiceReceiver);
            stopSelf();
        }
    };

    private BroadcastReceiver appsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> listeningApps = intent.getStringArrayListExtra("data");
            setListeningPackageNames(listeningApps);
            Log.d( TAG, "Recieved listeningApps");
        }
    };
}
