package com.example.chris.outapp.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.adapter.OutGoerAdapter;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;

import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = FeedActivity.class.getSimpleName();

    private Spinner spinnerUserIAm;
    private UserAdapter userAdapter;
    private OutGoerAdapter outGoerAdapter;
    private ListView listViewOutGoers;

    private UserViewModel userViewModel;
    private OutGoerViewModel outGoerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        spinnerUserIAm = findViewById(R.id.spinnerUserIAm);
        listViewOutGoers = findViewById(R.id.listViewOutGoers);

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
                            outGoerAdapter = new OutGoerAdapter(getApplicationContext(), R.layout.view_feed_item, outGoers);
                            listViewOutGoers.setAdapter(outGoerAdapter);
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