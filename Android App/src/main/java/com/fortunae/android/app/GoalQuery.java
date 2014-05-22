package com.fortunae.android.app;

import com.parse.ParseQuery;

import java.io.Serializable;

/**
 * Class GoalQuery provides convenience methods for setting up common goal queries.
 */
public class GoalQuery extends ParseQuery<Goal> implements Serializable {
    public final static String TAG = GoalQuery.class.getSimpleName();

    public GoalQuery() {
        super(Goal.class);
    }

    public GoalQuery setCompleted(boolean completed) {
        return (GoalQuery) this.whereEqualTo(Goal.COMPLETED, completed);
    }

    public GoalQuery setPrivate(boolean isPrivate) {
        return (GoalQuery) this.whereEqualTo(Goal.PRIVATE, isPrivate);
    }

    public GoalQuery setCategory(String category) {
        return (GoalQuery) this.whereEqualTo(Goal.CATEGORY, category);
    }

}
