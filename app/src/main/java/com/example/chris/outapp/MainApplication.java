package com.example.chris.outapp;

import android.app.Application;
import android.content.Context;

import com.example.chris.outapp.model.User;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MainApplication extends Application {

    private static User currentUser;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainApplication.currentUser = currentUser;
    }

    public static RefWatcher getRefWatcher(Context context){
        MainApplication application = (MainApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
