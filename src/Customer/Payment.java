/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Customer;

import Users.FileOperation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Payment extends javax.swing.JFrame {

    /**
     * Creates new form Payment
     */
    private String hallID;
    private String hallType;
    private String reservationDate;
    private double startingTime;
    private double duration;    
    private double price;
    private double endingTime;
    private String email;
    private String[] bookingInfo;
    
    public void setHallID(String hallID) {
        this.hallID = hallID;
        idLabel.setText(hallID);
    }
    
    public String getHallID() {
        return hallID;
    }
    
    public void setHallType(String hallType) {
        this.hallType = hallType;
        hallLabel.setText(hallType);
    }
    
    public String hallType() {
        return hallType;
    }
    
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
        dateLabel.setText(reservationDate);
    }
    
    public String reservationDate() {
        return reservationDate;
    }
    
    public void setDuration(double duration) {
        this.duration = duration;
        String durationString = String.format("%.0f", duration);
        durationLabel.setText(durationString  + " hours");
    }
    
    public double getDuration() {
        return duration;
    }
    
    public void setTime(double startingTime) {
        this.startingTime = startingTime;
        String timeString = String.format("%.0f", startingTime);
        timeLabel.setText(timeString);
    }
    
    public double getTime() {
        return startingTime;
    }
    
    public void setPrice(double price) {
        this.price = price;
        String priceString = String.format("%.2f", price);
        amountLabel.setText(priceString);
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setEndingTime(double endingTime) {
        this.endingTime = endingTime;
        String endingTimeString = String.format("%.0f", endingTime);
        endingTimeLabel.setText(endingTimeString);
    }
    
    public double getEndingTime() {
        return endingTime;
    }
    
    public void setEmail(String email) {
        this.email = email;
        emailLabel.setText(email);
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setBookingInfo(String[] bookingInfo) {
        this.bookingInfo = bookingInfo;
    }
    
    
    public String[] getBookingInfo() {
        return bookingInfo;
    }
    
    
    public Payment() {
        initComponents();
        this.setLocationRelativeTo(null);        
    }
    
    class inputValidation {
        protected String nameInput;
        protected String email;
        
        
        public void checkInput() {
            nameInput = payerName.getText();
            email = emailLabel.getText();
            
            if(nameInput.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please fill in all the required fields to complete your payment.", " Required Fields Missing", JOptionPane.ERROR_MESSAGE);
            } else {
            }
        }
    }
    
    class userCredit {
        private ArrayList<String> walletInfo;
        private String userFromFile;
        protected String creditFromFile;
        protected int credit;
        protected UserWallet wallet = new UserWallet();
        
        protected String incrementIdByOne;
        protected String incrementIdByOne2;
        protected boolean pageChange = false;
        protected boolean appended = false;
        protected boolean appendedPayment = false;
        
        
        public void checkCredit() {
            FileOperation file = new FileOperation();
            file.setFile("resources/database/wallet.txt");
            file.checkFilePath();
            file.retrieveData();
            walletInfo = file.getRetrievedData();
            for(String row : walletInfo) {
                String[] line = row.split(",");
                
                userFromFile = line[0];
                creditFromFile = line[1];
                
                if(email.equals(userFromFile)) {
                    credit = Integer.parseInt(creditFromFile);
                    wallet.setAmount(credit);
                    creditLabel.setText(creditFromFile);
                }
            } 
        }
        
        public boolean rentalOperation() {
            wallet.deductAmount((int) getPrice());
            
            for (int i = 0; i < walletInfo.size(); i++) {
                String[] line = walletInfo.get(i).split(",");
                if (email.equals(line[0])) {
                    walletInfo.set(i, email + "," + wallet.getAmount());
                    break;
                }
            }
        
            // Write the updated walletInfo back to the file
            FileOperation file = new FileOperation();
            file.setFile("resources/database/wallet.txt");
            file.checkFilePath();
            file.updateData(walletInfo);
            
            file.setFile("resources/database/reservation.txt");
            file.checkFilePath();
            file.retrieveData();
            file.setSplitElement("R");
            file.IdOperation();
            int newIdToBeAssigned = file.getIdNum();
            incrementIdByOne = "R" + newIdToBeAssigned;
            for(String nr : bookingInfo) {
                System.out.println("test:" + nr);
                String updatedRecord = incrementIdByOne + "," + nr;
                file.appendData(updatedRecord);
                appended = true;
            }
            
            file.setFile("resources/database/payment.txt");
            file.checkFilePath();
            file.retrieveData();
            file.setSplitElement("P");
            file.IdOperation();
            int newIdToBeAssigned2 = file.getIdNum();
            incrementIdByOne2 = "P" + newIdToBeAssigned2;
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            String strDate = formatter.format(date);
            file.appendData(incrementIdByOne2 + "," + emailLabel.getText() + "," + strDate + "," + getPrice() + ",hall rental,completed," + incrementIdByOne);
            appendedPayment = true;
            
            
            if(wallet.navigation && appended && appendedPayment) {
                pageChange = true;
            }
            return pageChange;
        }
        
        public String getIncrementIdByOne2() {
            return incrementIdByOne2;
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        background = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        content = new javax.swing.JPanel();
        pageTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        infomationTitle = new javax.swing.JLabel();
        informationIcon = new javax.swing.JLabel();
        payerName = new javax.swing.JTextField();
        emailTitle = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        hallLabel = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        durationLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        endingTimeLabel = new javax.swing.JLabel();
        payButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        creditLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        clicktorevealLink = new javax.swing.JLabel();
        terminateButton = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(248, 246, 240));
        background.setPreferredSize(new java.awt.Dimension(850, 550));

        header.setBackground(new java.awt.Color(85, 85, 85));
        header.setMinimumSize(new java.awt.Dimension(850, 55));
        header.setPreferredSize(new java.awt.Dimension(850, 55));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(85, 85, 85));
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 59));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Select Hall Type");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        header.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(85, 85, 85));
        jPanel3.setPreferredSize(new java.awt.Dimension(180, 59));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Select Date and Time");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        header.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, -1, -1));

        jPanel4.setBackground(new java.awt.Color(85, 85, 85));
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 59));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Complete Booking Form");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        header.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 180, -1));

        jPanel5.setBackground(new java.awt.Color(248, 246, 240));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 50));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("Payment Session");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        header.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, -1, 55));

        jPanel6.setBackground(new java.awt.Color(85, 85, 85));
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 59));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("View Payment Summary");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        header.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 190, -1));

        content.setBackground(new java.awt.Color(248, 246, 240));
        content.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contentMouseClicked(evt);
            }
        });

        pageTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        pageTitle.setText("Hurray! Time for Payment");

        infomationTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        infomationTitle.setForeground(new java.awt.Color(255, 153, 0));
        infomationTitle.setText("Payment Accepted Through E-Wallet Only");

        informationIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/info.png"))); // NOI18N

        payerName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        emailTitle.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailTitle.setText("Email Address");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel21.setText("Order Summary");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Selected Hall Type : ");

        hallLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hallLabel.setText("Test");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("Hall ID :");

        idLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        idLabel.setText("H1");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Reservation Date : ");

        dateLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dateLabel.setText("Date");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Reservation Time :");

        timeLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        timeLabel.setText("Time");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setText("Rental Duration : ");

        durationLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        durationLabel.setText("2");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 51, 51));
        jLabel32.setText("TOTAL AMOUNT : ");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 51, 51));
        jLabel33.setText("RM");

        amountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        amountLabel.setForeground(new java.awt.Color(255, 51, 51));
        amountLabel.setText("500.00");

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/shopping-list.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Ending Time : ");

        endingTimeLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        endingTimeLabel.setText("endingTime");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(durationLabel))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLabel))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hallLabel))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(idLabel))
                                .addComponent(jLabel21))
                            .addGap(83, 83, 83)
                            .addComponent(jLabel35))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amountLabel))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endingTimeLabel))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeLabel)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(idLabel)))
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(hallLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(dateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(timeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(durationLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(endingTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(amountLabel))
                .addGap(18, 18, 18))
        );

        payButton.setBackground(new java.awt.Color(85, 85, 85));
        payButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        payButton.setForeground(new java.awt.Color(255, 255, 255));
        payButton.setText("PAY");
        payButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/ewallet.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 204, 51));
        jLabel8.setText("Balance in E-Wallet : RM ");

        creditLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        creditLabel.setForeground(new java.awt.Color(0, 204, 51));
        creditLabel.setText("xxxx");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Payer Name");

        emailLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailLabel.setText("hongjx0321@gmail.com");

        clicktorevealLink.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clicktorevealLink.setForeground(new java.awt.Color(0, 204, 51));
        clicktorevealLink.setText("Click to reveal credits");
        clicktorevealLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clicktorevealLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clicktorevealLinkMouseClicked(evt);
            }
        });

        terminateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/delete.png"))); // NOI18N
        terminateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        terminateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                terminateButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(contentLayout.createSequentialGroup()
                                .addComponent(informationIcon)
                                .addGap(11, 11, 11)
                                .addComponent(infomationTitle))
                            .addComponent(jLabel11)
                            .addGroup(contentLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(creditLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clicktorevealLink)))
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(emailTitle)
                            .addComponent(payerName, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                            .addComponent(jSeparator3)
                            .addComponent(emailLabel)))
                    .addGroup(contentLayout.createSequentialGroup()
                        .addComponent(terminateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pageTitle)))
                .addGap(66, 66, 66)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(payButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pageTitle)
                            .addComponent(terminateButton))
                        .addGap(17, 17, 17)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(informationIcon)
                            .addGroup(contentLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(infomationTitle)))
                        .addGap(18, 18, 18)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(contentLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(creditLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(clicktorevealLink)))))
                        .addGap(29, 29, 29)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(payerName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(emailTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        inputValidation iv = new inputValidation();
        iv.checkInput();
        System.out.println("booking info: " + Arrays.toString(getBookingInfo()));
        
        userCredit uc = new userCredit();
        uc.checkCredit();
        
        boolean pc = uc.rentalOperation();
        if(pc) {
            JOptionPane.showMessageDialog(rootPane, "You have successfully rented the hall!", "Success Rental", JOptionPane.INFORMATION_MESSAGE);
            Receipt receipt = new Receipt();
            receipt.setPaymentID(uc.getIncrementIdByOne2());
            receipt.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_payButtonActionPerformed

    private void contentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contentMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_contentMouseClicked

    private void clicktorevealLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clicktorevealLinkMouseClicked
        userCredit uc = new userCredit();
        uc.checkCredit();
    }//GEN-LAST:event_clicktorevealLinkMouseClicked

    private void terminateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_terminateButtonMouseClicked
        BookHall hall = new BookHall();
        hall.setEmail(getEmail());
        hall.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_terminateButtonMouseClicked

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
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payment().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amountLabel;
    private javax.swing.JPanel background;
    private javax.swing.JLabel clicktorevealLink;
    private javax.swing.JPanel content;
    private javax.swing.JLabel creditLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel durationLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailTitle;
    private javax.swing.JLabel endingTimeLabel;
    private javax.swing.JLabel hallLabel;
    private javax.swing.JPanel header;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel infomationTitle;
    private javax.swing.JLabel informationIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JButton payButton;
    private javax.swing.JTextField payerName;
    private javax.swing.JLabel terminateButton;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
