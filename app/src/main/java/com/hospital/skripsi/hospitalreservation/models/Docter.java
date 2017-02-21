package com.hospital.skripsi.hospitalreservation.models;

/**
 * Created by Karen on 2/21/2017.
 */

public class Docter {
    public String id_docter;
    public String docter_name;
    public String speciality;

    public Docter(String id_docter,String docter_name,String speciality) {
        this.id_docter = id_docter;
        this.docter_name = docter_name;
        this.speciality = speciality;
    }
}
