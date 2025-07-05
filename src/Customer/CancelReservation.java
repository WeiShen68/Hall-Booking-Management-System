/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Customer;

import Users.FileOperation;
import Users.LoginPage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class CancelReservation extends javax.swing.JFrame {

    /**
     * Creates new form CancelReservation
     */
    private String email;
    private FileOperation file;
    private Date todayDate;
    private Date reservationDate;
    private SimpleDateFormat formatter;
    
    
    
    public void setEmail(String email) {
        this.email = email;
        userLabel.setText(email);
        generateTable();
    }
    
    public String getEmail() {
        return email;
    }
    
    public Date getTodayDate() {
        return todayDate;
    }
    
    public CancelReservation() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    
    public void generateTable() {
        String[] columns = {"Reservation ID","Hall ID", "Reservation Date", "Starting Time", "Ending Time", "Event", "Particiapnt Number"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // GET TODAY DATE
        // ONLY DISPLAY UPCOMING EVENTS (PAST EVENTS CANNOT BE CANCELLED)
        Date date = new Date();
        formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(date);
        
        
        // RETRIVE RESERVATION TO COMPARE DATE
        file = new FileOperation();
        file.setFile("resources/database/reservation.txt");
        file.checkFilePath();
        file.retrieveData();
        
        try {
            todayDate = formatter.parse(strDate);

            ArrayList<String> reservationRecords = file.getRetrievedData();
            
            for(String records:reservationRecords) {
                String[] row = records.split(",");

                String reservationID = row[0];
                String ID = row[1];
                String dateFromFile = row[2];
                String startingTime = row[3];
                String endingTime = row[5];
                String event = row[6];
                String participantNum = row[8];
                String emailFromFile = row[9];

                reservationDate = formatter.parse(dateFromFile);

                if(userLabel.getText().equals(emailFromFile)) {
                    if (reservationDate.after(todayDate)) {
                        model.addRow(new Object[] {reservationID, ID, dateFromFile, startingTime, endingTime, event, participantNum});
                    }
                }   
            }
        } catch (ParseException ex) {
            Logger.getLogger(CheckReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        reservationTable.setModel(model);
    }
    
    class refundOperation {
        protected boolean cancellation = false;
        protected String reservationID;
        protected int selectedRow;
        protected int amountToRefund;
        protected boolean refundAmount = false;

        public boolean checkDays() {
            DefaultTableModel table = (DefaultTableModel) reservationTable.getModel();
            selectedRow = reservationTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a reservation to cancel.", "No Reservation Selected", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // GET DATA FROM SELECTED ROW
            reservationID = (String) table.getValueAt(selectedRow, 0);
            String reserveDate = (String) table.getValueAt(selectedRow, 2);

            try {
                Date formattedDate = formatter.parse(reserveDate);
                long differenceInMillis = formattedDate.getTime() - todayDate.getTime();
                long differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);

                if (differenceInDays < 3) {
                    JOptionPane.showMessageDialog(null, "Cancellation is not allowed. The reservation is less than 3 days away from today.", "Failed to Cancel Reservation", JOptionPane.ERROR_MESSAGE);
                } else {
                    int response = JOptionPane.showConfirmDialog(null, "The selected reservation can be cancelled. Would you like to proceed?", "Cancellation of Event", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        cancellation = true;
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Cancellation Paused.");
                    }
                }

            } catch (ParseException ex) {
                Logger.getLogger(CancelReservation.class.getName()).log(Level.SEVERE, null, ex);
            }
            return cancellation;
        }

        public boolean cancelAction() {
            DefaultTableModel table = (DefaultTableModel) reservationTable.getModel();
            boolean proceedToCancel = checkDays();
            

            if(proceedToCancel) {
                // REMOVE FROM RESERVATION FILE
                FileOperation reservationFile = new FileOperation();
                reservationFile.setFile("resources/database/reservation.txt");
                reservationFile.checkFilePath();
                reservationFile.retrieveData();

                ArrayList<String> reservationRecords = reservationFile.getRetrievedData();
                
                // FIND THE CORRECT INDEX BASED ON reservationID FOR RESERVATION FILE
                int reservationIndexToRemove = -1;
                for (int i = 0; i < reservationRecords.size(); i++) {
                    String[] fields = reservationRecords.get(i).split(",");
                    if (fields[0].equals(reservationID)) { // Reservation ID is at index 0
                        reservationIndexToRemove = i;
                        break;
                    }
                }

                // REMOVE THE RESERVATION RECORD
                if (reservationIndexToRemove != -1) {
                    reservationRecords.remove(reservationIndexToRemove);
                    table.removeRow(selectedRow); // REMOVE FROM TABLE
                    reservationFile.deleteData(reservationRecords); // DELETE AND UPDATE TO THE FILE
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Unable to find the selected reservation in the file.", "Cancellation Error", JOptionPane.ERROR_MESSAGE);
                }
                
                // REMOVE FROM PAYMENT FILE
                FileOperation paymentFile = new FileOperation();
                paymentFile.setFile("resources/database/payment.txt");
                paymentFile.checkFilePath();
                paymentFile.retrieveData();
                ArrayList<String> paymentRecords = paymentFile.getRetrievedData();

                // FIND THE CORRECT INDEX BASED ON reservationID FOR PAYMENT FILE
                int paymentIndexToRemove = -1;
                for (int i = 0; i < paymentRecords.size(); i++) {
                    String[] fields = paymentRecords.get(i).split(",");
                    
                    String amount  = fields[3];
                    if (amount.endsWith(".0")) {
                        amount = amount.substring(0, amount.length() - 2);
                    }

                    amountToRefund = Integer.parseInt(amount);
                    if (fields[6].equals(reservationID)) { 
                        paymentIndexToRemove = i;
                        break;
                    }
                }

                // REMOVE THE PAYMENT RECORD
                if (paymentIndexToRemove != -1) {
                    paymentRecords.remove(paymentIndexToRemove);
                    paymentFile.deleteData(paymentRecords); // DELETE AND UPDATE TO THE FILE
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Unable to find the corresponding payment record in the file.", "Cancellation Error", JOptionPane.ERROR_MESSAGE);
                }
                
                if(paymentIndexToRemove != -1 && reservationIndexToRemove != -1) {
                    refundAmount = true;
                }
            }
            return refundAmount;
        }
        
        public void refundAction() {
            boolean refund = cancelAction();
            
            if(refund) {
                FileOperation walletFile = new FileOperation();
                walletFile.setFile("resources/database/wallet.txt");
                walletFile.checkFilePath();
                walletFile.retrieveData();
                
                ArrayList<String> walletInfo = walletFile.getRetrievedData();
                
                for(int i = 0; i < walletInfo.size(); i++) {
                    String[] records = walletInfo.get(i).split(",");

                    String userFromFile = records[0];
                    String creditFromFile = records[1];

                    if(userFromFile.equals(getEmail())) {
                        UserWallet wallet = new UserWallet();
                        int credit = Integer.parseInt(creditFromFile);
                        wallet.setAmount(credit);
                        wallet.addAmount(amountToRefund); 
                        walletInfo.set(i, userFromFile + "," + wallet.getAmount());
                        JOptionPane.showMessageDialog(null, "Your reservation has been successfully canceled, and the amount has been refunded to your account.", "Success Cancellation", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                walletFile.updateData(walletInfo);
                
                
            }
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

        jPanel1 = new javax.swing.JPanel();
        leftMenu = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        companyLogo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        homeNaviIcon = new javax.swing.JLabel();
        homeNaviLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        bookHallicon = new javax.swing.JLabel();
        bookHallLink = new javax.swing.JLabel();
        checkReservationIcon = new javax.swing.JLabel();
        checkReservationLink = new javax.swing.JLabel();
        refundIcon = new javax.swing.JLabel();
        refundLink = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        exitIcon = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pfpManagementIcon = new javax.swing.JLabel();
        pfpManagementLink = new javax.swing.JLabel();
        contactUsIcon = new javax.swing.JLabel();
        exitLink = new javax.swing.JLabel();
        contactUsLink = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reservationTable = new javax.swing.JTable();
        cancelButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(248, 246, 240));
        jPanel1.setPreferredSize(new java.awt.Dimension(980, 620));

        leftMenu.setBackground(new java.awt.Color(172, 225, 199));
        leftMenu.setPreferredSize(new java.awt.Dimension(220, 620));

        userLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userLabel.setText("hongjx0321@gmail.com");

        companyLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/city-hall-small.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("Hall Symphony Inc.");

        homeNaviIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/home.png"))); // NOI18N
        homeNaviIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        homeNaviLabel.setText("Home Page");
        homeNaviLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeNaviLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeNaviLabelMouseClicked(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(153, 153, 153));
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("General");

        bookHallicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/rent.png"))); // NOI18N
        bookHallicon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        bookHallLink.setText("Book Hall");
        bookHallLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookHallLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookHallLinkMouseClicked(evt);
            }
        });

        checkReservationIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/check-up.png"))); // NOI18N
        checkReservationIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        checkReservationLink.setText("Check Reserved Hall");
        checkReservationLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkReservationLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkReservationLinkMouseClicked(evt);
            }
        });

        refundIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/refund.png"))); // NOI18N
        refundIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        refundLink.setText("Hall Cancellation");
        refundLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refundLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refundLinkMouseClicked(evt);
            }
        });

        jSeparator1.setPreferredSize(new java.awt.Dimension(65, 10));

        exitIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/logout.png"))); // NOI18N
        exitIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Settings and Others");

        pfpManagementIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/user.png"))); // NOI18N
        pfpManagementIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        pfpManagementLink.setText("Profile Management");
        pfpManagementLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pfpManagementLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pfpManagementLinkMouseClicked(evt);
            }
        });

        contactUsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/help.png"))); // NOI18N
        contactUsIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        exitLink.setText("Log Out");
        exitLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitLinkMouseClicked(evt);
            }
        });

        contactUsLink.setText("Contact Us");
        contactUsLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout leftMenuLayout = new javax.swing.GroupLayout(leftMenu);
        leftMenu.setLayout(leftMenuLayout);
        leftMenuLayout.setHorizontalGroup(
            leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenuLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(contactUsIcon)
                        .addGap(18, 18, 18)
                        .addComponent(contactUsLink))
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(pfpManagementIcon)
                        .addGap(18, 18, 18)
                        .addComponent(pfpManagementLink))
                    .addComponent(jLabel16)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(exitIcon)
                        .addGap(18, 18, 18)
                        .addComponent(exitLink))
                    .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(homeNaviIcon)
                            .addGap(18, 18, 18)
                            .addComponent(homeNaviLabel))
                        .addComponent(jLabel8)
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(bookHallicon)
                            .addGap(18, 18, 18)
                            .addComponent(bookHallLink))
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(checkReservationIcon)
                            .addGap(18, 18, 18)
                            .addComponent(checkReservationLink))
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(refundIcon)
                            .addGap(18, 18, 18)
                            .addComponent(refundLink))
                        .addComponent(companyLogo)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userLabel)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        leftMenuLayout.setVerticalGroup(
            leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenuLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(companyLogo)
                .addGap(7, 7, 7)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(userLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(homeNaviIcon)
                    .addComponent(homeNaviLabel))
                .addGap(33, 33, 33)
                .addComponent(jLabel8)
                .addGap(13, 13, 13)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookHallicon)
                    .addComponent(bookHallLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(checkReservationIcon)
                        .addGap(18, 18, 18)
                        .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refundIcon)
                            .addComponent(refundLink)))
                    .addComponent(checkReservationLink))
                .addGap(28, 28, 28)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(pfpManagementIcon)
                        .addGap(18, 18, 18)
                        .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contactUsIcon)
                            .addComponent(contactUsLink))
                        .addGap(18, 18, 18)
                        .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(exitIcon)
                            .addComponent(exitLink)))
                    .addComponent(pfpManagementLink))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(248, 246, 240));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Reservation Cancellation");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/refund-2.png"))); // NOI18N

        reservationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(reservationTable);

        cancelButton.setBackground(new java.awt.Color(204, 204, 204));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setText("Cancel Reservation");
        cancelButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Please select a reservation from the table below to proceed with cancellation.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(156, 156, 156))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        refundOperation operation = new refundOperation();
        operation.refundAction();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void homeNaviLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeNaviLabelMouseClicked
        Dashboard db = new Dashboard();
        db.setSessionEmail(getEmail());
        db.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homeNaviLabelMouseClicked

    private void bookHallLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookHallLinkMouseClicked
        BookHall hallPage = new BookHall();
        hallPage.setEmail(getEmail());
        hallPage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bookHallLinkMouseClicked

    private void refundLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refundLinkMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_refundLinkMouseClicked

    private void pfpManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfpManagementLinkMouseClicked
        ProfileManagement pm = new ProfileManagement();
        pm.setEmail(getEmail());
        pm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pfpManagementLinkMouseClicked

    private void checkReservationLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkReservationLinkMouseClicked
        CheckReservation cr = new CheckReservation();
        cr.setEmail(getEmail());
        cr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_checkReservationLinkMouseClicked

    private void exitLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLinkMouseClicked
        JOptionPane.showMessageDialog(null, "You have successfully logout from your account!");
        LoginPage login = new LoginPage();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_exitLinkMouseClicked

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
            java.util.logging.Logger.getLogger(CancelReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CancelReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CancelReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CancelReservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CancelReservation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bookHallLink;
    private javax.swing.JLabel bookHallicon;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel checkReservationIcon;
    private javax.swing.JLabel checkReservationLink;
    private javax.swing.JLabel companyLogo;
    private javax.swing.JLabel contactUsIcon;
    private javax.swing.JLabel contactUsLink;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel exitLink;
    private javax.swing.JLabel homeNaviIcon;
    private javax.swing.JLabel homeNaviLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel leftMenu;
    private javax.swing.JLabel pfpManagementIcon;
    private javax.swing.JLabel pfpManagementLink;
    private javax.swing.JLabel refundIcon;
    private javax.swing.JLabel refundLink;
    private javax.swing.JTable reservationTable;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
