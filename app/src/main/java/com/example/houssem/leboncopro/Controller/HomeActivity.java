package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.houssem.leboncopro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Model.Client;

public class HomeActivity extends AppCompatActivity {

    EditText email, pwd;
    TextView forgot;
    String sEmail, sPwd;
    Button log, sign;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference mDatabase;
    ArrayList<Client> data;
    Client client;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clients");

        client = new Client();

        email = findViewById(R.id.logUser);
        pwd = findViewById(R.id.logPwd);
        log = findViewById(R.id.log);
        sign = findViewById(R.id.sign);
        progressBar = findViewById(R.id.progressBarIn);

        sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        sEmail = email.getText().toString().trim();
        sPwd = pwd.getText().toString().trim();
        if (!checkValid()) {
            Toast.makeText(this, "Log in has failed !", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(sEmail, sPwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                final String user_id = mAuth.getCurrentUser().getUid();
                                progressBar.setVisibility(View.GONE);
                                mDatabase.addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        data = new ArrayList<>();
                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            client = child.getValue(Client.class);
                                            if (client.getId().equals(user_id)) {
                                                saveInfo();
                                                state = client.getState();
                                                break;
                                            }
                                        }
                                        while (state == null) ;
                                        switch (client.getState()) {
                                            case "None": {
                                                Intent intent = new Intent(HomeActivity.this, CondoActivity.class);
                                                startActivity(intent);
                                                break;
                                            }
                                            case "Joined": {
                                                Intent intent = new Intent(HomeActivity.this, NextActivity.class);
                                                startActivity(intent);
                                                break;
                                            }
                                            default:
                                                Intent intent = new Intent(HomeActivity.this, WaitingActivity.class);
                                                startActivity(intent);
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            } else {
                                Toast.makeText(HomeActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    private void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", client.getId());
        editor.putString("firstName", client.getFirstName());
        editor.putString("lastName", client.getLastName());
        editor.putString("address", client.getAddress());
        editor.putString("city", client.getCity());
        editor.putString("country", client.getCountry());
        editor.putString("gender", client.getGender());
        editor.putInt("postalCode", client.getPostalCode());
        editor.putInt("phone", client.getPhone());
        editor.putString("state", client.getState());
        editor.apply();
    }

    private boolean checkValid() {
        boolean valid = true;
        if (sEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            email.setError("Please enter a valid email address");
            email.requestFocus();
            valid = false;
        }
        if (sPwd.isEmpty() || sPwd.length() < 6) {
            pwd.setError("Please enter a valid password");
            pwd.requestFocus();
            valid = false;
        }
        return valid;
    }


}
