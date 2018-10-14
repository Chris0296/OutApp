package com.example.chris.outapp.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.Utils;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.VenueRecyclerAdapter;
import com.example.chris.outapp.viewmodel.VenueViewModel;

import java.util.List;

public class VenueActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVenues;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

    private VenueViewModel venueViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);

        venueViewModel = ViewModelProviders.of(this).get(VenueViewModel.class);

        recyclerViewVenues = findViewById(R.id.recyclerVenues);
        recyclerViewVenues.setHasFixedSize(true);

        if(venueViewModel != null){
            LiveData<List<Venue>> venueLiveData = venueViewModel.getVenueLiveData();

            venueLiveData.observe(this, new Observer<List<Venue>>() {
                @Override
                public void onChanged(@Nullable List<Venue> venues) {
                    venues = Utils.sortVenuesByAttendees(venues);
                    recyclerManager = new LinearLayoutManager(VenueActivity.this);
                    recyclerViewVenues.setLayoutManager(recyclerManager);
                    recyclerAdapter = new VenueRecyclerAdapter(VenueActivity.this, venues, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Venue venue) {
                            Intent intent = new Intent(VenueActivity.this, VenueDetailActivity.class);
                            intent.putExtra("venue", venue);
                            startActivity(intent);
                        }

                        @Override
                        public void onItemClick(User friend) {
                            //null
                        }
                    });
                    recyclerViewVenues.setAdapter(recyclerAdapter);
                }
            });
        }
    }
}