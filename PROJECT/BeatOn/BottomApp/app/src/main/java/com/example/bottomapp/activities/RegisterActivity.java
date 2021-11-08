package com.example.bottomapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.helpers.EmailValidator;
import com.example.bottomapp.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{



    private AuthHelper authHelper;

    public static String oldId;

    private EditText editRegEmail, editRegPass, editRegLogin;
    private Button regButton, toLogin;

    private TextView regHint;

    private EmailValidator emailValidator;

    public Databaser databaser = new Databaser();

    public boolean checkemail = true;
    public boolean checklogin = true;

    static final String TAG = "!!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TypefaceUtil.overrideFont(getApplicationContext(), "SANS_SERIF", "@font/montserrat");
        setContentView(R.layout.activity_register);

        authHelper = new AuthHelper();

        authHelper.onCreate();



        editRegEmail = (EditText) findViewById(R.id.edit_reg_email);
        editRegPass = (EditText) findViewById(R.id.edit_reg_pass);
        editRegLogin = (EditText) findViewById(R.id.edit_reg_login);

        regButton = (Button) findViewById(R.id.reg_button);
        toLogin = (Button) findViewById(R.id.to_login);

        regButton.setOnClickListener(this);
        toLogin.setOnClickListener(this);



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        authHelper.onStart(this);
    }



    @Override
    public void onClick(View view) {

        emailValidator = new EmailValidator();

        if (view.getId() == R.id.reg_button) {

            String email = editRegEmail.getText().toString();
            String login = editRegLogin.getText().toString();
            String pass = editRegPass.getText().toString();


            databaser.existEmail(email, exist1 -> {


                databaser.existLogin(login, exist2 -> {


                    if(!(emailValidator.validateEmail(email))) {
                        Toast.makeText(this,"Incorrect Email",Toast.LENGTH_SHORT).show();
                    } else if (exist1) {
                        Toast.makeText(this,"This Email Is Already Used",Toast.LENGTH_SHORT).show();
                    } else if(!(emailValidator.validateLogin(login))) {
                        Toast.makeText(this,"Incorrect Login",Toast.LENGTH_SHORT).show();
                    } else if (exist2) {
                        Toast.makeText(this,"This Login Is Already Used",Toast.LENGTH_SHORT).show();
                    } else if (!(emailValidator.validatePass(pass))) {
                        Toast.makeText(this,"Use More Than 8 Signs In Password",Toast.LENGTH_SHORT).show();
                    } else {
                        authHelper.regAccount(email, login, pass, this);
                    }
                });


            });







        }  else if ((view.getId() == R.id.to_login)) {

            startActivity(new Intent(this, LogInActivity.class));

        }


    }






}
