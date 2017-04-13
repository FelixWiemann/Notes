package com.example.felix.notizen.old_files;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.felix.notizen.old_files.Objects.Note;
import com.example.felix.notizen.old_files.Objects.Note_Notification;

/**
 * Created by Felix "nepumuk" Wiemann on 05.06.2016
 * as part of Notizen
 */
public class Alarm extends Service {
    android.app.AlarmManager alarmManager;

    public void SetAlarm(Note note) {

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                context.unregisterReceiver(this); // this == BroadcastReceiver, not Activity
            }
        };

        this.registerReceiver(receiver, new IntentFilter(String.valueOf(Alarm.class)));

        Intent intent = new Intent(this, Note_Notification.class);
        intent.putExtra("NOTE_DUE", note);
        PendingIntent pintent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager manager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));

        // set alarm to fire 5 sec (1000*5) from now (SystemClock.elapsedRealtime())
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, note.getNoteCreatedDate() + 1000 * 5, pintent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
