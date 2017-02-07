package com.hospital.skripsi.hospitalreservation.models;

/**
 * Created by Karen on 2/7/2017.
 */

public class Room {
    public String id_room;
    public String room_name;
    public String image;
    public String price;
    public String label_price;
    public String description;

    public Room(String id_room, String room_name, String image, String price, String label_price, String description){
        this.id_room = id_room;
        this.room_name = room_name;
        this.image = image;
        this.price = price;
        this.label_price = label_price;
        this.description = description;
    }
}
