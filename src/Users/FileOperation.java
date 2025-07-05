/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class File {
    private String file;
    
    public String getFile() {
        return file;
    }
    
    public void setFile(String file) {
        this.file = file;
    }
}

class VerifyFile extends File {
    public void checkFilePath() {
        try (BufferedReader br = new BufferedReader(new FileReader(getFile()))) {
            if (br.readLine() != null) {
                System.out.println("File Exists");
            } else {
                System.out.println("File does not exist");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VerifyFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VerifyFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class GetFileData extends VerifyFile {
    private ArrayList<String> records = new ArrayList<>();
    
    public void retrieveData() {
        try (BufferedReader br = new BufferedReader(new FileReader(getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Reader error in retrieveData()");
        }
    }
    
    public ArrayList<String> getRetrievedData() {
        return records;
    }
}

public class FileOperation extends GetFileData {
    private int idNum;
    private String splitElement;
    
    public void deleteData(ArrayList<String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFile()))) {
            for (String row : data) {
                bw.write(row);
                bw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateData(ArrayList<String> updatedData) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFile()))) {
            for (String record : updatedData) {
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error writing to file in updateData()");
        }
    }
    
    public void appendData(String newRecord) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(), true))) {
            writer.write(newRecord);
            writer.newLine();
        } catch (IOException ex) {
            Logger.getLogger(FileOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void IdOperation() {
        ArrayList<String> row = getRetrievedData();
        int maxId = 0;
        for (String s : row) {
            String[] record = s.split(",");
            String idsFromFile = record[0];
            String[] spliter = idsFromFile.split(getSplitElement());

            // Handle cases where the split might not work as expected
            if (spliter.length > 1) {
                try {
                    int currentId = Integer.parseInt(spliter[1]);
                    if (currentId > maxId) {
                        maxId = currentId;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing ID: " + spliter[1]);
                }
            }
        }
        idNum = maxId + 1; // Increment after finding the max ID
    }

    public int getIdNum() {
        return idNum;
    }

    public String getSplitElement() {
        return splitElement;
    }

    public void setSplitElement(String splitElement) {
        this.splitElement = splitElement;
    }
    
    public String getHallById(String hallId) {
        retrieveData();
        
        for (String record : getRetrievedData()) {
            String[] fields = record.split(",");
            if (fields.length > 0 && fields[0].equals(hallId)) {
                return record;
            }
        }
        
        return null;
    }
}