/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Customer;

import Users.FileOperation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ReloadCredit extends javax.swing.JFrame {

    /**
     * Creates new form ReloadCredit
     */
    
    private String email;
    
    public ReloadCredit() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void setSessionEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    class InputOperation {
        protected String name;
        protected String cardNum;
        protected String password;
        protected String amount;
        protected boolean proceed = false;
        
        public String getSelectedAmount() {
            for (Enumeration<AbstractButton> buttons = amountButtonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();

                if (button.isSelected()) {
                    return button.getText();
                }
            }
            return null;
        }
        
        public void validation() {
            name = nameInput.getText();
            cardNum = cardNumberInput.getText();
            password = passInput.getText();
            amount = getSelectedAmount();
            
            if(name.isEmpty() || cardNum.isEmpty() || password.isEmpty() || amount == null) {
                JOptionPane.showMessageDialog(rootPane, "Opps empty value found! Please complete all required fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else {
                proceed = true;
            }
        }
        
        public boolean getProceedConfirmation() {
            return proceed;
        }
    }
    
    class FileAction extends InputOperation {
        protected int topupAmount;
        protected int credit;
        
        public void creditOperation() {
            boolean updateInfo = getProceedConfirmation();
            
            if(updateInfo) {
                // UPDATE BALANCE INTO WALLET DATABASE
                FileOperation file = new FileOperation();
                file.setFile("resources/database/wallet.txt");
                file.checkFilePath();
                file.retrieveData();
                UserWallet wallet = new UserWallet();
                ArrayList<String> walletInfo = file.getRetrievedData();

                for(int i = 0; i < walletInfo.size(); i++) {
                    String[] records = walletInfo.get(i).split(",");

                    String userFromFile = records[0];
                    String creditFromFile = records[1];

                    if(userFromFile.equals(getEmail())) {
                        credit = Integer.parseInt(creditFromFile);
                        topupAmount = (int) Float.parseFloat(amount);
                        wallet.setAmount(credit);
                        wallet.addAmount(topupAmount); 
                        walletInfo.set(i, userFromFile + "," + wallet.getAmount());
                        break;
                    }
                }

                file.updateData(walletInfo);

                // APPEND INTO RELOAD HISTORY DATABASE
                file.setFile("resources/database/reloadHistory.txt");
                file.checkFilePath();
                file.retrieveData();
                file.setSplitElement("T");
                file.IdOperation();
                int newIdForReloadHistory = file.getIdNum();
                String increseIdByOne = "T" + newIdForReloadHistory;

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                String strDate = formatter.format(date);
                file.appendData(increseIdByOne + "," + getEmail() + "," + strDate + "," + getSelectedAmount());

                // APPEND INTO PAYMENT DATABASE
                file.setFile("resources/database/payment.txt");
                file.checkFilePath();
                file.retrieveData();
                file.setSplitElement("P");
                file.IdOperation();
                int newIdForPayment = file.getIdNum();
                String incrementIdByOne = "P" + newIdForPayment;
                
                // Formatting
                String selectedAmountStr = getSelectedAmount();
                double amount = Double.parseDouble(selectedAmountStr);
                String formattedAmountStr = String.format("%.1f", amount);
               

                file.appendData(incrementIdByOne + "," + getEmail() + "," + strDate + "," + formattedAmountStr + ",reload wallet,completed," + increseIdByOne);
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

        amountButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        pageTitle = new javax.swing.JLabel();
        reloadLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        rm10 = new javax.swing.JRadioButton();
        rm20 = new javax.swing.JRadioButton();
        rm30 = new javax.swing.JRadioButton();
        rm50 = new javax.swing.JRadioButton();
        rm100 = new javax.swing.JRadioButton();
        rm500 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cardNumberInput = new javax.swing.JTextField();
        passInput = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        paymentLabel = new javax.swing.JLabel();
        noteLabel = new javax.swing.JLabel();
        reloadButton = new javax.swing.JButton();
        backNavi = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(248, 246, 240));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 600));

        pageTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        pageTitle.setText("Reload Balance");

        reloadLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reloadLabel.setText("TOP-UP AMOUNT");

        amountButtonGroup.add(rm10);
        rm10.setText("10.00");
        rm10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rm10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rm10ActionPerformed(evt);
            }
        });

        amountButtonGroup.add(rm20);
        rm20.setText("20.00");
        rm20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        amountButtonGroup.add(rm30);
        rm30.setText("30.00");
        rm30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        amountButtonGroup.add(rm50);
        rm50.setText("50.00");
        rm50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        amountButtonGroup.add(rm100);
        rm100.setText("100.00");
        rm100.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        amountButtonGroup.add(rm500);
        rm500.setText("500.00");
        rm500.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Cardholder Name");

        nameInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Card Number");

        cardNumberInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        passInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Password");

        paymentLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        paymentLabel.setText("PAYMENT CONFIRMATION");

        noteLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        noteLabel.setForeground(new java.awt.Color(255, 153, 0));
        noteLabel.setText("Note: Debit Card Payments Only");

        reloadButton.setBackground(new java.awt.Color(85, 85, 85));
        reloadButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reloadButton.setForeground(new java.awt.Color(255, 255, 255));
        reloadButton.setText("Reload");
        reloadButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadButtonActionPerformed(evt);
            }
        });

        backNavi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/back.png"))); // NOI18N
        backNavi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backNavi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backNaviMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/card-2.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/paypal.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/other_sources/images/visa.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pageTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backNavi, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(reloadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(rm30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rm10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rm20)
                                .addGap(18, 18, 18)
                                .addComponent(rm100))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rm50)
                                .addGap(18, 18, 18)
                                .addComponent(rm500, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentLabel)
                    .addComponent(noteLabel)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cardNumberInput, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nameInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(passInput, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(reloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pageTitle)
                    .addComponent(backNavi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reloadLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rm10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rm20)
                    .addComponent(rm100))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rm30)
                    .addComponent(rm50)
                    .addComponent(rm500))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paymentLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noteLabel)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardNumberInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(reloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rm10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rm10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rm10ActionPerformed

    private void reloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadButtonActionPerformed
        FileAction action = new FileAction();
        action.validation();
        action.creditOperation();
    }//GEN-LAST:event_reloadButtonActionPerformed

    private void backNaviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backNaviMouseClicked
        AccountWallet aw = new AccountWallet();
        aw.setEmail(getEmail());
        aw.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backNaviMouseClicked

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
            java.util.logging.Logger.getLogger(ReloadCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReloadCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReloadCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReloadCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReloadCredit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup amountButtonGroup;
    private javax.swing.JLabel backNavi;
    private javax.swing.JTextField cardNumberInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JLabel pageTitle;
    private javax.swing.JPasswordField passInput;
    private javax.swing.JLabel paymentLabel;
    private javax.swing.JButton reloadButton;
    private javax.swing.JLabel reloadLabel;
    private javax.swing.JRadioButton rm10;
    private javax.swing.JRadioButton rm100;
    private javax.swing.JRadioButton rm20;
    private javax.swing.JRadioButton rm30;
    private javax.swing.JRadioButton rm50;
    private javax.swing.JRadioButton rm500;
    // End of variables declaration//GEN-END:variables
}
