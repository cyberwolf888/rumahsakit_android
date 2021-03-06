package com.hospital.skripsi.hospitalreservation.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hospital.skripsi.hospitalreservation.LoginActivity;
import com.hospital.skripsi.hospitalreservation.MainActivity;

public class Session {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Auth";
    private static final String IS_LOGIN = "isLoggedIn";
    private static final String ID_USER = "id_user";
    private static final String ID_NAME = "fullname";
    private static final String ID_ALAMAT = "alamat";

    public Session(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String id_user, String fullname){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ID_USER, id_user);
        editor.putString(ID_NAME, fullname);
        editor.commit();
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    /**
     * Check login method wil check user login status
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
            //((Activity)_context).finish();
        }

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getFullname(){
        return pref.getString(ID_NAME,"");
    }

    public String getUserId(){
        return pref.getString(ID_USER,"");
    }

    public String getUserAlamat() {
        return pref.getString(ID_ALAMAT,"");
    }

    public void editName(String name){
        editor.putString(ID_NAME, name).apply();
    }
}
