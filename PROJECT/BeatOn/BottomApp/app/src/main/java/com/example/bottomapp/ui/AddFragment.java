package com.example.bottomapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomapp.PageFragment;
import com.example.bottomapp.helpers.AddBeatHelper;
import com.example.bottomapp.helpers.BeatUploader;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.helpers.ImageUploader;
import com.example.bottomapp.activities.MainActivity;
import com.example.bottomapp.R;


import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddFragment extends Fragment implements View.OnClickListener {

    private static ImageUploader imageUploader;
    private static BeatUploader beatUploader;


    private EditText editBeatTitle, editBeatDescription;

    private AddBeatHelper addBeatHelper;

    private Databaser databaser;

    private Button uploadBeatButton, submitButton;
    private static ImageButton coverButton;

    static final String TAG = "!!!";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);
 //      final TextView textView = root.findViewById(R.id.text_other);
 //      otherViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
 //          @Override
 //          public void onChanged(@Nullable String s) {
 //              textView.setText(s);
 //          }
 //      });

        if (PageFragment.playerView != null) {
            PageFragment.playerView.stop();
        }

        databaser = new Databaser();

        addBeatHelper = new AddBeatHelper();

        imageUploader = new ImageUploader(inflater, getActivity());
        beatUploader = new BeatUploader(inflater, getActivity());

        editBeatTitle = (EditText) root.findViewById(R.id.edit_title);
        editBeatDescription = (EditText) root.findViewById(R.id.edit_description);

        coverButton = (ImageButton) root.findViewById(R.id.button_cover);
        uploadBeatButton = (Button) root.findViewById(R.id.button_upload_beat);
        submitButton = (Button) root.findViewById(R.id.button_submit);

        coverButton.setOnClickListener(this);
        uploadBeatButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        return root;
    }

    public static void setImageButton (int requestCode, int resultCode, @Nullable Intent data, Activity activity) {
        if (requestCode == ImageUploader.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUploader.setFilePath(data.getData());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUploader.getFilePath());
                coverButton.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setBeat (int requestCode, int resultCode, @Nullable Intent data, Activity activity) {
        if (requestCode == BeatUploader.PICK_BEAT_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            beatUploader.setFilePath(data.getData());

        }
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }


    @Override
    public void onClick(View view) {

        if (view == coverButton) {
            imageUploader.showFileChooser();
        }

        else if (view == uploadBeatButton) {
            beatUploader.showFileChooser();
        }

        else if (view == submitButton) {
            if (BeatUploader.getDuration(this.getContext()) >= 31) {
                Toast.makeText(this.getActivity(), "Duration must be no more than 30 seconds", Toast.LENGTH_SHORT).show();
            }

            else if (addBeatHelper.checkBeat(beatUploader.isEmpty(), editBeatTitle.getText().toString(), imageUploader.isEmpty()) != "OK") {

                Toast.makeText(this.getActivity(), addBeatHelper.checkBeat(beatUploader.isEmpty(), editBeatTitle.getText().toString(), imageUploader.isEmpty()),Toast.LENGTH_SHORT).show();
            }

            else {
                Log.i("WOW", "Duration = " + BeatUploader.getDuration(this.getContext()));

                String coverURL = imageUploader.uploadFileCover();
                String beatURL = beatUploader.uploadFile();
                String duration = BeatUploader.parseDuration(BeatUploader.getDuration(this.getContext()));

                databaser.newBeat(beatURL, editBeatTitle.getText().toString(), editBeatDescription.getText().toString(), coverURL, duration);
                startActivity(new Intent(this.getActivity(), MainActivity.class));
            }

        }


    }

}