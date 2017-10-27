package com.annoyedandroid.ancora.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.data.GoogleSignInActivity;
import com.annoyedandroid.ancora.ui.ImageTrans_CircleTransform;
import com.annoyedandroid.ancora.ui.fragments.GoogleSignInFragment;
import com.annoyedandroid.ancora.ui.fragments.MainActivityFragment;
import com.annoyedandroid.ancora.ui.fragments.SettingsFragment;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Nullable
    @BindView(R.id.nvView)
    NavigationView navDrawer;
    @Nullable
    @BindView(R.id.googleBtn)
    SignInButton mGoogleSignInBtn;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    Context mContext;


    protected ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        FragmentManager fragmentManager = getSupportFragmentManager();
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        fragmentManager.beginTransaction().replace(R.id.flContent, mainActivityFragment).commit();

        setSupportActionBar(mToolbar);

        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        // Set Contents of Nav drawer
        setupDrawerContent(navDrawer);
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final String userName = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        Uri userImage = currentUser.getPhotoUrl();

        View header = navigationView.getHeaderView(0);
        TextView nameTxtVw = header.findViewById(R.id.nameTxt);
        TextView emailTxtVw = header.findViewById(R.id.emailTxt);
        ImageView profileImage = header.findViewById(R.id.profileImageView);
        // Load user info into Nav drawer
        Picasso.with(this)
                .load(userImage)
                .transform(new ImageTrans_CircleTransform())
                .into(profileImage);
        nameTxtVw.setText(userName);
        emailTxtVw.setText(email);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {

        Fragment fragment = null;
        Class fragmentClass = null;

        switch (menuItem.getItemId()) {
            case R.id.my_timers:
                fragmentClass = MainActivityFragment.class;
                break;
            case R.id.settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.upgrade:
                //todo build intent to open playstore for free version of app
                break;
            case R.id.signOut:
                signOut();
                //opens login screen
                startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                mToolbar.setVisibility(View.GONE);
                fragmentClass = GoogleSignInFragment.class;
//                mFab.setVisibility(View.GONE);
                break;
            default:
                fragmentClass = MainActivityFragment.class;
                break;
        }

        if (fragmentClass != null) {
            try {

                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        menuItem.setChecked(true);

        setTitle(menuItem.getTitle());

        mDrawer.closeDrawers();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "You've been signed out.", Toast.LENGTH_SHORT).show();
    }
}

