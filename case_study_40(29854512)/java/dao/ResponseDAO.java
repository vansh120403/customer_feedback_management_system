package dao;

import models.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponseDAO {
    private final Connection connection;

    public ResponseDAO(Connection connection) {
        this.connection = connection;
    }

    public void addResponse(Response response){
        String query = "INSERT INTO Response (response_id, feedback_id, response_date, response_text, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, response.getResponseId());
            stmt.setInt(2, response.getFeedbackId());
            stmt.setDate(3, new java.sql.Date(response.getResponseDate().getTime()));
            stmt.setString(4, response.getResponseText());
            stmt.setString(5, response.getStatus());
            stmt.executeUpdate();
            System.out.println("Response recorded.");
        }catch(Exception e){
            System.out.println("Invalid feedback ID!");
        }
    }

    public Response getResponse(int responseId) throws SQLException {
        String query = "SELECT * FROM Response WHERE response_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, responseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Response response = new Response();
                response.setResponseId(rs.getInt("response_id"));
                response.setFeedbackId(rs.getInt("feedback_id"));
                response.setResponseDate(rs.getDate("response_date"));
                response.setResponseText(rs.getString("response_text"));
                response.setStatus(rs.getString("status"));
                return response;
            }
        }
        return null;
    }

    public List<Response> getAllResponses() throws SQLException {
        List<Response> responses = new ArrayList<>();
        String query = "SELECT * FROM Response";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Response response = new Response();
                response.setResponseId(rs.getInt("response_id"));
                response.setFeedbackId(rs.getInt("feedback_id"));
                response.setResponseDate(rs.getDate("response_date"));
                response.setResponseText(rs.getString("response_text"));
                response.setStatus(rs.getString("status"));
                responses.add(response);
            }
        }
        return responses;
    }

    public void updateResponse(Response response) throws SQLException {
        String query = "UPDATE Response SET feedback_id = ?, response_date = ?, response_text = ?, status = ? WHERE response_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, response.getFeedbackId());
            stmt.setDate(2, new java.sql.Date(response.getResponseDate().getTime()));
            stmt.setString(3, response.getResponseText());
            stmt.setString(4, response.getStatus());
            stmt.setInt(5, response.getResponseId());
            stmt.executeUpdate();
        }
    }

    public void deleteResponse(int responseId) throws SQLException {
        String query = "DELETE FROM Response WHERE response_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, responseId);
            stmt.executeUpdate();
        }
    }
}
