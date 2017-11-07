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
    int timerHour, timerMin, timerSec;
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
        timerHour = hourNumbPicker != null ? hourNumbPicker.getValue() : 0;
        timerMin = minNumbPicker != null ? minNumbPicker.getValue() : 0;
        timerSec = secNumbPicker != null ? secNumbPicker.getValue() : 0;


        if (Objects.equals(timerName, "") || Objects.equals(timerName, "Enter TimerModel Name")) {
            Toast.makeText(this.getActivity(), "Please Enter TimerModel Name", Toast.LENGTH_SHORT).show();
        } else {
            // Save user's timer
            writeNewTimer(timerName, timerHour, timerMin, timerSec);
            // Open MainActivity
            // TODO: 10/20/17 set intent extras to save countdown timer for mainActivity to start the timer
            Intent intent = new Intent(getContext(), MainActivity.class);
            Intent serviceIntent = new Intent(TimerService.ACTION_FOREGROUND);
            serviceIntent.putExtra(TIMER_NAME, timerName);
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

    private void writeNewTimer(String timerName, Integer hour, Integer minute, Integer second) {
        TimerModel timer = new TimerModel(timerName, hour, minute, second);

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
