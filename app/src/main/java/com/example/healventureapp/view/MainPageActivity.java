package com.example.healventureapp.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.MainActivity;
import com.example.healventureapp.R;
import com.example.healventureapp.model.LoginModel;

import java.util.Calendar;
import java.util.Locale;

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

    public void onCLickBookApp(View v){
        Intent mainPage = new Intent(this, BookAppointment.class);
        mainPage.putExtra("testData","test");
        startActivity(mainPage);
    }

}
