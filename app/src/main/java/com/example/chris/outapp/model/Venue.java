package com.example.chris.outapp.model;


public class Venue {
    private int id;
    private String name;
    private String cover;
    private int attendees;

    public Venue(int id, String name, String cover, int attendees) {
        this.id = id;
        this.name = name;
        this.cover = cover;
        this.attendees = attendees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }
}
