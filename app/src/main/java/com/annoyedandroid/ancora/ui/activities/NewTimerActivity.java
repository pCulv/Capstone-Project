package com.annoyedandroid.ancora.ui.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.annoyedandroid.ancora.R;

import butterknife.ButterKnife;

import static com.annoyedandroid.ancora.R.drawable.abc_ic_ab_back_material;

public class NewTimerActivity extends MainActivity {

    Drawable upArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_timer);
        ButterKnife.bind(this);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        upArrow = getDrawable(abc_ic_ab_back_material);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            upArrow.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }
}
