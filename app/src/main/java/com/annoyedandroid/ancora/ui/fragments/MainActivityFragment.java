package com.annoyedandroid.ancora.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.Timer;
import com.annoyedandroid.ancora.ui.activities.NewTimerActivity;
import com.annoyedandroid.ancora.ui.adapters.TimerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.annoyedandroid.ancora.R.id.recyclerView;


public class MainActivityFragment extends Fragment {

    private TimerAdapter mAdapter;
    private List<Timer> mTimers = new ArrayList<>();
    private DatabaseReference databaseReference;
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.noTimerPlaceHolder)
    ImageView noTimerPlaceHolder;
    @BindView(R.id.timers_progress_bar)
    ProgressBar progressBar;
    @Nullable
    @BindView(R.id.new_timer_fab)
    FloatingActionButton mFab;
    Context mContext;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

    public MainActivityFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        //code for recyclerView

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

//        mAdapter = new TimerAdapter(mTimers, mContext);
//
//
//        mRecyclerView.setAdapter(mAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/" + uid + "/timers");

        databaseReference.orderByChild("timerName").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                noTimerPlaceHolder.setVisibility(View.GONE);
                getAllTask(dataSnapshot);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                getAllTask(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                if (mTimers.size() == 0) {
                    progressBar.setVisibility(View.GONE);
                    noTimerPlaceHolder.setVisibility(View.VISIBLE);

                    Toast.makeText(getActivity(), "Please Create New Timer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (mFab != null) {
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TAG", "Clicked");
                    Intent newTimer = new Intent(MainActivityFragment.this.getActivity(), NewTimerActivity.class);
                    startActivity(newTimer);
                }
            });
        }
        return view;
    }

    // retrieve all timers from database to recyclerview
    private void getAllTask(DataSnapshot dataSnapshot){

        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

            if (java.util.Objects.equals(singleSnapshot.getKey(), "timerName")) {
                String timerName = singleSnapshot.getValue(String.class);
                mTimers.add(new Timer(timerName));
                mAdapter = new TimerAdapter(mTimers, MainActivityFragment.this.getContext());
                mRecyclerView.setAdapter(mAdapter);

            }
        }
    }

//    private void deleteTimer(DataSnapshot dataSnapshot) {
//        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//            String timerName = singleSnapshot.getValue(String.class);
//            for (int i = 0; i < mTimers.size(); i++) {
//                if (mTimers.get(i).getTimerName().equals(timerName)) {
//                    mTimers.remove(i);
//                }
//            }



}
