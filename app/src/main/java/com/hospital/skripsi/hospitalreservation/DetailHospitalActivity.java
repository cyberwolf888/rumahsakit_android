package com.hospital.skripsi.hospitalreservation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hospital.skripsi.hospitalreservation.adapter.RoomAdapter;
import com.hospital.skripsi.hospitalreservation.models.Room;
import com.hospital.skripsi.hospitalreservation.utility.RequestServer;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class DetailHospitalActivity extends AppCompatActivity {

    private String id_hospital;
    private String hospital_name;
    private String telp;
    private String email;
    private String image;
    private String thumb_image;
    private String description;
    private String address;
    private String price;
    private String label_price;

    private View mProgressView;
    private List<Room> rooms;
    protected RecyclerView mRecyclerView;
    protected RoomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    public JsonArray data;

    private ImageView imageHospital;
    private TextView tvHospitalName,tvDescription,tvAdress,tvTelp,tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_hospital = getIntent().getStringExtra("id_hospital");
        hospital_name = getIntent().getStringExtra("hospital_name");
        telp = getIntent().getStringExtra("telp");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        thumb_image = getIntent().getStringExtra("thumb_image");
        description = getIntent().getStringExtra("description");
        address = getIntent().getStringExtra("address");
        price = getIntent().getStringExtra("price");
        label_price = getIntent().getStringExtra("label_price");
        setContentView(R.layout.activity_detail_hospital);

        getSupportActionBar().setTitle(hospital_name);

        mProgressView = findViewById(R.id.main_progress);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvRoom);
        mLayoutManager = new LinearLayoutManager(DetailHospitalActivity.this);

        imageHospital = (ImageView) findViewById(R.id.imageHospital);
        tvHospitalName = (TextView) findViewById(R.id.tvHospitalName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvAdress = (TextView) findViewById(R.id.tvAdress);
        tvTelp = (TextView) findViewById(R.id.tvTelp);
        tvEmail = (TextView) findViewById(R.id.tvEmail);

        Ion.with(this).load(image).withBitmap().asBitmap().setCallback(new FutureCallback<Bitmap>() {
            @Override
            public void onCompleted(Exception e, Bitmap result) {
                imageHospital.setImageBitmap(result);
                imageHospital.getLayoutParams().width = 1280;
            }
        });

        tvHospitalName.setText(hospital_name);
        tvDescription.setText(description);
        tvAdress.setText(address);
        tvTelp.setText(telp);
        tvEmail.setText(email);
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
            showProgress(true);

            rooms = new ArrayList<>();
            data = new JsonArray();
            String url = new RequestServer().getServer_url() + "getRoom";

            JsonObject jsonReq = new JsonObject();
            jsonReq.addProperty("hospital_id", id_hospital);

            Ion.with(DetailHospitalActivity.this)
                    .load(url)
                    .setJsonObjectBody(jsonReq)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            data = result.getAsJsonArray("data");
                            for (int i=0; i<data.size(); i++){
                                JsonObject objData = data.get(i).getAsJsonObject();
                                String photo = "";
                                if(!objData.get("image").isJsonNull()){
                                    photo = objData.get("image").getAsString();
                                }
                                rooms.add(new Room(
                                        objData.get("id").getAsString(),
                                        objData.get("name").getAsString(),
                                        objData.get("image").getAsString(),
                                        objData.get("total").getAsString(),
                                        objData.get("label_price").getAsString(),
                                        objData.get("description").getAsString()
                                ));

                            }
                            //Log.d("rooms",">"+data);
                            mAdapter = new RoomAdapter(DetailHospitalActivity.this,rooms);
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.setLayoutManager(mLayoutManager);

                            showProgress(false);
                        }
                    });

        }else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_network), Toast.LENGTH_LONG).show();
        }
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


    /**
     * Shows the progress UI and hides the main view.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            Intent i = new Intent(DetailHospitalActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
