package com.example.chris.outapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.chris.outapp.R;
import com.example.chris.outapp.model.User;
import com.example.chris.outapp.model.Venue;
import com.example.chris.outapp.view.fragment.FeedFragment;
import com.example.chris.outapp.view.fragment.FriendFragment;
import com.example.chris.outapp.view.fragment.GoingOutFragment;
import com.example.chris.outapp.view.fragment.ProfileFragment;
import com.example.chris.outapp.view.fragment.VenueFragment;
import com.example.chris.outapp.viewmodel.UserViewModel;
import com.example.chris.outapp.viewmodel.VenueViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity{

    private FrameLayout frameLayoutContainer;
    private BottomNavigationView bottomNavigationView;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private UserViewModel userViewModel;
    private VenueViewModel venueViewModel;

    final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayoutContainer = findViewById(R.id.framelayoutContainer);
        bottomNavigationView = findViewById(R.id.navigationBar);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        venueViewModel = ViewModelProviders.of(this).get(VenueViewModel.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()){
                    case R.id.feedTab :
                        selectedFragment = FeedFragment.newInstance();
                        break;
                    case R.id.venuesTab :
                        selectedFragment = VenueFragment.newInstance();
                        break;
                    case R.id.friendsTab :
                        selectedFragment = FriendFragment.newInstance();
                        break;
                    case R.id.goingOutTab :
                        selectedFragment = GoingOutFragment.newInstance();
                        break;
                    case R.id.profileTab :
                        selectedFragment = ProfileFragment.newInstance();
                        break;
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(frameLayoutContainer.getId(), selectedFragment);
                fragmentTransaction.commit();
                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutContainer, FeedFragment.newInstance());
        fragmentTransaction.commit();

        //createUsersAndVenues();
    }

    private void createUsersAndVenues() {
        //create users and add to firebase
        User user1 = new User(reference.push().getKey(), "Chris", null, null);
        User user2 = new User(reference.push().getKey(), "Jack", null, null);
        User user3 = new User(reference.push().getKey(), "Rich", null, null);
        User user4 = new User(reference.push().getKey(), "Cian", null, null);
        User user5 = new User(reference.push().getKey(), "Deasy", null, null);
        userViewModel.createUser(user1);
        userViewModel.createUser(user2);
        userViewModel.createUser(user3);
        userViewModel.createUser(user4);
        userViewModel.createUser(user5);

        //Create Venues and add to firebase
        Venue venue1 = new Venue(reference.push().getKey(), "Xico", null);
        Venue venue2 = new Venue(reference.push().getKey(), "Diceys", null);
        Venue venue3 = new Venue(reference.push().getKey(), "Coppers", null);
        Venue venue4 = new Venue(reference.push().getKey(), "Workmans", null);
        Venue venue5 = new Venue(reference.push().getKey(), "Opium", null);
        venueViewModel.createVenue(venue1);
        venueViewModel.createVenue(venue2);
        venueViewModel.createVenue(venue3);
        venueViewModel.createVenue(venue4);
        venueViewModel.createVenue(venue5);
    }

    public void setActionBarTitle(int stringId){
        getSupportActionBar().setTitle(stringId);
    }

    public void setActionBarTitle(String string) {getSupportActionBar().setTitle(string);}

    public void setDisplayHomeAsUpEnabled(Boolean val){
        getSupportActionBar().setDisplayHomeAsUpEnabled(val);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}