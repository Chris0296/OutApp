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
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.outapp.MainApplication;
import com.example.chris.outapp.R;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.model.adapter.VenueFriendRecyclerAdapter;
import com.example.chris.outapp.view.OnItemClickListener;
import com.example.chris.outapp.view.MainActivity;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;
import com.example.chris.outapp.viewmodel.VenueViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VenueDetailFragment extends Fragment {

    private TextView textViewVenueName;
    private ToggleButton tglUserGoingOut;
    // --
    private RecyclerView recyclerViewVenueFriends;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    // --
    private UserViewModel userViewModel;
    private VenueViewModel venueViewModel;
    private OutGoerViewModel outGoerViewModel;

    private UserAdapter userAdapter;
    private User currentUser;


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

        // ---
        recyclerViewVenueFriends = fragmentView.findViewById(R.id.recyclerVenueFriends);

        // ---

        //spinnerCurrentUser = fragmentView.findViewById(R.id.spinnerUserThatIAm);

        textViewVenueName = fragmentView.findViewById(R.id.textViewVenueName);
        tglUserGoingOut = fragmentView.findViewById(R.id.tglUserGoingOut);
        Venue chosenVenue = (Venue) getArguments().getSerializable("venue");
        textViewVenueName.setText(chosenVenue.getVenueName());

        if(userViewModel != null && chosenVenue != null){
            LiveData<List<User>> userLiveData = userViewModel.getUserLiveData();
            userLiveData.observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> users) {

                    List<User> venueAttendeeUsers = new ArrayList<>();

                    if(chosenVenue.getAttendees() != null) {
                        List<String> venueAttendees = new ArrayList<String>(chosenVenue.getAttendees().keySet());
                        //List<String> currentUserFriendsList = new ArrayList<String>(currentUser.getFriends().keySet());

                        // Loop through friendsList or venueAttendeeList first ??
                        // Whichever list is smaller - friendsList or venueAttendeeList ??
                        // I think friendsList should be smaller in theory

                        for (User user : users) {
                            for (int i = 0; i < venueAttendees.size(); i++) {

                                String currentUserId = user.getUserId();
                                String currentVenueAtt = venueAttendees.get(i);
                                //String currentFriend = currenUserFriendsList.get(i);


                                if (venueAttendees.get(i).equals(user.getUserId())) {
                                    venueAttendeeUsers.add(user);
                                    venueAttendees.remove(i);
                                    //currentUserFriendsList.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                    if(venueAttendeeUsers.size() == 0) {
                        User blankUser = new User();
                        venueAttendeeUsers.add(blankUser);
                    }


                    userAdapter = new UserAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, users);
                    userAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    //spinnerCurrentUser.setAdapter(userAdapter);

                    recyclerManager = new LinearLayoutManager(getContext());
                    recyclerViewVenueFriends.setLayoutManager(recyclerManager);
                    recyclerAdapter = new VenueFriendRecyclerAdapter(getContext(), venueAttendeeUsers, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Venue venue) {
                            // null
                        }
                        @Override
                        public void onItemClick(User friend) {
                            /*
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", friend);
                            VenueDetailFragment venueDetailFragment= VenueDetailFragment.newInstance();
                            venueDetailFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.framelayoutContainer, venueDetailFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            */
                        }
                        @Override
                        public void onItemClick(OutGoer outGoer) {
                            //null
                        }
                    });
                    recyclerViewVenueFriends.setAdapter(recyclerAdapter);
                }
            });
        }

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