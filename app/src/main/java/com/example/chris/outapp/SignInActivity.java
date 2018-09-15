package com.example.chris.outapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.example.chris.outapp.databinding.ActivitySignInBinding;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    LoginButton loginButton;
    AccessToken accessToken;

    private static final String EMAIL = "email";
    CallbackManager callbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        accessToken = AccessToken.getCurrentAccessToken();
        boolean isSignedIn = accessToken != null && !accessToken.isExpired();
        checkSignIn(isSignedIn);

        callbackManager = CallbackManager.Factory.create();

        binding.loginButton.setReadPermissions(Arrays.asList(EMAIL));

        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isSignedIn = accessToken != null && !accessToken.isExpired();
                signIn(isSignedIn);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e(SignInActivity.class.getSimpleName(), error.toString());
            }
        });


    }

    private void signIn(boolean isSignedIn) {
        if(isSignedIn){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void checkSignIn(boolean isSignedIn) {
        if(isSignedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
