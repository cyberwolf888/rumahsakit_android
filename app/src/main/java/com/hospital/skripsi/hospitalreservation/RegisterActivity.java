package com.hospital.skripsi.hospitalreservation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hospital.skripsi.hospitalreservation.utility.RequestServer;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;

    private EditText etName,etEmail,etPassword,etConfirmPassword,etIDNo,etTelp,etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etIDNo = (EditText) findViewById(R.id.etIDNo);
        etTelp = (EditText) findViewById(R.id.etTelp);
        etAddress = (EditText) findViewById(R.id.etAddress);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        mLoginFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.main_progress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void register(){
        etName.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);
        etIDNo.setError(null);
        etTelp.setError(null);
        etAddress.setError(null);

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirm_password = etConfirmPassword.getText().toString();
        String idno = etIDNo.getText().toString();
        String telp = etTelp.getText().toString();
        String address = etAddress.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            etName.setError(getString(R.string.error_field_required));
            focusView = etName;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView = etPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_email));
            focusView = etPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(confirm_password)) {
            etConfirmPassword.setError(getString(R.string.error_field_required));
            focusView = etConfirmPassword;
            cancel = true;
        } else if (!isPasswordConfirmed(password,confirm_password)) {
            etConfirmPassword.setError("Password does not match.");
            focusView = etConfirmPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(idno)) {
            etIDNo.setError(getString(R.string.error_field_required));
            focusView = etIDNo;
            cancel = true;
        }

        if (TextUtils.isEmpty(telp)) {
            etTelp.setError(getString(R.string.error_field_required));
            focusView = etTelp;
            cancel = true;
        }

        if (TextUtils.isEmpty(address)) {
            etAddress.setError(getString(R.string.error_field_required));
            focusView = etAddress;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            if(isNetworkAvailable()){
                showProgress(true);
                String url = new RequestServer().getServer_url() + "register";

                JsonObject jsonReq = new JsonObject();
                jsonReq.addProperty("name", name);
                jsonReq.addProperty("email", email);
                jsonReq.addProperty("password", password);
                jsonReq.addProperty("password_confirmation", confirm_password);
                jsonReq.addProperty("no_id", idno);
                jsonReq.addProperty("telp", telp);
                jsonReq.addProperty("address", address);

                Ion.with(RegisterActivity.this)
                        .load(url)
                        .setJsonObjectBody(jsonReq)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                showProgress(false);
                                String status = result.get("status").toString();
                                if (status.equals("1")){
                                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(i);
                                    Toast.makeText(RegisterActivity.this,"Your account has been created.",Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this,result.get("error").getAsString(),Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.error_network), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPasswordConfirmed(String password, String confirm) {
        return password.equals(confirm);
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
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
