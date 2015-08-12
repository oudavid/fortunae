package com.fortunae.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int NEW_GOAL_REQUEST = 1;
    protected static final int EXPLORE_REQUEST = 2;
    protected static final int SETTINGS_REQUEST = 3;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent newGoalIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(newGoalIntent, SETTINGS_REQUEST);
            return true;
        }

        if (id == R.id.action_sign_out) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    // Show a simple Toast message upon successful registration
                    Toast.makeText(BaseActivity.this, "Signing out...", Toast.LENGTH_LONG).show();

                    // Send user to LauncherActivity.class
                    Intent intent = new Intent(BaseActivity.this, LauncherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == NEW_GOAL_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // TODO: The user added a new goal
            }
        } else if (requestCode == EXPLORE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // TODO: The user explored new goals
            }
        }
    }
}
