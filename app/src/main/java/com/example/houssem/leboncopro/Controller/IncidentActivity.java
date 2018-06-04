package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.houssem.leboncopro.Controller.Adapter.MyAdapter2;
import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Incident;

public class IncidentActivity extends AppCompatActivity {

    Button fab;
    private RecyclerView mRecyclerView;
    private MyAdapter2 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Incident> data = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        String idCondo = sharedPreferences.getString("idCondo", "");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Incidents").child(idCondo);

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Incident incident = child.getValue(Incident.class);
                    data.add(incident);
                }
                Collections.reverse(data);
                mAdapter.setFilter(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intend = new Intent(IncidentActivity.this, MenuActivity.class);
                startActivity(intend);
            }
        });

        mRecyclerView = findViewById(R.id.recycler2);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter2(this, data);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }
}
