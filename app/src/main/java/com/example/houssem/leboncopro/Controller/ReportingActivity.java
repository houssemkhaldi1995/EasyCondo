package com.example.houssem.leboncopro.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.houssem.leboncopro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;

import Model.Incident;


public class ReportingActivity extends AppCompatActivity {

    EditText msg;
    ImageView img;
    String subject, message;
    Uri imgret;
    ProgressBar progressBar;

    String downloadUrl;
    DatabaseReference mDatabase;
    String idClient;
    Timestamp time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);

        time = new Timestamp(System.currentTimeMillis());

        SharedPreferences sharedPreferences = getSharedPreferences("Client", Context.MODE_PRIVATE);
        String idCondo = sharedPreferences.getString("idCondo", "");
        idClient = sharedPreferences.getString("id", "");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Incidents").child(idCondo);

        img = findViewById(R.id.img);
        msg = findViewById(R.id.msg);
        subject = getIntent().getStringExtra("name");
        progressBar = findViewById(R.id.progressBarReport);
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String picDir = pic.getPath();
        Uri data = Uri.parse(picDir);
        intent.setDataAndType(data, "image/*");
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 20) {
                imgret = data.getData();

                try {
                    InputStream inputStream = getContentResolver().openInputStream(imgret);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    img.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean uploadImg() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Incidents/" + time.getTime());
        final boolean[] success = {true};
        if (imgret != null) {
            progressBar.setVisibility(View.VISIBLE);
            storageReference.putFile(imgret)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            downloadUrl = taskSnapshot.getDownloadUrl().toString();

                            progressBar.setVisibility(View.GONE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressBar.setVisibility(View.GONE);
                            success[0] = false;
                        }
                    });
        }
        return success[0];
    }

    public boolean uploadInfo() {

        Incident incident = new Incident();
        incident.setMessage(message);
        incident.setSeen(false);
        incident.setSender(idClient);
        incident.setState("new");
        incident.setSubject(subject);
        incident.setTimestamp(time.getTime());

        mDatabase.push().setValue(incident);

        return true;
    }


    public void onSendClicked(View view) {
        message = msg.getText().toString();
        if (message.isEmpty()) {
            msg.setError("Please enter a valid report message");
            msg.requestFocus();
            return;
        }
        if (uploadImg() && uploadInfo()) {
            Toast.makeText(ReportingActivity.this, "Send a report has succeed", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ReportingActivity.this, NextActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 2000);
        } else {
            Toast.makeText(ReportingActivity.this, "Send a report has failed", Toast.LENGTH_SHORT).show();
        }
    }
}
