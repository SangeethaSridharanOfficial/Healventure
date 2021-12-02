package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText fname, lname, uname, pwd, cfrmPwd;
    RadioGroup gender;
    RadioButton selectedGender;
    private DatabaseReference healventureDatabase;
    private DatabaseReference loginEndPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        fname = findViewById(R.id.f_name);
        lname = findViewById(R.id.fL_name);
        uname= findViewById(R.id.username);
        pwd = findViewById(R.id.pwd);
        cfrmPwd = findViewById(R.id.cfrmPwd);
        gender = findViewById(R.id.rbg);
        healventureDatabase = FirebaseDatabase.getInstance().getReference();
        loginEndPoint = healventureDatabase.child("login");
    }
    private Boolean validateRegistration(){
        Boolean result = true;
        if(fname.getText().toString().equals("") || lname.getText().toString().equals("") ||
                uname.getText().toString().equals("") || pwd.getText().toString().equals("") ||
                cfrmPwd.getText().toString().equals("")) result = false;
        int selectedId = gender.getCheckedRadioButtonId();
        selectedGender = (RadioButton) findViewById(selectedId);
        if(selectedGender == null) result = false;
        if(!pwd.getText().toString().equals(cfrmPwd.getText().toString())) result = false;
        return result;
    }

    public void registerClicked(View view){

        if(validateRegistration() == true){
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
                            }
                        }
                        if(isUserAvailable == false){
                            LoginModel login = new LoginModel();
                            login.setFname(fname.getText().toString());
                            login.setLname(lname.getText().toString());
                            login.setEmail(uname.getText().toString());
                            login.setGender(selectedGender.getText().toString());
                            login.setRole("patient");
                            login.setPassword(pwd.getText().toString());
                            loginEndPoint.child(login.getEmail()).setValue(login).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Signup.this,"OOPS!! Something wrong. Try again!", Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent mainPage = new Intent(Signup.this, MainPageActivity.class);
                                    mainPage.putExtra("username", login.getEmail());
                                    startActivity(mainPage);
                                }
                            });
                        }else{
                            Toast.makeText(Signup.this,"User already Available!! Please Login", Toast.LENGTH_LONG).show();

                        }
                    }else{
                        Toast.makeText(Signup.this,"There is no data", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }else{
            Toast.makeText(Signup.this,"Please check the details", Toast.LENGTH_LONG).show();
        }
    }
}
