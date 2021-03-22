package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private final int registerNewUser = 205;

    private ProgressBar loader;
    private TextInputLayout usernameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout passwordAgainLayout;
    private Button registerButton;

    private TextView usernameField;
    private TextView emailField;
    private TextView passwordField;
    private TextView passwordAgainField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loader = findViewById(R.id.progressBar);
        usernameLayout = findViewById(R.id.usernameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        passwordAgainLayout = findViewById(R.id.passwordAgainLayout);
        registerButton = findViewById(R.id.registerButton);

        usernameField = findViewById(R.id.usernameInput);
        emailField = findViewById(R.id.emailInput);
        passwordField = findViewById(R.id.passwordInput);
        passwordAgainField = findViewById(R.id.passwordAgainInput);
    }

    public void test(View view) {
        showFailedSnackbar();
    }

    // When activity exits after being called from startActivityForResult(intent, requestCode).
    // it returns here, and by matching the requestCodes we can figure out where & what the result is
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == registerNewUser) { // Specific user
            loaderEnd();
            if (resultCode == RESULT_OK) {
                String userID = data.getData().toString();
            } else {
                showFailedSnackbar();
            }
        }
    }

    private void showFailedSnackbar() {
        View contextView = this.findViewById(android.R.id.content);

        Snackbar snackbar = Snackbar
                .make(contextView, R.string.registration_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction("Try again.", view -> {
                    Snackbar snackbar1 = Snackbar.make(contextView, "Account registered", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                });

        snackbar.show();
    }

    public void loaderStart() {
        usernameLayout.setAlpha((float) 0.6);
        emailLayout.setAlpha((float) 0.6);
        passwordLayout.setAlpha((float) 0.6);
        passwordAgainLayout.setAlpha((float) 0.6);
        usernameLayout.setAlpha((float) 0.6);
        registerButton.setAlpha((float) 0.6);

        loader.setVisibility(View.VISIBLE);
    }

    public void loaderEnd() {
        usernameLayout.setAlpha((float) 0);
        emailLayout.setAlpha((float) 0);
        passwordLayout.setAlpha((float) 0);
        passwordAgainLayout.setAlpha((float) 0);
        usernameLayout.setAlpha((float) 0);
        registerButton.setAlpha((float) 0);

        loader.setVisibility(View.INVISIBLE);
    }

    public void registerUser(View view) {
        loaderStart();

        String username = usernameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String passwordAgain = passwordAgainField.getText().toString();

        // TODO:
        //  - Check that username/email isn't registered
        //  - Check password is valid
        //  - Change password to hashed one. (Create own algorithm?)

        Intent intent = new Intent(this, UserDatabaseActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("requestCode", registerNewUser);
        extras.putString("username", username);
        extras.putString("email", email);
        extras.putString("password", password);
        intent.putExtras(extras);
        startActivityForResult(intent, registerNewUser);
    }
}