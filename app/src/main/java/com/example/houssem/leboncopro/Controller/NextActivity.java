package com.example.houssem.leboncopro.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.houssem.leboncopro.R;
import com.google.firebase.auth.FirebaseAuth;

public class NextActivity extends AppCompatActivity {

    Button report, pay, notif, setting, out, expenditure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        report = findViewById(R.id.reportmenu);
        pay = findViewById(R.id.payment);
        notif = findViewById(R.id.notification);
        setting = findViewById(R.id.stg);
        out = findViewById(R.id.out);
        expenditure = findViewById(R.id.expenditure);

        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, IncidentActivity.class);
                startActivity(intent);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(NextActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        notif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, IncidentActivity.class);
                startActivity(intent);
            }
        });

        expenditure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, ExpenditureActivity.class);
                startActivity(intent);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}
