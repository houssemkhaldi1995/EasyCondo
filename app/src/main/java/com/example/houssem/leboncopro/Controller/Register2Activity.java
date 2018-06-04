package com.example.houssem.leboncopro.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.houssem.leboncopro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.Client;

public class Register2Activity extends AppCompatActivity {

    EditText city, country, address, postalCode, phone;
    RadioButton male, female;
    String sCity, sCountry, sAddress, sGender, sPhone, sPostalCode, sEmail, sPwd, sFname, sLname;
    Button sign;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference mDatabase;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Intent intent = getIntent();
        sEmail = intent.getExtras().getString("sEmail");
        sPwd = intent.getExtras().getString("sPwd");
        sFname = intent.getExtras().getString("sFname");
        sLname = intent.getExtras().getString("sLname");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clients");
        client = new Client();

        progressBar = findViewById(R.id.progressBarUp);

        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        address = findViewById(R.id.address);
        postalCode = findViewById(R.id.postalCode);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.city);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        sign = findViewById(R.id.register);

        sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                register();
            }
        });
    }


    public void register() {
        sAddress = address.getText().toString().trim();
        sCity = city.getText().toString().trim();
        sCountry = country.getText().toString().trim();
        sPhone = phone.getText().toString().trim();
        sPostalCode = postalCode.getText().toString().trim();

        if (!validate()) {
            Toast.makeText(this, "Sign up has failed !", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(sEmail, sPwd)
                    .addOnCompleteListener(Register2Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {

                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String user_id = firebaseUser.getUid();

                                mDatabase = mDatabase.child(user_id);

                                client.setId(user_id);
                                client.setFirstName(sFname);
                                client.setLastName(sLname);
                                client.setCity(sCity);
                                client.setGender(sGender);
                                client.setPhone(Integer.parseInt(sPhone));
                                client.setPostalCode(Integer.parseInt(sPostalCode));
                                client.setCountry(sCountry);
                                client.setAddress(sAddress);


                                mDatabase.setValue(client);
                                Toast.makeText(Register2Activity.this, "Sign up has succeed", Toast.LENGTH_LONG).show();

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(Register2Activity.this, "You are already registered", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Register2Activity.this, "Sign up has failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Register2Activity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 4000);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.male:
                if (checked) {
                    sGender = male.getText().toString().trim();
                    break;
                }
            case R.id.female:
                if (checked) {
                    sGender = female.getText().toString().trim();
                    break;
                }
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (!male.isChecked() && !female.isChecked()) {
            male.setError("Please check your gender");
            male.requestFocus();
            female.requestFocus();
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
