package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.example.healventureapp.model.LoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private DatabaseReference healventureDatabase;
    private DatabaseReference profileEndPoint;
    private DatabaseReference docProf;
    String username;
    TextView firstName,surName,userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        this.setTitle("Profile");
        firstName=findViewById(R.id.txtname);
        surName=findViewById(R.id.txtSurename);
        userName=findViewById(R.id.txtUsername);
        healventureDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();

        if(intent.hasExtra("username")){
            username = intent.getStringExtra("username");
        }
        profileEndPoint = healventureDatabase.child("login");
        LoginModel login = new LoginModel();
        profileEndPoint.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    boolean isUserAvailable = false;
                    Map<String,String> availableUser = null;
                    for(DataSnapshot doc: task.getResult().getChildren()){
                        Map<String,String> user=(HashMap<String, String>)doc.getValue();
                        if(user.get("email").equals(username)){
                            isUserAvailable = true;
                            availableUser = (HashMap<String, String>)doc.getValue();
                            firstName.setText("Name:  "+availableUser.get("fname"));
                            surName.setText("Surename:  " +availableUser.get("lname"));
                            userName.setText("User Name:  "+availableUser.get("email"));
                        }
                    }
                    if(isUserAvailable == false){
                        docProf = healventureDatabase.child("doctorLogin");
                        LoginModel login = new LoginModel();
                        docProf.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean isUserAvailable = false;
                                    Map<String, String> availableUser = null;
                                    for (DataSnapshot doc : task.getResult().getChildren()) {
                                        Map<String, String> user = (HashMap<String, String>) doc.getValue();
                                        availableUser = (HashMap<String, String>) doc.getValue();
                                        firstName.setText("Name:  " + availableUser.get("email"));
                                        surName.setText("Surename:  " + availableUser.get("email"));
                                        userName.setText("User Name:  " + availableUser.get("email"));
                                    }
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(Profile.this,"There is no data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void logoutClicked(View v){
        Intent mainPage = new Intent(this, Login.class);
        startActivity(mainPage);
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

                break;
            // action with ID action_settings was selected
            case R.id.notification:

                Intent mainPage1 = new Intent(this, NotificationPatients.class);
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