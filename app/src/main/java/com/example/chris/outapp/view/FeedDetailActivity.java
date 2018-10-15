package com.example.chris.outapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.OutGoer;

public class FeedDetailActivity extends AppCompatActivity {
    private TextView textViewDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);
        textViewDetails = findViewById(R.id.textViewDetails);
        OutGoer outGoer = (OutGoer) getIntent().getSerializableExtra("outGoer");
        textViewDetails.setText(outGoer.getUserName() + " is going to " + outGoer.getVenueName());
    }
}