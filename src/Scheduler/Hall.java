/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scheduler;

public class Hall {
    private String hallId;
    private String hallType;
    private int price;
    private int capacity;
    private int openingTime;
    private int closingTime;
    private boolean availability;
    private boolean maintenanceStatus;
    private String maintenanceStartDate;
    private String maintenanceEndDate;

    public Hall(String hallId, String hallType, int price, int capacity, int openingTime, int closingTime,
                boolean availability, boolean maintenanceStatus, String maintenanceStartDate, String maintenanceEndDate) {
        this.hallId = hallId;
        this.hallType = hallType;
        this.price = price;
        this.capacity = capacity;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.availability = availability;
        this.maintenanceStatus = maintenanceStatus;
        this.maintenanceStartDate = maintenanceStartDate;
        this.maintenanceEndDate = maintenanceEndDate;
    }

    // Getters and setters for all fields
    public String getHallId() { return hallId; }
    public void setHallId(String hallId) { this.hallId = hallId; }
    
    public String getHallType() { return hallType; }
    public void setHallType(String hallType) { this.hallType = hallType; }
    
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public int getOpeningTime() { return openingTime; }
    public void setOpeningTime(int openingTime) { this.openingTime = openingTime; }
    
    public int getClosingTime() { return closingTime; }
    public void setClosingTime(int closingTime) { this.closingTime = closingTime; }
    
    public boolean isMaintenanceStatus() { return maintenanceStatus; }
    public void setMaintenanceStatus(boolean maintenanceStatus) { this.maintenanceStatus = maintenanceStatus; }
    
    public String getMaintenanceStartDate() { return maintenanceStartDate; }
    public void setMaintenanceStartDate(String maintenanceStartDate) { this.maintenanceStartDate = maintenanceStartDate; }
    
    public String getMaintenanceEndDate() { return maintenanceEndDate; }
    public void setMaintenanceEndDate(String maintenanceEndDate) { this.maintenanceEndDate = maintenanceEndDate; }

    @Override
    public String toString() {
        return hallId + "," + hallType + "," + price + "," + capacity + "," + openingTime + "," + closingTime + ","
                + availability + "," + maintenanceStatus + "," + maintenanceStartDate + "," + maintenanceEndDate;
    }
}
