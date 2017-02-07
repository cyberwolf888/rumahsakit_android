package com.hospital.skripsi.hospitalreservation.models;

/**
 * Created by Karen on 2/6/2017.
 */

public class Hospital {
    public String id_hospital;
    public String hospital_name;
    public String telp;
    public String email;
    public String image;
    public String thumb_image;
    public String description;
    public String address;
    public String price;
    public String label_price;

    public Hospital(String id_hospital, String hospital_name, String telp, String email, String image, String thumb_image, String description, String address, String price, String label_price){
        this.id_hospital = id_hospital;
        this.hospital_name = hospital_name;
        this.telp = telp;
        this.email = email;
        this.image = image;
        this.thumb_image = thumb_image;
        this.description = description;
        this.address = address;
        this.price = price;
        this.label_price = label_price;
    }
}
