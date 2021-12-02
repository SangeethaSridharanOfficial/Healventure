package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.example.healventureapp.model.LoginModel;

public class MainPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);
        Intent intent = getIntent();

        if(intent.hasExtra("loginObj")){
            LoginModel login = (LoginModel) intent.getSerializableExtra("loginObj");
            Toast.makeText(this, "Welcome "+login.getEmail(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void onCLickBookApp(View v){
        Intent mainPage = new Intent(this, BookAppointment.class);
        mainPage.putExtra("testData","test");
        startActivity(mainPage);
    }

    public void onCLickReport(View v){
        Intent mainPage = new Intent(this, Report.class);
        mainPage.putExtra("testData","test");
        startActivity(mainPage);
    }

    public void onCLickFeedback(View v){
        Intent mainPage = new Intent(this, Feedback.class);
        mainPage.putExtra("testData","test");
        startActivity(mainPage);
    }

    public void onCLickServices(View v){
        Intent mainPage = new Intent(this, Services.class);
        mainPage.putExtra("testData","test");
        startActivity(mainPage);
    }


    public void onCLickAboutUs(View v){
        Intent mainPage = new Intent(this, AboutUs.class);
        mainPage.putExtra("testData","test");
        startActivity(mainPage);
    }

}
