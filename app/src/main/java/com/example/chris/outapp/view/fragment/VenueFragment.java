package com.example.chris.outapp.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.outapp.R;
import com.example.chris.outapp.Utils;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.VenueRecyclerAdapter;
import com.example.chris.outapp.view.MainActivity;
import com.example.chris.outapp.view.OnItemClickListener;
import com.example.chris.outapp.viewmodel.VenueViewModel;

import java.util.List;

public class VenueFragment extends Fragment {
    private RecyclerView recyclerViewVenues;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private VenueViewModel venueViewModel;
    public VenueFragment() {
        // Required empty public constructor
    }
    public static VenueFragment newInstance(){
        return new VenueFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_venue, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(R.string.venues);
        ((MainActivity) getActivity()).setDisplayHomeAsUpEnabled(false);

        venueViewModel = ViewModelProviders.of(this).get(VenueViewModel.class);
        recyclerViewVenues = fragmentView.findViewById(R.id.recyclerVenues);
        recyclerViewVenues.setHasFixedSize(true);
        if(venueViewModel != null){
            LiveData<List<Venue>> venueLiveData = venueViewModel.getVenueLiveData();
            venueLiveData.observe(VenueFragment.this, new Observer<List<Venue>>() {
                @Override
                public void onChanged(@Nullable List<Venue> venues) {
                    venues = Utils.sortVenuesByAttendees(venues);
                    recyclerManager = new LinearLayoutManager(getContext());
                    recyclerViewVenues.setLayoutManager(recyclerManager);
                    recyclerAdapter = new VenueRecyclerAdapter(getContext(), venues, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Venue venue) {
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("venue", venue);
                            VenueDetailFragment venueDetailFragment= VenueDetailFragment.newInstance();
                            venueDetailFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.framelayoutContainer, venueDetailFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        @Override
                        public void onItemClick(User friend) {
                            //null
                        }
                        @Override
                        public void onItemClick(OutGoer outGoer) {
                            //null
                        }
                    });
                    recyclerViewVenues.setAdapter(recyclerAdapter);
                }
            });
        }
        return fragmentView;
    }
}