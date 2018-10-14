package com.example.chris.outapp.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {
    private String userId;
    private String userName;
    @Nullable
    private Map<String, Boolean> friends;
    @Nullable
    private Map<String, Boolean> destinations;

    public User() {
    }

    public User(String userId, String userName, Map<String, Boolean> friends, Map<String, Boolean> destinations) {
        this.userId = userId;
        this.userName = userName;
        this.friends = friends;
        this.destinations = destinations;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Nullable
    public Map<String, Boolean> getFriends() {
        return friends;
    }

    public void setFriends(@Nullable Map<String, Boolean> friends) {
        this.friends = friends;
    }

    @Nullable
    public Map<String, Boolean> getDestinations() {
        return destinations;
    }

    public void setDestinations(@Nullable Map<String,Boolean> destinations) {
        this.destinations = destinations;
    }
}

