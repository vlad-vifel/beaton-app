package com.example.bottomapp.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arges.sepan.argmusicplayer.ArgPlayerSmallViewRoot;
import com.arges.sepan.argmusicplayer.Enums.AudioType;
import com.arges.sepan.argmusicplayer.IndependentClasses.ArgAudio;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerSmallView;
import com.example.bottomapp.activities.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class BeatUploader {

    public static final int PICK_BEAT_REQUEST = 235;
    private LayoutInflater inflater;

    private Activity activity;


    private static Uri filePath;

    public Uri getFilePath() {
        return filePath;
    }

    public void setFilePath(Uri filePath) {
        this.filePath = filePath;
    }

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    public BeatUploader(@NonNull LayoutInflater inflater, @NonNull Activity activity) {

        this.inflater = inflater;
        this.activity = activity;
    }

    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Beat"), PICK_BEAT_REQUEST);
    }

    public boolean isEmpty() {
        if(filePath != null) { return false;}
        else {return true;}
    }

    public static int getDuration (Context context) {
        if(filePath != null) {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(context, filePath);
            String duration =
                    metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return (Integer.parseInt(duration) / 1000);
        }
        else {
            return 0;
        }
    }

    public static String parseDuration (int duration) {
        String out = "";
        String seconds = String.valueOf(duration % 60);
        String minutes = String.valueOf(duration / 60);
        if (seconds.length() == 1) {
            out = (minutes + ":0" + seconds);
        }
        else {
            out = (minutes + ":" + seconds);
        }
        return out;
    }

    public String uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(inflater.getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference ref = storageReference.child("beats/" + UUID.randomUUID().toString() + ".mp3");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog

                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(activity.getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(activity.getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
            return "https://firebasestorage.googleapis.com/v0/b/beaton-4a6c6.appspot.com/o" + "/beats%2F" + ref.getPath().substring(7) + "?alt=media";
        }
        //if there is not any file
        else {
            return null;
        }


    }
}
