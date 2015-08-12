package com.fortunae.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ExploreActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RVAdapter mRecyclerViewAdapter;

    private Toolbar mToolbar;
    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newGoalIntent = new Intent(ExploreActivity.this, NewGoalActivity.class);
                startActivityForResult(newGoalIntent, NEW_GOAL_REQUEST);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ParseQuery<ParseObject> categoryGoalsListQuery = ParseQuery.getQuery("Goal");
        categoryGoalsListQuery.orderByDescending("updatedAt");
        categoryGoalsListQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> categoryGoals, ParseException e) {
                if (e == null) {
                    List<String> goalsList = new ArrayList<>();
                    for (ParseObject categoryGoal : categoryGoals) {
                        goalsList.add(categoryGoal.getString("Action"));
                    }
                    mRecyclerViewAdapter = new RVAdapter(goalsList);
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);

                    mToolbar.inflateMenu(R.menu.menu_main);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public class RVAdapter extends RecyclerView.Adapter<GoalViewHolder> {
        private List<String> mGoalsList;

        public RVAdapter(List<String> goalsList) {
            mGoalsList = goalsList;
        }

        @Override
        public GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            GoalViewHolder pvh = new GoalViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(GoalViewHolder holder, int position) {
            holder.action.setText(mGoalsList.get(position));
        }

        @Override
        public int getItemCount() {
            return mGoalsList.size();
        }
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView action;

        GoalViewHolder(View itemView) {
            super(itemView);
            action = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}
