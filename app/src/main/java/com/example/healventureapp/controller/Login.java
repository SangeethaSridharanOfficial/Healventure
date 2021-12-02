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
import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {
    private DatabaseReference healventureDatabase;
    private DatabaseReference loginEndPoint;
    private DatabaseReference doctorLoginEndPoint;
    EditText email, password;
    RadioGroup roleGrp;
    RadioButton role;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        healventureDatabase = FirebaseDatabase.getInstance().getReference();
        loginEndPoint = healventureDatabase.child("login");
        doctorLoginEndPoint = healventureDatabase.child("doctorLogin");
        setDoctorLoginDetails();
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        roleGrp = findViewById(R.id.roleGrp);
    }

    private void setDoctorLoginDetails(){
        LoginModel docLogin = new LoginModel();
        docLogin.setRole("doctor");
        docLogin.setEmail("doctor");
        docLogin.setPassword("doctor123");
        doctorLoginEndPoint.child(doctorLoginEndPoint.push().getKey()).setValue(docLogin).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this,"OOPS!! Something wrong. Try again!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }
    public void loginClicked(View view) {
        int selectedRole = roleGrp.getCheckedRadioButtonId();

        if(!email.getText().toString().equals("") && !password.getText().toString().equals("") && email != null && password != null && selectedRole > 0){
            role = (RadioButton) findViewById(selectedRole);
            if(role.getText().toString().toLowerCase().equals("doctor")){
                Intent docMainPage = new Intent(Login.this, MainPageActivity.class);
                docMainPage.putExtra("username",role.getText().toString());
                startActivity(docMainPage);
            }else{
                LoginModel login = new LoginModel();
                login.setEmail(email.getText().toString());
                login.setPassword(password.getText().toString());

                login.setRole(role.getText().toString());
                System.out.println("Login Details " + login.toString());
                loginEndPoint.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            boolean isUserAvailable = false;
                            Map<String,String> availableUser = null;
                            for(DataSnapshot doc: task.getResult().getChildren()){
                                Map<String,String> user=(HashMap<String, String>)doc.getValue();
                                if(user.get("email").equals(email.getText().toString())){
                                    isUserAvailable = true;
                                    availableUser = (HashMap<String, String>)doc.getValue();
                                }
                            }
                            if(isUserAvailable == false){
                                Toast.makeText(Login.this,"Invalid User!! Please Register", Toast.LENGTH_LONG).show();
                            }else{
                                Intent mainPage = new Intent(Login.this, MainPageActivity.class);
                                mainPage.putExtra("username",login.getEmail());
                                startActivity(mainPage);
                            }
                        }else{
                            Toast.makeText(Login.this,"There is no data", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }else{
            Toast.makeText(Login.this,"Please enter username and password to proceed!!", Toast.LENGTH_LONG).show();
        }
    }
    public void onCLickForgotPassword(View view) {
        Intent login = new Intent(this, ForgotPassword.class);
        login.putExtra("testData", "test");
        startActivity(login);
    }
    public void signupClicked(View view){
        Intent signupPage = new Intent(this, Signup.class);
        signupPage.putExtra("email",email.getText().toString());
        startActivity(signupPage);
    }
}
