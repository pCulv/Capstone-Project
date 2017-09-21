package com.annoyedandroid.ancora.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.annoyedandroid.ancora.R;

import butterknife.ButterKnife;

public class NavDrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_draw);
        ButterKnife.bind(this);


    }


}
