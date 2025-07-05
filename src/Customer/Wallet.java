/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import javax.swing.JOptionPane;

public class Wallet {
    private int amount;
    

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public void addAmount(int amount) {
        this.amount += amount;
    }
    
    public void deductAmount(int amount) {
        this.amount -= amount;
    }
}

class UserWallet extends Wallet {
    boolean navigation = false;
    @Override
    public void addAmount(int amount) {
        if (amount > 0) {
            super.addAmount(amount); 
            JOptionPane.showMessageDialog(null, "Top-up successful! New balance: " + getAmount(), "Reload Successfully", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid top-up amount.", "Reload Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void deductAmount(int amount) {
        if (getAmount() >= amount) {
            super.deductAmount(amount);  
            JOptionPane.showMessageDialog(null, "Payment successful! New balance: " + getAmount(), "Payment Success", JOptionPane.INFORMATION_MESSAGE);
            navigation = true;
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient Balance! Please topup!", "Payment Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}

