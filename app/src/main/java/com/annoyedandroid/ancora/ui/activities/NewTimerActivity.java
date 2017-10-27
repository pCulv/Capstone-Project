package com.annoyedandroid.ancora.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.ui.fragments.NewTimerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

//Extends MainActivity to allow Nav Bar functionality
public class NewTimerActivity extends MainActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Drawable upArrow;
    @Nullable
    @BindView(R.id.startBtn)
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_new_timer, null, false);
        mDrawer.addView(contentView, 0);
        ButterKnife.bind(this);

        // Creates Fragment inside activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewTimerFragment newTimerFragment = new NewTimerFragment();
        fragmentManager.beginTransaction().replace(R.id.flContent, newTimerFragment).commit();


        // nav up button to return to main activity
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


        if (toolbar != null) {
            Drawable closeIcon = getDrawable(R.drawable.ic_close_black_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                closeIcon.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(closeIcon);

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewTimerActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
