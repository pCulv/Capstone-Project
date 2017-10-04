package com.annoyedandroid.ancora.data;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.ui.fragments.GoogleSignInFragment;

public class GoogleLoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        if (findViewById(R.id.google_signIn_fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            GoogleSignInFragment googleSignInFragment = new GoogleSignInFragment();

            googleSignInFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.google_signIn_fragment_container, googleSignInFragment)
                    .commit();
        }
    }
}