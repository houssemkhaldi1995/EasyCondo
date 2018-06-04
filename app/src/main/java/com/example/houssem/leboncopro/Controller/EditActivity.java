package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Model.Client;

public class EditActivity extends AppCompatActivity {

    EditText city, country, address, postalCode, phone, first, last;
    String sCity, sCountry, sAddress, sPhone, sPostalCode, sFname, sLname;
    Button save;
    Client client;
    DatabaseReference mDatabase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        first = findViewById(R.id.uFirst);
        last = findViewById(R.id.uLast);
        address = findViewById(R.id.uAddress);
        city = findViewById(R.id.uCity);
        country = findViewById(R.id.uCountry);
        postalCode = findViewById(R.id.uPostal);
        phone = findViewById(R.id.phoneNum);

        save = findViewById(R.id.save);

        sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clients").child(sharedPreferences.getString("id", ""));
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                client = dataSnapshot.getValue(Client.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        first.setText(sharedPreferences.getString("firstName", ""));
        last.setText(sharedPreferences.getString("lastName", ""));
        address.setText(sharedPreferences.getString("address", ""));
        city.setText(sharedPreferences.getString("city", ""));
        country.setText(sharedPreferences.getString("country", ""));
        postalCode.setText(sharedPreferences.getInt("postalCode", 0) + "");
        phone.setText(sharedPreferences.getInt("phone", 0) + "");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        sFname = first.getText().toString().trim();
        sLname = last.getText().toString().trim();
        sAddress = address.getText().toString().trim();
        sPhone = phone.getText().toString().trim();
        sCity = city.getText().toString().trim();
        sCountry = country.getText().toString().trim();
        sPostalCode = postalCode.getText().toString().trim();

        if (!checkValid()) {
            Toast.makeText(this, "Edit profile has failed !", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firstName", sFname);
            editor.putString("lastName", sLname);
            editor.putString("address", sAddress);
            editor.putString("city", sCity);
            editor.putString("country", sCountry);
            editor.putInt("postalCode", Integer.valueOf(sPostalCode));
            editor.putInt("phone", Integer.valueOf(sPhone));
            editor.apply();

            client.setId(sharedPreferences.getString("id", ""));
            client.setFirstName(sharedPreferences.getString("firstName", ""));
            client.setLastName(sharedPreferences.getString("lastName", ""));
            client.setCity(sharedPreferences.getString("city", ""));
            client.setGender(sharedPreferences.getString("gender", ""));
            client.setPhone(Integer.valueOf(sPhone));
            client.setPostalCode(sharedPreferences.getInt("postalCode", 0));
            client.setCountry(sharedPreferences.getString("country", ""));
            client.setAddress(sharedPreferences.getString("address", ""));
            client.setState(client.getState());
            client.setHouseNumber(client.getHouseNumber());
            client.setBlocNumber(client.getBlocNumber());

            mDatabase.setValue(client);
        }
    }

    private boolean checkValid() {
        boolean valid = true;

        if (sFname.isEmpty() || sFname.length() > 30) {
            first.setError("Please enter a valid first name");
            first.requestFocus();
            valid = false;
        }
        if (sLname.isEmpty() || sLname.length() > 30) {
            last.setError("Please enter a valid last name");
            last.requestFocus();
            valid = false;
        }
        if (sAddress.isEmpty() || sAddress.length() > 30) {
            address.setError("Please enter a valid address");
            address.requestFocus();
            valid = false;
        }
        if (sCity.isEmpty() || sCity.length() > 30) {
            city.setError("Please enter a valid city name");
            city.requestFocus();
            valid = false;
        }
        if (sCountry.isEmpty() || sCountry.length() > 30) {
            country.setError("Please enter a valid country name");
            country.requestFocus();
            valid = false;
        }
        if (sPostalCode.isEmpty() || sPostalCode.length() > 30) {
            postalCode.setError("Please enter a valid postal code number");
            postalCode.requestFocus();
            valid = false;
        }
        if (sPhone.isEmpty() || sPhone.length() > 30) {
            phone.setError("Please enter a valid postal code number");
            phone.requestFocus();
            valid = false;
        }

        return valid;
    }
}
