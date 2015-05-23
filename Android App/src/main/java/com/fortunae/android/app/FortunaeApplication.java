package com.fortunae.android.app;

import android.app.Application;

import com.parse.Parse;

public class FortunaeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "whfS4Rmhu3SHNvUxSnnIFZ3BjMRXOswQr1z4hDi1", "qA6cUTQQZkrVxkjgWUgJXjDbwk8YqxgvkPeb4njk");

    }
}
