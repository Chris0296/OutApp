package com.example.chris.outapp.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.chris.outapp.MainApplication;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.network.FirebaseQueryLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VenueViewModel extends ViewModel {
    private static final DatabaseReference VENUE_REF = FirebaseDatabase.getInstance().getReference().child("Venues");

    private List<Venue> venueList = new ArrayList<>();
    private List<Venue> destinationList = new ArrayList<>();


    @NonNull
    public LiveData<List<Venue>> getVenueLiveData(){
        FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(VENUE_REF);
        LiveData<List<Venue>> venuesLiveData = Transformations.map(liveData, new Deserialiser());
        return venuesLiveData;
    }

    public LiveData<List<Venue>> getDestinationVenueLiveData() {
        FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(VENUE_REF);
        LiveData<List<Venue>> destinationLiveData = Transformations.map(liveData, new DestinationDeserialiser());
        return destinationLiveData;
    }

    private class Deserialiser implements Function<DataSnapshot, List<Venue>> {
        @Override
        public List<Venue> apply(DataSnapshot dataSnapshot) {
            venueList.clear();
            for(DataSnapshot dsp: dataSnapshot.getChildren()){
                Venue venue = dsp.getValue(Venue.class);
                venueList.add(venue);
            }
            return venueList;
        }
    }

    private class DestinationDeserialiser implements Function<DataSnapshot, List<Venue>>{
        @Override
        public List<Venue> apply(DataSnapshot dataSnapshot) {
            destinationList.clear();
            for(DataSnapshot dsp: dataSnapshot.getChildren()){
                Venue destination = dsp.getValue(Venue.class);
                if(destination.getAttendees() != null && MainApplication.getCurrentUser().getDestinations() != null){
                    for(String venueId: MainApplication.getCurrentUser().getDestinations().keySet()){
                        if(venueId.equals(destination.getVenueId())){
                            destinationList.add(destination);
                        }
                    }
                }
            }
            return destinationList;
        }
    }

    public void addAttendee(User selectedUser, Venue selectedVenue){
        VENUE_REF.child(selectedVenue.getVenueId()).child("attendees").child(selectedUser.getUserId()).setValue(true);
    }

    public void createVenue(Venue newVenue){
        VENUE_REF.child(newVenue.getVenueId()).setValue(newVenue);
    }

    public void removeAttendee(User selectedUser, Venue selectedVenue){
        VENUE_REF.child(selectedVenue.getVenueId()).child("attendees").child(selectedUser.getUserId()).removeValue();
    }

    public void updateVenue(Venue currentVenue, Map<String, Object> newNameMap){
        VENUE_REF.child(currentVenue.getVenueId()).updateChildren(newNameMap);
    }
}