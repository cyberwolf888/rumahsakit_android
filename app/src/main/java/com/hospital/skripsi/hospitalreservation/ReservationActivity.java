package com.hospital.skripsi.hospitalreservation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hospital.skripsi.hospitalreservation.utility.RequestServer;
import com.hospital.skripsi.hospitalreservation.utility.Session;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {
    private DatePickerDialog dp_checkin;
    private EditText etCheckin,etDuration;
    private TextView tvTotal,tvDescription;
    private ImageView imageRoom;
    private String id_room;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(ReservationActivity.this);
        if (!session.isLoggedIn()){
            Intent i = new Intent(ReservationActivity.this, LoginActivity.class);
            startActivity(i);
        }

        id_room = getIntent().getStringExtra("id_room");

        setContentView(R.layout.activity_reservation);
        etCheckin = (EditText)findViewById(R.id.etCheckin);
        etDuration = (EditText)findViewById(R.id.etDuration);

        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvDescription = (TextView)findViewById(R.id.tvDescription);

        imageRoom = (ImageView)findViewById(R.id.imageRoom);

        etCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateField();
                dp_checkin.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        getData();
    }

    private void getData(){
        if(isNetworkAvailable()){
            String url = new RequestServer().getServer_url() + "detailRoom";
            JsonObject jsonReq = new JsonObject();
            jsonReq.addProperty("id_room", id_room);

            Ion.with(ReservationActivity.this)
                    .load(url)
                    .setJsonObjectBody(jsonReq)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            JsonObject data = result.getAsJsonObject("data");
                            tvDescription.setText(data.get("description").getAsString());
                            tvTotal.setText(data.get("label_price").getAsString());

                            Ion.with(ReservationActivity.this).load(data.get("image").getAsString()).withBitmap().asBitmap().setCallback(new FutureCallback<Bitmap>() {
                                @Override
                                public void onCompleted(Exception e, Bitmap result) {
                                    imageRoom.setImageBitmap(result);
                                    imageRoom.getLayoutParams().width = 1280;
                                }
                            });
                        }
                    });
        }else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_network), Toast.LENGTH_LONG).show();
        }
    }


    private void setDateField() {
        final Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)+1);
        dp_checkin = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String formatted = formater.format(newDate.getTime());
                if(newDate.after(newCalendar)){
                    etCheckin.setText(formatted);
                }else{
                    Toast.makeText(getApplicationContext(), "Min check-in date is 1 day forward.", Toast.LENGTH_LONG).show();
                }

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent i = new Intent(ReservationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.action_account) {
            Intent i = new Intent(ReservationActivity.this, MyProfileActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.action_reservation) {
            Intent i = new Intent(ReservationActivity.this, MyReservationActivity.class);
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
