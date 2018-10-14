package com.example.chris.outapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.User;

public class FriendDetailActivity extends AppCompatActivity {

    private TextView friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        friendName = findViewById(R.id.friendName);

        User friend = (User) getIntent().getSerializableExtra("friend");
        friendName.setText(friend.getUserName());
    }
}