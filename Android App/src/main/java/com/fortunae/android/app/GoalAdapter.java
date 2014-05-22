package com.fortunae.android.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class GoalAdapter extends ParseQueryAdapter<Goal> {

    public GoalAdapter(Context context, final GoalQuery goalQuery) {
        super(context, new ParseQueryAdapter.QueryFactory<Goal>() {
            public ParseQuery<Goal> create() {
                return goalQuery;
            }
        });
    }

    @Override
    public View getItemView(Goal goal, View view, ViewGroup parent) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.item_goal_list, null);
        }

        super.getItemView(goal, view, parent);

        ParseImageView userProfileIcon = (ParseImageView) view.findViewById(R.id.icon);
        ParseFile photoFile = goal.getParseFile("photo");
        if (photoFile != null) {
            userProfileIcon.setParseFile(photoFile);
            userProfileIcon.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView usernameTextView = (TextView) view.findViewById(R.id.username);
        String username = goal.getCreatedBy().getUsername();
        if (username != null) {
            usernameTextView.setText(username);
        }

        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        String title = goal.getTitle();
        if (title != null) {
            titleTextView.setText(title);
        }

        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
        String description = goal.getDescription();
        if (description != null) {
            descriptionTextView.setText(description);
        }

        return view;
    }
}
