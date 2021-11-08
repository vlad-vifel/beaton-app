package com.example.bottomapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomapp.PageFragment;
import com.example.bottomapp.activities.LikedViewActivity;
import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.Beat;
import com.example.bottomapp.BeatAdapter;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.R;
import com.example.bottomapp.activities.EditProfileActivity;


import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "Profile";
    private TextView userLogin;
    private ImageView userAvatar;
    private ImageButton signoutButton, likedButton;
    private Button editProfileButton;
    public Databaser databaser;

    private final ArrayList<Beat> beatsArray = new ArrayList<Beat>();
    private RecyclerView recView;
    private String uid;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        if (PageFragment.playerView != null) {
            PageFragment.playerView.stop();
        }



        recView = root.findViewById(R.id.beat_recyclerview);

        databaser = new Databaser();

        editProfileButton = (Button) root.findViewById(R.id.button_edit_profile);
        editProfileButton.setOnClickListener(this);

        userLogin = (TextView) root.findViewById(R.id.user_login);

        signoutButton = (ImageButton) root.findViewById(R.id.button_signout);
        signoutButton.setOnClickListener(this);

        likedButton = (ImageButton) root.findViewById(R.id.button_liked);
        likedButton.setOnClickListener(this);


        userAvatar = (ImageView) root.findViewById(R.id.user_avatar);
        databaser.getUserData(AuthHelper.getCurrentUserUid(), user -> {

            uid = user.getUid();

            userLogin.setText(user.getLogin());

            Glide.with(this).load(user.getAvatar()).into(userAvatar);


        });


        final BeatAdapter beatAdapter = new BeatAdapter(this.getContext(), beatsArray, uid);

        databaser.getUserData(AuthHelper.getCurrentUserUid(), user -> {
            databaser.getBeatsArray(user, beats -> {
                beatAdapter.setBeats(beats);
            });
        });


        recView.setAdapter(beatAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this.getContext()));



        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == editProfileButton) {
            startActivity(new Intent(this.getActivity(), EditProfileActivity.class));
        }

        if (view == signoutButton) {
            //Log.i("WWW", "works!");
            AuthHelper authHelper = new AuthHelper();
            authHelper.signOut(this.getActivity());
        }

        if (view == likedButton) {
            startActivity(new Intent(this.getActivity(), LikedViewActivity.class));
        }
    }
}