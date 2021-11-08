package com.example.bottomapp.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bottomapp.Beat;
import com.example.bottomapp.User;
import com.example.bottomapp.helpers.AuthHelper;
import com.example.bottomapp.listeners.BeatReceiveListener;
import com.example.bottomapp.listeners.BeatsArrayReceiveListener;
import com.example.bottomapp.listeners.ExistReceiveListener;
import com.example.bottomapp.listeners.LikeReceiveListener;
import com.example.bottomapp.listeners.UidReceiveListener;
import com.example.bottomapp.listeners.UserDataReceiveListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Databaser {

    private FirebaseAuth mAuth;

    private static String TAG = "Databaser";
    private FirebaseFirestore db;
    public User user, user1;
    public String userId, userLogin;
    public ArrayList<Beat> beats = new ArrayList<Beat>();
    public ArrayList<Beat> allBeats = new ArrayList<Beat>();
    public ArrayList<Beat> likedBeats = new ArrayList<Beat>();
    public ArrayList<String> stringArrayList = new ArrayList<String>();
    public Beat beat;
    public DocumentSnapshot document;
    public boolean isliked = false;
    public boolean exist = false;

    public Databaser() {
    }


    public void newUser (String uid, String email, String login, String pass,  UserDataReceiveListener listener) {

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("uid", uid);
        user.put("email", email);
        user.put("login", login);
        user.put("password", pass);
        user.put("avatar", "https://firebasestorage.googleapis.com/v0/b/beaton-4a6c6.appspot.com/o/images%2Fdefault_profile.png?alt=media");
        user.put("vk", "");
        user.put("inst", "");
        user.put("yt", "");

        user1 = new User("", uid, email, login, pass,"https://firebasestorage.googleapis.com/v0/b/beaton-4a6c6.appspot.com/o/images%2Fdefault_profile.png?alt=media", "", "", "");


        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        listener.onUserReceived(user1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public void existEmail (String email, ExistReceiveListener listener) {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            exist = true;
                            if (task.getResult().isEmpty()) {
                                exist = false;
                            }
                            else {
                                exist = true;
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        listener.onExistReceived(exist);
                    }
                });
    }

    public void existLogin (String login, ExistReceiveListener listener) {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("login", login)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            exist = true;
                            if (task.getResult().isEmpty()) {
                                exist = false;
                            }
                            else {
                                exist = true;
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        listener.onExistReceived(exist);
                    }
                });
    }

    public void updateVk (String vk) {

        getUserData(AuthHelper.getCurrentUserUid(), user -> {
            mAuth = FirebaseAuth.getInstance();

            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getDocId());

            docRef.update("vk", vk);
        });
    }

    public void updateInst (String inst) {

        getUserData(AuthHelper.getCurrentUserUid(), user -> {
            mAuth = FirebaseAuth.getInstance();

            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getDocId());

            docRef.update("inst", inst);
        });
    }

    public void updateYt(String yt) {

        getUserData(AuthHelper.getCurrentUserUid(), user -> {
            mAuth = FirebaseAuth.getInstance();

            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getDocId());

            docRef.update("yt", yt);
        });
    }

    public void updateLogin(String login) {
        getUserData(AuthHelper.getCurrentUserUid(), user -> {
            mAuth = FirebaseAuth.getInstance();

            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getDocId());

            docRef.update("login", login);
        });
    }

    public void updateAvatar(String avatarURL) {
        getUserData(AuthHelper.getCurrentUserUid(), user -> {
            mAuth = FirebaseAuth.getInstance();

            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getDocId());

            docRef.update("avatar", avatarURL);
        });

    }

    public void addLike (String beatId, String userId) {

        getBeatDataByBeatId(beatId, beat -> {

            List<String> list = beat.getLiked();
            if(!(list.contains(userId))) {
                list.add(userId);
            }

            FirebaseFirestore.getInstance().collection("beats").document(beatId).update("liked", list);

        });

    }

    public void removeLike (String beatId, String userId) {

        getBeatDataByBeatId(beatId, beat -> {

            List<String> list = beat.getLiked();
            if (list.contains(userId)) {
                list.remove(userId);
            }
            FirebaseFirestore.getInstance().collection("beats").document(beatId).update("liked", list);

        });

    }

    public void isLiked (String beatId, String userId, LikeReceiveListener listener) {

        getBeatDataByBeatId(beatId, beat -> {

            List<String> list = beat.getLiked();
            if (list.contains(userId)) {
                isliked = true;
            } else {
                isliked = false;
            }
            listener.onLikeReceived(isliked);
        });

    }




    public void newBeat (String beatURL, String beatTitle, String beatDescription, String coverURL, String duration) {
        getUserData(AuthHelper.getCurrentUserUid(), user -> {
            String authorId = user.getUid();
            db = FirebaseFirestore.getInstance();

            ArrayList<String> liked = new ArrayList<String>();

            Map<String, Object> beat = new HashMap<>();

            beat.put("authorId", authorId);
            beat.put("beatURL", beatURL);
            beat.put("beatTitle", beatTitle);
            beat.put("beatDescription", beatDescription);
            beat.put("coverURL", coverURL);
            beat.put("duration", duration);
            beat.put("liked", liked);




            db.collection("beats")
                    .add(beat)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        });


    }


    public void getLikedBeats (User user, BeatsArrayReceiveListener listener) {

        userId = user.getUid();

        db = FirebaseFirestore.getInstance();
        db.collection("beats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            likedBeats = new ArrayList<Beat>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                List<String> liked = (List<String>) document.get("liked");


                                getBeatDataByBeatId(document.getId(), beat -> {

                                    if (liked.contains(userId)) {
                                        likedBeats.add(beat);
                                    }

                                    listener.onBeatsArrayReceived(likedBeats);
                                });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        //Log.i("LOL", "getBeatsArray. beats = " + beats);

                        listener.onBeatsArrayReceived(likedBeats);

                    }
                });

    }

    public void getAllBeatsArray (BeatsArrayReceiveListener listener) {
        //beats = new ArrayList<Beat>();

        db = FirebaseFirestore.getInstance();

        db.collection("beats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            allBeats = new ArrayList<Beat>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                List<String> liked = (List<String>) document.get("liked");

                                Beat beat = new Beat(document.getId(),
                                        document.get("authorId").toString(),
                                        document.get("beatURL").toString(),
                                        document.get("beatTitle").toString(),
                                        document.get("beatDescription").toString(),
                                        document.get("coverURL").toString(),
                                        document.get("duration").toString(),
                                        liked);
                                Log.d(TAG, beat.getAuthorId());
                                allBeats.add(beat);
                                listener.onBeatsArrayReceived(allBeats);

                                //listener.onUserReceived(user);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        listener.onBeatsArrayReceived(allBeats);
                    }
                });

    }



    public void getBeatsArray(User user, BeatsArrayReceiveListener listener) {


        userId = user.getUid();
        userLogin = user.getLogin();

        db = FirebaseFirestore.getInstance();
        db.collection("beats")
                .whereEqualTo("authorId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            beats = new ArrayList<Beat>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                List<String> liked = (List<String>) document.get("liked");

                                Beat beat = new Beat(document.getId(),
                                        userId,
                                        document.get("beatURL").toString(),
                                        document.get("beatTitle").toString(),
                                        document.get("beatDescription").toString(),
                                        document.get("coverURL").toString(),
                                        document.get("duration").toString(),
                                        liked);
                                beats.add(beat);
                                listener.onBeatsArrayReceived(beats);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        listener.onBeatsArrayReceived(beats);

                    }
                });

    }

    public void getUserUidByEmail (String email, UidReceiveListener listener) {



        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, "WORKS!!!");

                            if (task.getResult().isEmpty()) {

                                //Special user with random password
                                listener.onUidReceived("11111");
                            }
                            else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    listener.onUidReceived(document.get("uid").toString());
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void isPassMatch (String email, String pass, LikeReceiveListener listener) {

        getUserUidByEmail(email, uid -> {

            getUserData(uid, user -> {

                if (pass.equals(user.getPass())) {
                    listener.onLikeReceived(true);
                } else {
                    listener.onLikeReceived(false);
                }

            });

        });
    }


    public void getUserData(String uid, UserDataReceiveListener listener) {

        db = FirebaseFirestore.getInstance();


        db.collection("users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                user = new User( document.getId(),
                                        document.get("uid").toString(),
                                        document.get("email").toString(),
                                        document.get("login").toString(),
                                        document.get("password").toString(),
                                        document.get("avatar").toString(),
                                        document.get("vk").toString(),
                                        document.get("inst").toString(),
                                        document.get("yt").toString());
                                listener.onUserReceived(user);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getBeatDataByBeatId (String beatId, BeatReceiveListener listener) {

        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("beats").document(beatId);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            document = task.getResult();

                            Log.d(TAG, document.getId() + " => " + document.getData());

                            List<String> liked = (List<String>) document.get("liked");

                            beat = new Beat(document.getId(),
                                    document.get("authorId").toString(),
                                    document.get("beatURL").toString(),
                                    document.get("beatTitle").toString(),
                                    document.get("beatDescription").toString(),
                                    document.get("coverURL").toString(),
                                    document.get("duration").toString(),
                                    liked);

                            listener.onBeatReceived(beat);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


    }

}
