package com.annoyedandroid.ancora.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.ui.DrawerHeader;
import com.annoyedandroid.ancora.ui.DrawerMenuItem;
import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawer;
    @Nullable
    @BindView(R.id.drawerView)
    PlaceHolderView mDrawerView;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FloatingActionButton fab = findViewById(R.id.new_timer_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTimer = new Intent(MainActivity.this, NewTimerActivity.class);
                startActivity(newTimer);
            }
        });

        setupDrawer();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    private void setupDrawer() {

        //add drawer contents here
        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext(),
                        DrawerMenuItem.DRAWER_MENU_ITEM_MY_TIMERS))
                .addView(new DrawerMenuItem(this.getApplicationContext(),
                        DrawerMenuItem.DRAWER_MENU_ITEM_UPGRADE));

        mDrawerToggle =
                    new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                            R.string.navigation_drawer_close) {
                        @Override
                        public void onDrawerOpened(View drawerView) {
                            super.onDrawerOpened(drawerView);

                        }

                        @Override
                        public void onDrawerClosed(View drawerView) {
                            super.onDrawerClosed(drawerView);

                        }
                    };
            mDrawer.addDrawerListener(mDrawerToggle);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.syncState();
        }
    }

