package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDatabaseActivity extends AppCompatActivity {
    private final int registerNewUser = 205;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_database);
        Bundle extras = getIntent().getExtras();
        int requestCode = extras.getInt("requestCode");

        if (requestCode == registerNewUser) { // get specific user by email
            registerUser(extras.getString("username"), extras.getString("email"), extras.getString("password"));
        }
    }

    protected void registerUser(String username, String email, String password) {
        final String TAG = "registerUser()";
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> returnUserID(documentReference.getId(), true))
                .addOnFailureListener(e -> returnUserID("error", false));
    }

    protected void returnUserID(String userID, boolean ok) {
        Intent result = new Intent();
        result.setData(Uri.parse(userID));

        if (ok) setResult(RESULT_OK, result);
        else setResult(RESULT_CANCELED, result);

        finish();
    }



}