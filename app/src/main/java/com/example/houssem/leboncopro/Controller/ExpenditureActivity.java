package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.houssem.leboncopro.Controller.Adapter.MyAdapter5;
import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Bill;


public class ExpenditureActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private MyAdapter5 mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<Bill> data = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);


        SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        String idCondo = sharedPreferences.getString("idCondo", "");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bills").child(idCondo);

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Bill bill = child.getValue(Bill.class);
                    bill.setKey(Integer.parseInt(child.getKey()));
                    data.add(bill);
                }
                Collections.reverse(data);
                mAdapter.setFilter(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });

        mRecyclerView = findViewById(R.id.recycler5);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter5(this, data);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
}
