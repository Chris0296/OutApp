package com.example.chris.outapp.model;

import java.io.Serializable;

public class OutGoer implements Serializable {
    private String userId;
    private String userName;
    private String venueId;
    private String venueName;
    private Long timeMillis;

    public OutGoer() {
    }

    public OutGoer(String userId, String venueId, Long timeMillis) {
        this.userId = userId;
        this.venueId = venueId;
        this.timeMillis = timeMillis;
    }

    public OutGoer(String userId, String userName, String venueId, String venueName, Long timeMillis) {
        this.userId = userId;
        this.userName = userName;
        this.venueId = venueId;
        this.venueName = venueName;
        this.timeMillis = timeMillis;
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

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(Long timeMillis) {
        this.timeMillis = timeMillis;
    }
}
