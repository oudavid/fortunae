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
        Parse.initialize(this, "YOUR APP ID", "YOUR CLIENT KEY");
        ParseUser.enableAutomaticUser();
    }
}
