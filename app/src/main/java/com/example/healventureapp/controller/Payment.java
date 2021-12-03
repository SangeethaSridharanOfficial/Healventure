package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.google.firebase.database.annotations.Nullable;

public class Payment extends AppCompatActivity {

    EditText amount, nameOnCard,cardNumber, expiry, cvv;
    String username="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        this.setTitle("Payment");
        amount = findViewById(R.id.amount_text);
        nameOnCard = findViewById(R.id.card_text);
        cardNumber = findViewById(R.id.card_no_txt);
        expiry = findViewById(R.id.exp);
        cvv = findViewById(R.id.cvv);
        Intent user = getIntent();
        if (user.hasExtra("username")) {
         username = user.getStringExtra("username");
        }

    }
    public void payNowClicked(View view) {
        if(!amount.getText().toString().equals("") && !nameOnCard.getText().toString().equals("")
                && !cardNumber.getText().toString().equals("") && !expiry.getText().toString().equals("")
                && !cvv.getText().toString().equals("") && amount != null && nameOnCard != null && cardNumber != null
        && expiry != null && cvv != null){
            if (cardNumber.getText().toString().length() != 16 ){
                Toast.makeText(Payment.this,"Invalid input!!Please enter the correct card number.", Toast.LENGTH_LONG).show();
            }
            else if(cvv.getText().toString().length() != 3){
                Toast.makeText(Payment.this,"Invalid input!!Please enter the correct cvv.", Toast.LENGTH_LONG).show();
            }else{
                Intent pay = new Intent(this, MainPageActivity.class);
                startActivity(pay);
            }
        }else {

            Toast.makeText(Payment.this, "Please enter all details to proceed!!", Toast.LENGTH_LONG).show();
        }
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
}
