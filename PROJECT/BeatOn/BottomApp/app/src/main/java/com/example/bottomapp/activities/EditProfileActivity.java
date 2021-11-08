package com.example.bottomapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.helpers.ImageUploader;
import com.example.bottomapp.R;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton avatarButton;
    private Button saveAvatarButton, saveLoginButton, saveSocialButton;
    private EditText editLogin, editVK, editINST, editYT;

    private static ImageUploader imageUploader;

    static final String TAG = "EditProfile";

    private Databaser databaser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "WOW");

        setContentView(R.layout.activity_edit_profile);

        imageUploader = new ImageUploader(getLayoutInflater(), this);

        databaser = new Databaser();

        editLogin = (EditText) findViewById(R.id.edit_login);
        editVK = (EditText) findViewById(R.id.edit_vk);
        editINST = (EditText) findViewById(R.id.edit_inst);
        editYT = (EditText) findViewById(R.id.edit_yt);

        avatarButton = (ImageButton) findViewById(R.id.button_avatar);
        saveAvatarButton = (Button) findViewById(R.id.button_save_avatar);
        saveLoginButton = (Button) findViewById(R.id.button_save_login);
        saveSocialButton = (Button) findViewById(R.id.button_save_social);

        avatarButton.setOnClickListener(this);
        saveAvatarButton.setOnClickListener(this);
        saveLoginButton.setOnClickListener(this);
        saveSocialButton.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setImageButton(requestCode, resultCode, data, this);
    }

    public void setImageButton (int requestCode, int resultCode, @Nullable Intent data, Activity activity) {
        if (requestCode == ImageUploader.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUploader.setFilePath(data.getData());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUploader.getFilePath());
                avatarButton.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (view == avatarButton) {
            imageUploader.showFileChooser();
        }

        if (view == saveSocialButton) {
            String vk = editVK.getText().toString();
            String inst = editINST.getText().toString();
            String yt = editYT.getText().toString();
            if (vk.isEmpty() && inst.isEmpty() && yt.isEmpty()) {
                Toast.makeText(this,"Empty fields",Toast.LENGTH_SHORT).show();
            }
            else {
                if (!vk.isEmpty()) {
                    databaser.updateVk(vk);
                }
                if (!inst.isEmpty()) {
                    databaser.updateInst(inst);
                }
                if (!yt.isEmpty()) {
                    databaser.updateYt(yt);
                }
                Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show();
                databaser.getUserData(AuthHelper.getCurrentUserUid(), user -> {
                    startActivity(new Intent(this, MainActivity.class));
                });
            }
        }

        if(view == saveLoginButton) {
            String login = editLogin.getText().toString();

            databaser.existLogin(login, exist -> {
                if(login.isEmpty()) {
                    Toast.makeText(this,"Empty login field",Toast.LENGTH_SHORT).show();
                } else if (exist) {
                    Toast.makeText(this,"This login is already used",Toast.LENGTH_SHORT).show();
                } else {
                    databaser.updateLogin(login);
                    Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show();
                    databaser.getUserData(AuthHelper.getCurrentUserUid(), user -> {
                        startActivity(new Intent(this, MainActivity.class));
                    });
                }
            });


        }

        if(view == saveAvatarButton) {
            String avatarURL = imageUploader.uploadFileAvatar();

            if(imageUploader.isEmpty()) {
                Toast.makeText(this,"No photo",Toast.LENGTH_SHORT).show();
            }
            else {
                databaser.updateAvatar(avatarURL);
                Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show();
                databaser.getUserData(AuthHelper.getCurrentUserUid(), user -> {
                    startActivity(new Intent(this, MainActivity.class));
                });
            }
        }

    }
}
