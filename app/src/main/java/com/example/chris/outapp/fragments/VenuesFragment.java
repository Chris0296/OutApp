package com.example.chris.outapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.outapp.R;

public class VenuesFragment extends Fragment {

    public VenuesFragment() {
        // Required empty public constructor
    }

    public static VenuesFragment newInstance(){
        return new VenuesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venues, container, false);
    }

}
