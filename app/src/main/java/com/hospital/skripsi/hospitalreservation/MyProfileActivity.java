package com.hospital.skripsi.hospitalreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hospital.skripsi.hospitalreservation.utility.Session;

public class MyProfileActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(MyProfileActivity.this);
        if (!session.isLoggedIn()){
            Intent i = new Intent(MyProfileActivity.this, LoginActivity.class);
            startActivity(i);
        }
        setContentView(R.layout.activity_my_profile);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent i = new Intent(MyProfileActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        if (id == R.id.action_reservation) {
            Intent i = new Intent(MyProfileActivity.this, MyReservationActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.action_logout) {
            session.logoutUser();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
