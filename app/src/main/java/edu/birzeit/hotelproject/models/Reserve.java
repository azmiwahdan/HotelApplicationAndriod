package edu.birzeit.hotelproject.models;

public class Reserve {

    private int roomNumber ;
    private String startDate , endDate ;

    public Reserve(int roomNumber, String startDate, String endDate) {
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "roomNumber=" + roomNumber +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
