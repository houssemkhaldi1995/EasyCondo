package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.houssem.leboncopro.Controller.Adapter.MyAdapter3;
import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Message;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter3 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Message> data = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages").child(id);

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Message message = child.getValue(Message.class);
                    message.setKey(child.getKey());
                    data.add(message);
                }
                Collections.reverse(data);
                mAdapter.setFilter(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });

        mRecyclerView = findViewById(R.id.recycler3);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter3(this, data);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
