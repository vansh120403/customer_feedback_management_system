package dao;

import models.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    private final Connection connection;

    //Constructor to initialize the DAO with a database connection
    public FeedbackDAO(Connection connection) {
        this.connection = connection;
    }

    //Method to add a new feedback record to the database
    public void addFeedback(Feedback feedback){
        String query = "INSERT INTO Feedback (feedback_id, customer_id, feedback_date, feedback_text, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            //Setting parameters for the prepared statement
            stmt.setInt(1, feedback.getFeedbackId());
            stmt.setInt(2, feedback.getCustomerId());
            stmt.setDate(3, new java.sql.Date(feedback.getFeedbackDate().getTime()));
            stmt.setString(4, feedback.getFeedbackText());
            stmt.setString(5, feedback.getStatus());
            //Executing the update
            stmt.executeUpdate();
            System.out.println("Feedback recoded.");
        }catch(Exception e){
            //Handling exception
            System.out.println("Invalid customer ID!");
        }
    }

    //Method to retrieve a feedback record by its ID from the database
    public Feedback getFeedback(int feedbackId) throws SQLException {
        String query = "SELECT * FROM Feedback WHERE feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, feedbackId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                //Creating a Feedback object from the retrieved data
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setCustomerId(rs.getInt("customer_id"));
                feedback.setFeedbackDate(rs.getDate("feedback_date"));
                feedback.setFeedbackText(rs.getString("feedback_text"));
                feedback.setStatus(rs.getString("status"));
                return feedback;
            }
        }
        return null; //Return null if no feedback found
    }

    //Method to retrieve all feedback records from the database
    public List<Feedback> getAllFeedbacks() throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM Feedback";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                //Creating Feedback objects for each retrieved record and adding to the list
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setCustomerId(rs.getInt("customer_id"));
                feedback.setFeedbackDate(rs.getDate("feedback_date"));
                feedback.setFeedbackText(rs.getString("feedback_text"));
                feedback.setStatus(rs.getString("status"));
                feedbacks.add(feedback);
            }
        }
        return feedbacks; //Returning the list of feedbacks
    }

    //Method to update an existing feedback record in the database
    public void updateFeedback(Feedback feedback) throws SQLException {
        String query = "UPDATE Feedback SET customer_id = ?, feedback_date = ?, feedback_text = ?, status = ? WHERE feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            //Setting parameters for the prepared statement
            stmt.setInt(1, feedback.getCustomerId());
            stmt.setDate(2, new java.sql.Date(feedback.getFeedbackDate().getTime()));
            stmt.setString(3, feedback.getFeedbackText());
            stmt.setString(4, feedback.getStatus());
            stmt.setInt(5, feedback.getFeedbackId());
            //Executing the update
            stmt.executeUpdate();
        }
    }

    //Method to delete a feedback record from the database by its ID
    public void deleteFeedback(int feedbackId) throws SQLException {
        String query = "DELETE FROM Feedback WHERE feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            //Setting the parameter for the prepared statement
            stmt.setInt(1, feedbackId);
            //Executing the delete operation
            stmt.executeUpdate();
        }
    }
}
