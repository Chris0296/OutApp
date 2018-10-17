package com.example.chris.outapp.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.OutGoer;

public class FeedDetailFragment extends Fragment {
    private TextView textViewDetails;
    public FeedDetailFragment() {
        // Required empty public constructor
    }
    public static FeedDetailFragment newInstance(){
        return new FeedDetailFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView  = inflater.inflate(R.layout.fragment_feed_detail, container, false);
        textViewDetails = fragmentView.findViewById(R.id.textViewDetails);
        OutGoer outGoer = (OutGoer) getArguments().getSerializable("outGoer");
        textViewDetails.setText(outGoer.getUserName() + " is going to " + outGoer.getVenueName());
        return fragmentView;
    }
}
