package com.annoyedandroid.ancora.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        //code for recyclerView
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
//
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//
//        mAdapter = new TimerAdapter(this.getActivity(), mTimers);
//
//        mRecyclerView.setAdapter(mAdapter);


        return view;
    }
}
