/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Administrator;

import Users.FileOperation;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS TUF
 */
public class ProfileManagement extends javax.swing.JFrame {

    /**
     * Creates new form ProfileManagement
     */
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        userLabel.setText(getEmail());
        emailLabel.setText(getEmail());
        setInformation();
    }
    
    public ProfileManagement() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void setInformation() {
        FileOperation file = new FileOperation();
        file.setFile("resources/database/users.txt");
        file.checkFilePath();
        file.retrieveData();
        ArrayList<String> records = file.getRetrievedData();
        
        for(String row : records) {
            String[] line = row.split(",");
            
            String emailFromFile = line[0];
            String usernameFromFile = line[1];
            String phoneFromFile = line[2];
            String ageFromFile = line[6];
            
            if(emailFromFile.equals(getEmail())) {
                usernameField.setText(usernameFromFile);
                phoneNumField.setText(phoneFromFile);
                ageField.setText(ageFromFile);
                break;
            }
        }
    }
    
    class InfoPanelOperation {
        protected String username;
        protected String phoneNumber;
        protected String gender;
        protected String formattedGender;
        protected String age;
        protected boolean proceedModification = false;
        
        public boolean checkInfoInput() {
            username = usernameField.getText();
            phoneNumber = phoneNumField.getText();
            gender = (String) genderComboBox.getSelectedItem();
            formattedGender = gender.toLowerCase();
            age = ageField.getText();
            
            if(username.isEmpty() || phoneNumber.isEmpty() || gender.isEmpty() || age.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please complete all required fields!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else {
                proceedModification = true;
            }
            return proceedModification;
        }
        
        public void dataModification() {
            boolean modification = checkInfoInput();
            boolean updated = false;
            if(modification) {
                FileOperation file = new FileOperation();
                file.setFile("resources/database/users.txt");
                file.checkFilePath();
                file.retrieveData();
                ArrayList<String> records = file.getRetrievedData();
                
                for(int i = 0; i < records.size(); i++) {
                    String[] row = records.get(i).split(",");
                    
                    String emailFromFile = row[0];
                    String passFromFile = row[3];
                    String roleFromFile = row[4];
                    String statusFromFile = row[7];
                    
                    if(emailFromFile.equals(getEmail())) {
                        records.set(i, getEmail() + "," + username + "," + phoneNumber + "," + passFromFile + "," + roleFromFile + "," + formattedGender + "," + age + "," + statusFromFile);
                        updated = true;
                        break;
                    }
                }
                
                
                
                if(updated) {
                    int response = JOptionPane.showConfirmDialog(rootPane, "Are you certain about saving your latest information?",  "Confirmation of Modification", JOptionPane.YES_NO_OPTION);
                    switch(response) {
                        case JOptionPane.YES_OPTION -> {
                            file.updateData(records);
                            JOptionPane.showMessageDialog(rootPane, "You have successfully updated you profile!", "Success Modification", JOptionPane.INFORMATION_MESSAGE);
                        }
                        case JOptionPane.NO_OPTION -> JOptionPane.showMessageDialog(rootPane, "Modification Paused.");
                        case JOptionPane.CANCEL_OPTION -> JOptionPane.showMessageDialog(rootPane, "Modification Paused.");
                    }
                }
            }
        }
    }
    
    class passwordPanelOperation extends InfoPanelOperation {
        protected String newPassword;
        protected String passwordConfirmation;
        protected boolean changePassword = false;
        
        @Override
        public boolean checkInfoInput() {
            newPassword = passwordField.getText();
            passwordConfirmation = confirmPassField.getText();
            
            if(newPassword.isEmpty() || passwordConfirmation.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Please enter new password to be changed!", "Invalid Password Input", JOptionPane.ERROR_MESSAGE);
            } else {
                if(passwordConfirmation.equals(newPassword)) {
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
            
            if(modification) {
                FileOperation file = new FileOperation();
                file.setFile("resources/database/users.txt");
                file.checkFilePath();
                file.retrieveData();
                ArrayList<String> records = file.getRetrievedData();
                
                for(int i = 0; i < records.size(); i++) {
                    String[] row = records.get(i).split(",");
                    
                    String emailFromFile = row[0];
                    String usernameFromFile = row[1];
                    String phoneFromFile = row[2];
                    String roleFromFile = row[4];
                    String genderFromFile = row[5];
                    String ageFromFile = row[6];
                    String statusFromFile = row[7];
                    
                    if(emailFromFile.equals(getEmail())) {
                        records.set(i, getEmail() + "," + usernameFromFile + "," + phoneFromFile + "," + passwordConfirmation + "," + roleFromFile + "," + genderFromFile + "," + ageFromFile + "," + statusFromFile);
                        updatedPass = true;
                        break;
                    }
                }
                
                
                
                if(updatedPass) {
                    int response = JOptionPane.showConfirmDialog(rootPane, "Are you certain about changing your password?",  "Confirmation of Changing Password", JOptionPane.YES_NO_OPTION);
                    switch(response) {
                        case JOptionPane.YES_OPTION -> {
                            file.updateData(records);
                            JOptionPane.showMessageDialog(rootPane, "You have successfully changed your password!", "Success Password Change", JOptionPane.INFORMATION_MESSAGE);
                        }
                        case JOptionPane.NO_OPTION -> JOptionPane.showMessageDialog(rootPane, "Action Paused.");
                        case JOptionPane.CANCEL_OPTION -> JOptionPane.showMessageDialog(rootPane, "Action Paused.");
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
        homePageLink = new javax.swing.JLabel();
        staffManagementLink = new javax.swing.JLabel();
        userManagementLink = new javax.swing.JLabel();
        companyLogo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        staffIcon = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        bookingsListLink = new javax.swing.JLabel();
        pfpManagementLink = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        rightMenu = new javax.swing.JPanel();
        pageTitle = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        emailTitle = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        emailLabel = new javax.swing.JLabel();
        usernameTitle = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        phoneNumTitle = new javax.swing.JLabel();
        phoneNumField = new javax.swing.JTextField();
        genderTitle = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        ageTitle = new javax.swing.JLabel();
        ageField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        passPanel = new javax.swing.JPanel();
        ChangePassTitle = new javax.swing.JLabel();
        passTitle = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmPassTitle = new javax.swing.JLabel();
        confirmPassField = new javax.swing.JPasswordField();
        savePassButton = new javax.swing.JButton();
        homeIcon = new javax.swing.JLabel();
        clearIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reset/Modify Admin Information (Current Logged Admin))");
        setResizable(false);

        leftMenu.setBackground(new java.awt.Color(172, 225, 199));
        leftMenu.setPreferredSize(new java.awt.Dimension(220, 620));

        homePageLink.setText("Home Page");
        homePageLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homePageLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homePageLinkMouseClicked(evt);
            }
        });

        staffManagementLink.setText("Staff Management");
        staffManagementLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        staffManagementLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffManagementLinkMouseClicked(evt);
            }
        });

        userManagementLink.setText("User Management");
        userManagementLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userManagementLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userManagementLinkMouseClicked(evt);
            }
        });

        companyLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/city-hall-small.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setText("Hall Symphony Inc.");

        userLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userLabel.setText("example@hotmail.com");

        jSeparator1.setPreferredSize(new java.awt.Dimension(65, 10));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/home.png"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel8.setBackground(new java.awt.Color(153, 153, 153));
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("General");

        staffIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/account-tie.png"))); // NOI18N
        staffIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/account-group.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/table-eye.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/logout.png"))); // NOI18N
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/user.png"))); // NOI18N
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Settings and Others");

        bookingsListLink.setText("Bookings List");
        bookingsListLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookingsListLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingsListLinkMouseClicked(evt);
            }
        });

        pfpManagementLink.setText("Profile Management");
        pfpManagementLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pfpManagementLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pfpManagementLinkMouseClicked(evt);
            }
        });

        jLabel19.setText("Log Out");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout leftMenuLayout = new javax.swing.GroupLayout(leftMenu);
        leftMenu.setLayout(leftMenuLayout);
        leftMenuLayout.setHorizontalGroup(
            leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(pfpManagementLink))
                    .addComponent(jLabel16)
                    .addComponent(companyLogo)
                    .addComponent(jLabel2)
                    .addComponent(userLabel)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(homePageLink))
                    .addComponent(jLabel8)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(staffIcon)
                        .addGap(18, 18, 18)
                        .addComponent(staffManagementLink))
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(userManagementLink))
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(bookingsListLink))
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)))
                .addGap(28, 28, 28))
        );
        leftMenuLayout.setVerticalGroup(
            leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenuLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(companyLogo)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addGap(7, 7, 7)
                        .addComponent(userLabel)
                        .addGap(7, 7, 7)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel9))
                    .addComponent(homePageLink))
                .addGap(33, 33, 33)
                .addComponent(jLabel8)
                .addGap(13, 13, 13)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffIcon)
                    .addComponent(staffManagementLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftMenuLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(bookingsListLink))
                        .addGap(35, 35, 35)
                        .addComponent(jLabel16))
                    .addComponent(userManagementLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(pfpManagementLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rightMenu.setBackground(new java.awt.Color(248, 246, 240));
        rightMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pageTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        pageTitle.setText("Profile Management");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/boy.png"))); // NOI18N

        infoPanel.setBackground(new java.awt.Color(85, 85, 85));
        infoPanel.setPreferredSize(new java.awt.Dimension(342, 417));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Update Profile Information");

        emailTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailTitle.setForeground(new java.awt.Color(255, 255, 255));
        emailTitle.setText("Email Address");

        emailLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("Current Email");

        usernameTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        usernameTitle.setForeground(new java.awt.Color(255, 255, 255));
        usernameTitle.setText("Username");

        usernameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        phoneNumTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        phoneNumTitle.setForeground(new java.awt.Color(255, 255, 255));
        phoneNumTitle.setText("Phone Number");

        phoneNumField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        genderTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        genderTitle.setForeground(new java.awt.Color(255, 255, 255));
        genderTitle.setText("Gender");

        genderComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        genderComboBox.setForeground(new java.awt.Color(255, 255, 255));
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Prefer not to say" }));

        ageTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        ageTitle.setForeground(new java.awt.Color(255, 255, 255));
        ageTitle.setText("Age");

        ageField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        saveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        saveButton.setText("Save Information");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(phoneNumField)
                    .addComponent(usernameField)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator2)
                    .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emailTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameTitle)
                    .addComponent(phoneNumTitle)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genderTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(ageTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65))
                            .addComponent(ageField))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(emailTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(emailLabel)
                .addGap(4, 4, 4)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usernameTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phoneNumTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderTitle)
                    .addComponent(ageTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

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

        savePassButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        savePassButton.setText("Save Password");
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
                .addGroup(passPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(passPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(savePassButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, passPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(passPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .addComponent(confirmPassField)
                            .addComponent(ChangePassTitle)
                            .addComponent(passTitle)
                            .addComponent(confirmPassTitle))))
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
                .addGap(28, 28, 28)
                .addComponent(savePassButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        homeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/home.png"))); // NOI18N
        homeIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeIconMouseClicked(evt);
            }
        });

        clearIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/clean.png"))); // NOI18N
        clearIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout rightMenuLayout = new javax.swing.GroupLayout(rightMenu);
        rightMenu.setLayout(rightMenuLayout);
        rightMenuLayout.setHorizontalGroup(
            rightMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightMenuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(rightMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rightMenuLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(pageTitle)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(rightMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rightMenuLayout.createSequentialGroup()
                        .addComponent(clearIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(homeIcon))
                    .addComponent(passPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        rightMenuLayout.setVerticalGroup(
            rightMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightMenuLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(rightMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(rightMenuLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(rightMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(homeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearIcon)
                            .addComponent(pageTitle))))
                .addGap(18, 18, 18)
                .addGroup(rightMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(rightMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(rightMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leftMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homePageLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePageLinkMouseClicked
        AdminDash adminDash = new AdminDash();
        adminDash.setSessionEmail(getEmail());
        adminDash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homePageLinkMouseClicked

    private void staffManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffManagementLinkMouseClicked
        ManageStaff manageStaff = new ManageStaff();
        manageStaff.setEmail(userLabel.getText());
        manageStaff.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_staffManagementLinkMouseClicked

    private void userManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userManagementLinkMouseClicked
        ManageUser manageUser = new ManageUser();
        manageUser.setEmail(userLabel.getText());
        manageUser.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userManagementLinkMouseClicked

    private void bookingsListLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingsListLinkMouseClicked
        ManageBooking manageBooking = new ManageBooking();
        manageBooking.setEmail(getEmail());
        manageBooking.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bookingsListLinkMouseClicked

    private void pfpManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfpManagementLinkMouseClicked
        ProfileManagement pfp = new ProfileManagement();
        pfp.setEmail(getEmail());
        pfp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pfpManagementLinkMouseClicked

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        InfoPanelOperation operation = new InfoPanelOperation();
        operation.dataModification();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void savePassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePassButtonActionPerformed
        passwordPanelOperation pass = new passwordPanelOperation();
        pass.dataModification();
    }//GEN-LAST:event_savePassButtonActionPerformed

    private void homeIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeIconMouseClicked
        AdminDash db = new AdminDash();
        db.setSessionEmail(getEmail());
        db.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homeIconMouseClicked

    private void clearIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearIconMouseClicked
        usernameField.setText("");
        phoneNumField.setText("");
        ageField.setText("");
        passwordField.setText("");
        confirmPassField.setText("");
    }//GEN-LAST:event_clearIconMouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        JOptionPane.showMessageDialog(null, "You have successfully logout from your account!");
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jLabel19MouseClicked

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
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProfileManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChangePassTitle;
    private javax.swing.JTextField ageField;
    private javax.swing.JLabel ageTitle;
    private javax.swing.JLabel bookingsListLink;
    private javax.swing.JLabel clearIcon;
    private javax.swing.JLabel companyLogo;
    private javax.swing.JPasswordField confirmPassField;
    private javax.swing.JLabel confirmPassTitle;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailTitle;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel genderTitle;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel homePageLink;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel leftMenu;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JPanel passPanel;
    private javax.swing.JLabel passTitle;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel pfpManagementLink;
    private javax.swing.JTextField phoneNumField;
    private javax.swing.JLabel phoneNumTitle;
    private javax.swing.JPanel rightMenu;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton savePassButton;
    private javax.swing.JLabel staffIcon;
    private javax.swing.JLabel staffManagementLink;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userManagementLink;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameTitle;
    // End of variables declaration//GEN-END:variables
}
