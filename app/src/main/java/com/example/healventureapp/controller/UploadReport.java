package com.example.healventureapp.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    EditText selectPdf;
    Button uploadBtn;

    StorageReference storageRef;
    DatabaseReference databaseReference;
    DatabaseReference reportEndPoint;

    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_report);
        selectPdf = findViewById(R.id.pdfName);
        uploadBtn = findViewById(R.id.uploadBtn);
        storageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reportEndPoint = databaseReference.child("reports");
        uploadBtn.setEnabled(false);
        selectPdf.setOnClickListener(new View.OnClickListener(){
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
                                    System.out.println("Data " + data);
                                    uploadPDFFileFirebase(data.getData());
                                }
                            });
                        }
                    }
                });
    }

    private void selectPDF() {
        Intent pickFile = new Intent();
        pickFile.setType("application/pdf");
        pickFile.setAction(pickFile.ACTION_GET_CONTENT);
        activityResultLauncher.launch(pickFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){

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
                UploadPdf pdf = new UploadPdf(selectPdf.getText().toString(), uri.toString());
                reportEndPoint.child(reportEndPoint.push().getKey()).setValue(pdf);
                Toast.makeText(UploadReport.this, "File Uploaded", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded.... " + (int) progress+"%");
            }
        });
    }
}