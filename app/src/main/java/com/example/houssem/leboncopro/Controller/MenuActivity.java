package com.example.houssem.leboncopro.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.houssem.leboncopro.R;

public class MenuActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button fire, leak, gardening, blackout, noise, paint, road, other, trash, light, repair, cleanliness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fire = findViewById(R.id.fire);
        leak = findViewById(R.id.leak);
        gardening = findViewById(R.id.gardening);
        blackout = findViewById(R.id.blackout);
        noise = findViewById(R.id.noise);
        paint = findViewById(R.id.paint);
        road = findViewById(R.id.road);
        other = findViewById(R.id.other);
        trash = findViewById(R.id.trash);
        light = findViewById(R.id.lighting);
        repair = findViewById(R.id.repair);
        cleanliness = findViewById(R.id.cleanliness);

        fire.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Fire");
                startActivity(intent);
            }
        });
        leak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Water Leak");
                startActivity(intent);
            }
        });
        gardening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Gardening");
                startActivity(intent);
            }
        });
        blackout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Blackout");
                startActivity(intent);
            }
        });
        noise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Noise");
                startActivity(intent);
            }
        });
        paint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Paint");
                startActivity(intent);
            }
        });
        road.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Road Fix");
                startActivity(intent);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Other");
                startActivity(intent);
            }
        });
        trash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Empty Trash Can");
                startActivity(intent);
            }
        });
        light.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Lighting");
                startActivity(intent);
            }
        });
        repair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Repair");
                startActivity(intent);
            }
        });
        cleanliness.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ReportingActivity.class);
                intent.putExtra("name", "Cleanliness");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
