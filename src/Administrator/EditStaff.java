/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Administrator;

import static Administrator.EditStaff.InfoPanelOperation.originalAge;
import static Administrator.EditStaff.InfoPanelOperation.originalGender;
import static Administrator.EditStaff.InfoPanelOperation.originalPhoneNumber;
import static Administrator.EditStaff.InfoPanelOperation.originalUsername;
import Users.FileOperation;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS TUF
 */
public class EditStaff extends javax.swing.JFrame {

    /**
     * Creates new form EditStaff
     */
    private String email;
    private String userEmailLabel;

    public String getEmail() {
        return email;
    }
    
    public EditStaff() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void setEmailLabel(String email) {
        this.userEmailLabel = email;
        userLabel.setText(email);
    }
    
    public void setEmail(String email) {
        this.email = email;
        emailLabel.setText(email);
        setInformation();
    }
    
    public void setInformation() {
        FileOperation file = new FileOperation();
        file.setFile("resources/database/users.txt");
        file.checkFilePath();
        file.retrieveData();
        ArrayList<String> records = file.getRetrievedData();

        for (String row : records) {
            String[] line = row.split(",");

            String emailFromFile = line[0];
            String usernameFromFile = line[1];
            String phoneFromFile = line[2];
            String ageFromFile = line[6];
            String genderFromFile = line[5].trim();

            if (emailFromFile.equals(getEmail())) {
                usernameField.setText(usernameFromFile);
                phoneNumField.setText(phoneFromFile);
                ageField.setText(ageFromFile);
                // Compare and select gender ignoring case, default to index 0 if not found
                boolean genderFound = false;
                for (int i = 0; i < genderComboBox.getItemCount(); i++) {
                    String item = (String) genderComboBox.getItemAt(i);
                    if (item.equalsIgnoreCase(genderFromFile)) {
                        genderComboBox.setSelectedIndex(i);  // Set the genderComboBox to the correct index
                        genderFound = true;
                        break;
                    }
                }
                if (!genderFound) {
                    genderComboBox.setSelectedIndex(0);  // Default to the first index if no match is found
                }
                
                // Store the original values
                InfoPanelOperation.originalUsername = usernameFromFile;
                InfoPanelOperation.originalPhoneNumber = phoneFromFile;
                InfoPanelOperation.originalAge = ageFromFile;
                InfoPanelOperation.originalGender = genderFromFile;

                break;
            }
        }
    }
    
    // Helper method to validate age
    public boolean isValidAge(String age) {
        try {
            int userAge = Integer.parseInt(age);
            return userAge > 0;  // Age should be greater than 0
        } catch (NumberFormatException e) {
            return false;  // Invalid age format
        }
    }
    
    class InfoPanelOperation {
        // Store the original values
        protected static String originalUsername;
        protected static String originalPhoneNumber;
        protected static String originalGender;
        protected static String originalAge;

        protected String username;
        protected String phoneNumber;
        protected String gender;
        protected String formattedGender;
        protected String age;
        protected boolean proceedModification = false;

        public boolean checkInfoInput() {
            username = usernameField.getText().trim();
            phoneNumber = phoneNumField.getText().trim();
            gender = (String) genderComboBox.getSelectedItem();
            formattedGender = gender != null ? gender.toLowerCase() : "";
            age = ageField.getText().trim();

            if (username.isEmpty() || phoneNumber.isEmpty() || gender.equals("Select Gender") || age.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please complete all required fields!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!isValidAge(age)) {
                JOptionPane.showMessageDialog(rootPane, "Invalid age. Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            proceedModification = true;
            return true;
        }

        public void dataModification() {
            boolean modification = checkInfoInput();
            
            if (modification) {
                // Check if the current values are the same as the original values
                if (username.equals(originalUsername) && phoneNumber.equals(originalPhoneNumber) && age.equals(originalAge) && gender.equalsIgnoreCase(originalGender)) {
                    JOptionPane.showMessageDialog(rootPane, "No Changes Detected.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    return;  // Exit the method, no further action needed
                }
                
                FileOperation file = new FileOperation();
                file.setFile("resources/database/users.txt");
                file.checkFilePath();
                file.retrieveData();
                ArrayList<String> records = file.getRetrievedData();
                
                boolean updated = false;
                for (int i = 0; i < records.size(); i++) {
                    String[] row = records.get(i).split(",");

                    String emailFromFile = row[0];
                    String passFromFile = row[3];
                    String roleFromFile = row[4];
                    String status = row[7];

                    if (emailFromFile.equals(getEmail())) {
                        records.set(i, getEmail() + "," + username + "," + phoneNumber + "," + passFromFile + "," + roleFromFile + "," + formattedGender + "," + age + "," + status);
                        updated = true;
                        break;
                    }
                }

                if (updated) {
                    int response = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to edit existing scheduler staff information?", "Confirmation of Modification", JOptionPane.YES_NO_OPTION);
                    switch (response) {
                        case JOptionPane.YES_OPTION -> {
                            file.updateData(records);
                            JOptionPane.showMessageDialog(rootPane, "Information Successfully Updated!", "Success Modification", JOptionPane.INFORMATION_MESSAGE);
                            setInformation();
                        }
                        case JOptionPane.NO_OPTION, JOptionPane.CANCEL_OPTION -> {
                            // Reset to the original values
                            usernameField.setText(originalUsername);
                            phoneNumField.setText(originalPhoneNumber);
                            ageField.setText(originalAge);

                            // Compare and select gender ignoring case, default to index 0 if not found
                            boolean genderFound = false;
                            for (int i = 0; i < genderComboBox.getItemCount(); i++) {
                                String item = (String) genderComboBox.getItemAt(i);
                                if (item.equalsIgnoreCase(originalGender)) {
                                    genderComboBox.setSelectedIndex(i);  // Set the genderComboBox to the correct index
                                    genderFound = true;
                                    break;
                                }
                            }
                            if (!genderFound) {
                                genderComboBox.setSelectedIndex(0);  // Default to the first index if no match is found
                            }

                            JOptionPane.showMessageDialog(rootPane, "Modification Declined. Original values restored.");
                        }
                    }
                }
            }
        }
    }
    
    class passwordPanelOperation extends InfoPanelOperation { // Inheritance 

        protected String newPassword;
        protected String passwordConfirmation;
        protected boolean changePassword = false;

        @Override
        public boolean checkInfoInput() {
            newPassword = passwordField.getText();
            passwordConfirmation = confirmPassField.getText();

            if (newPassword.isEmpty() || passwordConfirmation.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter new password to be changed!", "Invalid Password Input", JOptionPane.ERROR_MESSAGE);
            } else {
                if (passwordConfirmation.equals(newPassword)) {
                    System.out.println("proceed to change");
                    changePassword = true;
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Password Mismatch: Please ensure both fields contain the same password!", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                }
            }
            return changePassword;
        }

        @Override
        public void dataModification() {
            boolean modification = checkInfoInput();
            boolean updatedPass = false;

            if (modification) {
                FileOperation file = new FileOperation();
                file.setFile("resources/database/users.txt");
                file.checkFilePath();
                file.retrieveData();
                ArrayList<String> records = file.getRetrievedData();

                for (int i = 0; i < records.size(); i++) {
                    String[] row = records.get(i).split(",");

                    String emailFromFile = row[0];
                    String usernameFromFile = row[1];
                    String phoneFromFile = row[2];
                    String roleFromFile = row[4];
                    String genderFromFile = row[5];
                    String ageFromFile = row[6];
                    String statusFromFile = row[7];

                    if (emailFromFile.equals(getEmail())) {
                        records.set(i, getEmail() + "," + usernameFromFile + "," + phoneFromFile + "," + passwordConfirmation + "," + roleFromFile + "," + genderFromFile + "," + ageFromFile + "," + statusFromFile);
                        updatedPass = true;
                        break;
                    }
                }

                if (updatedPass) {
                    int response = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to reset existing scheduler staff password?", "Confirmation of Changing Password", JOptionPane.YES_NO_OPTION);
                    switch (response) {
                        case JOptionPane.YES_OPTION -> {
                            file.updateData(records);
                            JOptionPane.showMessageDialog(rootPane, "You have successfully reset existing scheduler staff password!", "Success Password Change", JOptionPane.INFORMATION_MESSAGE);
                            passwordField.setText("");
                            confirmPassField.setText("");
                        }
                        case JOptionPane.NO_OPTION ->
                            JOptionPane.showMessageDialog(rootPane, "Action Declined.");
                        case JOptionPane.CANCEL_OPTION ->
                            JOptionPane.showMessageDialog(rootPane, "Action Declined.");
                    }
                }
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
        staffManagementLink = new javax.swing.JLabel();
        checkReservationIcon = new javax.swing.JLabel();
        userManagementLink = new javax.swing.JLabel();
        refundIcon = new javax.swing.JLabel();
        bookingsListLink = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        exitIcon = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pfpManagementIcon = new javax.swing.JLabel();
        pfpManagementLink = new javax.swing.JLabel();
        exitLink = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pageTitle2 = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        usernameTitle2 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        genderTitle2 = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        ageField = new javax.swing.JTextField();
        phoneNumTitle2 = new javax.swing.JLabel();
        phoneNumField = new javax.swing.JTextField();
        emailTitle2 = new javax.swing.JLabel();
        ageTitle2 = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();
        clearIcon = new javax.swing.JLabel();
        passPanel = new javax.swing.JPanel();
        ChangePassTitle = new javax.swing.JLabel();
        passTitle = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmPassTitle = new javax.swing.JLabel();
        confirmPassField = new javax.swing.JPasswordField();
        savePassButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edit Existing Scheduler Staff Information");
        setResizable(false);

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

        bookHallicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/account-tie.png"))); // NOI18N
        bookHallicon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        staffManagementLink.setText("Staff Management");
        staffManagementLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        staffManagementLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffManagementLinkMouseClicked(evt);
            }
        });

        checkReservationIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/account-group.png"))); // NOI18N
        checkReservationIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        userManagementLink.setText("User Management");
        userManagementLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userManagementLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userManagementLinkMouseClicked(evt);
            }
        });

        refundIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/table-eye.png"))); // NOI18N
        refundIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        bookingsListLink.setText("Bookings List");
        bookingsListLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookingsListLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingsListLinkMouseClicked(evt);
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

        exitLink.setText("Log Out");
        exitLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitLinkMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout leftMenuLayout = new javax.swing.GroupLayout(leftMenu);
        leftMenu.setLayout(leftMenuLayout);
        leftMenuLayout.setHorizontalGroup(
            leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenuLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(pfpManagementIcon)
                        .addGap(18, 18, 18)
                        .addComponent(pfpManagementLink))
                    .addComponent(jLabel16)
                    .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(homeNaviIcon)
                            .addGap(18, 18, 18)
                            .addComponent(homeNaviLabel))
                        .addComponent(jLabel8)
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(bookHallicon)
                            .addGap(18, 18, 18)
                            .addComponent(staffManagementLink))
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(checkReservationIcon)
                            .addGap(18, 18, 18)
                            .addComponent(userManagementLink))
                        .addGroup(leftMenuLayout.createSequentialGroup()
                            .addComponent(refundIcon)
                            .addGap(18, 18, 18)
                            .addComponent(bookingsListLink))
                        .addComponent(companyLogo)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userLabel)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(exitIcon)
                        .addGap(18, 18, 18)
                        .addComponent(exitLink)))
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
                    .addComponent(staffManagementLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(checkReservationIcon)
                        .addGap(18, 18, 18)
                        .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refundIcon)
                            .addComponent(bookingsListLink)))
                    .addComponent(userManagementLink))
                .addGap(28, 28, 28)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pfpManagementIcon)
                    .addComponent(pfpManagementLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitLink)
                    .addComponent(exitIcon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(248, 246, 240));

        pageTitle2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        pageTitle2.setText("Edit Scheduler Profile");

        infoPanel.setBackground(new java.awt.Color(85, 85, 85));
        infoPanel.setPreferredSize(new java.awt.Dimension(342, 417));

        usernameTitle2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        usernameTitle2.setForeground(new java.awt.Color(255, 255, 255));
        usernameTitle2.setText("Username");

        usernameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        genderTitle2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        genderTitle2.setForeground(new java.awt.Color(255, 255, 255));
        genderTitle2.setText("Gender");

        genderComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        genderComboBox.setForeground(new java.awt.Color(255, 255, 255));
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Gender", "Male", "Female", "Prefer not to say" }));

        ageField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        phoneNumTitle2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        phoneNumTitle2.setForeground(new java.awt.Color(255, 255, 255));
        phoneNumTitle2.setText("Phone Number");

        phoneNumField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        emailTitle2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailTitle2.setForeground(new java.awt.Color(255, 255, 255));
        emailTitle2.setText("Email Address");

        ageTitle2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        ageTitle2.setForeground(new java.awt.Color(255, 255, 255));
        ageTitle2.setText("Age");

        emailLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("Current Email");

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(phoneNumField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genderTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameTitle2)
                            .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(usernameField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(phoneNumTitle2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailTitle2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(emailTitle2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(emailLabel)
                .addGap(23, 23, 23)
                .addComponent(usernameTitle2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderTitle2)
                    .addComponent(ageTitle2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phoneNumTitle2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        updateBtn.setBackground(new java.awt.Color(172, 225, 199));
        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        returnButton.setBackground(new java.awt.Color(85, 85, 85));
        returnButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        returnButton.setForeground(new java.awt.Color(255, 255, 255));
        returnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/return.png"))); // NOI18N
        returnButton.setText("Return");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        clearIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/clean.png"))); // NOI18N
        clearIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearIconMouseClicked(evt);
            }
        });

        passPanel.setBackground(new java.awt.Color(85, 85, 85));

        ChangePassTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ChangePassTitle.setForeground(new java.awt.Color(255, 255, 255));
        ChangePassTitle.setText("Change New Password");

        passTitle.setBackground(new java.awt.Color(255, 255, 255));
        passTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passTitle.setForeground(new java.awt.Color(255, 255, 255));
        passTitle.setText("New Password");

        confirmPassTitle.setBackground(new java.awt.Color(255, 255, 255));
        confirmPassTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        confirmPassTitle.setForeground(new java.awt.Color(255, 255, 255));
        confirmPassTitle.setText("Confirmation of New Password");

        savePassButton.setBackground(new java.awt.Color(255, 0, 0));
        savePassButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        savePassButton.setForeground(new java.awt.Color(255, 255, 255));
        savePassButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/reset-password.png"))); // NOI18N
        savePassButton.setText("Reset");
        savePassButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        savePassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePassButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout passPanelLayout = new javax.swing.GroupLayout(passPanel);
        passPanel.setLayout(passPanelLayout);
        passPanelLayout.setHorizontalGroup(
            passPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(passPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(savePassButton)
                    .addGroup(passPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                        .addComponent(confirmPassField)
                        .addComponent(ChangePassTitle)
                        .addComponent(passTitle)
                        .addComponent(confirmPassTitle)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        passPanelLayout.setVerticalGroup(
            passPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(ChangePassTitle)
                .addGap(18, 18, 18)
                .addComponent(passTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(confirmPassTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(savePassButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/employee.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(34, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(returnButton)
                                .addGap(503, 503, 503)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(passPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pageTitle2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(clearIcon)
                            .addComponent(pageTitle2))
                        .addGap(43, 43, 43)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(passPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeNaviLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeNaviLabelMouseClicked
        // Retrieve the current form values
        String currentUsername = usernameField.getText();
        String currentPhoneNumber = phoneNumField.getText();
        String currentAge = ageField.getText();
        String currentGender = (String) genderComboBox.getSelectedItem();

        // Check if any modification was made by comparing with original values
        boolean modificationDetected = !currentUsername.equals(originalUsername)
                || !currentPhoneNumber.equals(originalPhoneNumber)
                || !currentAge.equals(originalAge)
                || (!currentGender.equalsIgnoreCase(originalGender)
                && !(currentGender.equals("Select Gender") && originalGender.equalsIgnoreCase("unknown gender")));

        if (modificationDetected) {
            // Prompt user if they want to discard changes
            int response = JOptionPane.showConfirmDialog(rootPane,
                    "Unsaved changes detected. Do you want to discard the changes?",
                    "Discard Changes",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                AdminDash home = new AdminDash();
                home.setSessionEmail(getEmail());
                home.setVisible(true);
                this.dispose();
            } else {
                // User chose not to discard, remain on the same page
                JOptionPane.showMessageDialog(rootPane, "Modification restored. You remain continued on the current page.", "Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            AdminDash home = new AdminDash();
            home.setSessionEmail(getEmail());
            home.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_homeNaviLabelMouseClicked

    private void staffManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffManagementLinkMouseClicked
        // Retrieve the current form values
        String currentUsername = usernameField.getText();
        String currentPhoneNumber = phoneNumField.getText();
        String currentAge = ageField.getText();
        String currentGender = (String) genderComboBox.getSelectedItem();

        // Check if any modification was made by comparing with original values
        boolean modificationDetected = !currentUsername.equals(originalUsername)
                || !currentPhoneNumber.equals(originalPhoneNumber)
                || !currentAge.equals(originalAge)
                || (!currentGender.equalsIgnoreCase(originalGender)
                && !(currentGender.equals("Select Gender") && originalGender.equalsIgnoreCase("unknown gender")));

        if (modificationDetected) {
            // Prompt user if they want to discard changes
            int response = JOptionPane.showConfirmDialog(rootPane,
                    "Unsaved changes detected. Do you want to discard the changes?",
                    "Discard Changes",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                ManageStaff manageStaffPage = new ManageStaff();
                manageStaffPage.setEmail(userLabel.getText());
                manageStaffPage.setVisible(true);
                this.dispose(); // Close the current window
            } else {
                // User chose not to discard, remain on the same page
                JOptionPane.showMessageDialog(rootPane, "Modification restored. You remain continued on the current page.", "Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            ManageStaff manageStaffPage = new ManageStaff();
            manageStaffPage.setEmail(userLabel.getText());
            manageStaffPage.setVisible(true);
            this.dispose(); // Close the current window
        }
    }//GEN-LAST:event_staffManagementLinkMouseClicked

    private void userManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userManagementLinkMouseClicked
        // Retrieve the current form values
        String currentUsername = usernameField.getText();
        String currentPhoneNumber = phoneNumField.getText();
        String currentAge = ageField.getText();
        String currentGender = (String) genderComboBox.getSelectedItem();

        // Check if any modification was made by comparing with original values
        boolean modificationDetected = !currentUsername.equals(originalUsername)
                || !currentPhoneNumber.equals(originalPhoneNumber)
                || !currentAge.equals(originalAge)
                || (!currentGender.equalsIgnoreCase(originalGender)
                && !(currentGender.equals("Select Gender") && originalGender.equalsIgnoreCase("unknown gender")));

        if (modificationDetected) {
            // Prompt user if they want to discard changes
            int response = JOptionPane.showConfirmDialog(rootPane,
                    "Unsaved changes detected. Do you want to discard the changes?",
                    "Discard Changes",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                ManageUser manageUser = new ManageUser();
                manageUser.setEmail(userLabel.getText());
                manageUser.setVisible(true);
                this.dispose();
            } else {
                // User chose not to discard, remain on the same page
                JOptionPane.showMessageDialog(rootPane, "Modification restored. You remain continued on the current page.", "Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            ManageUser manageUser = new ManageUser();
            manageUser.setEmail(userLabel.getText());
            manageUser.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_userManagementLinkMouseClicked

    private void bookingsListLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingsListLinkMouseClicked
        // Retrieve the current form values
        String currentUsername = usernameField.getText();
        String currentPhoneNumber = phoneNumField.getText();
        String currentAge = ageField.getText();
        String currentGender = (String) genderComboBox.getSelectedItem();

        // Check if any modification was made by comparing with original values
        boolean modificationDetected = !currentUsername.equals(originalUsername)
                || !currentPhoneNumber.equals(originalPhoneNumber)
                || !currentAge.equals(originalAge)
                || (!currentGender.equalsIgnoreCase(originalGender)
                && !(currentGender.equals("Select Gender") && originalGender.equalsIgnoreCase("unknown gender")));

        if (modificationDetected) {
            // Prompt user if they want to discard changes
            int response = JOptionPane.showConfirmDialog(rootPane,
                    "Unsaved changes detected. Do you want to discard the changes?",
                    "Discard Changes",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                ManageBooking manageBooking = new ManageBooking();
                manageBooking.setEmail(getEmail());
                manageBooking.setVisible(true);
                this.dispose();
            } else {
                // User chose not to discard, remain on the same page
                JOptionPane.showMessageDialog(rootPane, "Modification restored. You remain continued on the current page.", "Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            ManageBooking manageBooking = new ManageBooking();
            manageBooking.setEmail(getEmail());
            manageBooking.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_bookingsListLinkMouseClicked

    private void pfpManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfpManagementLinkMouseClicked
         // Retrieve the current form values
        String currentUsername = usernameField.getText();
        String currentPhoneNumber = phoneNumField.getText();
        String currentAge = ageField.getText();
        String currentGender = (String) genderComboBox.getSelectedItem();

        // Check if any modification was made by comparing with original values
        boolean modificationDetected = !currentUsername.equals(originalUsername)
                || !currentPhoneNumber.equals(originalPhoneNumber)
                || !currentAge.equals(originalAge)
                || (!currentGender.equalsIgnoreCase(originalGender)
                && !(currentGender.equals("Select Gender") && originalGender.equalsIgnoreCase("unknown gender")));

        if (modificationDetected) {
            // Prompt user if they want to discard changes
            int response = JOptionPane.showConfirmDialog(rootPane,
                    "Unsaved changes detected. Do you want to discard the changes?",
                    "Discard Changes",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                ProfileManagement pfp = new ProfileManagement();
                pfp.setEmail(getEmail());
                pfp.setVisible(true);
                this.dispose();
            } else {
                // User chose not to discard, remain on the same page
                JOptionPane.showMessageDialog(rootPane, "Modification restored. You remain continued on the current page.", "Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            ProfileManagement pfp = new ProfileManagement();
            pfp.setEmail(getEmail());
            pfp.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_pfpManagementLinkMouseClicked

    private void exitLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLinkMouseClicked
         // Retrieve the current form values
        String currentUsername = usernameField.getText();
        String currentPhoneNumber = phoneNumField.getText();
        String currentAge = ageField.getText();
        String currentGender = (String) genderComboBox.getSelectedItem();

        // Check if any modification was made by comparing with original values
        boolean modificationDetected = !currentUsername.equals(originalUsername)
                || !currentPhoneNumber.equals(originalPhoneNumber)
                || !currentAge.equals(originalAge)
                || (!currentGender.equalsIgnoreCase(originalGender)
                && !(currentGender.equals("Select Gender") && originalGender.equalsIgnoreCase("unknown gender")));

        if (modificationDetected) {
            // Prompt user if they want to discard changes
            int response = JOptionPane.showConfirmDialog(rootPane,
                    "Unsaved changes detected. Do you want to discard the changes?",
                    "Discard Changes",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "You have successfully logout from your account!");
                this.dispose();
                System.exit(0);
            } else {
                // User chose not to discard, remain on the same page
                JOptionPane.showMessageDialog(rootPane, "Modification restored. You remain continued on the current page.", "Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You have successfully logout from your account!");
            this.dispose();
            System.exit(0);
        }
    }//GEN-LAST:event_exitLinkMouseClicked

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        InfoPanelOperation operation = new InfoPanelOperation();
        operation.dataModification();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        // Retrieve the current form values
        String currentUsername = usernameField.getText();
        String currentPhoneNumber = phoneNumField.getText();
        String currentAge = ageField.getText();
        String currentGender = (String) genderComboBox.getSelectedItem();

        // Check if any modification was made by comparing with original values
        boolean modificationDetected = !currentUsername.equals(originalUsername)
                || !currentPhoneNumber.equals(originalPhoneNumber)
                || !currentAge.equals(originalAge)
                || (!currentGender.equalsIgnoreCase(originalGender)
                && !(currentGender.equals("Select Gender") && originalGender.equalsIgnoreCase("unknown gender")));

        if (modificationDetected) {
            // Prompt user if they want to discard changes
            int response = JOptionPane.showConfirmDialog(rootPane,
                    "Unsaved changes detected. Do you want to discard the changes?",
                    "Discard Changes",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                // User chose to discard changes and proceed to ManageStaff
                ManageStaff manageStaff = new ManageStaff();
                manageStaff.setEmail(userLabel.getText());
                manageStaff.setVisible(true);
                this.dispose();
            } else {
                // User chose not to discard, remain on the same page
                JOptionPane.showMessageDialog(rootPane, "Modification restored. You remain continued on the current page.", "Modification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // No modification detected, directly navigate to ManageStaff
            ManageStaff manageStaff = new ManageStaff();
            manageStaff.setEmail(userLabel.getText());
            manageStaff.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_returnButtonActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void clearIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearIconMouseClicked
        usernameField.setText("");
        genderComboBox.setSelectedIndex(0);
        phoneNumField.setText("");
        ageField.setText("");
        passwordField.setText("");
        confirmPassField.setText("");
    }//GEN-LAST:event_clearIconMouseClicked

    private void savePassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePassButtonActionPerformed
        passwordPanelOperation pass = new passwordPanelOperation();
        pass.dataModification();
    }//GEN-LAST:event_savePassButtonActionPerformed

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
            java.util.logging.Logger.getLogger(EditStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditStaff().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChangePassTitle;
    private javax.swing.JTextField ageField;
    private javax.swing.JLabel ageTitle2;
    private javax.swing.JLabel bookHallicon;
    private javax.swing.JLabel bookingsListLink;
    private javax.swing.JLabel checkReservationIcon;
    private javax.swing.JLabel clearIcon;
    private javax.swing.JLabel companyLogo;
    private javax.swing.JPasswordField confirmPassField;
    private javax.swing.JLabel confirmPassTitle;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailTitle2;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel exitLink;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel genderTitle2;
    private javax.swing.JLabel homeNaviIcon;
    private javax.swing.JLabel homeNaviLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel leftMenu;
    private javax.swing.JLabel pageTitle2;
    private javax.swing.JPanel passPanel;
    private javax.swing.JLabel passTitle;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel pfpManagementIcon;
    private javax.swing.JLabel pfpManagementLink;
    private javax.swing.JTextField phoneNumField;
    private javax.swing.JLabel phoneNumTitle2;
    private javax.swing.JLabel refundIcon;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton savePassButton;
    private javax.swing.JLabel staffManagementLink;
    private javax.swing.JButton updateBtn;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userManagementLink;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameTitle2;
    // End of variables declaration//GEN-END:variables
}
