package edu.birzeit.hotelproject.models;

public class Room {

    private int room_id;
    private String room_number;
    private String room_type;
    private String room_price;
    private String imageUrl;
    private String room_description;
    private boolean room_reserve;// 0 for unreserved | 1 for reserved : is reserved is boolean not integer.

    public Room() {}

    public Room(int room_id, String room_number, String room_type, String room_price,
                String imageUrl, String room_description, boolean room_reserve) {
        this.room_id = room_id;
        this.room_number = room_number;
        this.room_type = room_type;
        this.room_price = room_price;
        this.imageUrl = imageUrl;
        this.room_description = room_description;
        this.room_reserve = room_reserve;

    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_price() {
        return room_price;
    }

    public void setRoom_price(String room_price) {
        this.room_price = room_price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRoom_description() {
        return room_description;
    }

    public void setRoom_description(String room_description) {
        this.room_description = room_description;
    }

    public boolean isRoom_reserve() {
        return room_reserve;
    }

    public void setRoom_reserve(boolean room_reserve) {
        this.room_reserve = room_reserve;
    }

    @Override
    public String toString() {
        return
                "Room ID : " + room_id + "\n" +
                "Room Number : " + room_number + "\n" +
                "Room Type : " + room_type + "\n" +
                "Room Price : " + room_price;
    }
}
