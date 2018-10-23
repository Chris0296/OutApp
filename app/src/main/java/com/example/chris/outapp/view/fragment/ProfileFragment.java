package com.example.chris.outapp.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chris.outapp.MainApplication;
import com.example.chris.outapp.R;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.ProfileDestinationRecyclerAdapter;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.view.MainActivity;
import com.example.chris.outapp.view.OnItemClickListener;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;
import com.example.chris.outapp.viewmodel.VenueViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private EditText editTextNewName;
    private RecyclerView recyclerViewDestinations;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private Button buttonUpdate;

    private UserViewModel userViewModel;
    private VenueViewModel venueViewModel;
    private OutGoerViewModel outGoerViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(R.string.update);
        ((MainActivity) getActivity()).setDisplayHomeAsUpEnabled(false);

        editTextNewName = fragmentView.findViewById(R.id.editTextNewName);
        recyclerViewDestinations = fragmentView.findViewById(R.id.recyclerDestinations);
        recyclerViewDestinations.setHasFixedSize(true);
        buttonUpdate = fragmentView.findViewById(R.id.buttonUpdate);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        venueViewModel = ViewModelProviders.of(this).get(VenueViewModel.class);
        outGoerViewModel = ViewModelProviders.of(this).get(OutGoerViewModel.class);

        editTextNewName.setText(MainApplication.getCurrentUser().getUserName());

        if(venueViewModel != null){
            LiveData<List<Venue>>destinationVenues = venueViewModel.getDestinationVenueLiveData();
            destinationVenues.observe(this, new Observer<List<Venue>>() {
                @Override
                public void onChanged(@Nullable List<Venue> destinations) {
                    recyclerLayoutManager = new LinearLayoutManager(getContext());
                    recyclerViewDestinations.setLayoutManager(recyclerLayoutManager);
                    recyclerAdapter = new ProfileDestinationRecyclerAdapter(getContext(), destinations, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Venue venue) {
                        }
                        @Override
                        public void onItemClick(User friend) {
                        }
                        @Override
                        public void onItemClick(OutGoer outGoer) {
                        }
                    });
                    recyclerViewDestinations.setAdapter(recyclerAdapter);
                }
            });
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextNewName.getText().toString().length() != 0 && editTextNewName.getText() != null){
                    Map<String, Object> newNameMap = new HashMap<>();
                    newNameMap.put("userName", editTextNewName.getText().toString());
                    userViewModel.updateUser(MainApplication.getCurrentUser(), newNameMap);
                    outGoerViewModel.updateOutGoerUser(MainApplication.getCurrentUser(), newNameMap);
                }
            }
        });

        return fragmentView;
    }

}