package com.example.houssem.leboncopro.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Client;

public class DetailsActivity extends AppCompatActivity {

    TextView message, subject, sender, state, date;
    String sMessage, sSubject, sSender, sState, sDate;
    Button icon;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clients");

        message = findViewById(R.id.dMessage);
        subject = findViewById(R.id.dSub);
        sender = findViewById(R.id.dSender);
        state = findViewById(R.id.dState);
        date = findViewById(R.id.dDate);
        icon = findViewById(R.id.dSubject);

        sMessage = getIntent().getExtras().getString("message");
        sSubject = getIntent().getExtras().getString("subject");
        sSender = getIntent().getExtras().getString("sender");
        sState = getIntent().getExtras().getString("state");
        sDate = getIntent().getExtras().getString("date");

        message.setText(sMessage);
        subject.setText(sSubject);
        state.setText(sState);
        date.setText(sDate);

        switch (sSubject) {
            case "Fire":
                icon.setBackgroundResource(R.drawable.miscellaneous);
                break;
            case "Water Leak":
                icon.setBackgroundResource(R.drawable.water);
                break;
            case "Gardening":
                icon.setBackgroundResource(R.drawable.jardin);
                break;
            case "Blackout":
                icon.setBackgroundResource(R.drawable.electricity);
                break;
            case "Noise":
                icon.setBackgroundResource(R.drawable.tongzhi);
                break;
            case "Paint":
                icon.setBackgroundResource(R.drawable.paint);
                break;
            case "Road Fix":
                icon.setBackgroundResource(R.drawable.road);
                break;
            case "Other":
                icon.setBackgroundResource(R.drawable.other);
                break;
            case "Empty Trash Can":
                icon.setBackgroundResource(R.drawable.trash);
                break;
            case "Lighting":
                icon.setBackgroundResource(R.drawable.lighting);
                break;
            case "Repair":
                icon.setBackgroundResource(R.drawable.repair);
                break;
            case "Cleanliness":
                icon.setBackgroundResource(R.drawable.cleanliness);
                break;
        }

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Client client = child.getValue(Client.class);
                    if (client.getId().equals(sSender)) {
                        sender.setText(String.format("%s %s", client.getFirstName(), client.getLastName()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
