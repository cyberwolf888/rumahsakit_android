package com.hospital.skripsi.hospitalreservation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailScheduleActivity extends AppCompatActivity {

    private String id_docter,docter_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id_docter = getIntent().getStringExtra("id_docter");
        docter_name = getIntent().getStringExtra("docter_name");

        setContentView(R.layout.activity_detail_schedule);

        getSupportActionBar().setTitle(docter_name);


    }
}
