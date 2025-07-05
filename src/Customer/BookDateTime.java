/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Customer;

import Users.FileOperation;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author User
 */
public class BookDateTime extends javax.swing.JFrame {
    /**
     * Creates new form BookDateTime
     */
    private String email;
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public BookDateTime() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void setID(String hallID) {
        idLabel.setText(hallID);
    }
    
    public String getID() {
        return idLabel.getText();
    }
    
    public void setHallType(String hallType) {
        hallTypeLabel.setText(hallType);
    }
    
    public String getHallType() {
        return hallTypeLabel.getText();
    }
    
    public void setRentalPrice(double price) {
        priceLabel.setText(String.format("%.2f", price));
    }
    
    public double getRentalPrice() {
         double price = Double.parseDouble(priceLabel.getText());
         return price;
    }
    
    public void setCapacity(int capacity) {
        capacityLabel.setText(Integer.toString(capacity));
    }
    
    public int getCapacity() {
        return Integer.parseInt(capacityLabel.getText());
    }
    
    class ValidateUserInput {
        protected double time;
        protected double duration;
        private Date reservationDate;
        protected String date;
        protected boolean completedInput = false;
        protected HallDateTime hdt;

        public void userInput() {
            // Retrieve user input
            reservationDate = dateInput.getDate();
            String timeString = (String) timeInput.getSelectedItem();
            String durationString = (String) durationInput.getSelectedItem();

            if (reservationDate == null) {
                JOptionPane.showMessageDialog(rootPane, "Please specify a reservation date!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else {
                completedInput = true;
                // Format date, time, and duration
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                date = sdf.format(reservationDate);
                time = Double.parseDouble(timeString);
                duration = Double.parseDouble(durationString);

                setUserInput();
                getUserInput();

                System.out.println("Reservation date: " + hdt.getDate());
                System.out.println("Reservation starting time: " + hdt.getStartingTime());
                System.out.println("Reservation duration: " + hdt.getDuration());
            }
        }

        public void setUserInput() {
            hdt = new HallDateTime();
            hdt.setDate(normalizeDate(date));
            hdt.setStartingTime(time);
            hdt.setDuration(duration);
        }

        public void getUserInput() {
            hdt.getDate();
            hdt.getStartingTime();
            hdt.getDuration();
        }

        private String normalizeDate(String date) {
            String[] parts = date.split(" ");
            if (parts[0].length() == 1) {
                parts[0] = "0" + parts[0]; // Add leading zero for single-digit days
            }
            return String.join(" ", parts);
        }
    }

    class OperationHour extends ValidateUserInput {
        protected double endingTime;
        protected double closingHour; 
        protected boolean operating = false;

        public double getEndingTime() {
            endingTime = time + duration;
            return endingTime;
        }
        
        public boolean checkOperationTime() {
            try {
                closingHour = hdt.getClosingTime();
                double endTime = getEndingTime();
                if(endTime > closingHour) {
                    JOptionPane.showMessageDialog(rootPane, "Sorry, the reservation hour exceeds operating hours. Please select a time again.", "Out of Operation", JOptionPane.ERROR_MESSAGE);
                } else {
                    operating = true;
                }
            } catch (Exception e) {
                System.out.println("Checking Operation Time without specifying date is not allowed");
            }
            return operating;
        }
    }
    
    class HallDetails extends OperationHour {
        FileOperation file = new FileOperation();
        private ArrayList<String> fileData;
        private String idFromFile;
        private String dateFromFile;
        private double startTimeFromFile;
        private double durationFromFile;
        private double EndTimeFromFile;
        private boolean clashedTime = false;
        
        public void fileSetup() {
            file.setFile("resources/database/reservation.txt");
            file.checkFilePath();
        }
        
        public boolean checkRecords() {
            if(operating) {
                hdt.setReservationEndTime(getEndingTime());
                System.out.println("ending " + hdt.getReservationEndTime());
                
                file.retrieveData();
                fileData = file.getRetrievedData();
                for(String row : fileData) {
                    String[] line = row.split(",");
                    
                    idFromFile = line[1];
                    dateFromFile = line[2];
                    startTimeFromFile = Double.parseDouble(line[3]);
                    durationFromFile = Double.parseDouble(line[4]);
                    EndTimeFromFile = Double.parseDouble(line[5]);
                    
                    if(idFromFile.equals(idLabel.getText())) {
                        if (dateFromFile.equals(hdt.getDate())) {
                            boolean overlaps = time < EndTimeFromFile && endingTime > startTimeFromFile;
                            if (overlaps) {
                                clashedTime = true;
                                break;
                            }
                        }
                    }
                }
                
                if(clashedTime) {
                    JOptionPane.showMessageDialog(rootPane, "Clashed Hall Reservation! Please try again..", "Clashed Reservation", JOptionPane.ERROR_MESSAGE);
                }
            }
            return clashedTime;
        }
    }
    
    class HallOperation extends HallDetails {
        boolean validTime = false;
        public boolean checkHall() {
            FileOperation hallFile = new FileOperation();
            hallFile.setFile("resources/database/hall.txt");
            hallFile.checkFilePath();
            hallFile.retrieveData();
            ArrayList<String> hallDetails = hallFile.getRetrievedData();

            for (String row : hallDetails) {
                String[] records = row.split(",");

                String ID = records[0];
                Integer hStartingTime = records[4].equals("null") ? null : Integer.parseInt(records[4]);
                Integer hEndingTime = records[5].equals("null") ? null : Integer.parseInt(records[5]);
                boolean operating = Boolean.parseBoolean(records[6]);
                boolean underMaintenance = Boolean.parseBoolean(records[7]);

                // Check if the hall is selected and operating
                if (ID.equals(idLabel.getText()) && operating && !underMaintenance) {
                    double selectedStartTime = time;
                    double selectedEndTime = getEndingTime();

                    // Validate the selected times against the hall's available times
                    if (hStartingTime != null && selectedStartTime < hStartingTime) {
                        JOptionPane.showMessageDialog(rootPane, "Selected start time is earlier than the allowed start time for this hall.", "Invalid Start Time", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }

                    if (hEndingTime != null && selectedEndTime > hEndingTime) {
                        JOptionPane.showMessageDialog(rootPane, "Selected end time exceeds the allowed end time for this hall.", "Invalid End Time", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    
                    validTime = true;
                }
            }
            return validTime;
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        backLink = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        hallTypeLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        capacityLabel = new javax.swing.JLabel();
        modifyLink = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dateInput = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        timeInput = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        durationInput = new javax.swing.JComboBox<>();
        confirmButton = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(248, 246, 240));
        background.setPreferredSize(new java.awt.Dimension(850, 550));

        header.setPreferredSize(new java.awt.Dimension(850, 55));
        header.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(85, 85, 85));
        jPanel1.setPreferredSize(new java.awt.Dimension(150, 59));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Select Hall Type");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        header.add(jPanel1);
        jPanel1.setBounds(0, 0, 150, 59);

        jPanel2.setBackground(new java.awt.Color(248, 246, 240));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 55));

        jLabel1.setBackground(new java.awt.Color(248, 246, 240));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("Select Date and Time");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1))
        );

        header.add(jPanel2);
        jPanel2.setBounds(150, 0, 170, 55);

        jPanel4.setBackground(new java.awt.Color(85, 85, 85));
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 59));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Complete Booking Form");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4))
        );

        header.add(jPanel4);
        jPanel4.setBounds(320, 0, 190, 59);

        jPanel5.setBackground(new java.awt.Color(85, 85, 85));
        jPanel5.setPreferredSize(new java.awt.Dimension(100, 59));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Payment Session");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5))
        );

        header.add(jPanel5);
        jPanel5.setBounds(510, 0, 150, 59);

        jPanel3.setBackground(new java.awt.Color(85, 85, 85));
        jPanel3.setPreferredSize(new java.awt.Dimension(150, 59));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("View Payment Summary");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        header.add(jPanel3);
        jPanel3.setBounds(660, 0, 190, 59);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel6.setText("Choose Your Preferred Date and Time for Booking");

        backLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/back.png"))); // NOI18N
        backLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backLinkMouseClicked(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hallTypeLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        hallTypeLabel.setText("Auditorium");
        jPanel6.add(hallTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Current Hall Selection : ");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/check-mark.png"))); // NOI18N
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/price-tag.png"))); // NOI18N
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Rental Rate (per hour) :");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        priceLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        priceLabel.setText("300.00");
        jPanel6.add(priceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/people.png"))); // NOI18N
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setText("Hall Capacity : ");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        capacityLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        capacityLabel.setText("1000");
        jPanel6.add(capacityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, -1));

        modifyLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        modifyLink.setForeground(new java.awt.Color(255, 153, 0));
        modifyLink.setText("Modify");
        modifyLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        modifyLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modifyLinkMouseClicked(evt);
            }
        });
        jPanel6.add(modifyLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, -1, -1));

        idLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        idLabel.setText("H1");
        jPanel6.add(idLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("RM");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Reservation Date");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Starting Time");

        timeInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timeInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8.00", "9.00", "10.00", "11.00", "12.00", "13.00", "14.00", "15.00", "16.00", "17.00", "18.00", "19.00", "20.00", "21.00", "22.00" }));
        timeInput.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Duration (hours)");

        durationInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        durationInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        durationInput.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        confirmButton.setBackground(new java.awt.Color(85, 85, 85));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("CONFIRM");
        confirmButton.setBorder(new javax.swing.border.MatteBorder(null));
        confirmButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        jLabel22.setText("Note :");

        jLabel23.setText("1. Please double check your selected hall.");

        jLabel24.setText("2. Rental Price is charged by hour.");

        jLabel25.setText("3. Hall will be closed at 12pm.");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(backLink)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(142, 142, 142)
                                .addComponent(jLabel18))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(dateInput, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(timeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(durationInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(confirmButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(29, 29, 29)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(backLink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel22)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel23)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25)))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(timeInput, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(durationInput)))
                .addGap(18, 18, 18)
                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 114, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void modifyLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modifyLinkMouseClicked
        BookHall hallPage = new BookHall();
        hallPage.setEmail(getEmail());
        hallPage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_modifyLinkMouseClicked

    private void backLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLinkMouseClicked
        BookHall hallPage = new BookHall();
        hallPage.setEmail(getEmail());
        hallPage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backLinkMouseClicked

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed

        HallOperation hall = new HallOperation();
        hall.userInput();
        hall.fileSetup();
        hall.checkOperationTime();
        
        boolean validTime = hall.checkHall();
        boolean clashedTime = hall.checkRecords();
        boolean inOperation = hall.checkOperationTime();
//        System.out.println("ot? " + inOperation);
        if(!clashedTime && inOperation && validTime) {
            JOptionPane.showMessageDialog(rootPane, "The hall is available! Proceeding to next phase...");
            BookingForm bf = new BookingForm();
            bf.setEmail(getEmail());
            bf.setHallID(getID());
            bf.setReservationDate(hall.date);
            bf.setStartingTime(hall.time);
            bf.setDuration(hall.duration);
            bf.setPrice(getRentalPrice());
            bf.setCapacity(getCapacity());
            bf.setHallType(getHallType());
            bf.setVisible(true);
            this.dispose();
        }

    }//GEN-LAST:event_confirmButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookDateTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookDateTime().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backLink;
    private javax.swing.JPanel background;
    private javax.swing.JLabel capacityLabel;
    private javax.swing.JButton confirmButton;
    private com.toedter.calendar.JDateChooser dateInput;
    private javax.swing.JComboBox<String> durationInput;
    private javax.swing.JLabel hallTypeLabel;
    private javax.swing.JPanel header;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel modifyLink;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JComboBox<String> timeInput;
    // End of variables declaration//GEN-END:variables
}
