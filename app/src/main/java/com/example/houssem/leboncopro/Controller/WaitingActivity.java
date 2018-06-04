package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.houssem.leboncopro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingActivity extends AppCompatActivity {

    TextView condo, numHouse, numBlock;
    Button cancel, out;
    DatabaseReference mDatabase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        condo = findViewById(R.id.wCondo);
        numHouse = findViewById(R.id.wHouse);
        numBlock = findViewById(R.id.wBlock);
        cancel = findViewById(R.id.wCancel);
        out = findViewById(R.id.wLogout);

        sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        condo.setText(sharedPreferences.getString("nameCondo", ""));
        numBlock.setText(sharedPreferences.getInt("numBloc", 0) + "");
        numHouse.setText(sharedPreferences.getInt("numHouse", 0) + "");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clients").child(sharedPreferences.getString("id", "")).child("state");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String state = dataSnapshot.getValue(String.class);
                if (state.equals("Joined")) {
                    finish();
                    Intent intent = new Intent(WaitingActivity.this, NextActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(WaitingActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mDatabase.setValue("None");
                finish();
                Intent intent = new Intent(WaitingActivity.this, CondoActivity.class);
                startActivity(intent);
            }
        });

    }
}
