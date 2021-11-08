package com.example.bottomapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomapp.Beat;
import com.example.bottomapp.BeatAdapter;
import com.example.bottomapp.PageFragment;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.R;


import java.util.ArrayList;

public class ProfileViewActivity extends Activity implements View.OnClickListener{

    private ImageView avatar;
    private TextView login;
    private ImageButton vk, inst, yt;

    public Databaser databaser;

    private final ArrayList<Beat> beatsArray = new ArrayList<Beat>();
    private RecyclerView recView;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (PageFragment.playerView != null) {
            PageFragment.playerView.stop();
        }

        avatar = findViewById(R.id.profile_avatar);
        login = findViewById(R.id.profile_login);
        vk = findViewById(R.id.button_vk);
        inst = findViewById(R.id.button_inst);
        yt = findViewById(R.id.button_yt);
        recView = findViewById(R.id.profile_recyclerview);

        Intent intent = getIntent();

        uid = intent.getStringExtra("uid");

        Log.i("RRR", "UID!!! = " + uid);

        databaser = new Databaser();

        databaser.getUserData(uid, user -> {
            Glide.with(this).load(user.getAvatar()).into(avatar);
            login.setText(user.getLogin());

            if (user.getVk() == "") {
                vk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setClickable(false);
                    }
                });
            } else {
                vk.setBackgroundResource(R.drawable.ic_vk_normal_40dp);
                vk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(user.getVk()));
                        startActivity(intent);
                    }
                });
            }

            if (user.getInst() == "") {
                inst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setClickable(false);
                    }
                });
            } else {
                inst.setBackgroundResource(R.drawable.ic_inst_normal_40dp);
                inst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(user.getInst()));
                        startActivity(intent);
                    }
                });
            }

            if (user.getYt() == "") {
                yt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setClickable(false);
                    }
                });
            } else {
                yt.setBackgroundResource(R.drawable.ic_yt_normal_40dp);
                yt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(user.getYt()));
                        startActivity(intent);
                    }
                });
            }

        });

        final BeatAdapter beatAdapter = new BeatAdapter(this, beatsArray, uid);

        databaser.getUserData(uid, user -> {
            databaser.getBeatsArray(user, beats -> {
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
