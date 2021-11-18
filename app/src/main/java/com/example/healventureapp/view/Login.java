package com.example.healventureapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healventureapp.MainActivity;
import com.example.healventureapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private DatabaseReference lDatabase;
    private DatabaseReference loginEndPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        lDatabase = FirebaseDatabase.getInstance().getReference();
        loginEndPoint = lDatabase.child("login");
        Toast.makeText(this, "Firebase Connected", Toast.LENGTH_LONG).show();
    }

    public void loginClicked(View view) {
        setContentView(R.layout.main_page_activity);
//        Intent openPage = new Intent(Intent.ACTION_SEND);
//        if (openPage.resolveActivity(getPackageManager()) != null) {
//            startActivity(openPage);
        loginEndPoint.setValue("Test").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
    }
}
