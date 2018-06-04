package com.example.houssem.leboncopro.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.houssem.leboncopro.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent HomeActivity = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(HomeActivity);
            }
        }, 4000);
    }
}
