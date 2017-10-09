package com.annoyedandroid.ancora.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.annoyedandroid.ancora.R;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.annoyedandroid.ancora.R.drawable.abc_ic_ab_back_material;

//Extends MainActivity to allow Nav Bar functionality
public class NewTimerActivity extends MainActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Drawable upArrow;

    // Access a Cloud Firestore instance from your Activity

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_new_timer, null, false);
        mDrawer.addView(contentView, 0);
        ButterKnife.bind(this);

        mFab.setVisibility(View.GONE);

        upArrow = getDrawable(abc_ic_ab_back_material);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            upArrow.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        drawerToggle.setDrawerIndicatorEnabled(false);
//
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
