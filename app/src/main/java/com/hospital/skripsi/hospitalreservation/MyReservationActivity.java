package com.hospital.skripsi.hospitalreservation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hospital.skripsi.hospitalreservation.adapter.ReservationAdapter;
import com.hospital.skripsi.hospitalreservation.models.Reservation;
import com.hospital.skripsi.hospitalreservation.utility.RequestServer;
import com.hospital.skripsi.hospitalreservation.utility.Session;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MyReservationActivity extends AppCompatActivity {
    private List<Reservation> reservations;
    protected RecyclerView mRecyclerView;
    protected ReservationAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    public JsonArray data;

    private View mProgressView;
    private SwipeRefreshLayout mMainView;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(MyReservationActivity.this);
        session.checkLogin();
        setContentView(R.layout.activity_my_reservation);

        mMainView = (SwipeRefreshLayout) findViewById(R.id.reservation_view);
        mProgressView = findViewById(R.id.main_progress);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvReservation);
        mLayoutManager = new LinearLayoutManager(MyReservationActivity.this);

        mMainView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyReservationActivity.this, MainActivity.class);
        ComponentName cn = i.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        startActivity(mainIntent);
    }

    @Override
    public void onResume(){
        super.onResume();
        getData();
    }

    private void getData(){
        mMainView.setRefreshing(false);
        if(isNetworkAvailable()){
            showProgress(true);

            reservations = new ArrayList<>();
            data = new JsonArray();
            String url = new RequestServer().getServer_url() + "getReservation";
            Log.d("Url",">"+url);

            JsonObject jsonReq = new JsonObject();
            jsonReq.addProperty("user_id", session.getUserId());
            Log.d("jsonReq",">"+jsonReq);

            Ion.with(MyReservationActivity.this)
                    .load(url)
                    .setJsonObjectBody(jsonReq)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Log.d("result",">"+result);
                            String status = result.get("status").toString();
                            if (status.equals("1")){
                                data = result.getAsJsonArray("data");
                                for (int i=0; i<data.size(); i++){
                                    JsonObject objData = data.get(i).getAsJsonObject();
                                    String photo = "";
                                    if(!objData.get("thumb").isJsonNull()){
                                        photo = objData.get("thumb").getAsString();
                                    }
                                    reservations.add(new Reservation(
                                            objData.get("id").getAsString(),
                                            objData.get("rumahsakit").getAsString(),
                                            objData.get("kamar").getAsString(),
                                            objData.get("checkin").getAsString(),
                                            objData.get("checkout").getAsString(),
                                            objData.get("duration").getAsString(),
                                            objData.get("label_total").getAsString(),
                                            objData.get("label_status").getAsString(),
                                            photo,
                                            objData.get("created").getAsString()
                                    ));
                                    mAdapter = new ReservationAdapter(MyReservationActivity.this, reservations);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                }
                            }
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

            mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
            mMainView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
            Intent i = new Intent(MyReservationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.action_account) {
            Intent i = new Intent(MyReservationActivity.this, MyProfileActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.action_reservation) {
            Intent i = new Intent(MyReservationActivity.this, MyReservationActivity.class);
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
