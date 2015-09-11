package com.solderbyte.openfit;

import android.content.Intent;
import android.content.IntentFilter;

public class Alarm {

    public static final IntentFilter INTENT_FILTER;

    static  {
        INTENT_FILTER = new IntentFilter();
        INTENT_FILTER.addAction( "com.android.deskclock.ALARM_ALERT" );
        INTENT_FILTER.addAction( "com.android.deskclock.ALARM_SNOOZE" );
        INTENT_FILTER.addAction( "com.android.deskclock.ALARM_DISMISS" );
        INTENT_FILTER.addAction( "com.android.deskclock.ALARM_DONE" );
    }
    
    public static String getAction(Intent intent) {
        String action = intent.getAction();

        if(action.equals("com.android.deskclock.ALARM_ALERT")) {
            action = "START";
        }
        else if(action.equals("com.android.deskclock.ALARM_SNOOZE")) {
            action = "SNOOZE";
        }
        else if(action.equals("com.android.deskclock.ALARM_DISMISS")) {
            action = "STOP";
        }
        else if(action.equals("com.android.deskclock.ALARM_DONE")) {
            action = "STOP";
        }
        else {
            action = "STOP";
        }

        return action;
    }

    public static Intent snoozeAlarm() {
        Intent intent = new Intent();
        intent.setAction("com.android.deskclock.ALARM_SNOOZE");
        return intent;
    }

    public static Intent dismissAlarm() {
        Intent intent = new Intent();
        intent.setAction("com.android.deskclock.ALARM_DISMISS");
        return intent;
    }
}
