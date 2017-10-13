package com.annoyedandroid.ancora.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;


public class NewTimerFragment extends Fragment {


    public NewTimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_new_timer, container, false);
        if (container != null) {
            container.removeAllViews();
        }
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "attached", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
