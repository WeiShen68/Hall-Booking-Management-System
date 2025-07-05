/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Users;

import javax.swing.JOptionPane;

public class FeedbackAndMessage {
    public interface FeedbackHandler {
        public abstract void provideFeedback();
        public abstract void updateStatus(); // EXP: ISSUE 1 UPDATED FROM INCOMPLETE TO COMPLETE
        public abstract void updateSolution(); // EXP: ENHANCED THE HALL SECURITY
    }

    public abstract class Feedback {
        protected String feedbackID;
        protected String reservationID;
        protected String userEmail;
        protected String fullName;
        protected String message;
        protected String status;
        protected String solution;

        public Feedback(String feedbackID, String reservationID, String userEmail, String fullName, String message) {
            this.feedbackID = feedbackID;
            this.reservationID = reservationID;
            this.userEmail = userEmail;
            this.fullName = fullName;
            this.message = message;
            this.status = "Pending";
            this.solution = "null";  
        }

        public String getFeedbackID() {
            return feedbackID;
        }

        public String getReservationID() {
            return reservationID;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getFullName() {
            return fullName;
        }

        public String getMessage() {
            return message;
        }

        public abstract String getCustomerFeedback();
    }

    public class Customer extends Feedback implements FeedbackHandler {
        protected String status = "paused";
        public Customer(String feedbackID, String reservationID, String userEmail, String fullName, String message) {
            super(feedbackID, reservationID, userEmail, fullName, message);
        }

        @Override
        public void provideFeedback() {
            FileOperation file = new FileOperation();
            file.setFile("resources/database/customerFeedback.txt");
            file.retrieveData();

            String newFeedbackData = getFeedbackID() + "," + getReservationID() + "," + getUserEmail() + "," + getFullName() + "," + getMessage() + ",pending,null";
            file.appendData(newFeedbackData);
        }

        @Override
        public void updateStatus() {
            // Customers do not typically update the status
            System.out.println("Customer cannot update the status.");
        }

        @Override
        public void updateSolution() {
            // Customers do not typically provide solutions
            System.out.println("Customer cannot provide a solution.");
        }

        @Override
        public String getCustomerFeedback() {
            String feedbackMessage = String.format(
                "Are you certain about sending the feedback as below?\n\n" +
                "Feedback ID: %s\n" +
                "Reservation ID: %s\n" +
                "User Email: %s\n" +
                "Full Name: %s\n" +
                "Message: %s",
                getFeedbackID(), getReservationID(), getUserEmail(), getFullName(), getMessage()
            );

            int response = JOptionPane.showConfirmDialog(null, feedbackMessage, "Confirm Feedback", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION) {
                status = "sent";
            } else {
                status = "paused";
            }
            return status;
        }
    }

    public class Manager extends Feedback implements FeedbackHandler {

        public Manager(String feedbackID, String reservationID, String userEmail, String fullName, String message) {
            super(feedbackID, reservationID, userEmail, fullName, message);
        }

        @Override
        public void provideFeedback() {
            System.out.println("Manager reviewing feedback from " + fullName + ": " + message);
        }

        @Override
        public void updateStatus() {
            this.status = "Resolved";
            System.out.println("Manager updated status to: " + status);
        }

        @Override
        public void updateSolution() {
            this.solution = "Enhanced cleaning schedule.";
            System.out.println("Manager provided solution: " + solution);
        }

        @Override
        public String getCustomerFeedback() {
            return "FeedbackID: " + feedbackID + ", ReservationID: " + reservationID + ", UserEmail: " + userEmail +
                   ", FullName: " + fullName + ", Message: " + message + ", Status: " + status + ", Solution: " + solution;
        }
    }
}
