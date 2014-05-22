package com.fortunae.android.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Goal")
public class Goal extends ParseObject {
    public static final String CREATED_BY = "goalCreatedBy";
    public static final String TITLE = "goalTitle";
    public static final String DESCRIPTION = "goalDescription";
    public static final String CATEGORY = "goalCategory";
    public static final String COMPLETED = "goalCompleted";
    public static final String PRIVATE = "goalPrivate";

    public ParseUser getCreatedBy() {
        return (ParseUser) get(CREATED_BY);
    }

    public void setCreatedBy(ParseUser user) {
        put(CREATED_BY, user);
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public void setTitle(String goalTitle) {
        put(TITLE, goalTitle);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public void setDescription(String goalDescription) {
        put(DESCRIPTION, goalDescription);
    }

    public String getCategory() {
        return getString(CATEGORY);
    }

    public void setCategory(String goalCategory) {
        put(CATEGORY, goalCategory);
    }

    public boolean isCompleted() {
        return getBoolean(COMPLETED);
    }

    public void setCompleted(boolean completed) {
        put(COMPLETED, completed);
    }

    public boolean isPrivate() {
        return getBoolean(PRIVATE);
    }

    public void setPrivate(boolean isPrivate) {
        put(PRIVATE, isPrivate);
    }
}
