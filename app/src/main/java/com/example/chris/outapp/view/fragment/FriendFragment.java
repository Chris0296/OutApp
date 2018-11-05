package com.example.chris.outapp.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.chris.outapp.MainApplication;
import com.example.chris.outapp.R;
import com.example.chris.outapp.Utils;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.FriendRecyclerAdapter;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.view.MainActivity;
import com.example.chris.outapp.view.OnItemClickListener;
import com.example.chris.outapp.viewmodel.UserViewModel;

import java.util.List;

public class FriendFragment extends Fragment {

    private RecyclerView recyclerViewFriends;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private UserViewModel userViewModel;
    public FriendFragment() {
        // Required empty public constructor
    }
    public static FriendFragment newInstance(){
        return new FriendFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_friend, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(R.string.friends);
        ((MainActivity) getActivity()).setDisplayHomeAsUpEnabled(false);

        recyclerViewFriends = fragmentView.findViewById(R.id.recyclerFriends);
        recyclerViewFriends.setHasFixedSize(true);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if(userViewModel != null){
            LiveData<List<User>> friendLiveData = userViewModel.getFriendLiveData(MainApplication.getCurrentUser().getFriends().keySet());
            friendLiveData.observe(FriendFragment.this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> friends) {
                    friends = Utils.sortFriendsByDestinations(friends);
                    recyclerManager = new LinearLayoutManager(
                            getContext());
                    recyclerViewFriends.setLayoutManager(recyclerManager);
                    recyclerAdapter = new FriendRecyclerAdapter(getContext(), friends, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Venue venue) {
                            //null
                        }
                        @Override
                        public void onItemClick(User friend) {
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("friend", friend);
                            FriendDetailFragment friendDetailFragment= FriendDetailFragment.newInstance();
                            friendDetailFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.framelayoutContainer, friendDetailFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        @Override
                        public void onItemClick(OutGoer outGoer) {
                            //null
                        }
                    });
                    recyclerViewFriends.setAdapter(recyclerAdapter);
                }
            });
        }

        return fragmentView;
    }
}