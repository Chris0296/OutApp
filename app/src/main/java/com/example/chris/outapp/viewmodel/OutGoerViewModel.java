package com.example.chris.outapp.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.network.FirebaseQueryLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutGoerViewModel extends ViewModel {

    private static final String TAG = OutGoerViewModel.class.getSimpleName();

    private static final DatabaseReference OUTGOER_REF = FirebaseDatabase.getInstance().getReference().child("OutGoer");

    List<String> idList = new ArrayList<>();

    private List<OutGoer> outGoerList = new ArrayList<>();

    @NonNull
    public LiveData<List<OutGoer>> getOutGoerLiveData(String viewerId){
        FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(OUTGOER_REF.child(viewerId));
        LiveData<List<OutGoer>> outGoerLiveData = Transformations.map(liveData, new Deserialiser());
        return outGoerLiveData;
    }

    private class Deserialiser implements Function<DataSnapshot, List<OutGoer>> {
        @Override
        public List<OutGoer> apply(DataSnapshot dataSnapshot) {
            outGoerList.clear();
            for(DataSnapshot dsp: dataSnapshot.getChildren()) {
                String userID = dsp.getKey();
                Log.i(TAG + "User ID: ", userID);
                for(DataSnapshot abc: dsp.getChildren()){
                    String venueID = abc.getKey();
                    Log.i(TAG + " Venue ID: ", venueID);
                    OutGoer outGoer = abc.getValue(OutGoer.class);
                    outGoerList.add(outGoer);
                }
            }
            return outGoerList;
        }
    }

    public List<String> getIds(){
        OUTGOER_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp: dataSnapshot.getChildren()){
                    idList.add(dsp.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return idList;
    }

    public void onUserGoingOut(User selectedUser, Venue selectedVenue){
        final OutGoer outGoer = new OutGoer(selectedUser.getUserId(), selectedUser.getUserName(), selectedVenue.getVenueId(),
                selectedVenue.getVenueName(), System.currentTimeMillis());

        for(String friendKey: selectedUser.getFriends().keySet()){
            OUTGOER_REF.child(friendKey).child(selectedUser.getUserId()).child(selectedVenue.getVenueId()).setValue(outGoer);
        }
    }

    public void updateOutGoerUser(User currentUser, Map<String, Object> newNameMap){
        for(String userKey: currentUser.getFriends().keySet()){
            for(String venueKey: currentUser.getDestinations().keySet()){
                OUTGOER_REF.child(userKey).child(currentUser.getUserId()).child(venueKey).updateChildren(newNameMap);
            }
        }
    }
}