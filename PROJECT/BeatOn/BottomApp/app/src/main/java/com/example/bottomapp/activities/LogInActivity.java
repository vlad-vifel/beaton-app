package com.example.bottomapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.helpers.EmailValidator;
import com.example.bottomapp.R;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {


    private AuthHelper authHelper;
    private int a = 0;

    private EditText editLogEmail, editLogPass;
    private Button logButton, toRegister;

    private EmailValidator emailValidator;

    public Databaser databaser = new Databaser();

    static final String TAG = "!!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        authHelper = new AuthHelper();

        authHelper.onCreate();


        editLogEmail = (EditText) findViewById(R.id.edit_log_email);
        editLogPass = (EditText) findViewById(R.id.edit_log_pass);


        logButton = (Button) findViewById(R.id.log_button);
        toRegister = (Button) findViewById(R.id.to_register);

        logButton.setOnClickListener(this);
        toRegister.setOnClickListener(this);

        setProgressBarVisibility(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        authHelper.onStartLogin(this);


    }



    @Override
    public void onClick(View view) {

        emailValidator = new EmailValidator();

        if (view.getId() == R.id.log_button) {
            String email = editLogEmail.getText().toString();
            String pass = editLogPass.getText().toString();

            databaser.existEmail(email, exist -> {



                databaser.isPassMatch(email, pass, match -> {



                    if (email.isEmpty()) {

                        Toast.makeText(this,"Empty Email Field",Toast.LENGTH_SHORT).show();
                    } else if (!emailValidator.validateEmail(email)) {
                        Toast.makeText(this,"Incorrect Email",Toast.LENGTH_SHORT).show();
                    } else if(!exist) {
                        Toast.makeText(this,"There Is No User With Such Email",Toast.LENGTH_SHORT).show();
                    } else if (!emailValidator.validatePass(pass)) {
                        Toast.makeText(this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                    } else if (!match) {
                        Toast.makeText(this,"The Password Doesn't Match",Toast.LENGTH_SHORT).show();
                    } else {
                        authHelper.logAccount(email, pass, this);

                    }

                });

            });


        }  else if ((view.getId() == R.id.to_register)) {

            startActivity(new Intent(this, RegisterActivity.class));

        }
    }



}
