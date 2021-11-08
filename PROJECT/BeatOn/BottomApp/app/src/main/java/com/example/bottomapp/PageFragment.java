package com.example.bottomapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arges.sepan.argmusicplayer.Enums.AudioType;
import com.arges.sepan.argmusicplayer.IndependentClasses.ArgAudio;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerSmallView;
import com.bumptech.glide.Glide;
import com.example.bottomapp.activities.ProfileViewActivity;
import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.helpers.Databaser;

import java.util.ArrayList;

public class PageFragment extends Fragment implements View.OnClickListener {

    static final String ARGUMENT_AUDIO_ID = "arg_audio_id";

    ArgAudio audio;
    ArrayList<String> audioData;

    public static ArgPlayerSmallView playerView;

    public Databaser databaser;

    public static PageFragment newInstance(Beat beat) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();

        ArrayList<String> arrayList = beat.toArrayList();


        //arguments.putInt(ARGUMENT_AUDIO_ID, page);
        arguments.putStringArrayList(ARGUMENT_AUDIO_ID, arrayList);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        audioData = getArguments().getStringArrayList(ARGUMENT_AUDIO_ID);

        //Random rnd = new Random();
        //backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));


    }

    public static void Play (Beat beat) {
        ArgAudio audio = new ArgAudio("", "", beat.getBeatURL(), AudioType.URL);

        playerView.play(audio);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

        databaser = new Databaser();

        ImageView imageView = (ImageView) view.findViewById(R.id.feed_cover);
        TextView title = (TextView) view.findViewById(R.id.feed_title);
        TextView author = (TextView) view.findViewById(R.id.feed_author);

        CheckBox like = (CheckBox) view.findViewById(R.id.feed_like);

        ImageButton profile = (ImageButton) view.findViewById(R.id.feed_profile);

        playerView = (ArgPlayerSmallView) view.findViewById(R.id.feed_player);

        databaser.getBeatDataByBeatId(audioData.get(0), beat -> {
            Glide.with(this).load(beat.getCoverURL()).into(imageView);

            title.setText(beat.getBeatTitle());

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

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ProfileViewActivity.class);
                    intent.putExtra("uid", beat.getAuthorId());
                    getContext().startActivity(intent);
                }
            });

            audio = new ArgAudio("", "", beat.getBeatURL(), AudioType.URL);

        });


        databaser.getUserData(audioData.get(1), user -> {
            Glide.with(this).load(user.getAvatar()).into(profile);
            author.setText(user.getLogin());
        });

        //audio = new ArgAudio("", "", audioData.get(2), AudioType.URL);
//
        //playerView.play(audio);




        //TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        //tvPage.setText("Page " + pageNumber);
        //tvPage.setBackgroundColor(backColor);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}