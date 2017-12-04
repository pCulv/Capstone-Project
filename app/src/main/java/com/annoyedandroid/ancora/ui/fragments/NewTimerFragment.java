package com.annoyedandroid.ancora.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.data.TimerService;
import com.annoyedandroid.ancora.model.TimerModel;
import com.annoyedandroid.ancora.ui.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class NewTimerFragment extends Fragment {

    public static final String HOUR = "Hour";
    public static final String MIN = "Min";
    public static final String SEC = "Sec";
    public static final String TIMER_NAME = "timerName";
    private final static String TAG = "BroadcastService";
    public static final String CHANNEL_ID = "1";

    NotificationCompat.Builder mBuilder;
    Button startButton;
    EditText timerEditTxt;
    NumberPicker hourNumbPicker, minNumbPicker, secNumbPicker;
    int mTimerHour, mTimerMin, mTimerSec;
    long mTotalTime;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                long millisUntilFinished = intent.getLongExtra("countdown", 0);
                Log.i(TAG, "Countdown time remaining: " + millisUntilFinished / 3600000 + ":" +
                        millisUntilFinished % 3600000 / 60000 + ":" + millisUntilFinished % 60000 / 1000);
            } else {
                Log.i(TAG, "No extras found");
            }
        }
    };

    public NewTimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_timer, container, false);

        // Clear Text in editText form
        timerEditTxt = view.findViewById(R.id.timerEditText);
        if (timerEditTxt != null) {
            timerEditTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerEditTxt.setText("");
                }
            });
        }

        // Start button which will save timer to firestore and then start the timer
        startButton = view.findViewById(R.id.startBtn);
        if (startButton != null) {
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerStart();
                }
            });
        }
        return view;
    }

    public void timerStart() {

        hourNumbPicker = getView().findViewById(R.id.newHourPicker);
        minNumbPicker = getView().findViewById(R.id.newMinPicker);
        secNumbPicker = getView().findViewById(R.id.newSecPicker);


        final String timerName = timerEditTxt != null ? timerEditTxt.getText().toString() : null;
        mTimerHour = hourNumbPicker != null ? hourNumbPicker.getValue() : 0;
        mTimerMin = minNumbPicker != null ? minNumbPicker.getValue() : 0;
        mTimerSec = secNumbPicker != null ? secNumbPicker.getValue() : 0;

        long timerHour = TimeUnit.HOURS.toMillis(mTimerHour);
        long timerMin = TimeUnit.MINUTES.toMillis(mTimerMin);
        long timerSec = TimeUnit.SECONDS.toMillis(mTimerSec);

        final long totalTimeMillis = timerHour + timerMin + timerSec;


        if (Objects.equals(timerName, "") || Objects.equals(timerName, "Enter TimerModel Name")) {
            Toast.makeText(this.getActivity(), "Please Enter Timer Name", Toast.LENGTH_SHORT).show();
        } else {
            // Save user's timer
            writeNewTimer(timerName, totalTimeMillis);
            // Open MainActivity
            Intent intent = new Intent(getContext(), MainActivity.class);
            // Starts service and adds extras to serviceIntent
            Intent serviceIntent = new Intent(TimerService.ACTION_FOREGROUND);
//            serviceIntent.putExtra(TIMER_NAME, timerName);
            serviceIntent.putExtra("totalTime", totalTimeMillis);
            serviceIntent.putExtra(HOUR, timerHour);
            serviceIntent.putExtra(MIN, timerMin);
            serviceIntent.putExtra(SEC, timerSec);
            serviceIntent.setClass(getContext(), TimerService.class);
            // open mainActivity
            startActivity(intent);
            // start countdown service
            getActivity().startService(serviceIntent);
        }
    }

    private void writeNewTimer(String timerName, long totalTime) {
        TimerModel timer = new TimerModel(timerName, totalTime);

        // Retrieve current authenticated user from firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = null;
        if (currentUser != null) {
            uid = currentUser.getUid();
        }

        // Saver Unique timer to database
        mDatabase.child("users").child(uid).child("timers").push().setValue(timer);
        mDatabase.orderByChild("timers");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
