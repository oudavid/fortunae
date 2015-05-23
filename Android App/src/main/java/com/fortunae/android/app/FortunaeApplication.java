package com.fortunae.android.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;

public class FortunaeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseUser.enableAutomaticUser();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "YI6I96eOon3041gbGJyMSy6HhsfeuKBkrq1w7G0u", "9sjEJgRfZyW8XYDUbaALHHoXj6liYTAdZ8OGmO4Z");

    }
}
