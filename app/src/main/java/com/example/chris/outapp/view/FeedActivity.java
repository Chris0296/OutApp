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
import android.widget.ListView;
import android.widget.Spinner;

import com.example.chris.outapp.R;
import com.example.chris.outapp.Utils;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.OutGoerAdapter;
import com.example.chris.outapp.model.adapter.OutGoerRecyclerAdapter;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;

import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = FeedActivity.class.getSimpleName();

    private Spinner spinnerUserIAm;
    private UserAdapter userAdapter;

    private RecyclerView recyclerViewOutGoers;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;

    private UserViewModel userViewModel;
    private OutGoerViewModel outGoerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        spinnerUserIAm = findViewById(R.id.spinnerUserIAm);
        recyclerViewOutGoers = findViewById(R.id.recyclerViewOutGoers);
        recyclerViewOutGoers.setHasFixedSize(true);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        outGoerViewModel = ViewModelProviders.of(this).get(OutGoerViewModel.class);

        if(userViewModel != null){
            LiveData<List<User>> userLiveData = userViewModel.getUserLiveData();

            userLiveData.observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> userList) {
                    userAdapter = new UserAdapter(FeedActivity.this, R.layout.support_simple_spinner_dropdown_item, userList);
                    userAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerUserIAm.setAdapter(userAdapter);
                }
            });
        }

        spinnerUserIAm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(outGoerViewModel != null){
                    LiveData<List<OutGoer>> outGoerLiveData = outGoerViewModel.getOutGoerLiveData(userAdapter.getItem(i).getUserId());

                    outGoerLiveData.observe(FeedActivity.this, new Observer<List<OutGoer>>() {
                        @Override
                        public void onChanged(@Nullable List<OutGoer> outGoers) {
                            outGoers = Utils.sortOutGoersByTime(outGoers);
                            recyclerLayoutManager = new LinearLayoutManager(FeedActivity.this);
                            recyclerViewOutGoers.setLayoutManager(recyclerLayoutManager);
                            recyclerAdapter = new OutGoerRecyclerAdapter(FeedActivity.this, outGoers, new OnItemClickListener() {
                                @Override
                                public void onItemClick(Venue venue) {
                                    //null
                                }

                                @Override
                                public void onItemClick(User friend) {
                                    //null
                                }

                                @Override
                                public void onItemClick(OutGoer outGoer) {
                                    Intent intent = new Intent(FeedActivity.this, FeedDetailActivity.class);
                                    intent.putExtra("outGoer", outGoer);
                                    startActivity(intent);
                                }
                            });
                            recyclerViewOutGoers.setAdapter(recyclerAdapter);
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