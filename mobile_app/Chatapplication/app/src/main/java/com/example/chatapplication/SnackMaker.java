package com.example.chatapplication;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class SnackMaker extends AppCompatActivity {

    protected void registrationSuccess(View contextView) {
        Snackbar snackbar = Snackbar
                .make(contextView, R.string.registration_success, Snackbar.LENGTH_LONG)
                .setAction("Yay!", view -> {});
        snackbar.show();
    }

}
