package com.example.healventureapp.controller;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.example.healventureapp.controller.MyReport;
import com.example.healventureapp.model.BookAppointmentModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BookAppointment extends AppCompatActivity {
    //Toolbar toolbar;
    TextView txtTim;
    EditText date_text;
    DatabaseReference databaseReference;
    DatabaseReference bookappointmentEndPoint;
    Button bookApp;
    Button timeSelected;
    String username;
    BookAppointmentModel bAmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_appointment_activity);
        date_text = findViewById(R.id.text_date);
        bookApp = findViewById(R.id.btn_bookapp);
        txtTim = findViewById(R.id.select_date);
        this.setTitle("BookAppointment");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        bookappointmentEndPoint = databaseReference.child("bookAppointment");
        Intent user = getIntent();
        System.out.println("user"+ user.getStringExtra("username"));
        if (user.hasExtra("username")) {
            username = user.getStringExtra("username");
        } else {
            Intent mainPage = new Intent(this, Login.class);
            startActivity(mainPage);
        }
    }

    public void onCLickBookApps(View v) {
        String date = date_text.getText().toString();
        String time = txtTim.getText().toString();
        BookAppointmentModel btp = new BookAppointmentModel(date, time, username);
        System.out.println(btp + "-------id for book appointment");
        bookappointmentEndPoint.child(bookappointmentEndPoint.push().getKey()).setValue(btp).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookAppointment.this, "OOPS!! Something wrong. Try again!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent mainPage = new Intent(BookAppointment.this, Payment.class);
                mainPage.putExtra("testData", "test");
                startActivity(mainPage);
            }
        });

    }

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    public void onCLickEditText(View v) {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onClickTimeSelected(View v) {
        timeSelected = (Button) v;
        txtTim.setText(timeSelected.getText());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_text.setText(sdf.format(myCalendar.getTime()));
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
                Intent mainPage = new Intent(this, Profile.class);
                mainPage.putExtra("testData", "test");
                startActivity(mainPage);
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.notification:

                Intent mainPage1 = new Intent(this, NotificationPatients.class);
                mainPage1.putExtra("testData", "test");
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