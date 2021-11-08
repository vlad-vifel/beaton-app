package com.example.bottomapp.helpers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bottomapp.activities.LogInActivity;
import com.example.bottomapp.activities.MainActivity;
import com.example.bottomapp.activities.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class    AuthHelper {

    public FirebaseAuth mAuth;

    private Databaser databaser;

    static final String TAG = "AuthHelper";

    public static String getCurrentUserUid () {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void onCreate() {
        mAuth = FirebaseAuth.getInstance();
        databaser = new Databaser();
    }


    public void onStart(Activity activity) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, activity);
    }

    public void onStartLogin(Activity activity) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUILogin(currentUser, activity);
    }

    public void updateUI (FirebaseUser account, Activity activity){

        if(account != null){
            Toast.makeText(activity,"Now Verify Your Email",Toast.LENGTH_SHORT).show();
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            activity.startActivity(new Intent(activity, LogInActivity.class));

        } else {

        }

    }

    public void updateUILogin (FirebaseUser account, Activity activity){

        if(account != null){
            Toast.makeText(activity,"U Signed In",Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity, MainActivity.class));

        } else {

        }

    }


    public void signOut(Activity activity) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        activity.startActivity(new Intent(activity, StartActivity.class));
    }


    public void regAccount (String email, String login, String pass, Activity activity) {


        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            databaser.newUser(user.getUid(), email, login, pass, user1 ->  {
                                Log.w(TAG, "im here");
                                user.sendEmailVerification();

                                updateUI(user, activity);

                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }

                        // ...
                    }
                });

    }


    public void logAccount (String email, String pass, Activity activity) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user.isEmailVerified()) {
                                updateUILogin(user, activity);
                            } else {
                                Toast.makeText(activity,"Verify Your Email",Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification();

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            updateUILogin(null, null);
                        }

                        // ...
                    }
                });


    }
}
