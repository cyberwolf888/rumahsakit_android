package com.hospital.skripsi.hospitalreservation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hospital.skripsi.hospitalreservation.utility.Session;

public class MyReservationActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(MyReservationActivity.this);
        session.checkLogin();
        setContentView(R.layout.activity_my_reservation);
    }
}
