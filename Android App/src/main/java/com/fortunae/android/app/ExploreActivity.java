package com.fortunae.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ExploreActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String[] mCategories;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mCategories = getResources().getStringArray(R.array.default_categories);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ExploreFragment (defined as a static inner class below).
            return ExploreFragment.newInstance(mCategories[position]);
        }

        @Override
        public int getCount() {
            return mCategories.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return mCategories[position].toUpperCase(l);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ExploreFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_CATEGORY = "section_category";

        private ListView mCategoryGoalsListView;
        private List<String> mCategoryGoalsList;
        private ArrayAdapter<String> mCategoryGoalsListAdapter;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ExploreFragment newInstance(String sectionCategory) {
            ExploreFragment fragment = new ExploreFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_CATEGORY, sectionCategory);
            fragment.setArguments(args);
            return fragment;
        }

        public ExploreFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_explore, container, false);

            mCategoryGoalsListView = (ListView) rootView.findViewById(R.id.listview);

            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mCategoryGoalsList = new ArrayList<>();
            ParseQuery<ParseObject> categoryGoalsListQuery = ParseQuery.getQuery("Goal");
            categoryGoalsListQuery.whereEqualTo("Category", getArguments().getString(ARG_SECTION_CATEGORY));
            categoryGoalsListQuery.orderByDescending("updatedAt");
            categoryGoalsListQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> categoryGoals, ParseException e) {
                    if (e == null) {
                        for (ParseObject categoryGoal : categoryGoals) {
                            mCategoryGoalsList.add(categoryGoal.getString("Action"));
                        }
                        mCategoryGoalsListAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_list_item_1, android.R.id.text1, mCategoryGoalsList);
                        mCategoryGoalsListView.setAdapter(mCategoryGoalsListAdapter);
                    }
                }
            });
        }
    }

}
