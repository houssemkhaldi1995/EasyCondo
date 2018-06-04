package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends AppCompatActivity {

    TextView name, address, city, country, postalCode, numBlocs, numBlocHouses;
    EditText numBlock, numHouse;
    Button send;
    String sName, sAddress, sCity, sCountry, sNumBlock, sNumHouse, sID;
    int iPostalCode, iNumBlocs, iNumBlocHouses, iNumBlock, iNumHouse;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent intent = getIntent();

        sID = intent.getExtras().getString("id");
        sName = intent.getExtras().getString("name");
        sAddress = intent.getExtras().getString("address");
        sCity = intent.getExtras().getString("city");
        sCountry = intent.getExtras().getString("country");
        iPostalCode = intent.getExtras().getInt("postalCode");
        iNumBlocs = intent.getExtras().getInt("numBlocs");
        iNumBlocHouses = intent.getExtras().getInt("numBlocHouses");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Condos").child(sID).child("joinRequests");

        name = findViewById(R.id.condoField);
        address = findViewById(R.id.addressField);
        city = findViewById(R.id.cityField);
        country = findViewById(R.id.countryField);
        postalCode = findViewById(R.id.postalCodeField);
        numBlocs = findViewById(R.id.blockField);
        numBlocHouses = findViewById(R.id.houseField);
        numBlock = findViewById(R.id.numBlock);
        numHouse = findViewById(R.id.numHouse);
        send = findViewById(R.id.send);

        name.setText(sName);
        address.setText(sAddress);
        city.setText(sCity);
        country.setText(sCountry);
        postalCode.setText(iPostalCode + "");
        numBlocs.setText(iNumBlocs + "");
        numBlocHouses.setText(iNumBlocHouses + "");

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {
        sNumBlock = numBlock.getText().toString().trim();
        sNumHouse = numHouse.getText().toString().trim();
        if (!validate()) {
            Toast.makeText(RequestActivity.this, "Send a join request has failed", Toast.LENGTH_SHORT).show();
        } else {
            Map map = new HashMap();
            SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);

            map.put("client", sharedPreferences.getString("id", ""));
            Timestamp time = new Timestamp(System.currentTimeMillis());
            map.put("timestamps", time.getTime());
            map.put("numBloc", iNumBlock);
            map.put("numHouse", iNumHouse);
            mDatabase.push().setValue(map);

            mDatabase = FirebaseDatabase.getInstance().getReference().child("Clients").child(sharedPreferences.getString("id", "")).child("state");
            mDatabase.setValue("Waiting");

            Toast.makeText(RequestActivity.this, "Send a join request has succeed", Toast.LENGTH_SHORT).show();
            saveInfo();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Intent intent = new Intent(RequestActivity.this, WaitingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 2000);
        }
    }

    private void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numBloc", iNumBlock);
        editor.putInt("numHouse", iNumHouse);
        editor.putString("idCondo", sID);
        editor.putString("nameCondo", sName);
        editor.putString("addressCondo", sAddress);
        editor.putString("cityCondo", sCity);
        editor.putString("countryCondo", sCountry);
        editor.putInt("postalCodeCondo", iPostalCode);
        editor.putInt("numBlocsCondo", iNumBlocs);
        editor.putInt("numBlocHousesCondo", iNumBlocHouses);
        editor.apply();
    }

    private boolean validate() {
        boolean valid = true;
        if (sNumBlock.isEmpty() || sNumBlock.length() > 4) {
            numBlock.setError("Please enter a valid block number");
            numBlock.requestFocus();
            valid = false;
        } else {
            iNumBlock = Integer.parseInt(sNumBlock);
            if (iNumBlock > iNumBlocs) {
                numBlock.setError("Please enter a valid block number");
                numBlock.requestFocus();
                valid = false;
            }
        }

        if (sNumHouse.isEmpty() || sNumHouse.length() > 4) {

            numHouse.setError("Please enter a valid house number");
            numHouse.requestFocus();
            valid = false;
        } else {
            iNumHouse = Integer.parseInt(sNumHouse);
            if (iNumHouse > iNumBlocHouses) {
                address.setError("Please enter a valid house number");
                address.requestFocus();
                valid = false;
            }
        }
        return valid;
    }
}
