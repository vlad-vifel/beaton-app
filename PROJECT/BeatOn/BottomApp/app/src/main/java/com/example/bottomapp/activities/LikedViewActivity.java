package com.example.bottomapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomapp.Beat;
import com.example.bottomapp.BeatAdapter;
import com.example.bottomapp.PageFragment;
import com.example.bottomapp.R;
import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.helpers.Databaser;

import java.util.ArrayList;

public class LikedViewActivity extends Activity implements View.OnClickListener{

    public Databaser databaser;

    private final ArrayList<Beat> beatsArray = new ArrayList<Beat>();
    private RecyclerView recView;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        if (PageFragment.playerView != null) {
            PageFragment.playerView.stop();
        }

        recView = findViewById(R.id.liked_recyclerview);

        uid = AuthHelper.getCurrentUserUid();

        databaser = new Databaser();

        final BeatAdapter beatAdapter = new BeatAdapter(this, beatsArray, uid);

        databaser.getUserData(uid, user -> {
            databaser.getLikedBeats(user, beats -> {
                beatAdapter.setBeats(beats);
            });
        });

        recView.setAdapter(beatAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));


    }



    @Override
    public void onClick(View v) {

    }
}
