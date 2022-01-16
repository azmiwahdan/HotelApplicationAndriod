package edu.birzeit.hotelproject.models;

public class Reservations {
    private int book_id;
    private int customer;
    private int room;
    private String start_date;
    private String end_date;
    private String customer_message;

    public Reservations(int book_id, int customer, int room, String start_date, String end_date, String customer_message) {
        this.book_id = book_id;
        this.customer = customer;
        this.room = room;
        this.start_date = start_date;
        this.end_date = end_date;
        this.customer_message = customer_message;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCustomer_message() {
        return customer_message;
    }

    public void setCustomer_message(String customer_message) {
        this.customer_message = customer_message;
    }

    @Override
    public String toString() {
        return
                "book ID : " + book_id + "\n" +
                "Customer ID : " + customer + "\n" +
                "Room ID : " + room + "\n" +
                "Start date : " + start_date + "\n" +
                "End date : " + end_date;
    }
}
