package com.example.chris.outapp;

import android.content.Context;
import android.content.res.Resources;

import com.example.chris.outapp.model.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String calculateTimeSince(Context context, Long timeMillis){
        long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - timeMillis);
        if(seconds < 60){
            return seconds + context.getString(R.string.seconds);
        } else if(seconds < 3600){
            double d = Math.floor(seconds/ 60);
            Long l = (long) d;
            return l + context.getString(R.string.minutes);
        } else if(seconds < 86400){
            double d = Math.floor(seconds/ 3600);
            Long l = (long) d;
            return l + context.getString(R.string.hours);
        } else if(seconds < 604800){
            double d = Math.floor(seconds/ 86400);
            Long l = (long) d;
            return l + context.getString(R.string.days);
        } else if(seconds < 31536000){
            double d = Math.floor(seconds/ 604800);
            Long l = (long) d;
            return l + context.getString(R.string.weeks);
        } else {
            double d = Math.floor(seconds/ 31536000);
            Long l = (long) d;
            return l + context.getString(R.string.years);
        }
    }

    public static List<User> sortFriendsByDestinations(List<User> friends){
        Collections.sort(friends, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                if(user1.getDestinations() == null){
                    return (user2.getDestinations() == null) ? 0 : 1;
                }
                if(user2.getDestinations() == null){
                    return -1;
                }

                if(user1.getDestinations().size() > user2.getDestinations().size()){
                    return -1;
                } else if (user1.getDestinations().size() < user2.getDestinations().size()){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return friends;
    }

    public static List<Venue> sortVenuesByAttendees(List<Venue> venues){
        Collections.sort(venues, new Comparator<Venue>() {
            @Override
            public int compare(Venue venue1, Venue venue2) {
                if(venue1.getAttendees() == null){
                    return (venue2.getAttendees() == null) ? 0 : 1;
                }
                if(venue2.getAttendees() == null){
                    return -1;
                }

                if(venue1.getAttendees().size() > venue2.getAttendees().size()){
                    return -1;
                } else if (venue1.getAttendees().size() < venue2.getAttendees().size()){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return venues;
    }


}
