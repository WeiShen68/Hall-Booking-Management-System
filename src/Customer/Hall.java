/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author User
 */
public class Hall {
    private String hallID;
    private String hallType;
    private double pricePerHour;
    private int capacity;

    public String getHallID() {
        return hallID;
    }

    public void setHallID(String hallID) {
        this.hallID = hallID;
    }

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

class HallDateTime extends Hall {
    private String date;
    private double startingTime;
    private double duration;
    private double reservationEndTime;
    private double closingTime = 24;
    
    public double getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(double reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(double startingTime) {
        this.startingTime = startingTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(double closingTime) {
        this.closingTime = closingTime;
    }
}

//class Booking extends HallDateTime {
//    private String eventTitle;
//    private String rentingPurpose;
//    private int participants;
//    private String email;
//    private String emergencyContact;
//
//    public Booking(String eventTitle, String rentingPurpose, int participants, String email, String emergencyContact) {
//        this.eventTitle = eventTitle;
//        this.rentingPurpose = rentingPurpose;
//        this.participants = participants;
//        this.email = email;
//        this.emergencyContact = emergencyContact;
//    }
//    
//    public String toString() {
//        return eventTitle + "," + rentingPurpose + "," + participants + "," + email + "," +  emergencyContact;
//    }
//}
