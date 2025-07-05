/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Administrator;

import Users.FileOperation;
import java.awt.Color;
import java.awt.Component;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ASUS TUF
 */
public class ManageUser extends javax.swing.JFrame {

    /**
     * Creates new form ManageUser
     */
    private String currentFilter = "All";
    private String email;
    
    public void setEmail(String email) {
        this.email = email;
        userLabel.setText(email);
    }
    
    public String getEmail() {
        return userLabel.getText();
    }
    
    public ManageUser() {
        initComponents();
        this.setLocationRelativeTo(null);
        blockOrUnblockCustomerBtn.setVisible(false);
        blockOrUnblockSchedulerBtn.setVisible(false);
        blockOrUnblockManagerBtn.setVisible(false);
        generateTable();
    }
    
     public void generateTable() {
        String columns[] = {"Name", "Gender", "Age", "Contact No.", "Email Address", "Role", "Status"};
        DefaultTableModel customerModel = new DefaultTableModel(columns, 0);
        
        String schedulerColumns[] = {"Name", "Gender", "Age", "Contact No.", "Email Address", "Role", "Status"};
        DefaultTableModel schedulerModel = new DefaultTableModel(schedulerColumns, 0);
        
        String managerColumns[] = {"Name", "Gender", "Age", "Contact No.", "Email Address", "Role", "Status"};
        DefaultTableModel managerModel = new DefaultTableModel(managerColumns, 0);

        FileOperation file = new FileOperation();
        file.setFile("resources/database/users.txt");
        file.checkFilePath();
        file.retrieveData();

        ArrayList<String> userRecords = file.getRetrievedData();

         for (String record : userRecords) {
             String[] details = record.split(",");

             String name = details[1];
             String email = details[0];
             String gender = details[5];
             int age = Integer.parseInt(details[6]);
             String role = details[4];
             String contactNo = details[2];
             String status = details[7];

             if (role.equalsIgnoreCase("Customer")) {
                 boolean shouldAdd = false;
                 if (currentFilter.equals("All")) {
                     shouldAdd = true;
                 } else if (currentFilter.equals("Active")) {
                     shouldAdd = status.toLowerCase().equals("active");
                 } else if (currentFilter.equals("Blocked")) {
                     shouldAdd = status.toLowerCase().equals("blocked");
                 }

                 if (shouldAdd) {
                     customerModel.addRow(new Object[]{name, gender, age, contactNo, email, role, status});
                 }
             }

             // Populate schedulerTable with schedulers
             if (role.equalsIgnoreCase("Scheduler")) {
                 schedulerModel.addRow(new Object[]{name, gender, age, contactNo, email, role, status});
             }
             
             // Populate schedulerTable with schedulers
             if (role.equalsIgnoreCase("Manager")) {
                 managerModel.addRow(new Object[]{name, gender, age, contactNo, email, role, status});
             }
         }

        customerTable.setModel(customerModel);
        schedulerTable.setModel(schedulerModel);
        managerTable.setModel(managerModel);

        // Hide the "Status" column
        customerTable.getColumnModel().getColumn(6).setMinWidth(0);
        customerTable.getColumnModel().getColumn(6).setMaxWidth(0);
        customerTable.getColumnModel().getColumn(6).setWidth(0);
        
        schedulerTable.getColumnModel().getColumn(6).setMinWidth(0);
        schedulerTable.getColumnModel().getColumn(6).setMaxWidth(0);
        schedulerTable.getColumnModel().getColumn(6).setWidth(0);
        
        managerTable.getColumnModel().getColumn(6).setMinWidth(0);
        managerTable.getColumnModel().getColumn(6).setMaxWidth(0);
        managerTable.getColumnModel().getColumn(6).setWidth(0);
        
        // Set column widths
        int[] columnWidths = {140, 110, 30, 100, 180, 100, 56}; // Total = 736

        for (int i = 0; i < columns.length; i++) {
            customerTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            schedulerTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            managerTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Apply custom renderer to highlight blocked rows
        customerTable.setDefaultRenderer(Object.class, new StatusCellRenderer());
        schedulerTable.setDefaultRenderer(Object.class, new StatusCellRenderer());
        managerTable.setDefaultRenderer(Object.class, new StatusCellRenderer());
    }
     
     public class StatusCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Assuming the "Status" column is at index 6
        String status = table.getValueAt(row, 6).toString();

        // Set row color based on the "Status" column value
        if (status.equalsIgnoreCase("blocked")) {
            cell.setBackground(Color.RED);   // Set background to red if blocked
            cell.setForeground(Color.WHITE);
        } else {
            cell.setBackground(Color.WHITE); // Default background for other statuses
            cell.setForeground(Color.BLACK);
        }

       // Keep default selection highlight if row is selected
        if (isSelected) {
            cell.setBackground(table.getSelectionBackground()); // Default selection background
            cell.setForeground(table.getSelectionForeground()); // Default selection text color
        }

        return cell;
    }
}
     
      private String getNewStatus(String currentStatus) {
        switch (currentStatus.toLowerCase()) {
            case "active":
                return "blocked";
            case "blocked":
                return "active";
            default:
                return null;
        }
    }

    private void updateFileStatus(String email, String currentStatus) {
        FileOperation file = new FileOperation();
        file.setFile("resources/database/users.txt");
        file.checkFilePath();
        file.retrieveData();
        ArrayList<String> records = file.getRetrievedData();

        boolean updated = false;
        String newStatus = currentStatus.equalsIgnoreCase("active") ? "active" : "blocked"; // Determine the new status
        String role = "";

        for (int i = 0; i < records.size(); i++) {
            String[] row = records.get(i).split(",");

            if (row[0].equals(email)) { // Email matches
                role = row[4];
                records.set(i, String.join(",", row[0], row[1], row[2], row[3], row[4], row[5], row[6], newStatus));
                updated = true;
                break;
            }
        }

        if (updated) {
            // Create a confirmation message based on the current status
            String action = currentStatus.equalsIgnoreCase("active") ? "unblock" : "block";
            int response = JOptionPane.showConfirmDialog(
                    rootPane,
                    "Are you sure you want to " + action + " this " + role + "?",
                    "Confirmation of Status Update",
                    JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                file.updateData(records);
                showInfo(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase() + " successfully " + (action.equals("block") ? "blocked" : "unblocked") + "!");
                generateTable(); // Refresh the table to reflect changes

                // Call appropriate method based on role
                if (role.equalsIgnoreCase("Customer")) {
                    handleNoSelectionCustomer();
                } else if (role.equalsIgnoreCase("Scheduler")) {
                    handleNoSelectionScheduler();
                } else if (role.equalsIgnoreCase("Manager")) {
                    handleNoSelectionManager();
                }
            } else {
                showInfo("Status update cancelled. No changes made.");
            }
        } else {
            showError("User not found. No changes made.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleNoSelectionCustomer() {
        // Hide the button if no row is selected
        blockOrUnblockCustomerBtn.setVisible(false);
    }
    
    private void handleNoSelectionScheduler() {
        // Hide the button if no row is selected
        blockOrUnblockSchedulerBtn.setVisible(false);
    }
    
    private void handleNoSelectionManager() {
        // Hide the button if no row is selected
        blockOrUnblockManagerBtn.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filterUserComboBox = new javax.swing.JComboBox<>();
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
        jPanel2 = new javax.swing.JPanel();
        pageTitle = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tabbedPaneCustom1 = new raven.tabbed.TabbedPaneCustom();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        printCustomerButton = new javax.swing.JLabel();
        blockOrUnblockCustomerBtn = new javax.swing.JButton();
        searchCustomerField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        clearCustomerFieldIcon = new javax.swing.JLabel();
        customerReturnBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        schedulerTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        printSchedulerButton = new javax.swing.JLabel();
        blockOrUnblockSchedulerBtn = new javax.swing.JButton();
        searchSchedulerField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        clearSchedulerFieldIcon = new javax.swing.JLabel();
        schedulerReturnBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        managerTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        printManagerButton = new javax.swing.JLabel();
        blockOrUnblockManagerBtn = new javax.swing.JButton();
        searchManagerField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        clearManagerFieldIcon = new javax.swing.JLabel();
        managerReturnBtn = new javax.swing.JButton();

        filterUserComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Active", "Blocked" }));
        filterUserComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterUserComboBoxActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("View & Filter & Block/Unblock Existing User Information");
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

        jPanel2.setBackground(new java.awt.Color(248, 246, 240));

        pageTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        pageTitle.setText("User Management");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/group.png"))); // NOI18N

        tabbedPaneCustom1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tabbedPaneCustom1.setSelectedColor(new java.awt.Color(172, 225, 199));

        jPanel3.setBackground(new java.awt.Color(248, 246, 240));

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Gender", "Age", "Contact No.", "Email Address", "Role", "Status"
            }
        ));
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(customerTable);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("*Please select a customer from the table below to block/unblock");

        printCustomerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/printer.png"))); // NOI18N
        printCustomerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printCustomerButtonMouseClicked(evt);
            }
        });

        blockOrUnblockCustomerBtn.setBackground(new java.awt.Color(255, 0, 0));
        blockOrUnblockCustomerBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        blockOrUnblockCustomerBtn.setForeground(new java.awt.Color(255, 255, 255));
        blockOrUnblockCustomerBtn.setText("Block");
        blockOrUnblockCustomerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockOrUnblockCustomerBtnActionPerformed(evt);
            }
        });

        searchCustomerField.setToolTipText("Keyword Search Field");
        searchCustomerField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchCustomerFieldKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/keyword.png"))); // NOI18N
        jLabel5.setToolTipText("Search Keywords");

        clearCustomerFieldIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/clean.png"))); // NOI18N
        clearCustomerFieldIcon.setToolTipText("Clear Search");
        clearCustomerFieldIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearCustomerFieldIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearCustomerFieldIconMouseClicked(evt);
            }
        });

        customerReturnBtn.setBackground(new java.awt.Color(85, 85, 85));
        customerReturnBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerReturnBtn.setForeground(new java.awt.Color(255, 255, 255));
        customerReturnBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/return.png"))); // NOI18N
        customerReturnBtn.setText("Return");
        customerReturnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerReturnBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(customerReturnBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(blockOrUnblockCustomerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(searchCustomerField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearCustomerFieldIcon)
                        .addGap(317, 317, 317)
                        .addComponent(printCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(printCustomerButton)
                    .addComponent(searchCustomerField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(clearCustomerFieldIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blockOrUnblockCustomerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerReturnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Customer", jPanel3);

        jPanel4.setBackground(new java.awt.Color(248, 246, 240));

        schedulerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Gender", "Age", "Contact No.", "Email Address", "Role", "Status"
            }
        ));
        schedulerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                schedulerTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(schedulerTable);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("*Please select a scheduler from the table below to block/unblock");

        printSchedulerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/printer.png"))); // NOI18N
        printSchedulerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printSchedulerButtonMouseClicked(evt);
            }
        });

        blockOrUnblockSchedulerBtn.setBackground(new java.awt.Color(255, 0, 0));
        blockOrUnblockSchedulerBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        blockOrUnblockSchedulerBtn.setForeground(new java.awt.Color(255, 255, 255));
        blockOrUnblockSchedulerBtn.setText("Block");
        blockOrUnblockSchedulerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockOrUnblockSchedulerBtnActionPerformed(evt);
            }
        });

        searchSchedulerField.setToolTipText("Keyword Search Field");
        searchSchedulerField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchSchedulerFieldKeyReleased(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/keyword.png"))); // NOI18N
        jLabel10.setToolTipText("Search Keywords");

        clearSchedulerFieldIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/clean.png"))); // NOI18N
        clearSchedulerFieldIcon.setToolTipText("Clear Search");
        clearSchedulerFieldIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearSchedulerFieldIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearSchedulerFieldIconMouseClicked(evt);
            }
        });

        schedulerReturnBtn.setBackground(new java.awt.Color(85, 85, 85));
        schedulerReturnBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        schedulerReturnBtn.setForeground(new java.awt.Color(255, 255, 255));
        schedulerReturnBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/return.png"))); // NOI18N
        schedulerReturnBtn.setText("Return");
        schedulerReturnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schedulerReturnBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(schedulerReturnBtn)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(blockOrUnblockSchedulerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(searchSchedulerField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(clearSchedulerFieldIcon)
                            .addGap(317, 317, 317)
                            .addComponent(printSchedulerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(printSchedulerButton)
                    .addComponent(clearSchedulerFieldIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchSchedulerField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blockOrUnblockSchedulerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(schedulerReturnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Scheduler", jPanel4);

        jPanel5.setBackground(new java.awt.Color(248, 246, 240));

        managerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Gender", "Age", "Contact No.", "Email Address", "Role", "Status"
            }
        ));
        managerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                managerTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(managerTable);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setText("*Please select a manager from the table below to block/unblock");

        printManagerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/printer.png"))); // NOI18N
        printManagerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printManagerButtonMouseClicked(evt);
            }
        });

        blockOrUnblockManagerBtn.setBackground(new java.awt.Color(255, 0, 0));
        blockOrUnblockManagerBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        blockOrUnblockManagerBtn.setForeground(new java.awt.Color(255, 255, 255));
        blockOrUnblockManagerBtn.setText("Block");
        blockOrUnblockManagerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockOrUnblockManagerBtnActionPerformed(evt);
            }
        });

        searchManagerField.setToolTipText("Keyword Search Field");
        searchManagerField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchManagerFieldKeyReleased(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/keyword.png"))); // NOI18N
        jLabel12.setToolTipText("Search Keywords");

        clearManagerFieldIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/clean.png"))); // NOI18N
        clearManagerFieldIcon.setToolTipText("Clear Search");
        clearManagerFieldIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearManagerFieldIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearManagerFieldIconMouseClicked(evt);
            }
        });

        managerReturnBtn.setBackground(new java.awt.Color(85, 85, 85));
        managerReturnBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        managerReturnBtn.setForeground(new java.awt.Color(255, 255, 255));
        managerReturnBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/return.png"))); // NOI18N
        managerReturnBtn.setText("Return");
        managerReturnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managerReturnBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(searchManagerField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearManagerFieldIcon)
                        .addGap(317, 317, 317)
                        .addComponent(printManagerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(managerReturnBtn)
                        .addGap(534, 534, 534)
                        .addComponent(blockOrUnblockManagerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(printManagerButton)
                    .addComponent(clearManagerFieldIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchManagerField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blockOrUnblockManagerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(managerReturnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Manager", jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(pageTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(pageTitle)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homeNaviLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeNaviLabelMouseClicked
        AdminDash home = new AdminDash();
        home.setSessionEmail(getEmail());
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homeNaviLabelMouseClicked

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

    private void exitLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLinkMouseClicked
        JOptionPane.showMessageDialog(null, "You have successfully logout from your account!");
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitLinkMouseClicked

    private void customerReturnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerReturnBtnActionPerformed
        AdminDash home = new AdminDash();
        home.setSessionEmail(getEmail());
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_customerReturnBtnActionPerformed

    private void blockOrUnblockCustomerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockOrUnblockCustomerBtnActionPerformed
        int selectedRow = customerTable.getSelectedRow();

        if (selectedRow == -1) {
            showError("Please select a customer to block or unblock.");
            return;
        }

        // Retrieve current status and email
        String currentStatus = (String) customerTable.getValueAt(selectedRow, 6); // Column 6 is "Status"
        String email = (String) customerTable.getValueAt(selectedRow, 4); // Column 4 is "Email Address"

        // Determine new status
        String newStatus = getNewStatus(currentStatus);

        if (newStatus == null) {
            showError("Invalid status value.");
            return;
        }

        // Update the status in the table and file
        updateFileStatus(email, newStatus);

    }//GEN-LAST:event_blockOrUnblockCustomerBtnActionPerformed

    private void searchCustomerFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchCustomerFieldKeyReleased
        DefaultTableModel obj = (DefaultTableModel) customerTable.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter(obj);
        customerTable.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(searchCustomerField.getText()));
        if (obj1.getViewRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No record found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_searchCustomerFieldKeyReleased

    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked
        int selectedRow = customerTable.getSelectedRow();

        if (selectedRow == -1) {
            handleNoSelectionCustomer();
            return;
        }
        
        // Retrieve the email and status of the selected customer
        String email = customerTable.getValueAt(selectedRow, 4).toString();
        String status = customerTable.getValueAt(selectedRow, 6).toString();

        // Display the block/unblock button
        blockOrUnblockCustomerBtn.setVisible(true);

        // Set button text and color based on current status
        if (status.equalsIgnoreCase("active")) {
            blockOrUnblockCustomerBtn.setText("Block");
            blockOrUnblockCustomerBtn.setBackground(new java.awt.Color(255, 0, 0)); // Red for block
        } else if (status.equalsIgnoreCase("blocked")) {
            blockOrUnblockCustomerBtn.setText("Unblock");
            blockOrUnblockCustomerBtn.setBackground(new java.awt.Color(0, 102, 0)); // Green for unblock
        }
    }//GEN-LAST:event_customerTableMouseClicked

    private void clearCustomerFieldIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearCustomerFieldIconMouseClicked
        // Clear the search field text
        searchCustomerField.setText("");

        // Clear the filter applied to the table
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        customerTable.setRowSorter(sorter);
        sorter.setRowFilter(null); // This clears the filter and shows all rows again
    }//GEN-LAST:event_clearCustomerFieldIconMouseClicked

    private void filterUserComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterUserComboBoxActionPerformed
        // Get the selected filter option from the combo box
        currentFilter = filterUserComboBox.getSelectedItem().toString();
        
        // Generate the table with the selected filter
        generateTable();
        
        handleNoSelectionCustomer();
    }//GEN-LAST:event_filterUserComboBoxActionPerformed

    private void printCustomerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printCustomerButtonMouseClicked
        // Create header and footer with the updated header text
        MessageFormat header = new MessageFormat("Registered Customers List");
        MessageFormat footer = new MessageFormat("Page {0, number, integer}");

        try {
            // Print the customer table with the updated header and footer
            customerTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_printCustomerButtonMouseClicked

    private void schedulerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_schedulerTableMouseClicked
        int selectedRow = schedulerTable.getSelectedRow();

        if (selectedRow == -1) {
            handleNoSelectionScheduler();
            return;
        }
        
        // Retrieve the email and status of the selected customer
        String email = schedulerTable.getValueAt(selectedRow, 4).toString();
        String status = schedulerTable.getValueAt(selectedRow, 6).toString();

        // Display the block/unblock button
        blockOrUnblockSchedulerBtn.setVisible(true);

        // Set button text and color based on current status
        if (status.equalsIgnoreCase("active")) {
            blockOrUnblockSchedulerBtn.setText("Block");
            blockOrUnblockSchedulerBtn.setBackground(new java.awt.Color(255, 0, 0)); // Red for block
        } else if (status.equalsIgnoreCase("blocked")) {
            blockOrUnblockSchedulerBtn.setText("Unblock");
            blockOrUnblockSchedulerBtn.setBackground(new java.awt.Color(0, 102, 0)); // Green for unblock
        }
    }//GEN-LAST:event_schedulerTableMouseClicked

    private void printSchedulerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printSchedulerButtonMouseClicked
        // Create header and footer with the updated header text
        MessageFormat header = new MessageFormat("Registered Schedulers List");
        MessageFormat footer = new MessageFormat("Page {0, number, integer}");

        try {
            // Print the customer table with the updated header and footer
            schedulerTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_printSchedulerButtonMouseClicked

    private void blockOrUnblockSchedulerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockOrUnblockSchedulerBtnActionPerformed
        int selectedRow = schedulerTable.getSelectedRow();

        if (selectedRow == -1) {
            showError("Please select a scheduler to block or unblock.");
            return;
        }

        // Retrieve current status and email
        String currentStatus = (String) schedulerTable.getValueAt(selectedRow, 6); // Column 6 is "Status"
        String email = (String) schedulerTable.getValueAt(selectedRow, 4); // Column 4 is "Email Address"

        // Determine new status
        String newStatus = getNewStatus(currentStatus);

        if (newStatus == null) {
            showError("Invalid status value.");
            return;
        }

        // Update the status in the table and file
        updateFileStatus(email, newStatus);
    }//GEN-LAST:event_blockOrUnblockSchedulerBtnActionPerformed

    private void searchSchedulerFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchSchedulerFieldKeyReleased
        DefaultTableModel obj = (DefaultTableModel) schedulerTable.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter(obj);
        schedulerTable.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(searchSchedulerField.getText()));
        if (obj1.getViewRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No record found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_searchSchedulerFieldKeyReleased

    private void clearSchedulerFieldIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearSchedulerFieldIconMouseClicked
        // Clear the search field text
        searchSchedulerField.setText("");

        // Clear the filter applied to the table
        DefaultTableModel model = (DefaultTableModel) schedulerTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        schedulerTable.setRowSorter(sorter);
        sorter.setRowFilter(null); // This clears the filter and shows all rows again
    }//GEN-LAST:event_clearSchedulerFieldIconMouseClicked

    private void managerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_managerTableMouseClicked
        int selectedRow = managerTable.getSelectedRow();

        if (selectedRow == -1) {
            handleNoSelectionManager();
            return;
        }
        
        // Retrieve the email and status of the selected customer
        String email = managerTable.getValueAt(selectedRow, 4).toString();
        String status = managerTable.getValueAt(selectedRow, 6).toString();

        // Display the block/unblock button
        blockOrUnblockManagerBtn.setVisible(true);

        // Set button text and color based on current status
        if (status.equalsIgnoreCase("active")) {
            blockOrUnblockManagerBtn.setText("Block");
            blockOrUnblockManagerBtn.setBackground(new java.awt.Color(255, 0, 0)); // Red for block
        } else if (status.equalsIgnoreCase("blocked")) {
            blockOrUnblockManagerBtn.setText("Unblock");
            blockOrUnblockManagerBtn.setBackground(new java.awt.Color(0, 102, 0)); // Green for unblock
        }
    }//GEN-LAST:event_managerTableMouseClicked

    private void printManagerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printManagerButtonMouseClicked
        // Create header and footer with the updated header text
        MessageFormat header = new MessageFormat("Registered Manager List");
        MessageFormat footer = new MessageFormat("Page {0, number, integer}");

        try {
            // Print the customer table with the updated header and footer
            managerTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_printManagerButtonMouseClicked

    private void blockOrUnblockManagerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockOrUnblockManagerBtnActionPerformed
        int selectedRow = managerTable.getSelectedRow();

        if (selectedRow == -1) {
            showError("Please select a manager to block or unblock.");
            return;
        }

        // Retrieve current status and email
        String currentStatus = (String) managerTable.getValueAt(selectedRow, 6); // Column 6 is "Status"
        String email = (String) managerTable.getValueAt(selectedRow, 4); // Column 4 is "Email Address"

        // Determine new status
        String newStatus = getNewStatus(currentStatus);

        if (newStatus == null) {
            showError("Invalid status value.");
            return;
        }

        // Update the status in the table and file
        updateFileStatus(email, newStatus);
    }//GEN-LAST:event_blockOrUnblockManagerBtnActionPerformed

    private void searchManagerFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchManagerFieldKeyReleased
        DefaultTableModel obj = (DefaultTableModel) managerTable.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter(obj);
        managerTable.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(searchManagerField.getText()));
        if (obj1.getViewRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No record found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_searchManagerFieldKeyReleased

    private void clearManagerFieldIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearManagerFieldIconMouseClicked
        // Clear the search field text
        searchManagerField.setText("");

        // Clear the filter applied to the table
        DefaultTableModel model = (DefaultTableModel) managerTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        managerTable.setRowSorter(sorter);
        sorter.setRowFilter(null); // This clears the filter and shows all rows again
    }//GEN-LAST:event_clearManagerFieldIconMouseClicked

    private void managerReturnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerReturnBtnActionPerformed
        AdminDash home = new AdminDash();
        home.setSessionEmail(getEmail());
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_managerReturnBtnActionPerformed

    private void schedulerReturnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schedulerReturnBtnActionPerformed
        AdminDash home = new AdminDash();
        home.setSessionEmail(getEmail());
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_schedulerReturnBtnActionPerformed

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
            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton blockOrUnblockCustomerBtn;
    private javax.swing.JButton blockOrUnblockManagerBtn;
    private javax.swing.JButton blockOrUnblockSchedulerBtn;
    private javax.swing.JLabel bookHallicon;
    private javax.swing.JLabel bookingsListLink;
    private javax.swing.JLabel checkReservationIcon;
    private javax.swing.JLabel clearCustomerFieldIcon;
    private javax.swing.JLabel clearManagerFieldIcon;
    private javax.swing.JLabel clearSchedulerFieldIcon;
    private javax.swing.JLabel companyLogo;
    private javax.swing.JButton customerReturnBtn;
    private javax.swing.JTable customerTable;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel exitLink;
    private javax.swing.JComboBox<String> filterUserComboBox;
    private javax.swing.JLabel homeNaviIcon;
    private javax.swing.JLabel homeNaviLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel leftMenu;
    private javax.swing.JButton managerReturnBtn;
    private javax.swing.JTable managerTable;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JLabel pfpManagementIcon;
    private javax.swing.JLabel pfpManagementLink;
    private javax.swing.JLabel printCustomerButton;
    private javax.swing.JLabel printManagerButton;
    private javax.swing.JLabel printSchedulerButton;
    private javax.swing.JLabel refundIcon;
    private javax.swing.JButton schedulerReturnBtn;
    private javax.swing.JTable schedulerTable;
    private javax.swing.JTextField searchCustomerField;
    private javax.swing.JTextField searchManagerField;
    private javax.swing.JTextField searchSchedulerField;
    private javax.swing.JLabel staffManagementLink;
    private raven.tabbed.TabbedPaneCustom tabbedPaneCustom1;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userManagementLink;
    // End of variables declaration//GEN-END:variables
}
