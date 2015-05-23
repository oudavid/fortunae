package com.fortunae.android.app;

import android.support.annotation.NonNull;

/**
 * Created by DOu on 5/18/15.
 */
public class StringUtils {

    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public static boolean isGoalActionValid(@NonNull String goalAction) {
        return goalAction.length() > 0;
    }
}
