package com.fortunae.android.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class NewGoalActivity extends BaseActivity {
    private TextView mActionTextView;
    private Spinner mCategorySpinner;
    private SeekBar mAdventurousSeekBar;
    private SeekBar mTimeCommitmentSeekBar;
    private SeekBar mCostSeekBar;
    private Button mAddGoalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        mActionTextView = (TextView) findViewById(R.id.action);
        mCategorySpinner = (Spinner) findViewById(R.id.category);
        mAdventurousSeekBar = (SeekBar) findViewById(R.id.adventurous);
        mTimeCommitmentSeekBar = (SeekBar) findViewById(R.id.time_commitment);
        mCostSeekBar = (SeekBar) findViewById(R.id.cost);
        mAddGoalButton = (Button) findViewById(R.id.add_goal);

        mAddGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddGoalButton.setEnabled(false);
                attemptAddNewGoal();
            }
        });
    }

    /**
     * Attempts to register the account specified by the sign up form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual sign up attempt is made.
     */
    private void attemptAddNewGoal() {
        // Reset errors.
        mActionTextView.setError(null);

        String userId = ParseUser.getCurrentUser().getObjectId();
        String action = mActionTextView.getText().toString();
        String category = (String) mCategorySpinner.getSelectedItem();
        int adventurous = mAdventurousSeekBar.getProgress();
        int timeCommitment = mTimeCommitmentSeekBar.getProgress();
        int cost = mCostSeekBar.getProgress();

        // Check for a valid goal, if the user entered one.
        if (!TextUtils.isEmpty(action) && !StringUtils.isGoalActionValid(action)) {
            mActionTextView.setError(getString(R.string.error_invalid_action));
        }

        ParseObject newGoal = new ParseObject("Goal");
        newGoal.put("userId", userId);
        newGoal.put("Action", action);
        newGoal.put("Category", category);
        newGoal.put("Adventurous", adventurous);
        newGoal.put("TimeCommitment", timeCommitment);
        newGoal.put("Cost", cost);
        newGoal.put("status", "backlog");

        // Send data to Parse.com for verification
        newGoal.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    mAddGoalButton.setEnabled(true);
                    Toast.makeText(NewGoalActivity.this, "Failed to add new goal:" + e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Show a simple Toast message upon successfully adding new goal
                    Toast.makeText(NewGoalActivity.this, "Successfully add new goal!", Toast.LENGTH_LONG).show();
                    // Finish activity and send user back to GoalActivity.class
                    finish();
                }
            }
        });
    }
}
