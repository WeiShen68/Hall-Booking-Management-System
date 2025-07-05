/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Users;

import Administrator.AdminDash;
import Customer.Dashboard;
import Manager.ManagerDashboard;
import Scheduler.SchedulerDashboard;
import javax.swing.JOptionPane;


public class User {
    private String email;
    private String name;
    private String contactNumber;
    private String password;
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void navigate(String username, String sessionEmail) {
        // Default implementation
    }
}

class Manager extends User {
    @Override
    public void navigate(String username, String sessionEmail) {
        JOptionPane.showMessageDialog(null, "Welcome Back " + username + "!");
        ManagerDashboard dashboard = new ManagerDashboard();
        dashboard.setEmail(sessionEmail);
        dashboard.setVisible(true);
    }
}

class Customer extends User {
    @Override
    public void navigate(String username, String sessionEmail) {
        JOptionPane.showMessageDialog(null, "Welcome Back " + username + "!");
        Dashboard dashboard = new Dashboard();
        dashboard.setSessionEmail(sessionEmail);
        dashboard.setVisible(true);
    }
} 

class Admin extends User {
    @Override
    public void navigate(String username, String sessionEmail) {
        JOptionPane.showMessageDialog(null, "Welcome Back " + username + "!");
        AdminDash dashboard = new AdminDash();
        dashboard.setSessionEmail(sessionEmail);
        dashboard.setVisible(true);
    }
}

class Scheduler extends User {
    @Override
    public void navigate(String username, String sessionEmail) {
        JOptionPane.showMessageDialog(null, "Welcome Back " + username + "!");
        SchedulerDashboard dashboard = new SchedulerDashboard();
        dashboard.setEmail(sessionEmail);
        dashboard.setVisible(true);
    }
}


