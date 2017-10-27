package com.annoyedandroid.ancora.data;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.annoyedandroid.ancora.ui.fragments.NewTimerFragment;

import java.util.concurrent.TimeUnit;


public class TimerService extends Service{
    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "com.annoyedandroid.ancora.data.Alarm";
    Intent bi = new Intent(COUNTDOWN_BR);
    Alarm alarm = new Alarm();
    CountDownTimer cdt = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {

        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarm.setAlarm(this);

        if (intent != null) {

            Log.i(TAG, "Starting timer...");
            // Todo: retrieve timer from database

            int getHour = intent.getIntExtra(NewTimerFragment.HOUR, 0);
            int getMin = intent.getIntExtra(NewTimerFragment.MIN, 0);
            int getSec = intent.getIntExtra(NewTimerFragment.SEC, 0);

            long timerHour = TimeUnit.HOURS.toMillis(getHour);
            long timerMin = TimeUnit.MINUTES.toMillis(getMin);
            long timerSec = TimeUnit.SECONDS.toMillis(getSec);

            long totalTime = timerHour + timerMin + timerSec;

            cdt = new CountDownTimer(totalTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
//                timerChronometer.setText((millisUntilFinished / 3600000) + ":" +
//                        (millisUntilFinished % 3600000 / 60000) + ":"+(millisUntilFinished % 60000 / 1000));
                    Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);

                    // Todo: place total time retrieved from database for timer here
                    bi.putExtra("countdown", millisUntilFinished);
                    sendBroadcast(bi);
                }

                @Override
                public void onFinish() {
                    // Todo: build notification service to notify the user when their timer expires
                    Toast.makeText(getApplicationContext(), "Timer Finished", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Timer Finished!");
                }
            };

            cdt.start();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
