package com.example.chris.outapp.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.view.OnItemClickListener;

import java.util.List;

public class VenueRecyclerAdapter extends RecyclerView.Adapter<VenueRecyclerAdapter.VenueViewHolder> {

    private List<Venue> venueList;
    private LayoutInflater inflater;
    private final OnItemClickListener listener;
    private Context context;

    public static class VenueViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewNumAttendees;

        public VenueViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.venueName);
            textViewNumAttendees = itemView.findViewById(R.id.numAttendees);
        }

        public void bind(Venue venue, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(venue);
                }
            });
        }
    }

    Venue getItem(int id){
        return venueList.get(id);
    }

    public VenueRecyclerAdapter(Context context, List<Venue> venueList, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.venueList = venueList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VenueRecyclerAdapter.VenueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.view_venue_item, viewGroup, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder venueViewHolder, int i) {
        venueViewHolder.bind(venueList.get(i), listener);
        venueViewHolder.textViewName.setText(venueList.get(i).getVenueName());
        if(venueList.get(i).getAttendees() != null){
            if(venueList.get(i).getAttendees().size() == 1){
                venueViewHolder.textViewNumAttendees.setText(
                        venueList.get(i).getAttendees().size() + context.getString(R.string.attendee));
            } else {
                venueViewHolder.textViewNumAttendees.setText(
                        venueList.get(i).getAttendees().size() + context.getString(R.string.attendees));
            }
        } else {
            venueViewHolder.textViewNumAttendees.setText(R.string.noAttendees);
        }
    }

    @Override
    public int getItemCount() {
        if(venueList != null){
            return venueList.size();
        } else {
            return 0;
        }
    }
}
