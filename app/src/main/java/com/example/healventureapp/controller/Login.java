package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.example.healventureapp.model.LoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private DatabaseReference healventureDatabase;
    private DatabaseReference loginEndPoint;
    EditText email, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        healventureDatabase = FirebaseDatabase.getInstance().getReference();
        loginEndPoint = healventureDatabase.child("login");
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void loginClicked(View view) {
        if(!email.getText().toString().equals("") && !password.getText().toString().equals("") && email != null && password != null){
            Intent mainPage = new Intent(this, MainPageActivity.class);
            LoginModel login = new LoginModel();
            login.setEmail(email.getText().toString());
            login.setPassword(password.getText().toString());
            mainPage.putExtra("loginObj",login);
            loginEndPoint.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        boolean isUserAvailable = false;
                        for(DataSnapshot doc: task.getResult().getChildren()){
                            Map<String,String> user=(HashMap<String, String>)doc.getValue();
                            if(user.get("email").equals(email.getText().toString())){
                                isUserAvailable = true;
                            }
                        }
                        if(isUserAvailable == false){
                            Toast.makeText(Login.this,"Invalid User!! Please Register", Toast.LENGTH_LONG).show();
                        }else{

                            startActivity(mainPage);
                        }
                    }else{
                        Toast.makeText(Login.this,"There is no data", Toast.LENGTH_LONG).show();
                    }
                }
            });

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
