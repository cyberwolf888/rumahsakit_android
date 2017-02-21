package com.hospital.skripsi.hospitalreservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hospital.skripsi.hospitalreservation.utility.RequestServer;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailScheduleActivity extends AppCompatActivity {

    private String id_docter,docter_name;
    ListView listView;
    ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> itemList;
    JsonArray mData;
    private TextView tvBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id_docter = getIntent().getStringExtra("id_docter");
        docter_name = getIntent().getStringExtra("docter_name");

        setContentView(R.layout.activity_detail_schedule);

        getSupportActionBar().setTitle(docter_name);

        listView = (ListView) findViewById(R.id.schedule_listview);
        tvBlank = (TextView) findViewById(R.id.tvBlank);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getData();
    }

    private void getData(){
        if(isNetworkAvailable()){
            pDialog = new ProgressDialog(DetailScheduleActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            String url = new RequestServer().getServer_url()+"getSchedule";
            Log.d("url Url",">"+url);

            JsonObject jsonReq = new JsonObject();
            jsonReq.addProperty("dokter_id", id_docter);
            Log.d("Req Data",">"+jsonReq);

            Ion.with(DetailScheduleActivity.this)
                    .load(url)
                    //.setLogging("ION_VERBOSE_LOGGING", Log.VERBOSE)
                    .setJsonObjectBody(jsonReq)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Log.d("Response",">"+result);
                            String status = result.get("status").toString();
                            if (status.equals("1")){
                                mData = result.getAsJsonArray("data");
                                ArrayList<HashMap<String, String>> xitemList = new ArrayList<HashMap<String, String>>();
                                for(int i=0; i<mData.size(); i++){
                                    JsonObject objData = mData.get(i).getAsJsonObject();
                                    HashMap<String, String> dataList = new HashMap<String, String>();
                                    dataList.put("hari",objData.get("hari").getAsString());
                                    dataList.put("waktu",objData.get("waktu").getAsString());
                                    xitemList.add(dataList);
                                }
                                itemList = xitemList;
                                ListAdapter adapter = new SimpleAdapter(
                                        DetailScheduleActivity.this,
                                        itemList,
                                        R.layout.list_item_schedule,
                                        new String[]{"hari","waktu"},
                                        new int[]{R.id.tvHari,R.id.tvWaktu}
                                );
                                listView.setAdapter(adapter);
                            }else{
                                tvBlank.setVisibility(View.VISIBLE);
                            }
                            pDialog.dismiss();
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
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
