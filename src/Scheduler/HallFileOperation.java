/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scheduler;
import Users.FileOperation;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Asus
 */
public class HallFileOperation extends HallManagement {
    private FileOperation fileOp = new FileOperation(); // Use your file operations

    @Override
    public void addHall(Hall hall) {
        fileOp.setFile("resources/database/hall.txt");
        fileOp.appendData(hall.toString());
    }

    @Override
    public void viewHalls() {
        fileOp.setFile("resources/database/hall.txt");
        fileOp.retrieveData();
        ArrayList<String> records = fileOp.getRetrievedData();
        for (String record : records) {
            System.out.println(record);
        }
    }

    @Override
    public void editHall(String hallId, Hall updatedHall) {
        fileOp.setFile("resources/database/hall.txt");
        fileOp.retrieveData();
        ArrayList<String> halls = fileOp.getRetrievedData();
        ArrayList<String> updatedData = new ArrayList<>();
        for (String record : halls) {
            String[] data = record.split(",");
            if (data[0].equals(hallId)) {
                updatedData.add(updatedHall.toString());
            } else {
                updatedData.add(record);
            }
        }
        fileOp.updateData(updatedData);
    }

    @Override
    public void deleteHall(String hallId) {
        fileOp.setFile("resources/database/hall.txt");
        fileOp.retrieveData();
        ArrayList<String> halls = fileOp.getRetrievedData();
        ArrayList<String> updatedData = new ArrayList<>();
        for (String record : halls) {
            String[] data = record.split(",");
            if (!data[0].equals(hallId)) {
                updatedData.add(record);
            }
        }
        fileOp.deleteData(updatedData);
    }

    @Override
    public void setMaintenance(String hallId, boolean availabilty,boolean status, String startDate, String endDate) {
        fileOp.setFile("resources/database/hall.txt");
        fileOp.retrieveData();
        ArrayList<String> halls = fileOp.getRetrievedData();
        ArrayList<String> updatedData = new ArrayList<>();
        for (String record : halls) {
            String[] data = record.split(",");
            if (data[0].equals(hallId)) {
                Hall hall = new Hall(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]),availabilty, status, startDate, endDate);
                updatedData.add(hall.toString());
            } else {
                updatedData.add(record);
            }
        }
        fileOp.updateData(updatedData);
    }
}