package com.example.chris.outapp.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.chris.outapp.R;

import com.example.chris.outapp.databinding.ListItemVenueBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VenueAdapter extends ArrayAdapter<Venue> {

    ListItemVenueBinding listItemVenueBinding;
    DatabaseReference reference;
    Venue venue;

    public VenueAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Venue getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Venue item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.list_item_venue,parent);
        }

        venue = getItem(position);

        if(venue != null){

            reference.child("Venues").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dsp: dataSnapshot.getChildren()){
                        venue = dsp.getValue(Venue.class);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            listItemVenueBinding.textVenueName.setText(venue.getName());
            listItemVenueBinding.textVenueAttendees.setText(venue.getAttendees());
            listItemVenueBinding.textVenueCover.setText(venue.getCover());
        }

        return convertView;
    }
}
