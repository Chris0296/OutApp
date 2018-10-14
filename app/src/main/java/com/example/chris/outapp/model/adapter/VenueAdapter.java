package com.example.chris.outapp.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chris.outapp.model.Venue;

import java.util.List;

public class VenueAdapter extends ArrayAdapter {
    private Context context;
    private List<Venue> venues;

    public VenueAdapter(@NonNull Context context, int resource, List<Venue> venues) {
        super(context, resource, venues);
        this.context = context;
        this.venues = venues;
    }

    @Override
    public int getCount() {
        return venues.size();
    }

    @Nullable
    @Override
    public Venue getItem(int position) {
        return venues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(venues.get(position).getVenueName());
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(venues.get(position).getVenueName());
        return textView;
    }
}
