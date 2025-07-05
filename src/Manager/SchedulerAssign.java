/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Manager;

import Users.FileOperation;
import Users.LoginPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class SchedulerAssign extends javax.swing.JFrame {

    /**
     * Creates new form SchedulerAssign
     */
    public SchedulerAssign() {
        initComponents();
        this.setLocationRelativeTo(null);
        generateTable();
        getScheduler();
        getHallDetails();
    }
    
    public void generateTable() {
        String columns[] = {"Name", "Hall ID", "Hall Type", "Date", "Remarks", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        FileOperation file = new FileOperation();
        file.setFile("resources/database/schedulerTask.txt");
        file.checkFilePath();
        file.retrieveData();

        ArrayList<String> scheduleRecords = file.getRetrievedData();
        for (String record : scheduleRecords) {
            String[] details = record.split(",");
            String name = details[0];
            String hallID = details[1];
            String hallType = details[2];
            String date = details[3];
            String remarks = details[4];
            String status = details[5];

            model.addRow(new Object[]{name, hallID, hallType, date, remarks, status});
        }

        schedTable.setModel(model);
    }
    
    public void getScheduler() {
        FileOperation file = new FileOperation();
        file.setFile("resources/database/users.txt");
        file.checkFilePath();
        file.retrieveData();
        ArrayList<String> schedulerRecords = file.getRetrievedData();
        
        for(String row : schedulerRecords) {
            String[] line = row.split(",");
            
            String emailFromFile = line[0];
            String roleFromFile = line[4];
            
            if(roleFromFile.equals("scheduler")) {
                schedulerComboBox.addItem(emailFromFile);
            }
        }
    }
    
    public void getHallDetails() {
        FileOperation file = new FileOperation();
        file.setFile("resources/database/hall.txt");
        file.checkFilePath();
        file.retrieveData();
        ArrayList<String> hallRecords = file.getRetrievedData();

        HashMap<String, String> hallMap = new HashMap<>();

        for (String row : hallRecords) {
            String[] line = row.split(",");
            String hallIDFromFile = line[0];   
            String hallTypeFromFile = line[1]; 

            IdComboBox.addItem(hallIDFromFile);
            hallMap.put(hallIDFromFile, hallTypeFromFile);
        }

        IdComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedHallID = (String) IdComboBox.getSelectedItem();

                String selectedHallType = hallMap.get(selectedHallID);

                htype.setText(selectedHallType);
            }
        });
    }
    
    class UserInput {
        protected String schedulerEmail;
        protected String hallID;
        protected String hallType;
        protected Date duedate;
        protected String formattedDuedate;
        protected String remarks;
        protected String status;
        
        public void inputOperation() {
            schedulerEmail = (String)schedulerComboBox.getSelectedItem();
            hallID = (String)IdComboBox.getSelectedItem();
            hallType = htype.getText();
            duedate = datechoose.getDate();
            remarks = remarksField.getText();
            status = (String)StatusCombo.getSelectedItem();
            
            if(schedulerEmail == null || hallID == null || hallType.isEmpty() || duedate == null || remarks.isEmpty() || status == null) {
                JOptionPane.showMessageDialog(rootPane, "Please complete all required fields!", "Empty Input is not allowed", JOptionPane.ERROR_MESSAGE);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                formattedDuedate = sdf.format(duedate);
                
            }
        }
    }
    
    class taskAssignment extends UserInput{
        
        public void addTask() {
            inputOperation();
            
            if (hallType.isEmpty() || duedate == null || remarks.isEmpty()) {
                return;
            }

            
            FileOperation file = new FileOperation();
            file.setFile("resources/database/schedulerTask.txt");
            file.checkFilePath();
            file.appendData(schedulerEmail + "," + hallID + "," + hallType + "," + formattedDuedate + "," + remarks + "," + status);
            JOptionPane.showMessageDialog(null, "You have successfully assigned the task!", "Success Task Assignment", JOptionPane.INFORMATION_MESSAGE);
            generateTable();
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
        leftMenu12 = new javax.swing.JPanel();
        homeLink = new javax.swing.JLabel();
        smLink = new javax.swing.JLabel();
        issueLink = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        Homebtn12 = new javax.swing.JLabel();
        jLabel208 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        jLabel212 = new javax.swing.JLabel();
        logoutLink = new javax.swing.JLabel();
        taskLink = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        jLabel207 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        schedTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        htype = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        StatusCombo = new javax.swing.JComboBox<>();
        remarksField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        datechoose = new com.toedter.calendar.JDateChooser();
        addButton = new javax.swing.JButton();
        schedulerComboBox = new javax.swing.JComboBox<>();
        IdComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(248, 246, 240));
        jPanel1.setMinimumSize(new java.awt.Dimension(980, 620));
        jPanel1.setPreferredSize(new java.awt.Dimension(980, 620));

        leftMenu12.setBackground(new java.awt.Color(172, 225, 199));
        leftMenu12.setPreferredSize(new java.awt.Dimension(220, 620));

        homeLink.setText("Home Page");
        homeLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeLinkMouseClicked(evt);
            }
        });

        smLink.setText("Sales Management");
        smLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        smLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                smLinkMouseClicked(evt);
            }
        });

        issueLink.setText("Customer Issues");
        issueLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        issueLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                issueLinkMouseClicked(evt);
            }
        });

        jSeparator13.setPreferredSize(new java.awt.Dimension(65, 10));

        Homebtn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/home.png"))); // NOI18N
        Homebtn12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Homebtn12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Homebtn12MouseClicked(evt);
            }
        });

        jLabel208.setBackground(new java.awt.Color(153, 153, 153));
        jLabel208.setForeground(new java.awt.Color(102, 102, 102));
        jLabel208.setText("General");

        jLabel209.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/rent.png"))); // NOI18N
        jLabel209.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel210.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/check-up.png"))); // NOI18N
        jLabel210.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel212.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/logout.png"))); // NOI18N
        jLabel212.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        logoutLink.setText("Log Out");
        logoutLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutLinkMouseClicked(evt);
            }
        });

        taskLink.setText("Scheduler Assign");
        taskLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel219.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/refund.png"))); // NOI18N
        jLabel219.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel207.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel207.setText("SCHEDULER");

        jLabel220.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel220.setText("ASSIGNMENT");

        javax.swing.GroupLayout leftMenu12Layout = new javax.swing.GroupLayout(leftMenu12);
        leftMenu12.setLayout(leftMenu12Layout);
        leftMenu12Layout.setHorizontalGroup(
            leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenu12Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel207)
                    .addGroup(leftMenu12Layout.createSequentialGroup()
                        .addComponent(jLabel219)
                        .addGap(18, 18, 18)
                        .addComponent(taskLink))
                    .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(leftMenu12Layout.createSequentialGroup()
                        .addComponent(Homebtn12)
                        .addGap(18, 18, 18)
                        .addComponent(homeLink))
                    .addComponent(jLabel208)
                    .addGroup(leftMenu12Layout.createSequentialGroup()
                        .addComponent(jLabel209)
                        .addGap(18, 18, 18)
                        .addComponent(smLink))
                    .addGroup(leftMenu12Layout.createSequentialGroup()
                        .addComponent(jLabel210)
                        .addGap(18, 18, 18)
                        .addComponent(issueLink))
                    .addGroup(leftMenu12Layout.createSequentialGroup()
                        .addComponent(jLabel212)
                        .addGap(18, 18, 18)
                        .addComponent(logoutLink))
                    .addComponent(jLabel220))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        leftMenu12Layout.setVerticalGroup(
            leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMenu12Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel207)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel220)
                .addGap(18, 18, 18)
                .addGroup(leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(leftMenu12Layout.createSequentialGroup()
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(Homebtn12))
                    .addComponent(homeLink))
                .addGap(18, 18, 18)
                .addComponent(jLabel208)
                .addGap(13, 13, 13)
                .addGroup(leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel209)
                    .addComponent(smLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel210)
                    .addComponent(issueLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel219)
                    .addComponent(taskLink))
                .addGap(18, 18, 18)
                .addGroup(leftMenu12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel212)
                    .addComponent(logoutLink))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(248, 246, 240));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("Current Assigned Task");

        schedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        schedTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                schedTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(schedTable);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setText("Assign to");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setText("ID");

        htype.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        htype.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Hall Type");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setText("Status");

        StatusCombo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        StatusCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Completed", "In Progress" }));

        remarksField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setText("Remarks");

        jLabel5.setText("Due Date");

        addButton.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        addButton.setText("Add Task Now");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        addButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addButtonKeyPressed(evt);
            }
        });

        IdComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(htype))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(schedulerComboBox, 0, 217, Short.MAX_VALUE)
                                    .addComponent(IdComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(datechoose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                    .addComponent(remarksField)
                                    .addComponent(StatusCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datechoose, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(schedulerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel8)
                        .addComponent(remarksField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(IdComboBox))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(htype, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(StatusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftMenu12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftMenu12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void Homebtn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Homebtn12MouseClicked
        ManagerDashboard manage = new ManagerDashboard();
        manage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Homebtn12MouseClicked

    private void schedTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_schedTableMouseClicked

    }//GEN-LAST:event_schedTableMouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        
    }//GEN-LAST:event_addButtonMouseClicked

    private void addButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addButtonKeyPressed

    }//GEN-LAST:event_addButtonKeyPressed

    private void logoutLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLinkMouseClicked
        LoginPage login = new LoginPage();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutLinkMouseClicked

    private void issueLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issueLinkMouseClicked
        CustomerStatus cs = new CustomerStatus();
        cs.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_issueLinkMouseClicked

    private void smLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_smLinkMouseClicked
        SalesManagement sales = new SalesManagement();
        sales.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_smLinkMouseClicked

    private void homeLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLinkMouseClicked
        ManagerDashboard dashboard = new ManagerDashboard();
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_homeLinkMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        taskAssignment task = new taskAssignment();
        task.addTask();
    }//GEN-LAST:event_addButtonActionPerformed

    private void IdComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(SchedulerAssign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SchedulerAssign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SchedulerAssign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SchedulerAssign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SchedulerAssign().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Homebtn12;
    private javax.swing.JComboBox<String> IdComboBox;
    private javax.swing.JComboBox<String> StatusCombo;
    private javax.swing.JButton addButton;
    private com.toedter.calendar.JDateChooser datechoose;
    private javax.swing.JLabel homeLink;
    private javax.swing.JTextField htype;
    private javax.swing.JLabel issueLink;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JPanel leftMenu12;
    private javax.swing.JLabel logoutLink;
    private javax.swing.JTextField remarksField;
    private javax.swing.JTable schedTable;
    private javax.swing.JComboBox<String> schedulerComboBox;
    private javax.swing.JLabel smLink;
    private javax.swing.JLabel taskLink;
    // End of variables declaration//GEN-END:variables
}
