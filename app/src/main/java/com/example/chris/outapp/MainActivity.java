package com.example.chris.outapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;

import com.example.chris.outapp.databinding.ActivityMainBinding;
import com.example.chris.outapp.fragments.FeedFragment;
import com.example.chris.outapp.fragments.FriendsFragment;
import com.example.chris.outapp.fragments.MessagesFragment;
import com.example.chris.outapp.fragments.VenuesFragment;
import com.example.chris.outapp.model.VenueAdapter;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        accessToken = AccessToken.getCurrentAccessToken();
        boolean isSignedIn = accessToken != null && !accessToken.isExpired();
        checkSignIn(isSignedIn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        activityMainBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.feedTab :
                        selectedFragment = FeedFragment.newInstance();
                        break;
                    case R.id.venuesTab :
                        selectedFragment = VenuesFragment.newInstance();
                        break;
                    case R.id.friendsTab :
                        selectedFragment = FriendsFragment.newInstance();
                        break;
                    case R.id.messagesTab :
                        selectedFragment = MessagesFragment.newInstance();
                        break;
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(activityMainBinding.frameLayout.getId(), selectedFragment);
                fragmentTransaction.commit();
                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, FeedFragment.newInstance());
        fragmentTransaction.commit();


//        Venue venue1 = new Venue(1, "Xico", "€10", 100);
//        Venue venue2 = new Venue(2, "Opium", "€5", 46);
//        Venue venue3 = new Venue(3, "Whelans", "€7", 23);
//        Venue venue4 = new Venue(4, "Flannerys", "€4", 88);
//        Venue venue5 = new Venue(5, "Diceys", "€2", 150);
//
//        reference.child("Venues").child(String.valueOf(venue1.getId())).setValue(venue1);
//        reference.child("Venues").child(String.valueOf(venue2.getId())).setValue(venue2);
//        reference.child("Venues").child(String.valueOf(venue3.getId())).setValue(venue3);
//        reference.child("Venues").child(String.valueOf(venue4.getId())).setValue(venue4);
//        reference.child("Venues").child(String.valueOf(venue5.getId())).setValue(venue5);


    }

    private void checkSignIn(boolean isSignedIn) {
        if(!isSignedIn) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }

    }
}
