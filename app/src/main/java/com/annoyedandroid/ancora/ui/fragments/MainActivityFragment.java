package com.annoyedandroid.ancora.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.Timer;
import com.annoyedandroid.ancora.ui.adapters.TimerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivityFragment extends Fragment {

    private TimerAdapter mAdapter;
    private List<Timer> mTimers = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ButterKnife.bind(this.getActivity());

        //code for recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TimerAdapter(this.getActivity(), mTimers);

        mRecyclerView.setAdapter(mAdapter);

        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
