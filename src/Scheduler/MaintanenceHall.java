/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Scheduler;

import Users.FileOperation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class MaintanenceHall extends javax.swing.JFrame {
    String hallId;
    String[] reservation;
    
    private String email;
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public MaintanenceHall(String hallId) {
        this.hallId = hallId;
        initComponents();
        loadHallData();
        setupDateChoosers();
        this.setLocationRelativeTo(null);
    }
    private void loadHallData() {
        FileOperation fileOp = new FileOperation();
        fileOp.setFile("resources/database/hall.txt");
        fileOp.setSplitElement(","); // Set the delimiter if needed

        String hallRecord = fileOp.getHallById(hallId);
        if (hallRecord != null) {
            // Assuming the fields are in order and you have corresponding JTextFields
            String[] fields = hallRecord.split(",");
            reservation = fields;
            hallName.setText(fields[0]);
            hallTypeComboBox.setText(fields[1]);
            openingTimeField.setText(fields[4]);
            closingTimeField.setText(fields[5]);
        } else {
            JOptionPane.showMessageDialog(this, "Hall ID not found.");
        }
    }
    private void setupDateChoosers() {
        // Set up the maximum date to the current date
        Date today = new Date();
        maintenanceStartDateChooser.setMinSelectableDate(today);
        maintenanceEndDateChooser.setMinSelectableDate(today);
    }
    private boolean isDateConflict(Date startDate, Date endDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    FileOperation file = new FileOperation();
    file.setFile("resources/database/reservation.txt");
    file.checkFilePath();
    file.retrieveData();
    ArrayList<String> reservationRecords = file.getRetrievedData();
    try {
        for (String record : reservationRecords) {
            String[] details = record.split(",");
            String reservationHallId = details[1];  // Get hall ID
            String reservationDateString = details[2];  // Get the reservation date
            
            // Parse reservation date from the record
            Date reservationDate = sdf.parse(reservationDateString);
            // Check if hall IDs match
            if (reservationHallId.equals(hallId)) {
                System.out.println(reservationDate);
                // Check if reservation date is within the maintenance period
                if ((reservationDate.after(startDate) && reservationDate.before(endDate)) 
                    || reservationDate.equals(startDate) || reservationDate.equals(endDate)) {
                    System.out.println(reservationDate);
                    return true; 
                }
            }
        }
    } catch (ParseException e) {
        e.printStackTrace();
    }
     return false; 
     // No conflict
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        hallName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        proceedButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        openingTimeField = new javax.swing.JTextField();
        closingTimeField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        maintenanceStartDateChooser = new com.toedter.calendar.JDateChooser();
        maintenanceEndDateChooser = new com.toedter.calendar.JDateChooser();
        hallTypeComboBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        terminateButton1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(248, 246, 240));
        background.setPreferredSize(new java.awt.Dimension(720, 520));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel11.setText("Hall Name");

        hallName.setEditable(false);
        hallName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setText("Hall Type");

        proceedButton.setBackground(new java.awt.Color(85, 85, 85));
        proceedButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        proceedButton.setForeground(new java.awt.Color(255, 255, 255));
        proceedButton.setText("Set Maintanence");
        proceedButton.setBorder(null);
        proceedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedButtonActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel12.setText("Hall Opening Time");

        openingTimeField.setEditable(false);
        openingTimeField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        closingTimeField.setEditable(false);
        closingTimeField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel13.setText("Hall Closing Time");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel14.setText("Hall Maintanence Start Date");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel15.setText("Hall Maintanence End Date");

        hallTypeComboBox.setEditable(false);
        hallTypeComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(172, 225, 199));

        terminateButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/delete.png"))); // NOI18N
        terminateButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        terminateButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                terminateButton1MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("HALL MAINTANANCE FORM");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(terminateButton1)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addContainerGap(403, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(terminateButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("SET MAINTANANCE AND OPERATION TIME");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hallName, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(openingTimeField))
                        .addGap(25, 25, 25)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(hallTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closingTimeField)))
                    .addComponent(maintenanceStartDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(maintenanceEndDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proceedButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hallName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hallTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openingTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closingTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(maintenanceStartDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(44, 44, 44))
                    .addComponent(maintenanceEndDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void proceedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedButtonActionPerformed
        Date startDate = maintenanceStartDateChooser.getDate();
        Date endDate = maintenanceEndDateChooser.getDate();
         if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both maintenance start and end dates.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
        String maintenanceStartDate = sdf.format(startDate);
        String maintenanceEndDate = sdf.format(endDate);

        if(isDateConflict(startDate,endDate) == false){
            HallManagement manager = new HallFileOperation();
            manager.setMaintenance(hallId, false, true, maintenanceStartDate, maintenanceEndDate); 
            JOptionPane.showMessageDialog(this, "maintanence set properly");
        }else{
            JOptionPane.showMessageDialog(this, "Hall has reservation");
        }
    }//GEN-LAST:event_proceedButtonActionPerformed

    private void terminateButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_terminateButton1MouseClicked
        SchedulerDashboard dashboard = new SchedulerDashboard();
        dashboard.setEmail(getEmail());
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_terminateButton1MouseClicked

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JTextField closingTimeField;
    private javax.swing.JTextField hallName;
    private javax.swing.JTextField hallTypeComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser maintenanceEndDateChooser;
    private com.toedter.calendar.JDateChooser maintenanceStartDateChooser;
    private javax.swing.JTextField openingTimeField;
    private javax.swing.JButton proceedButton;
    private javax.swing.JLabel terminateButton1;
    // End of variables declaration//GEN-END:variables
}
