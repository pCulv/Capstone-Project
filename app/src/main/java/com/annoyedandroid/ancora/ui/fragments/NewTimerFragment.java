package com.annoyedandroid.ancora.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.Timer;
import com.annoyedandroid.ancora.ui.adapters.TimerAdapter;
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
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Button startButton;
    EditText timerEditTxt;
    NumberPicker hourNumbPicker;
    NumberPicker minNumbPicker;
    NumberPicker secNumbPicker;

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
        int timerHour = hourNumbPicker != null ? hourNumbPicker.getValue() : 0;
        int timerMin = minNumbPicker != null ? minNumbPicker.getValue() : 0;
        int timerSec = secNumbPicker != null ? secNumbPicker.getValue() : 0;

        if (Objects.equals(timerName, "") || Objects.equals(timerName, "Enter Timer Name")) {
            Toast.makeText(this.getActivity(), "Please Enter Timer Name", Toast.LENGTH_SHORT).show();
        } else {
            // Save user's timer
            writeNewTimer(timerName, timerHour, timerMin, timerSec);
            // Open MainActivity
            // TODO: 10/20/17 set intent extras to save countdown timer for mainActivity to start the timer
            Intent intent = new Intent(getContext(), TimerAdapter.class);
            intent.putExtra(HOUR, timerHour);
            intent.putExtra(MIN, timerMin);
            intent.putExtra(SEC, timerSec);

            startActivity(intent);

            Toast.makeText(this.getActivity(), timerName + " Timer Saved", Toast.LENGTH_SHORT).show();

        }
    }


    private void writeNewTimer(String timerName, Integer hour, Integer minute, Integer second) {
        Timer timer = new Timer(timerName, hour, minute, second);

        // Retrieve current authenticated user from firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final String userName = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        String uid = currentUser.getUid();

        // Saver Unique timer to database
        mDatabase.child("users").child(uid).child("timers").push().setValue(timer);
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
