/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Administrator;

import Users.FileOperation;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS TUF
 */
// Abstract class for operations
abstract class Operation {
    protected String username;
    protected String contactNo;
    protected String email;
    protected String gender;
    protected String formattedGender;
    protected String age;
    protected String newPassword;
    protected String passwordConfirmation;
    protected boolean proceed = false;

    // Abstract method that all subclasses must implement
    public abstract void execute();

    // Method for validation (shared across all operations)
    public void validation() {
        // Validate basic fields
        if (username.isEmpty() || gender.equals("Select Gender") || age.isEmpty() || contactNo.isEmpty() || email.isEmpty() || newPassword.isEmpty() || passwordConfirmation.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Oops, empty value found! Please complete all required fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } else if (!passwordCheck(newPassword, passwordConfirmation)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match! Please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } else if (!isValidAge(age)) {
            JOptionPane.showMessageDialog(null, "Age must be a valid positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Email format is invalid. Please enter a valid email address.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } else {
            proceed = true;  // Mark as ready to proceed
        }
    }

    // Helper method to check if two passwords match
    public boolean passwordCheck(String variable1, String variable2) {
        return variable1.equals(variable2);
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
    
    // Helper method to validate email using regex
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    // Getter to check if validation passed
    public boolean getProceedConfirmation() {
        return proceed;
    }
}

// Concrete class extending the abstract Operation class
class FileAction extends Operation {
    private JFrame currentWindow;
    private JLabel userLabel; // Add reference to userLabel

    public FileAction(JFrame currentWindow, JLabel userLabel) {
        this.currentWindow = currentWindow;
        this.userLabel = userLabel;
    }

    // Implementation of the abstract execute method
    @Override
    public void execute() {
        if (getProceedConfirmation()) {
            FileOperation file = new FileOperation();
            file.setFile("resources/database/users.txt");
            file.checkFilePath();
            file.retrieveData();
            file.appendData(email + "," + username + "," + contactNo + "," + passwordConfirmation + ",scheduler," + formattedGender + "," + age + ",active");

            // Success message and navigation
            JOptionPane.showMessageDialog(null, "New scheduler has been successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
            navigateToManageStaff();
        }
    }

    // Method to navigate to the ManageStaff page (can be reused in other operations)
    private void navigateToManageStaff() {
        ManageStaff manageStaffPage = new ManageStaff();
        manageStaffPage.setEmail(userLabel.getText());
        manageStaffPage.setVisible(true);
        currentWindow.dispose(); // Close the current window
    }
}

public class AddStaff extends javax.swing.JFrame {

    /**
     * Creates new form AddStaff
     */
    private String email;
    
    // Variables to store initial values for modification check
    private String originalUsername;
    private String originalGender;
    private String originalAge;
    private String originalContactNo;
    private String originalEmail;
    private String originalPassword;
    
    // Initialize original field values to check for modifications
    private void initializeOriginalValues() {
        originalUsername = usernameField.getText();
        originalGender = genderComboBox.getSelectedItem().toString().toLowerCase();
        originalAge = ageField.getText();
        originalContactNo = phoneNumField.getText();
        originalEmail = emailAddressField.getText();
        originalPassword = passwordField.getText();
    }
    
    private boolean isFormModified() {
        return !usernameField.getText().equals(originalUsername) ||
               !genderComboBox.getSelectedItem().toString().toLowerCase().equals(originalGender) ||
               !ageField.getText().equals(originalAge) ||
               !phoneNumField.getText().equals(originalContactNo) ||
               !emailAddressField.getText().equals(originalEmail) ||
               !passwordField.getText().equals(originalPassword) ||
               !confirmPassField.getText().equals(originalPassword);
    }
    
    public void setEmail(String email) {
        this.email = email;
        userLabel.setText(email);
    }
    
    public String getEmail() {
        return userLabel.getText();
    }
    
    public AddStaff() {
        initComponents();
        this.setLocationRelativeTo(null);
        initializeOriginalValues();
    }
    
    // Method to navigate to the ManageStaff page
    private void navigateToManageStaff() {
        ManageStaff manageStaffPage = new ManageStaff();
        manageStaffPage.setEmail(userLabel.getText());
        manageStaffPage.setVisible(true);
        this.dispose(); // Close the current window
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
        refundLink = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        exitIcon = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pfpManagementIcon = new javax.swing.JLabel();
        pfpManagementLink = new javax.swing.JLabel();
        exitLink = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pageTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        usernameTitle = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        genderTitle = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        ageField = new javax.swing.JTextField();
        phoneNumTitle = new javax.swing.JLabel();
        phoneNumField = new javax.swing.JTextField();
        emailTitle = new javax.swing.JLabel();
        emailAddressField = new javax.swing.JTextField();
        ageTitle = new javax.swing.JLabel();
        passTitle = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmPassTitle = new javax.swing.JLabel();
        confirmPassField = new javax.swing.JPasswordField();
        createBtn = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        clearIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add New Scheduler Staff");
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

        refundLink.setText("Bookings List");
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
                            .addComponent(refundLink))
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
                            .addComponent(refundLink)))
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
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(248, 246, 240));

        pageTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        pageTitle.setText("New Scheduler Registration");

        jPanel3.setBackground(new java.awt.Color(85, 85, 85));

        usernameTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        usernameTitle.setForeground(new java.awt.Color(255, 255, 255));
        usernameTitle.setText("Username");

        usernameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        genderTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        genderTitle.setForeground(new java.awt.Color(255, 255, 255));
        genderTitle.setText("Gender");

        genderComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        genderComboBox.setForeground(new java.awt.Color(255, 255, 255));
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Gender", "Male", "Female", "Prefer not to say" }));

        ageField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        phoneNumTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        phoneNumTitle.setForeground(new java.awt.Color(255, 255, 255));
        phoneNumTitle.setText("Phone Number");

        phoneNumField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        emailTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailTitle.setForeground(new java.awt.Color(255, 255, 255));
        emailTitle.setText("Email Address");

        emailAddressField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        ageTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        ageTitle.setForeground(new java.awt.Color(255, 255, 255));
        ageTitle.setText("Age");

        passTitle.setBackground(new java.awt.Color(255, 255, 255));
        passTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passTitle.setForeground(new java.awt.Color(255, 255, 255));
        passTitle.setText("New Password");

        confirmPassTitle.setBackground(new java.awt.Color(255, 255, 255));
        confirmPassTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        confirmPassTitle.setForeground(new java.awt.Color(255, 255, 255));
        confirmPassTitle.setText("Confirmation of New Password");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(emailAddressField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(phoneNumField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(usernameField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(genderTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(usernameTitle))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(ageTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(ageField))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneNumTitle)
                            .addComponent(emailTitle))
                        .addGap(174, 174, 174)))
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordField)
                    .addComponent(confirmPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passTitle)
                    .addComponent(confirmPassTitle))
                .addGap(57, 57, 57))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(usernameTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(genderTitle)
                            .addComponent(ageTitle))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(passTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(confirmPassTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phoneNumTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(emailTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailAddressField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        createBtn.setBackground(new java.awt.Color(172, 225, 199));
        createBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        createBtn.setText("Create");
        createBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBtnActionPerformed(evt);
            }
        });

        cancelButton.setBackground(new java.awt.Color(85, 85, 85));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/employee.png"))); // NOI18N

        clearIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/clean.png"))); // NOI18N
        clearIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelButton)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(586, 586, 586)
                                .addComponent(createBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(43, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(pageTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clearIcon))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(clearIcon)
                            .addComponent(pageTitle))
                        .addGap(15, 15, 15)))
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeNaviLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeNaviLabelMouseClicked

        if (isFormModified()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(null, 
                    "You have unsaved changes. Do you want to discard them?", 
                    "Confirm", JOptionPane.YES_NO_OPTION);

            // If user chooses 'Yes', discard changes and proceed
            if (result == JOptionPane.YES_OPTION) {
                AdminDash home = new AdminDash();
                home.setSessionEmail(getEmail());
                home.setVisible(true);
                this.dispose();
            }
        } else {
            AdminDash home = new AdminDash();
            home.setSessionEmail(getEmail());
            home.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_homeNaviLabelMouseClicked

    private void staffManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffManagementLinkMouseClicked
        if (isFormModified()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(null, 
                    "You have unsaved changes. Do you want to discard them?", 
                    "Confirm", JOptionPane.YES_NO_OPTION);

            // If user chooses 'Yes', discard changes and proceed
            if (result == JOptionPane.YES_OPTION) {
                navigateToManageStaff();
            }
        } else {
            // If no changes, directly navigate to ManageStaff
            navigateToManageStaff();
        }
    }//GEN-LAST:event_staffManagementLinkMouseClicked

    private void userManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userManagementLinkMouseClicked
        if (isFormModified()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(null, 
                    "You have unsaved changes. Do you want to discard them?", 
                    "Confirm", JOptionPane.YES_NO_OPTION);

            // If user chooses 'Yes', discard changes and proceed
            if (result == JOptionPane.YES_OPTION) {
                ManageUser manageUser = new ManageUser();
                manageUser.setEmail(userLabel.getText());
                manageUser.setVisible(true);
                this.dispose();
            }
        } else {
            ManageUser manageUser = new ManageUser();
            manageUser.setEmail(userLabel.getText());
            manageUser.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_userManagementLinkMouseClicked

    private void refundLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refundLinkMouseClicked
        if (isFormModified()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(null,
                    "You have unsaved changes. Do you want to discard them?",
                    "Confirm", JOptionPane.YES_NO_OPTION);

            // If user chooses 'Yes', discard changes and proceed
            if (result == JOptionPane.YES_OPTION) {
                ManageBooking manageBooking = new ManageBooking();
                manageBooking.setEmail(getEmail());
                manageBooking.setVisible(true);
                this.dispose();
            }
        } else {
            ManageBooking manageBooking = new ManageBooking();
            manageBooking.setEmail(getEmail());
            manageBooking.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_refundLinkMouseClicked

    private void pfpManagementLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pfpManagementLinkMouseClicked
        if (isFormModified()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(null,
                    "You have unsaved changes. Do you want to discard them?",
                    "Confirm", JOptionPane.YES_NO_OPTION);

            // If user chooses 'Yes', discard changes and proceed
            if (result == JOptionPane.YES_OPTION) {
                ProfileManagement pfp = new ProfileManagement();
                pfp.setEmail(getEmail());
                pfp.setVisible(true);
                this.dispose();
            }
        } else {
            ProfileManagement pfp = new ProfileManagement();
            pfp.setEmail(getEmail());
            pfp.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_pfpManagementLinkMouseClicked

    private void exitLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLinkMouseClicked
        if (isFormModified()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(null,
                    "You have unsaved changes. Do you want to discard them?",
                    "Confirm", JOptionPane.YES_NO_OPTION);

            // If user chooses 'Yes', discard changes and proceed
            if (result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "You have successfully logout from your account!");
                this.dispose();
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You have successfully logout from your account!");
            this.dispose();
            System.exit(0);
        }
    }//GEN-LAST:event_exitLinkMouseClicked

    private void createBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBtnActionPerformed
        FileAction action = new FileAction(this, userLabel);  // Polymorphic behavior
        action.username = usernameField.getText();
        action.gender = (String) genderComboBox.getSelectedItem();
        action.formattedGender = action.gender.toLowerCase();
        action.age = ageField.getText();
        action.contactNo = phoneNumField.getText();
        action.email = emailAddressField.getText();
        action.newPassword = passwordField.getText();
        action.passwordConfirmation = confirmPassField.getText();

        action.validation();                     // Perform validation
        action.execute();                        // Execute the operation
    }//GEN-LAST:event_createBtnActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (isFormModified()) {
            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(null, 
                    "You have unsaved changes. Do you want to discard them?", 
                    "Confirm", JOptionPane.YES_NO_OPTION);

            // If user chooses 'Yes', discard changes and proceed
            if (result == JOptionPane.YES_OPTION) {
                navigateToManageStaff();
            }
        } else {
            // If no changes, directly navigate to ManageStaff
            navigateToManageStaff();
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void clearIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearIconMouseClicked
        // Clear all form fields
        usernameField.setText("");
        genderComboBox.setSelectedIndex(0);
        ageField.setText("");
        phoneNumField.setText("");
        emailAddressField.setText("");
        passwordField.setText("");
        confirmPassField.setText("");
        
        initializeOriginalValues(); 
    }//GEN-LAST:event_clearIconMouseClicked

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
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddStaff().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ageField;
    private javax.swing.JLabel ageTitle;
    private javax.swing.JLabel bookHallicon;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel checkReservationIcon;
    private javax.swing.JLabel clearIcon;
    private javax.swing.JLabel companyLogo;
    private javax.swing.JPasswordField confirmPassField;
    private javax.swing.JLabel confirmPassTitle;
    private javax.swing.JButton createBtn;
    private javax.swing.JTextField emailAddressField;
    private javax.swing.JLabel emailTitle;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel exitLink;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel genderTitle;
    private javax.swing.JLabel homeNaviIcon;
    private javax.swing.JLabel homeNaviLabel;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel leftMenu;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JLabel passTitle;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel pfpManagementIcon;
    private javax.swing.JLabel pfpManagementLink;
    private javax.swing.JTextField phoneNumField;
    private javax.swing.JLabel phoneNumTitle;
    private javax.swing.JLabel refundIcon;
    private javax.swing.JLabel refundLink;
    private javax.swing.JLabel staffManagementLink;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userManagementLink;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameTitle;
    // End of variables declaration//GEN-END:variables
}
