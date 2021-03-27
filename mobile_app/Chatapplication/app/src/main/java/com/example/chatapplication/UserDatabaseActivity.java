package com.example.chatapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDatabaseActivity extends AppCompatActivity {
    private final int registerNewUser = 205;
    private final int checkUsername = 206;
    private final int checkEmail = 207;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_database);
        Bundle extras = getIntent().getExtras();
        int requestCode = extras.getInt("requestCode");

        if (requestCode == registerNewUser) { // get specific user by email
            registerUser(extras.getString("username"), extras.getString("email"), extras.getString("password"));
        } else if (requestCode == checkUsername) {
            checkAvailability(extras.getString("username"), "");
        } else if (requestCode == checkEmail) {
            checkAvailability("", extras.getString("email"));
        }
    }

    private void checkAvailability(String username, String email) {
        if (!username.isEmpty()) {
            db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        wasItTaken(task.isSuccessful());
                    });
        } else if (!email.isEmpty()) {
            db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        wasItTaken(task.isSuccessful());
                    });
        } else {
            Log.e("checkAvailability() - Error", "Username and email are both empty. Error happened?");
        }
    }


    protected void wasItTaken(boolean taken) {
        Intent result = new Intent();
        if (taken) setResult(RESULT_OK, result);
        else setResult(RESULT_CANCELED, result);
        finish();
    }

    private void registerUser(String username, String email, String password) {
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