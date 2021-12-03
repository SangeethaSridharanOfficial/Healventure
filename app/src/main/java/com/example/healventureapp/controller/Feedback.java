package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healventureapp.R;

public class Feedback extends AppCompatActivity {

    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        setTitle("Feedback");
        // initiate rating bar and a button
        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar1);
        Button submitButton = (Button) findViewById(R.id.btnGet1);
        final RatingBar simpleRatingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        Button submitButton2 = (Button) findViewById(R.id.btnGet2);
        //perform click event on button
        Intent user = getIntent();
        if (user.hasExtra("username")) {
             username = user.getStringExtra("username");
        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values and then displayed in a toast
                String totalStars = "Total Stars:: " + simpleRatingBar.getNumStars();
                String rating = "Rating :: " + simpleRatingBar.getRating();
                Toast.makeText(getApplicationContext(), totalStars + "\n" + rating, Toast.LENGTH_LONG).show();

            }
        });

        submitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values and then displayed in a toast
                String totalStars = "Total Stars:: " + simpleRatingBar2.getNumStars();
                String rating = "Rating :: " + simpleRatingBar2.getRating();
                Toast.makeText(getApplicationContext(), totalStars + "\n" + rating, Toast.LENGTH_LONG).show();

            }
        });


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