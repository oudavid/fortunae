package com.fortunae.android.app;

import android.app.Application;

import com.parse.Parse;

public class FortunaeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "<Your Application ID>", "<Your Client Key>");
    }
}
