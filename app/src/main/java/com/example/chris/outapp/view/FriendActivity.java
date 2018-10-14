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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.chris.outapp.R;
import com.example.chris.outapp.Utils;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.FriendRecyclerAdapter;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.viewmodel.UserViewModel;

import java.util.List;

public class FriendActivity extends AppCompatActivity {

    private Spinner spinnerCurrentUser;
    private UserAdapter userAdapter;
    private User currentUser;

    private RecyclerView recyclerViewFriends;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        spinnerCurrentUser = findViewById(R.id.spinnerCurrentUser);
        recyclerViewFriends = findViewById(R.id.recyclerFriends);
        recyclerViewFriends.setHasFixedSize(true);

        if(userViewModel != null){
            LiveData<List<User>> userLiveData = userViewModel.getUserLiveData();

            userLiveData.observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> users) {
                    userAdapter = new UserAdapter(FriendActivity.this, R.layout.support_simple_spinner_dropdown_item,users);
                    userAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerCurrentUser.setAdapter(userAdapter);
                }
            });
        }

        spinnerCurrentUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentUser = userAdapter.getItem(i);
                if(userViewModel != null){
                    LiveData<List<User>> friendLiveData = userViewModel.getFriendLiveData(currentUser.getFriends().keySet());

                    friendLiveData.observe(FriendActivity.this, new Observer<List<User>>() {
                        @Override
                        public void onChanged(@Nullable List<User> friends) {
                            friends = Utils.sortFriendsByDestinations(friends);
                            recyclerManager = new LinearLayoutManager(FriendActivity.this);
                            recyclerViewFriends.setLayoutManager(recyclerManager);
                            recyclerAdapter = new FriendRecyclerAdapter(FriendActivity.this, friends, new OnItemClickListener() {
                                @Override
                                public void onItemClick(Venue venue) {
                                    //null
                                }

                                @Override
                                public void onItemClick(User friend) {
                                    Intent intent = new Intent(FriendActivity.this, FriendDetailActivity.class);
                                    intent.putExtra("friend", friend);
                                    startActivity(intent);
                                }
                            });
                            recyclerViewFriends.setAdapter(recyclerAdapter);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
