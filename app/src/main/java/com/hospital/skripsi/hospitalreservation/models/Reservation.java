package com.hospital.skripsi.hospitalreservation.models;

/**
 * Created by Karen on 2/17/2017.
 */

public class Reservation {
    public String id_reservation;
    public String rumahsakit;
    public String room;
    public String checkin;
    public String checkout;
    public String duration;
    public String total;
    public String status;
    public String reservation_image;
    public String created;

    public Reservation(String id_reservation, String rumahsakit, String room, String checkin, String checkout, String duration, String total, String status, String reservation_image, String created){
        this.id_reservation = id_reservation;
        this.rumahsakit = rumahsakit;
        this.room = room;
        this.checkin = checkin;
        this.checkout = checkout;
        this.duration = duration;
        this.total = total;
        this.status = status;
        this.reservation_image = reservation_image;
        this.created = created;

    }
}
