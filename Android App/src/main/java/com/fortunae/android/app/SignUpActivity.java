package com.fortunae.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class SignUpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onAddProfilePicture(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getSupportFragmentManager().findFragmentById(R.id.fragment)
                .startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), ImageUtils.REQUEST_CODE_GALLERY);
    }
}
