package com.annoyedandroid.ancora.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.annoyedandroid.ancora.R.drawable.abc_ic_ab_back_material;

//Extends MainActivity to allow Nav Bar functionality
public class NewTimerActivity extends MainActivity {
    public static final String TIMER_HOUR = "hour";
    public static final String TIMER_NAME = "timer name";
    public static final String TIMER_MIN = "min";
    public static final String TIMER_SEC = "sec";
    private static final String TAG = "TAG";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String USER_ID = "user id";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Drawable upArrow;
    @Nullable
    @BindView(R.id.startBtn)
    Button startButton;
    EditText timerEditTxt;
    @Nullable
    @BindView(R.id.newHourPicker)
    NumberPicker hourNumbPicker;
    @Nullable
    @BindView(R.id.newMinPicker)
    NumberPicker minNumbPicker;
    @Nullable
    @BindView(R.id.newSecPicker)
    NumberPicker secNumbPicker;
    // Access a Cloud Firestore instance from your Activity

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_new_timer, null, false);
        mDrawer.addView(contentView, 0);
        ButterKnife.bind(this);


//        mAuth = FirebaseAuth.getInstance();
        mFab.setVisibility(View.GONE);

        // nav up button to return to main activity
        upArrow = getDrawable(abc_ic_ab_back_material);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            upArrow.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        // Nav drawer
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go back to main MainActivity
                Intent goBack = new Intent(NewTimerActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Clear Text in editText form
        timerEditTxt = findViewById(R.id.timerEditText);
        if (timerEditTxt != null) {
            timerEditTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerEditTxt.setText("");
                }
            });
        }
        // Start button which will save timer to firestore and then start the timer
        startButton = findViewById(R.id.startBtn);
        if (startButton != null) {
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerStart();
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void timerStart() {

        // Retrieve current authenticated user from firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final String userName = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        String uid = currentUser.getUid();

        final String timerName = timerEditTxt != null ? timerEditTxt.getText().toString() : null;
        int timerHour = hourNumbPicker != null ? hourNumbPicker.getValue() : 0;
        int timerMin = minNumbPicker != null ? minNumbPicker.getValue() : 0;
        int timerSec = secNumbPicker != null ? secNumbPicker.getValue() : 0;

        // Document reference where the users timers will be saved in the database
        DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("users")
                .document(email)
                .collection("timers")
                .document(timerName);

        Map<String, Object> user = new HashMap<>();

            user.put(NAME, userName);
            user.put(EMAIL, email);
            user.put(USER_ID, uid);

            Map<String, Object> timer = new HashMap<>();
            timer.put(TIMER_NAME, timerName);
            timer.put(TIMER_HOUR, timerHour);
            timer.put(TIMER_MIN, timerMin);
            timer.put(TIMER_SEC, timerSec);

        db.collection("users").document(email)
                    .set(user, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: User was added");
                            Toast.makeText(NewTimerActivity.this, timerName.toUpperCase() + " timer created.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: User was not added");
                        }
                    });
        }
        

        // Create new timer


//        db.collection()

}
