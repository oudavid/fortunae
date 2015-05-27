package com.fortunae.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    static final int NEW_GOAL_REQUEST = 1;
    static final int EXPLORE_REQUEST = 2;
    static final int SETTINGS_REQUEST = 3;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the menu_main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                fragmentTransaction.replace(R.id.container, GoalsFragment.newInstance(position + 1));
                break;
            case 1:
                Intent newGoalIntent = new Intent(this, ExploreActivity.class);
                startActivityForResult(newGoalIntent, EXPLORE_REQUEST);
                break;
        }

        fragmentTransaction.commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section_dashboard);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_new_goal) {
            Intent newGoalIntent = new Intent(this, NewGoalActivity.class);
            startActivityForResult(newGoalIntent, NEW_GOAL_REQUEST);
            return true;
        }

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
                    Toast.makeText(MainActivity.this,
                            "Signing out...",
                            Toast.LENGTH_LONG).show();
                    // Send user to LauncherActivity.class
                    Intent intent = new Intent(MainActivity.this,
                            LauncherActivity.class);
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

    public static class GoalsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private TextView mActiveCountTextView;
        private TextView mBacklogCountTextView;
        private TextView mCompletedCountTextView;
        private TextView mEncouragementTextView;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static GoalsFragment newInstance(int sectionNumber) {
            GoalsFragment fragment = new GoalsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public GoalsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_goals, container, false);

            mActiveCountTextView = (TextView) rootView.findViewById(R.id.active_count);
            mBacklogCountTextView = (TextView) rootView.findViewById(R.id.backlog_count);
            mCompletedCountTextView = (TextView) rootView.findViewById(R.id.completed_count);
            mEncouragementTextView = (TextView) rootView.findViewById(R.id.encouragement);

            ParseQuery<ParseObject> goalsListQuery = ParseQuery.getQuery("Goal");
            goalsListQuery.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
            goalsListQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> goalsList, ParseException e) {
                    if (e == null) {
                        int activeCount = 0;
                        int backlogCount = 0;
                        int completedCount = 0;
                        for (ParseObject goal : goalsList) {
                            if ("active".equals(goal.getString("status"))) {
                                activeCount++;
                            } else if ("backlog".equals(goal.getString("status"))) {
                                backlogCount++;
                            } else if ("completed".equals(goal.getString("status"))) {
                                completedCount++;
                            }
                        }

                        mActiveCountTextView.setText(Integer.toString(activeCount));
                        mBacklogCountTextView.setText(Integer.toString(backlogCount));
                        mCompletedCountTextView.setText(Integer.toString(completedCount));

                        mEncouragementTextView.setText(String.format(getString(R.string.encouragement), activeCount));
                    }
                }
            });

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
