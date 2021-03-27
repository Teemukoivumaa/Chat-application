package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private final int registerNewUser = 205;
    private final int checkUsername = 206;
    private final int checkEmail = 207;

    private String username;
    private String email;
    private String password;
    private String passwordAgain;

    private boolean usernameTaken = true;
    private boolean emailTaken = true;
    private boolean passwordOk = false;

    SnackMaker snackMaker = new SnackMaker();

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

        registerButton.setEnabled(false);
        addTextListeners();
    }

    public void addTextListeners() {
        usernameField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkField(usernameLayout, usernameField);
                    }
                }
        );

        emailField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkField(emailLayout, emailField);
                    }
                }
        );

        passwordField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkPasswords();
                    }
                });

        passwordAgainField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkPasswords();
                    }
                });
    }

    // When activity exits after being called from startActivityForResult(intent, requestCode).
    // it returns here, and by matching the requestCodes we can figure out where & what the result is
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loader();
        View contextView = this.findViewById(android.R.id.content);
        if (requestCode == registerNewUser) { // Specific user
            if (resultCode == RESULT_OK) {
                snackMaker.registrationSuccess(contextView);
                String userID = data.getData().toString();
            } else showFailedSnackbar();
        } else if (requestCode == checkUsername) {
            if (resultCode == RESULT_OK) {
                usernameTaken = false;
                registerUser();
            } else {
                usernameTaken = true;
                usernameLayout.setError("Username taken.");
            }
        } else if (requestCode == checkEmail) {
            if (resultCode == RESULT_OK) {
                emailTaken = false;
                registerUser();
            } else {
                emailTaken = true;
                emailLayout.setError("User already registered with this email.");
            }
        }
    }

    private void showFailedSnackbar() {
        View contextView = this.findViewById(android.R.id.content);

        Snackbar snackbar = Snackbar
                .make(contextView, R.string.registration_failed, Snackbar.LENGTH_LONG)
                .setAction("Try again.", view -> {
                    registerUser();
                });

        snackbar.show();
    }

    private boolean isDisabled = false;

    public void loader() {
        usernameLayout.setEnabled(isDisabled);
        emailLayout.setEnabled(isDisabled);
        passwordLayout.setEnabled(isDisabled);
        passwordAgainLayout.setEnabled(isDisabled);
        usernameLayout.setEnabled(isDisabled);
        registerButton.setEnabled(isDisabled);

        if (!isDisabled) loader.setVisibility(View.VISIBLE);
        else loader.setVisibility(View.INVISIBLE);

        isDisabled = !isDisabled;
    }

    public void checkField(TextInputLayout textLayout, TextView textField) {
        String textFieldText = textField.getText().toString();
        if (textLayout.getId() == usernameLayout.getId()) {
            if (textFieldText.isEmpty()) {
                textLayout.setErrorEnabled(true);
                textLayout.setError("Please enter your username.");
            } else textLayout.setErrorEnabled(false);
        } else if (textLayout.getId() == emailLayout.getId()) {
            if (textFieldText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(textFieldText).matches()) {
                textLayout.setErrorEnabled(true);
                textLayout.setError("Please enter your email address in format: example@mail.com");
            } else textLayout.setErrorEnabled(false);
        }
    }

    public void checkPasswords() {
        if (!passwordField.getText().toString().isEmpty()) {
            passwordLayout.setErrorEnabled(false);
            passwordAgainLayout.setErrorEnabled(true);

            password = passwordField.getText().toString();
            passwordAgain = passwordAgainField.getText().toString();
            if (password.equals(passwordAgain)) {
                passwordAgainLayout.setErrorEnabled(false);
                passwordOk = true;
                registerButton.setEnabled(true);
            } else {
                passwordAgainLayout.setError("Passwords must match!");
            }
        } else {
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("Password field is empty.");
        }
    }

    public void startRegistering(View view) {
        if (passwordOk) {
            loader();

            username = usernameField.getText().toString();
            email = emailField.getText().toString();
            password = passwordField.getText().toString();
            passwordAgain = passwordAgainField.getText().toString();

            registerUser();
        }
    }

    public void registerUser() {
        Intent intent = new Intent(this, UserDatabaseActivity.class);
        Bundle extras = new Bundle();

        // TODO:
        //  - Check that username/email isn't registered
        //  - Check password is valid
        //  - Change password to hashed one. (Create own algorithm?)

        if (usernameTaken) {
            extras.putInt("requestCode", checkUsername);
            extras.putString("username", username);
            intent.putExtras(extras);
            startActivityForResult(intent, checkUsername);
        } else if (emailTaken) {
            extras.putInt("requestCode", checkEmail);
            extras.putString("email", email);
            intent.putExtras(extras);
            startActivityForResult(intent, checkEmail);
        } else {
            extras.putInt("requestCode", registerNewUser);
            extras.putString("username", username);
            extras.putString("email", email);
            extras.putString("password", password);
            intent.putExtras(extras);
            startActivityForResult(intent, registerNewUser);
        }
    }
}