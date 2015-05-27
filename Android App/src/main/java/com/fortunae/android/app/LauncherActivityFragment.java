package com.fortunae.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;


/**
 * A placeholder fragment containing a simple view.
 */
public class LauncherActivityFragment extends Fragment {

    public LauncherActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Determine whether the current user is an anonymous user
        if (ParseUser.getCurrentUser() == null || ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(getActivity(),
                    WelcomeActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to MainActivity.class
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(getActivity(),
                        WelcomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launcher, container, false);
    }
}
