package com.example.androidassignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button clickButton = findViewById(R.id.clickMe);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivity(intent);

            }
        });

        Button startChat = findViewById(R.id.startChat);
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked start chat");
                Intent chat = new Intent(MainActivity.this,ChatWindow.class);
                startActivity(chat);
            }
        });

        Button testToolbar = findViewById(R.id.testToolbar);
        testToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked start chat");
                Intent chat = new Intent(MainActivity.this,TestToolbar.class);
                startActivity(chat);
            }
        });

        Button WeatherForecast = findViewById(R.id.WeatherForecast);
        WeatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked WeatherForecast");
                Intent chat = new Intent(MainActivity.this,WeatherForecast.class);
                startActivity(chat);
            }
        });
    }

    public void disp() {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(MainActivity.this, R.string.checkbox_response, duration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
        Intent intent = new Intent();
        if (flag >= 1) {
            disp();
        }
        flag = flag + 1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}