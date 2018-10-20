package com.example.chris.outapp.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import com.example.chris.outapp.R;
import com.example.chris.outapp.Utils;
import com.example.chris.outapp.model.OutGoer;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.model.adapter.OutGoerRecyclerAdapter;
import com.example.chris.outapp.model.adapter.UserAdapter;
import com.example.chris.outapp.view.MainActivity;
import com.example.chris.outapp.view.OnItemClickListener;
import com.example.chris.outapp.viewmodel.OutGoerViewModel;
import com.example.chris.outapp.viewmodel.UserViewModel;

import java.util.List;

public class FeedFragment extends Fragment {
    private static final String TAG = FeedFragment.class.getSimpleName();
    private Spinner spinnerUserIAm;
    private UserAdapter userAdapter;
    private RecyclerView recyclerViewOutGoers;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private UserViewModel userViewModel;
    private OutGoerViewModel outGoerViewModel;
    public FeedFragment() {
        // Required empty public constructor
    }
    public static FeedFragment newInstance() {
        return new FeedFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_feed, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(R.string.feed);
        ((MainActivity) getActivity()).setDisplayHomeAsUpEnabled(false);

        spinnerUserIAm = fragmentView.findViewById(R.id.spinnerUserIAm);
        recyclerViewOutGoers = fragmentView.findViewById(R.id.recyclerViewOutGoers);
        recyclerViewOutGoers.setHasFixedSize(true);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        outGoerViewModel = ViewModelProviders.of(this).get(OutGoerViewModel.class);
        if(userViewModel != null){
            LiveData<List<User>> userLiveData = userViewModel.getUserLiveData();
            userLiveData.observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> userList) {
                    userAdapter = new UserAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, userList);
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
                    outGoerLiveData.observe(FeedFragment.this, new Observer<List<OutGoer>>() {
                        @Override
                        public void onChanged(@Nullable List<OutGoer> outGoers) {
                            outGoers = Utils.sortOutGoersByTime(outGoers);
                            recyclerLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewOutGoers.setLayoutManager(recyclerLayoutManager);
                            recyclerAdapter = new OutGoerRecyclerAdapter(getContext(), outGoers, new OnItemClickListener() {
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
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("outGoer", outGoer);
                                    FeedDetailFragment feedDetailFragment= FeedDetailFragment.newInstance();
                                    feedDetailFragment.setArguments(bundle);
                                    fragmentTransaction.replace(R.id.framelayoutContainer, feedDetailFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
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
        return fragmentView;
    }
}
