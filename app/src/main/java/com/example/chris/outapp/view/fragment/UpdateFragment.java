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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateFragment extends Fragment {
    private Spinner spinnerUserIAm;
    private EditText editTextNewName;
    private Button buttonUpdate;
    private UserViewModel userViewModel;
    private OutGoerViewModel outGoerViewModel;
    private UserAdapter userAdapter;
    private User currentUser;
    public UpdateFragment() {
        // Required empty public constructor
    }
    public static UpdateFragment newInstance(){
        return new UpdateFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_update, container, false);
        spinnerUserIAm = fragmentView.findViewById(R.id.spinnerWhoAmI);
        editTextNewName = fragmentView.findViewById(R.id.editTextNewName);
        buttonUpdate = fragmentView.findViewById(R.id.buttonUpdate);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        outGoerViewModel = ViewModelProviders.of(this).get(OutGoerViewModel.class);
        if(userViewModel != null){
            LiveData<List<User>> userLiveData = userViewModel.getUserLiveData();
            userLiveData.observe(UpdateFragment.this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> userList) {
                    userAdapter = new UserAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, userList);
                    userAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerUserIAm.setAdapter(userAdapter);
                }
            });
        }
        spinnerUserIAm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                currentUser = userAdapter.getItem(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextNewName.getText().toString().length() != 0 && editTextNewName.getText() != null){
                    Map<String, Object> newNameMap = new HashMap<>();
                    newNameMap.put("userName", editTextNewName.getText().toString());
                    userViewModel.updateUser(currentUser, newNameMap);
                    outGoerViewModel.updateOutGoerUser(currentUser, newNameMap);
                }
            }
        });
        return fragmentView;
    }
}