package com.example.healventureapp.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.example.healventureapp.model.UploadPdf;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadReport extends AppCompatActivity {
    EditText selectPdf, patientName;
    Button uploadBtn, chooseBtn;


    StorageReference storageRef;
    DatabaseReference databaseReference;
    DatabaseReference reportEndPoint;

    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_report);
        selectPdf = findViewById(R.id.pdfName);
        patientName = findViewById(R.id.patName);
        uploadBtn = findViewById(R.id.uploadBtn);
        chooseBtn = findViewById(R.id.choosePdf);
        storageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reportEndPoint = databaseReference.child("reports");
        uploadBtn.setEnabled(false);
        chooseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });

        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        uploadBtn.setEnabled(true);
                        Intent data = result.getData();
                        selectPdf.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
                        uploadBtn.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                uploadPDFFileFirebase(data.getData());
                            }
                        });
                    }
                }
            });
    }

    private void selectPDF() {
        if(patientName.getText().toString().equals("")){
            Toast.makeText(UploadReport.this, "Please enter the patient name", Toast.LENGTH_LONG).show();
        }else{
            Intent pickFile = new Intent();
            pickFile.setType("application/pdf");
            pickFile.setAction(pickFile.ACTION_GET_CONTENT);
            activityResultLauncher.launch(pickFile);
        }
    }

    private void uploadPDFFileFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading....");
        progressDialog.show();

        StorageReference ref = storageRef.child("upload"+ System.currentTimeMillis()+".pdf");
        ref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!urlTask.isComplete());
                Uri uri = urlTask.getResult();
                UploadPdf pdf = new UploadPdf(selectPdf.getText().toString(), uri.toString(), patientName.getText().toString());
                reportEndPoint.child(reportEndPoint.push().getKey()).setValue(pdf);
                Toast.makeText(UploadReport.this, "File Uploaded", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                uploadBtn.setEnabled(false);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded.... " + (int) progress+"%");
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.profile:
                Intent mainPage = new Intent(this, Profile.class);
                mainPage.putExtra("testData","test");
                startActivity(mainPage);
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.notification:

                Intent mainPage1 = new Intent(this, NotificationDoctor.class);
                mainPage1.putExtra("testData","test");
                startActivity(mainPage1);
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }
}