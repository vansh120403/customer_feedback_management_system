package services;

import dao.FeedbackDAO;
import models.Feedback;
import java.sql.SQLException;
import java.util.List;

public class FeedbackService {
    private final FeedbackDAO feedbackDAO;

    public FeedbackService(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }

    public void addFeedback(Feedback feedback){
        feedbackDAO.addFeedback(feedback);
    }

    public Feedback getFeedback(int feedbackId) throws SQLException {
        return feedbackDAO.getFeedback(feedbackId);
    }

    public List<Feedback> getAllFeedbacks() throws SQLException {
        return feedbackDAO.getAllFeedbacks();
    }

    public void updateFeedback(Feedback feedback) throws SQLException {
        feedbackDAO.updateFeedback(feedback);
    }

    public void deleteFeedback(int feedbackId) throws SQLException {
        feedbackDAO.deleteFeedback(feedbackId);
    }
}

