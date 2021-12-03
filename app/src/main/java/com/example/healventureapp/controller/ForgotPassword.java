package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.example.healventureapp.model.LoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    String username = "";
    EditText uname, pwd, cfrmpwd;
    private DatabaseReference healventureDatabase;
    private DatabaseReference loginEndPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        healventureDatabase = FirebaseDatabase.getInstance().getReference();
        loginEndPoint = healventureDatabase.child("login");
        this.setTitle("Forgetpassword");
        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.newPwd);
        cfrmpwd = findViewById(R.id.cfrmPwd);
        Intent user = getIntent();
        if (user.hasExtra("username")) {
            username = user.getStringExtra("username");
            uname.setText(username);
        }
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

    public void submitClicked(View view) {
        if(!uname.getText().toString().equals("") && !pwd.getText().toString().equals("")
                && !cfrmpwd.getText().toString().equals("") && pwd.getText().toString().equals(cfrmpwd.getText().toString())){
            LoginModel login = new LoginModel();
            login.setEmail(uname.getText().toString());
            login.setPassword(pwd.getText().toString());
            loginEndPoint.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        boolean isUserAvailable = false;
                        Map<String,String> availableUser = null;
                        for(DataSnapshot doc: task.getResult().getChildren()){
                            Map<String,String> user=(HashMap<String, String>)doc.getValue();
                            if(user.get("email").equals(uname.getText().toString())){
                                isUserAvailable = true;
                                availableUser = (HashMap<String, String>)doc.getValue();
                            }
                        }
                        if(isUserAvailable == false){
                            Toast.makeText(ForgotPassword.this,"Invalid User!! Please Register", Toast.LENGTH_LONG).show();
                        }else{
                            login.setFname(availableUser.get("fname"));
                            login.setLname(availableUser.get("lname"));
                            login.setGender(availableUser.get("gender"));
                            login.setRole("patient");
                            loginEndPoint.child(login.getEmail()).setValue(login).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForgotPassword.this,"OOPS!! Something wrong. Try again!", Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent mainPage = new Intent(ForgotPassword.this, Login.class);
                                    startActivity(mainPage);
                                }
                            });
                        }
                    }else{
                        Toast.makeText(ForgotPassword.this,"There is no data", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(ForgotPassword.this,"Please enter all details", Toast.LENGTH_LONG).show();
        }

    }
}
