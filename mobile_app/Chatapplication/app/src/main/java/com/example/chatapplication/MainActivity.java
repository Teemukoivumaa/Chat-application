package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final int registerNewUser = 205;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, registerNewUser);
    }

    /*
    public void registerNewUser(View view) {
        Intent intent = new Intent(this, UserDatabase.class);
        Bundle extras = new Bundle();
        extras.putInt("requestCode", getSpecificUserCode);
        extras.putString("email", "test2@gmail.com");
        intent.putExtras(extras);
        startActivityForResult(intent, getSpecificUserCode);
    }
    */

    public void returnToPrevious(String userID) {
        Intent result = new Intent(); //create an instance of an Intent object.
        result.setData(Uri.parse(userID)); //set the value/data to pass back
        setResult(RESULT_OK, result); //set a result code, It is either RESULT_OK or RESULT_CANCELLED
        finish(); //Close the activity
    }

    // When activity exits after being called from startActivityForResult(intent, requestCode).
    // it returns here, and by matching the requestCodes we can figure out where & what the result is
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == registerNewUser) { // Specific user
            if (resultCode == RESULT_OK) {
                String dataString = data.getData().toString();
            }
        }
    }
}