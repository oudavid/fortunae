package com.fortunae.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationView mNavigationDrawerView;

    private DrawerLayout mDrawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationDrawerView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationDrawerView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawers();

        // update the menu_main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (menuItem.getItemId()) {
            case R.id.navigation_item_dashboard:
                fragmentTransaction.replace(R.id.container, GoalsFragment.newInstance(1)).commit();
                return true;
            case R.id.navigation_item_explore:
                Intent newGoalIntent = new Intent(this, ExploreActivity.class);
                startActivityForResult(newGoalIntent, EXPLORE_REQUEST);
                break;
        }
        return false;
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
    }

}
