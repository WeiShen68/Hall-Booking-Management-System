/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scheduler;

import java.io.*;
import java.util.*;

public abstract class HallManagement {

    // Abstract method to add new hall information
    public abstract void addHall(Hall hall);

    // Abstract method to view and filter hall information
    public abstract void viewHalls();
   

    // Abstract method to edit existing hall information
    public abstract void editHall(String hallId, Hall updatedHall);

    // Abstract method to delete hall information
    public abstract void deleteHall(String hallId);

    // Abstract method to set hall maintenance
    public abstract void setMaintenance(String hallId,boolean availability, boolean status, String startDate, String endDate);
}