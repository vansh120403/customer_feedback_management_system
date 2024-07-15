package dao;

import models.Analysis;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnalysisDAO {
    private final Connection connection;

    //Constructor to initialize the DAO with database connection
    public AnalysisDAO(Connection connection) {
        this.connection = connection;
    }


    //Method to add a new analysis record to the database
    public void addAnalysis(Analysis analysis){
        String query = "INSERT INTO Analysis (analysis_id, feedback_id, analysis_date, analysis_details, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            //Setting parameters for the prepared statement
            stmt.setInt(1, analysis.getAnalysisId());
            stmt.setInt(2, analysis.getFeedbackId());
            stmt.setDate(3, new java.sql.Date(analysis.getAnalysisDate().getTime()));
            stmt.setString(4, analysis.getAnalysisDetails());
            stmt.setString(5, analysis.getStatus());

            //Executing the update
            stmt.executeUpdate();
            System.out.println("Analysis recorded.");
        }catch(Exception e){
            //Handling exceptions
            System.out.println("Invalid feedback ID!");
        }
    }


    //Method to retrieve an analysis record by its ID from the database
    public Analysis getAnalysis(int analysisId) throws SQLException {
        String query = "SELECT * FROM Analysis WHERE analysis_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, analysisId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                //Creating an analysis object from the retrieved data
                Analysis analysis = new Analysis();
                analysis.setAnalysisId(rs.getInt("analysis_id"));
                analysis.setFeedbackId(rs.getInt("feedback_id"));
                analysis.setAnalysisDate(rs.getDate("analysis_date"));
                analysis.setAnalysisDetails(rs.getString("analysis_details"));
                analysis.setStatus(rs.getString("status"));
                return analysis;
            }
        }
        return null; //Returning null if no analysis found
    }

    //Method to retrieve all analysis records form the database
    public List<Analysis> getAllAnalyses() throws SQLException {
        List<Analysis> analyses = new ArrayList<>();
        String query = "SELECT * FROM Analysis";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                //Creating analysis objects for each retrieved record and adding to the list
                Analysis analysis = new Analysis();
                analysis.setAnalysisId(rs.getInt("analysis_id"));
                analysis.setFeedbackId(rs.getInt("feedback_id"));
                analysis.setAnalysisDate(rs.getDate("analysis_date"));
                analysis.setAnalysisDetails(rs.getString("analysis_details"));
                analysis.setStatus(rs.getString("status"));
                analyses.add(analysis);
            }
        }
        return analyses; //returning the list of analyses
    }

    //Method to update an existing analysis record in the database
    public void updateAnalysis(Analysis analysis) throws SQLException {
        String query = "UPDATE Analysis SET feedback_id = ?, analysis_date = ?, analysis_details = ?, status = ? WHERE analysis_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            //Setting parameters for the prepared statement
            stmt.setInt(1, analysis.getFeedbackId());
            stmt.setDate(2, new java.sql.Date(analysis.getAnalysisDate().getTime()));
            stmt.setString(3, analysis.getAnalysisDetails());
            stmt.setString(4, analysis.getStatus());
            stmt.setInt(5, analysis.getAnalysisId());
            //Executing the update
            stmt.executeUpdate();
        }
    }

    //Method to delete an analysis record from the database by its ID
    public void deleteAnalysis(int analysisId) throws SQLException {
        String query = "DELETE FROM Analysis WHERE analysis_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            //Setting the parameter for the prepared statement
            stmt.setInt(1, analysisId);
            //Executing the delete operation
            stmt.executeUpdate();
        }
    }
}
