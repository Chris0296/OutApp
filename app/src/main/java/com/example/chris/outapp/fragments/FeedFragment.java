package com.example.chris.outapp.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.outapp.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import com.example.chris.outapp.databinding.FragmentFeedBinding;

import org.json.JSONArray;

public class FeedFragment extends Fragment {

    FragmentFeedBinding binding;
    GraphRequest friendRequest;
    AccessToken accessToken;

    //my branch

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(){
        return new FeedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        View view = binding.getRoot();

        accessToken = AccessToken.getCurrentAccessToken();

//        friendRequest = new GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback(){
//
//            @Override
//            public void onCompleted(JSONArray objects, GraphResponse response) {
//
//            }
//        });

        GraphRequest graphRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{friend-list-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {

                    }
                }
        );

        Profile userProfile = Profile.getCurrentProfile();
        binding.textViewMessage.setText(userProfile.getName());

        return view;
    }

}
