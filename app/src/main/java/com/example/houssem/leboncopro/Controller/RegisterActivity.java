package com.example.houssem.leboncopro.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.houssem.leboncopro.R;

public class RegisterActivity extends AppCompatActivity {

    EditText lname, email, pwd, cpwd, fname;
    String sEmail, sPwd, sCpwd, sFname, sLname;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        cpwd = findViewById(R.id.cpwd);
        fname = findViewById(R.id.firstname);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register() {
        sLname = lname.getText().toString().trim();
        sEmail = email.getText().toString().trim();
        sPwd = pwd.getText().toString().trim();
        sCpwd = cpwd.getText().toString().trim();
        sFname = fname.getText().toString().trim();

        if (!validate()) {
            Toast.makeText(this, "Sign up has failed !", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
            intent.putExtra("sFname", sFname);
            intent.putExtra("sEmail", sEmail);
            intent.putExtra("sLname", sLname);
            intent.putExtra("sPwd", sPwd);

            startActivity(intent);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (sFname.isEmpty() || sFname.length() > 30) {
            fname.setError("Please enter a valid first name");
            fname.requestFocus();
            valid = false;
        }
        if (sLname.isEmpty() || sLname.length() > 30) {
            lname.setError("Please enter a valid last name");
            lname.requestFocus();
            valid = false;
        }
        if (sEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            email.setError("Please enter a valid email address");
            email.requestFocus();
            valid = false;
        }
        if (sPwd.isEmpty() || sPwd.length() > 30) {
            pwd.setError("Please enter a valid password");
            pwd.requestFocus();
            valid = false;
        }
        if (sPwd.length() < 6) {
            pwd.setError("Minimum password length at least 6");
            pwd.requestFocus();
            valid = false;
        }

        if (sCpwd.isEmpty() || !sCpwd.equals(sPwd) || sCpwd.length() > 30) {
            cpwd.setError("Please enter a valid password");
            cpwd.requestFocus();
            valid = false;
        }

        return valid;
    }
}

