package com.annoyedandroid.ancora.ui.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.annoyedandroid.ancora.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.annoyedandroid.ancora.R.drawable.abc_ic_ab_back_material;

//Extends MainActivity to allow Nav Bar functionality
public class NewTimerActivity extends MainActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Drawable upArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_new_timer, null, false);
        mDrawer.addView(contentView, 0);

        mFab.setVisibility(View.GONE);
        ButterKnife.bind(this);


        drawerToggle.setDrawerIndicatorEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        upArrow = getDrawable(abc_ic_ab_back_material);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            upArrow.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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
}
