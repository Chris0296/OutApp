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
import android.widget.Button;
import android.widget.Spinner;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.view.MainActivity;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;
import com.example.chris.outapp.viewmodel.VenueViewModel;

import java.util.List;

public class GoingOutFragment extends Fragment {
    private Spinner spinnerUser1;
    private Spinner spinnerUser2;
    private Button buttonAdd;
    private UserViewModel userViewModel;
    private VenueViewModel venueViewModel;
    private OutGoerViewModel outGoerViewModel;
    private UserAdapter userAdapter;
    private UserAdapter otherUserAdapter;
    private User selectedUser;
    private User otherSelectedUser;
    public GoingOutFragment() {
        // Required empty public constructor
    }
    public static GoingOutFragment newInstance(){
        return new GoingOutFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_going_out, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(R.string.going_out);
        ((MainActivity) getActivity()).setDisplayHomeAsUpEnabled(false);

        spinnerUser1 = fragmentView.findViewById(R.id.spinnerUser1);
        spinnerUser2 = fragmentView.findViewById(R.id.spinnerUser2);
        buttonAdd = fragmentView.findViewById(R.id.buttonAdd);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        venueViewModel = ViewModelProviders.of(this).get(VenueViewModel.class);
        outGoerViewModel = ViewModelProviders.of(this).get(OutGoerViewModel.class);
        if(userViewModel != null){
            LiveData<List<User>> userLiveData = userViewModel.getUserLiveData();
            userLiveData.observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> users) {
                    userAdapter = new UserAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, users);
                    userAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerUser1.setAdapter(userAdapter);
                    otherUserAdapter = new UserAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, users);
                    otherUserAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerUser2.setAdapter(otherUserAdapter);
                }
            });
        }
        spinnerUser1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUser = userAdapter.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerUser2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                otherSelectedUser = otherUserAdapter.getItem(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.createFriendship(selectedUser, otherSelectedUser);
            }
        });
        return fragmentView;
    }
}