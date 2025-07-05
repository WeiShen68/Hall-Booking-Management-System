/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

/**
 *
 * @author User
 */
public class Booking {
    private String eventTitle;
    private String rentingPurpose;
    private int participants;
    private String email;
    private String emergencyContact;

    public Booking(String eventTitle, String rentingPurpose, int participants, String email, String emergencyContact) {
        this.eventTitle = eventTitle;
        this.rentingPurpose = rentingPurpose;
        this.participants = participants;
        this.email = email;
        this.emergencyContact = emergencyContact;
    }
    
    public String toString() {
        return eventTitle + "," + rentingPurpose + "," + participants + "," + email + "," +  emergencyContact;
    }
}

class BookingManagement extends Booking {
    private String hallID;
    private String reservationDate;
    private double startingTime;
    private double duration;
    private double endingTime;
    
    public BookingManagement(String hallID, String reservationDate, double startingTime, double duration, double endingTime,
                             String eventTitle, String rentingPurpose, int participants, String email, String emergencyContact) {
        super(eventTitle, rentingPurpose, participants, email, emergencyContact);
        this.hallID = hallID;
        this.reservationDate = reservationDate;
        this.startingTime = startingTime;
        this.duration = duration;
        this.endingTime = calculateEndingTime();
    }
    
     private double calculateEndingTime() {
        return this.startingTime + this.duration;
    }
     
    @Override
    public String toString() {
        return hallID + "," + reservationDate + "," + startingTime + "," + duration + "," + endingTime + "," + super.toString();
    }
    
}