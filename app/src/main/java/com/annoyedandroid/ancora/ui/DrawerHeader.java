package com.annoyedandroid.ancora.ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.annoyedandroid.ancora.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;



@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    @Resolve
    private void onResolved() {
//        nameTxt.setText("Phil Culver");
//        emailTxt.setText("philculver@gmail.com");

    }
}