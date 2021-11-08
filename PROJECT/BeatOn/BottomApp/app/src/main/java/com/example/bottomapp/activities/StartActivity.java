package com.example.bottomapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomapp.R;
import com.example.bottomapp.activities.LogInActivity;
import com.example.bottomapp.activities.RegisterActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private Button regButton, logButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        regButton = (Button) findViewById(R.id.reg_button);
        logButton = (Button) findViewById(R.id.log_button);

        regButton.setOnClickListener(this);
        logButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.reg_button) {

            startActivity(new Intent(this, RegisterActivity.class));

        } else if ((view.getId() == R.id.log_button)) {

            startActivity(new Intent(this, LogInActivity.class));

        }
    }
}
