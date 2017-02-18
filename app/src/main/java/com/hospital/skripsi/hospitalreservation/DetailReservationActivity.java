package com.hospital.skripsi.hospitalreservation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailReservationActivity extends AppCompatActivity {

    private String id_reservation;
    private String rumahsakit;
    private String room;
    private String checkin;
    private String checkout;
    private String duration;
    private String total;
    private String status;
    private String reservation_image;
    private String created;

    private TextView tvCreated,tvHospital,tvRoom,tvCheckin,tvCheckout,tvDuration,tvTotal,tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_reservation = getIntent().getStringExtra("id_reservation");
        rumahsakit = getIntent().getStringExtra("rumahsakit");
        room = getIntent().getStringExtra("room");
        checkin = getIntent().getStringExtra("checkin");
        checkout = getIntent().getStringExtra("checkout");
        duration = getIntent().getStringExtra("duration");
        total = getIntent().getStringExtra("total");
        status = getIntent().getStringExtra("status");
        reservation_image = getIntent().getStringExtra("reservation_image");
        created = getIntent().getStringExtra("created");

        setContentView(R.layout.activity_detail_reservation);

        tvCreated = (TextView) findViewById(R.id.tvCreated);
        tvHospital = (TextView) findViewById(R.id.tvHospital);
        tvRoom = (TextView) findViewById(R.id.tvRoom);
        tvCheckin = (TextView) findViewById(R.id.tvCheckin);
        tvCheckout = (TextView) findViewById(R.id.tvCheckout);
        tvDuration = (TextView) findViewById(R.id.tvDuration);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        tvCreated.setText(created);
        tvHospital.setText(rumahsakit);
        tvRoom.setText(room);
        tvCheckin.setText(checkin);
        tvCheckout.setText(checkout);
        tvDuration.setText(duration+" day");
        tvTotal.setText(total);
        tvStatus.setText(status);
    }
}
