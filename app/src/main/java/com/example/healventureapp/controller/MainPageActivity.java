package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.example.healventureapp.model.LoginModel;
public class MainPageActivity extends AppCompatActivity {
    String username;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);
        Intent intent = getIntent();

        if(intent.hasExtra("username")){

            username = intent.getStringExtra("username");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.profile:
                Intent mainPage = new Intent(this, Profile.class);
                mainPage.putExtra("username",username);
                startActivity(mainPage);
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.notification:
                Intent mainPage1 = new Intent(this, NotificationPatients.class);
                mainPage1.putExtra("username",username);
                startActivity(mainPage1);
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        return true;
    }


    public void onCLickBookApp(View v){
        Intent mainPage = new Intent(this, BookAppointment.class);
        mainPage.putExtra("username",username);
        startActivity(mainPage);
    }

    public void onCLickReport(View v){
        Intent mainPage = new Intent(this, MyReport.class);
        mainPage.putExtra("username",username);
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
