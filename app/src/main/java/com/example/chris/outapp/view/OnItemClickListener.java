package com.example.chris.outapp.view;

import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;

public interface OnItemClickListener {

    void onItemClick(Venue venue);

    void onItemClick(User friend);

}
