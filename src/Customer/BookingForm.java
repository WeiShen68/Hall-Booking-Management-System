/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Customer;

import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class BookingForm extends javax.swing.JFrame {

    /**
     * Creates new form BookingForm
     */
    private String hallID;
    private String reservationDate;
    private double startingTime;
    private double duration;
    private double price;
    private double endingTime;
    private String hallType;
    private int capacity;
    private String email;
    
    public void setEmail(String email) {
        this.email = email;
        emailLabel.setText(email);
    }
    
    public String getEmail() {
        return email;
    }
    
    public BookingForm() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void setHallID(String hallID) {
        this.hallID = hallID;
    }
    
    public String getHallID() {
        return hallID;
    }
    
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
    
    public String getReservationDate() {
        return reservationDate;
    }
    
    public void setStartingTime(double startingTime) {
        this.startingTime = startingTime;
    }
    
    public double getStartingTime() {
        return startingTime;
    }
    
    public void setDuration(double duration) {
        this.duration = duration;
    }
    
    public double getDuration() {
        return duration;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getPrice() {
        double totalPrice = price * getDuration();
        return totalPrice;
    }
    
    public void setEndingTime(double endingTime) {
        this.endingTime = endingTime;
    }
    
    public double getEndingTime() {
        double endTime = getStartingTime() + getDuration();
        return endTime;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setHallType(String hallType) {
        this.hallType = hallType;
    }
    
    public String getHallType() {
        return hallType;
    }
    
    class checkBookingForm {
        protected String eventTitle;
        protected String rentingPurpose;
        protected int participants;
        protected String emergencyContact;
        protected boolean checked;
        protected BookingManagement booking;
        protected String[] bookingArray;
        protected boolean pageChange = false;
        
        public boolean checkUserInput() {
            eventTitle = titleField.getText().toLowerCase();
            rentingPurpose = (String) purposeInput.getSelectedItem();
            participants = (int) participantsInput.getValue();
            emergencyContact = contactField.getText();
            checked = tncCheckBox.isSelected();
            
            if(eventTitle.isEmpty() || rentingPurpose == null || participants <= 0 || emergencyContact.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please complete all required fields correctly", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else {
                if(!checked) {
                    JOptionPane.showMessageDialog(rootPane, "Please agree to the terms and conditions", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if(participants > getCapacity()) {
                        JOptionPane.showMessageDialog(rootPane, "Participant amount has exceeded the hall capacity!", "Maximum Capacity Reached", JOptionPane.ERROR_MESSAGE);
                    } else {
                        setInputs();
                        System.out.println(Arrays.toString(getInputs()));
//                        System.out.println("hall id: " + getHallID());
//                        System.out.println("reservation date: " + getReservationDate());
//                        System.out.println("booking time: " + getStartingTime());
//                        System.out.println("duration: " + getDuration());
//                        System.out.println("total price: " + getPrice());
//                        System.out.println("ending time:" + getEndingTime());
//                        System.out.println("hall tyoe:" + getHallType());
//                        System.out.println("capacity: " + getCapacity());
                        
                        pageChange = true;
                    }
                    
                }
            }
            return pageChange;
        }
        
        public void setInputs() {
             booking = new BookingManagement(getHallID(), getReservationDate(), getStartingTime(), getDuration(),getEndingTime(), 
                     eventTitle, rentingPurpose, participants, getEmail(), emergencyContact);
        }
        
        public String[] getInputs() {
            bookingArray = new String[1];
            bookingArray[0] = booking.toString();

            // Optionally, print the array or use it as needed
//            for (String entry : bookingArray) {
//                System.out.println(entry);
//            }
            return bookingArray;
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

        jPanel2 = new javax.swing.JPanel();
        background = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        purposeInput = new javax.swing.JComboBox<>();
        tncCheckBox = new javax.swing.JCheckBox();
        proceedButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        participantsInput = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        contactField = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        terminateButton = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        emailLabel = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(248, 246, 240));
        background.setPreferredSize(new java.awt.Dimension(850, 550));

        header.setPreferredSize(new java.awt.Dimension(850, 55));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(85, 85, 85));
        jPanel1.setPreferredSize(new java.awt.Dimension(150, 59));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Select Hall Type");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        header.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(85, 85, 85));
        jPanel3.setPreferredSize(new java.awt.Dimension(150, 59));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Select Date and Time");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 19, -1, -1));

        header.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 160, -1));

        jPanel4.setBackground(new java.awt.Color(248, 246, 240));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.setMinimumSize(new java.awt.Dimension(150, 59));
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 56));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 0));
        jLabel3.setText("Complete Booking Form");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        header.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 190, 55));

        jPanel5.setBackground(new java.awt.Color(85, 85, 85));
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 59));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Payment Session");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        header.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, -1, -1));

        jPanel6.setBackground(new java.awt.Color(85, 85, 85));
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 59));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("View Payment Summary");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        header.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 200, -1));

        jPanel7.setBackground(new java.awt.Color(248, 246, 240));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setText("Secure Reservation: Complete Booking Form");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Email Address");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Purpose of Renting Hall");

        purposeInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        purposeInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Weddings", "Corporate Meetings", "Birthday Parties", "Anniversary Celebrations", "Graduation Parties", "Concerts/Performances", "Charity Event", "Others..." }));
        purposeInput.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tncCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tncCheckBox.setText("By clicking this, I agree to the terms and conditions outlined in the system. ");
        tncCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tncCheckBoxActionPerformed(evt);
            }
        });

        proceedButton.setBackground(new java.awt.Color(85, 85, 85));
        proceedButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        proceedButton.setForeground(new java.awt.Color(255, 255, 255));
        proceedButton.setText("Proceed");
        proceedButton.setBorder(null);
        proceedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Number of Participants");

        participantsInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        participantsInput.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Event Title");

        titleField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Emergency Contact Number");

        contactField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel8.setBackground(new java.awt.Color(85, 85, 85));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("1. No refunds beyond 3 days.");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Terms & Condition");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("2. Full payment accepted only. ");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("3. Smoking is not permitted.");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("4. We are not responsible for lost items.");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("5. Damage liability will be charged.");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("6. Venue must be in a clean condition.");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        terminateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/delete.png"))); // NOI18N
        terminateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        terminateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                terminateButtonMouseClicked(evt);
            }
        });

        emailLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailLabel.setText("User Email");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(terminateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(252, 252, 252)
                                .addComponent(jLabel12))
                            .addComponent(tncCheckBox)
                            .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(titleField)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(purposeInput, javax.swing.GroupLayout.Alignment.LEADING, 0, 296, Short.MAX_VALUE)
                                        .addComponent(jSeparator2))
                                    .addComponent(emailLabel))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contactField)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(participantsInput))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(terminateButton)
                    .addComponent(jLabel7))
                .addGap(17, 17, 17)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(participantsInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(purposeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(contactField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(emailLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addComponent(tncCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tncCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tncCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tncCheckBoxActionPerformed

    private void proceedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedButtonActionPerformed
        checkBookingForm booking = new checkBookingForm();
        boolean pageNavi = booking.checkUserInput();
        
        if(pageNavi) {
            Payment paymentPage = new Payment();
            paymentPage.setHallID(getHallID());
            paymentPage.setHallType(getHallType());
            paymentPage.setReservationDate(getReservationDate());
            paymentPage.setTime(getStartingTime());
            paymentPage.setDuration(getDuration());
            paymentPage.setPrice(getPrice());
            paymentPage.setEndingTime(getEndingTime());
            paymentPage.setBookingInfo(booking.bookingArray);
            paymentPage.setEmail(getEmail());
            paymentPage.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_proceedButtonActionPerformed

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
            java.util.logging.Logger.getLogger(BookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookingForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JTextField contactField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSpinner participantsInput;
    private javax.swing.JButton proceedButton;
    private javax.swing.JComboBox<String> purposeInput;
    private javax.swing.JLabel terminateButton;
    private javax.swing.JTextField titleField;
    private javax.swing.JCheckBox tncCheckBox;
    // End of variables declaration//GEN-END:variables
}
