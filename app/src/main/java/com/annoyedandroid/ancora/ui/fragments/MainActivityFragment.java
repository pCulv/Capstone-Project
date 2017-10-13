package com.annoyedandroid.ancora.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.Timer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.annoyedandroid.ancora.R.id.recyclerView;


public class MainActivityFragment extends Fragment {


    private List<Timer> mTimers = new ArrayList<>();

    @BindView(recyclerView)
    RecyclerView mRecyclerView;


    FirestoreRecyclerAdapter mAdapter = getFirestoreRecyclerAdapter();


    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter.startListening();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        //code for recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private FirestoreRecyclerAdapter getFirestoreRecyclerAdapter() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String email = currentUser.getEmail();

        Query query = FirebaseFirestore.getInstance().collection("users")
                .document(email)
                .collection("timers");

        FirestoreRecyclerOptions<Timer> options = new FirestoreRecyclerOptions.Builder<Timer>()
                .setQuery(query, Timer.class)
                .build();


        return new FirestoreRecyclerAdapter<Timer, TimerViewHolder>(options) {

            @Override
            public TimerViewHolder onCreateViewHolder(ViewGroup group, int viewType) {

                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.timer_list_item, group, false);

                return new TimerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(MainActivityFragment.TimerViewHolder holder, int position, Timer model) {
                holder.BindView(position, model);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                Toast.makeText(MainActivityFragment.this.getActivity(), "Data Changed", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private class TimerViewHolder extends RecyclerView.ViewHolder {

        TextView timerTextView;
        Chronometer timerChrono;

        public TimerViewHolder(View itemView) {
            super(itemView);
            timerTextView = itemView.findViewById(R.id.timerName);
            timerChrono = itemView.findViewById(R.id.chronometer2);
        }

        void BindView(int position, Timer model) {

            timerTextView.setText(model.getTimerName());
            timerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivityFragment.this.getActivity(), "Timer Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
