package com.example.chris.outapp.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.outapp.MainApplication;
import com.example.chris.outapp.R;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.view.MainActivity;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;
import com.example.chris.outapp.viewmodel.VenueViewModel;

import java.util.List;

public class VenueDetailFragment extends Fragment {

    private TextView textViewVenueName;
    private ToggleButton tglUserGoingOut;
    private UserViewModel userViewModel;
    private VenueViewModel venueViewModel;
    private OutGoerViewModel outGoerViewModel;

    public VenueDetailFragment() {
        // Required empty public constructor
    }
    public static VenueDetailFragment newInstance(){
        return new VenueDetailFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_venue_detail, container, false);

        ((MainActivity) getActivity()).setDisplayHomeAsUpEnabled(true);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        venueViewModel = ViewModelProviders.of(this).get(VenueViewModel.class);
        outGoerViewModel = ViewModelProviders.of(this).get(OutGoerViewModel.class);
        textViewVenueName = fragmentView.findViewById(R.id.textViewVenueName);
        tglUserGoingOut = fragmentView.findViewById(R.id.tglUserGoingOut);
        Venue chosenVenue = (Venue) getArguments().getSerializable("venue");
        textViewVenueName.setText(chosenVenue.getVenueName());

        if(MainApplication.getCurrentUser().getDestinations() != null){
            for(String venueID: MainApplication.getCurrentUser().getDestinations().keySet()){
                if(venueID.equals(chosenVenue.getVenueId())){
                    tglUserGoingOut.setChecked(true);
                    break;
                } else {
                    tglUserGoingOut.setChecked(false);
                }
            }
        } else {
            tglUserGoingOut.setChecked(false);
        }

        tglUserGoingOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(tglUserGoingOut.isPressed()){
                    if(checked){
                        userViewModel.addDestination(MainApplication.getCurrentUser(), chosenVenue);
                        venueViewModel.addAttendee(MainApplication.getCurrentUser(), chosenVenue);
                        outGoerViewModel.createOutGoer(MainApplication.getCurrentUser(), chosenVenue);
                    } else {
                        userViewModel.removeDestination(MainApplication.getCurrentUser(), chosenVenue);
                        venueViewModel.removeAttendee(MainApplication.getCurrentUser(), chosenVenue);
                        outGoerViewModel.deleteOutGoer(MainApplication.getCurrentUser(), chosenVenue);
                    }
                }
            }
        });
        ((MainActivity) getActivity()).setActionBarTitle(chosenVenue.getVenueName());

        return fragmentView;
    }
}