package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    public void sendMessage(View view) {
        TextView newMessage = findViewById(R.id.MessageInput);
        String message = newMessage.getText().toString();
        String url = "http://sendToComputer/" + message;
        setMessage(message, true);
        newMessage.setText("");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i("Information", response);
                }, error -> Log.e("Error", String.valueOf(error)));
        getUrl(stringRequest);
    }

    protected void setMessage(String message, boolean your) {
        if (!message.equals("")) {
            if (your) {
                TextView yourMessage = findViewById(R.id.message1);
                yourMessage.setText(message);
            }
            else {
                TextView reply = findViewById(R.id.response1);
                reply.setText("");

                message = message.replaceAll("\"", ""); message = message.replace("[", ""); message = message.replace("]", "");
                StringBuilder newMessage = new StringBuilder();
                int newMessages = 0;
                for (int i=0; i < message.length(); i++) {
                    char charForMessage = message.charAt(i);
                    if (charForMessage != ',') {
                        newMessage.append(charForMessage);
                    } else if (charForMessage == ',' || i >= message.length()) {
                        newMessages++;
                        reply.append(String.format("Message %d: %s \n", newMessages, newMessage));
                        newMessage = new StringBuilder();
                    }
                }
                newMessages++;
                reply.append(String.format("Message %d: %s ", newMessages, newMessage));
            }
        }
    }

    protected void getUrl(StringRequest request) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 3*1000; //Delay for 3 seconds.  One second = 1000 milliseconds.

    @Override
    protected void onResume() {

        handler.postDelayed( runnable = () -> {
            String url = "http://retriveMessageForPhone";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> setMessage(response, false), error -> Log.e("Error", String.valueOf(error)));
            getUrl(stringRequest);

            handler.postDelayed(runnable, delay);
        }, delay);

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }
}