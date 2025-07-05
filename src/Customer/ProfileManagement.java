/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Customer;

import Users.FileOperation;
import Users.LoginPage;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author User
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
                    
                    if(emailFromFile.equals(getEmail())) {
                        records.set(i, getEmail() + "," + username + "," + phoneNumber + "," + passFromFile + "," + roleFromFile + "," + formattedGender + "," + age);
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
                    
                    if(emailFromFile.equals(getEmail())) {
                        records.set(i, getEmail() + "," + usernameFromFile + "," + phoneFromFile + "," + passwordConfirmation + "," + roleFromFile + "," + genderFromFile + "," + ageFromFile);
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

        background = new javax.swing.JPanel();
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

        background.setBackground(new java.awt.Color(248, 246, 240));
        background.setPreferredSize(new java.awt.Dimension(980, 620));

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
                .addContainerGap(71, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(leftMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rightMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(leftMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void savePassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePassButtonActionPerformed
        passwordPanelOperation pass = new passwordPanelOperation();
        pass.dataModification();
    }//GEN-LAST:event_savePassButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        InfoPanelOperation operation = new InfoPanelOperation();
        operation.dataModification();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void clearIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearIconMouseClicked
        usernameField.setText("");
        phoneNumField.setText("");
        ageField.setText("");
        passwordField.setText("");
        confirmPassField.setText("");
    }//GEN-LAST:event_clearIconMouseClicked

    private void homeIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeIconMouseClicked
        Dashboard db = new Dashboard();
        db.setSessionEmail(getEmail());
        db.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homeIconMouseClicked

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

    private void checkReservationLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkReservationLinkMouseClicked
        CheckReservation cr = new CheckReservation();
        cr.setEmail(getEmail());
        cr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_checkReservationLinkMouseClicked

    private void refundLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refundLinkMouseClicked
        CancelReservation cr = new CancelReservation();
        cr.setEmail(getEmail());
        cr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_refundLinkMouseClicked

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
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfileManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JPanel background;
    private javax.swing.JLabel bookHallLink;
    private javax.swing.JLabel bookHallicon;
    private javax.swing.JLabel checkReservationIcon;
    private javax.swing.JLabel checkReservationLink;
    private javax.swing.JLabel clearIcon;
    private javax.swing.JLabel companyLogo;
    private javax.swing.JPasswordField confirmPassField;
    private javax.swing.JLabel confirmPassTitle;
    private javax.swing.JLabel contactUsIcon;
    private javax.swing.JLabel contactUsLink;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailTitle;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel exitLink;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel genderTitle;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel homeNaviIcon;
    private javax.swing.JLabel homeNaviLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel leftMenu;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JPanel passPanel;
    private javax.swing.JLabel passTitle;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel pfpManagementIcon;
    private javax.swing.JLabel pfpManagementLink;
    private javax.swing.JTextField phoneNumField;
    private javax.swing.JLabel phoneNumTitle;
    private javax.swing.JLabel refundIcon;
    private javax.swing.JLabel refundLink;
    private javax.swing.JPanel rightMenu;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton savePassButton;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameTitle;
    // End of variables declaration//GEN-END:variables
}
