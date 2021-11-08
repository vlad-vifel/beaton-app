package com.example.bottomapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.arges.sepan.argmusicplayer.Enums.AudioType;
import com.arges.sepan.argmusicplayer.IndependentClasses.ArgAudio;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerSmallView;
import com.bumptech.glide.Glide;
import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.R;

public class BeatViewActivity extends Activity implements View.OnClickListener{

    public ImageView cover;
    public TextView title, description, author;
    public CheckBox like;

    public Databaser databaser;

    public ArgPlayerSmallView playerView;

    private String beatId;

    public ArgAudio audio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beat);

        cover = findViewById(R.id.beat_cover);
        title = findViewById(R.id.beat_title);
        description = findViewById(R.id.beat_description);
        author = findViewById(R.id.beat_author);
        like  = findViewById(R.id.beat_like);

        playerView = (ArgPlayerSmallView) findViewById(R.id.beat_player);

        Intent intent = getIntent();

        beatId = intent.getStringExtra("beatId");

        Log.i("TTTTT", "BEATID! = " + beatId);

        databaser = new Databaser();
        databaser.getBeatDataByBeatId(beatId, beat -> {

            Log.i("TTTTT", "BEAT! = " + beat.toArrayList());

            Glide.with(this).load(beat.getCoverURL()).into(cover);

            title.setText(beat.getBeatTitle());
            if (beat.getBeatDescription().length() == 0) {
                description.setText("No Description");
            } else {
                description.setText(beat.getBeatDescription());
            }

            databaser.getUserData(beat.getAuthorId(), user -> {
                author.setText(user.getLogin());

                //audio = new ArgAudio(user.getLogin(), beat.getBeatTitle(), beat.getBeatURL(), AudioType.URL);
            });

            databaser.isLiked(beat.getBeatId(), AuthHelper.getCurrentUserUid(), isliked -> {
                if (isliked) {
                    like.setChecked(true);
                } else {
                    like.setChecked(false);
                }
            });


            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean checked = like.isChecked();
                    if (checked) {
                        databaser.addLike(beat.getBeatId(), AuthHelper.getCurrentUserUid());
                    } else if (!(checked)) {
                        databaser.removeLike(beat.getBeatId(), AuthHelper.getCurrentUserUid());
                    }

                }
            });

            audio = new ArgAudio("", "", beat.getBeatURL(), AudioType.URL);

            playerView.play(audio);


        });


        //title.setText("The Beat");
        //description.setText("Test Desc");
        //author.setText("me");


    }

    @Override
    public void onBackPressed() {
        playerView.stop();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

    }
}
