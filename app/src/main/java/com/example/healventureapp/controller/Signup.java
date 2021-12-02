package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;

public class Signup extends AppCompatActivity {
    EditText fname, lname, uname, pwd, cfrmPwd;
    RadioGroup gender;
    RadioButton selectedGender;
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
//            loginEndPoint.child(email.getText().toString()).setValue(login).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Login.this,"OOPS!! Something wrong. Try again!", Toast.LENGTH_LONG).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//
//                }
//            });
            Intent mainPage = new Intent(this, MainPageActivity.class);

            startActivity(mainPage);
        }else{
            Toast.makeText(Signup.this,"Please check the details", Toast.LENGTH_LONG).show();
        }

    }
}
