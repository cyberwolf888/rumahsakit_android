package com.hospital.skripsi.hospitalreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InvoiceActivity extends AppCompatActivity {

    private Button btnMyReservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        btnMyReservation = (Button) findViewById(R.id.btnMyReservation);

        btnMyReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InvoiceActivity.this,MyReservationActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
