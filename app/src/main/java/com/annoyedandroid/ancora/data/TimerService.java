package com.annoyedandroid.ancora.data;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.ui.activities.MainActivity;
import com.annoyedandroid.ancora.ui.fragments.NewTimerFragment;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class TimerService extends Service {
    private final static String TAG = "BroadcastService";
    public static final String ACTION_FOREGROUND = "com.annoyedandroid.ancora.data.TimerService.FOREGROUND";
    public static final String ACTION_BACKGROUND = "com.annoyedandroid.ancora.data.TimerService.BACKGROUND";

    public static final String COUNTDOWN_BR = "com.annoyedandroid.ancora.data.Alarm";
    public static final int NOTIFICATION_ID = 101;
    public static final int REQUEST_CODE = 1;
    public static final String CHANNEL_ID = "1";
    Intent bi = new Intent(COUNTDOWN_BR);
    Alarm alarm = new Alarm();
    CountDownTimer cdt = null;
    RemoteViews notifView;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {

        cdt.cancel();
        Log.i(TAG, "TimerModel cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarm.setAlarm(this);

        final String getTimerName = intent.getStringExtra(NewTimerFragment.TIMER_NAME);
        int getHour = intent.getIntExtra(NewTimerFragment.HOUR, 0);
        int getMin = intent.getIntExtra(NewTimerFragment.MIN, 0);
        int getSec = intent.getIntExtra(NewTimerFragment.SEC, 0);

        Log.i(TAG, "Starting timer...");
        // Todo: retrieve timer from database

        long timerHour = TimeUnit.HOURS.toMillis(getHour);
        long timerMin = TimeUnit.MINUTES.toMillis(getMin);
        long timerSec = TimeUnit.SECONDS.toMillis(getSec);

        long totalTime = timerHour + timerMin + timerSec;

        // If true service starts
        if (Objects.equals(intent.getAction(), ACTION_FOREGROUND)) {

            Log.i(TAG, "Received Start Foreground Intent ");

            notifView = new RemoteViews(getPackageName(), R.layout.notification);

            notifView.setTextViewText(R.id.content_title, getTimerName);
            // Displays live timer counting down in custom notification
            notifView.setChronometer(R.id.notif_tv, SystemClock.elapsedRealtime() + totalTime,
                    null, true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                notifView.setChronometerCountDown(R.id.notif_tv, true);
            }

            Intent showTaskIntent = new Intent(getApplicationContext(), MainActivity.class);
            showTaskIntent.setAction(Intent.ACTION_MAIN);
            showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                    0, showTaskIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                            .setCustomContentView(notifView)
                            .setContentIntent(contentIntent)
                            .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

            cdt = new CountDownTimer(totalTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    Log.i(TAG, "Countdown time remaining: " + millisUntilFinished / 3600000 + ":" +
                            millisUntilFinished % 3600000 / 60000 + ":" + millisUntilFinished % 60000 / 1000);

                    bi.putExtra("countdown", millisUntilFinished);
                    sendBroadcast(bi);
                }

                @Override
                public void onFinish() {
                    // Todo: build notification service to notify the user when their timer expires
                    Toast.makeText(getApplicationContext(), "TimerModel Finished", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "TimerModel Finished!");
                    //stops service once timer ends
                    Intent stopIntent = new Intent(getApplicationContext(), TimerService.class);
                    stopService(stopIntent);
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }
            };

            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;

            cdt.start();
            startForeground(NOTIFICATION_ID, notification);
        } else if (Objects.equals(intent.getAction(), ACTION_BACKGROUND)) {
            Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }

        return START_STICKY;
    }



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


}
