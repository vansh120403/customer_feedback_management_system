package main;

import services.FeedbackService;
import services.AnalysisService;
import services.ResponseService;

import models.Feedback;
import models.Analysis;
import models.Response;

import dao.FeedbackDAO;
import dao.AnalysisDAO;
import dao.ResponseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Date;

public class FeedbackManagementSystem {
    private static FeedbackService feedbackService;
    private static AnalysisService analysisService;
    private static ResponseService responseService;

    public static void main(String[] args) {
        try {
            //Connecting to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/feedback_management", "root", "Vansh@123");

            //initializing service classes with DAOs
            feedbackService = new FeedbackService(new FeedbackDAO(connection));
            analysisService = new AnalysisService(new AnalysisDAO(connection));
            responseService = new ResponseService(new ResponseDAO(connection));

            Scanner scanner = new Scanner(System.in);
            while (true) {
                //Menu options
                System.out.println("\nMenu:");
                System.out.println("1. Feedback Submission");
                System.out.println("2. Feedback Analysis");
                System.out.println("3. Feedback Response");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        handleFeedbackSubmission(scanner);
                        break;
                    case 2:
                        handleFeedbackAnalysis(scanner);
                        break;
                    case 3:
                        handleFeedbackResponse(scanner);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        connection.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            //Error if database connection is not completed
            System.out.println("Error: " + e);
        }
    }


    //Method to handle feedback submission operations
    private static void handleFeedbackSubmission(Scanner scanner) throws SQLException {
        System.out.println("Feedback Submission: ");
        System.out.println("1. Record new feedback");
        System.out.println("2. View feedback details");
        System.out.println("3. Update feedback status");
        System.out.println("4. Delete a feedback");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        int feedbackId;
        switch (choice) {
            case 1://For new feedback
                scanner.nextLine();
                System.out.print("Enter customer ID: ");
                int customerId = scanner.nextInt();
                System.out.print("Create feedback ID: ");
                feedbackId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter feedback text: ");
                String feedbackText = scanner.nextLine();
                Feedback feedback = new Feedback();
                feedback.setCustomerId(customerId);
                feedback.setFeedbackId(feedbackId);
                feedback.setFeedbackDate(new Date());
                feedback.setFeedbackText(feedbackText);
                feedback.setStatus("Pending");
                feedbackService.addFeedback(feedback);
                break;
            case 2://For viewing feedback details
                System.out.print("Enter feedback ID: ");
                feedbackId = scanner.nextInt();
                Feedback retrievedFeedback = feedbackService.getFeedback(feedbackId);
                if (retrievedFeedback != null) {
                    System.out.println("Feedback ID: " + retrievedFeedback.getFeedbackId());
                    System.out.println("Customer ID: " + retrievedFeedback.getCustomerId());
                    System.out.println("Feedback Date: " + retrievedFeedback.getFeedbackDate());
                    System.out.println("Feedback Text: " + retrievedFeedback.getFeedbackText());
                    System.out.println("Status: " + retrievedFeedback.getStatus());
                } else {
                    System.out.println("Feedback not found!");
                }
                break;
            case 3://For updating feedback status
                System.out.print("Enter feedback ID: ");
                int updateFeedbackId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new status: ");
                String newStatus = scanner.nextLine();
                Feedback updateFeedback = feedbackService.getFeedback(updateFeedbackId);
                if (updateFeedback != null) {
                    updateFeedback.setStatus(newStatus);
                    feedbackService.updateFeedback(updateFeedback);
                    System.out.println("Feedback status updated.");
                } else {
                    System.out.println("Feedback not found!");
                }
                break;
            case 4://for deleting a feedback
                System.out.print("Enter feedback ID: ");
                int deleteFeedbackId = scanner.nextInt();
                feedbackService.deleteFeedback(deleteFeedbackId);
                System.out.println("Feedback deleted.");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    //Method to handle feedback analysis operations
    private static void handleFeedbackAnalysis(Scanner scanner) throws SQLException {
        System.out.println("Feedback Analysis: ");
        System.out.println("1. Record new analysis");
        System.out.println("2. View analysis details");
        System.out.println("3. Update analysis information");
        System.out.println("4. Delete an analysis");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        int analysisId;

        switch (choice) {
            case 1://For recording new analysis
                scanner.nextLine();
                System.out.print("Enter feedback ID: ");
                int feedbackId = scanner.nextInt();
                System.out.print("Create analysis ID: ");
                analysisId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter analysis details: ");
                String analysisDetails = scanner.nextLine();
                Analysis analysis = new Analysis();
                analysis.setAnalysisId(analysisId);
                analysis.setFeedbackId(feedbackId);
                analysis.setAnalysisDate(new Date());
                analysis.setAnalysisDetails(analysisDetails);
                analysis.setStatus("Completed");
                analysisService.addAnalysis(analysis);
                break;
            case 2://For viewing analysis details
                System.out.print("Enter analysis ID: ");
                analysisId = scanner.nextInt();
                Analysis retrievedAnalysis = analysisService.getAnalysis(analysisId);
                if (retrievedAnalysis != null) {
                    System.out.println("Analysis ID: " + retrievedAnalysis.getAnalysisId());
                    System.out.println("Feedback ID: " + retrievedAnalysis.getFeedbackId());
                    System.out.println("Analysis Date: " + retrievedAnalysis.getAnalysisDate());
                    System.out.println("Analysis Details: " + retrievedAnalysis.getAnalysisDetails());
                    System.out.println("Status: " + retrievedAnalysis.getStatus());
                } else {
                    System.out.println("Analysis not found!");
                }
                break;
            case 3://For viewing analysis information
                System.out.print("Enter analysis ID: ");
                int updateAnalysisId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new analysis details: ");
                String newAnalysisDetails = scanner.nextLine();
                Analysis updateAnalysis = analysisService.getAnalysis(updateAnalysisId);
                if (updateAnalysis != null) {
                    updateAnalysis.setAnalysisDetails(newAnalysisDetails);
                    analysisService.updateAnalysis(updateAnalysis);
                    System.out.println("Analysis information updated.");
                } else {
                    System.out.println("Analysis not found!");
                }
                break;
            case 4://For deleting an analysis
                System.out.print("Enter analysis ID: ");
                int deleteAnalysisId = scanner.nextInt();
                analysisService.deleteAnalysis(deleteAnalysisId);
                System.out.println("Analysis deleted.");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    //Method to handle feedback response operations
    private static void handleFeedbackResponse(Scanner scanner) throws SQLException {
        System.out.println("Feedback Response: ");
        System.out.println("1. Record new response");
        System.out.println("2. View response history");
        System.out.println("3. Update response information");
        System.out.println("4. Delete a response");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        int responseId;

        switch (choice) {
            case 1://For recording new response
                scanner.nextLine();
                System.out.print("Enter feedback ID: ");
                int feedbackId = scanner.nextInt();
                System.out.print("Create response ID: ");
                responseId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter response text: ");
                String responseText = scanner.nextLine();
                Response response = new Response();
                response.setResponseId(responseId);
                response.setFeedbackId(feedbackId);
                response.setResponseDate(new Date());
                response.setResponseText(responseText);
                response.setStatus("Sent");
                responseService.addResponse(response);
                break;
            case 2://For viewing response history
                System.out.print("Enter response ID: ");
                responseId = scanner.nextInt();
                Response retrievedResponse = responseService.getResponse(responseId);
                if (retrievedResponse != null) {
                    System.out.println("Response ID: " + retrievedResponse.getResponseId());
                    System.out.println("Feedback ID: " + retrievedResponse.getFeedbackId());
                    System.out.println("Response Date: " + retrievedResponse.getResponseDate());
                    System.out.println("Response Text: " + retrievedResponse.getResponseText());
                    System.out.println("Status: " + retrievedResponse.getStatus());
                } else {
                    System.out.println("Response not found!");
                }
                break;
            case 3://For updating response information
                System.out.print("Enter response ID: ");
                int updateResponseId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new response text: ");
                String newResponseText = scanner.nextLine();
                Response updateResponse = responseService.getResponse(updateResponseId);
                if (updateResponse != null) {
                    updateResponse.setResponseText(newResponseText);
                    responseService.updateResponse(updateResponse);
                    System.out.println("Response information updated.");
                } else {
                    System.out.println("Response not found!");
                }
                break;
            case 4://For deleting a response
                System.out.print("Enter response ID: ");
                int deleteResponseId = scanner.nextInt();
                responseService.deleteResponse(deleteResponseId);
                System.out.println("Response deleted.");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}
