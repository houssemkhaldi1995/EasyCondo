package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.houssem.leboncopro.Controller.Adapter.MyAdapter4;
import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Payment;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter4 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Payment> data = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);

        String id = sharedPreferences.getString("id", "");
        String idCondo = sharedPreferences.getString("idCondo", "");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Payments").child(idCondo).child(id);

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Payment payment = child.getValue(Payment.class);
                    data.add(payment);
                }
                Collections.reverse(data);
                mAdapter.setFilter(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });

        mRecyclerView = findViewById(R.id.recycler4);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter4(this, data);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }
}
