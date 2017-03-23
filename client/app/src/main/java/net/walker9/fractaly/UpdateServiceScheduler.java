package net.walker9.fractaly;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by joel on 2/27/17.
 */

public class UpdateServiceScheduler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            schedule(context);
        }
    }

    private static PendingIntent make_pollservice_intent(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        return PendingIntent.getService(context, 0, intent, 0);
    }

    public static void schedule(Context context) {
        AlarmManager alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm_manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
                AlarmManager.INTERVAL_HALF_HOUR,
                make_pollservice_intent(context));
    }

    public static void cancel(Context context) {
        AlarmManager alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm_manager.cancel(make_pollservice_intent(context));
    }
}