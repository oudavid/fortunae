package com.fortunae.android.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class FortunaeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Goal.class);
        Parse.initialize(this, "5MFTQn8MToamBUUmBa9nPkZY0bIYlMR9YI4bOlGK", "satahj1MaBFIBgB7UX4FWxftt7Nsiirzv3wuv7C1");
        ParseUser.enableAutomaticUser();
    }
}
