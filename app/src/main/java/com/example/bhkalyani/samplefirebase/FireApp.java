package com.example.bhkalyani.samplefirebase;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Bhkalyani on 3/24/2017.
 */

public class FireApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        if(!FirebaseApp.getApps(this).isEmpty())
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
//        Picasso built = builder.build();
//        built.setIndicatorsEnabled(false);
//        built.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(built);

    }
}
