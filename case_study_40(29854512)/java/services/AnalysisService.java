package services;

import dao.AnalysisDAO;
import models.Analysis;
import java.sql.SQLException;
import java.util.List;

public class AnalysisService {
    private final AnalysisDAO analysisDAO;

    public AnalysisService(AnalysisDAO analysisDAO) {
        this.analysisDAO = analysisDAO;
    }

    public void addAnalysis(Analysis analysis){
        analysisDAO.addAnalysis(analysis);
    }

    public Analysis getAnalysis(int analysisId) throws SQLException {
        return analysisDAO.getAnalysis(analysisId);
    }

    public List<Analysis> getAllAnalyses() throws SQLException {
        return analysisDAO.getAllAnalyses();
    }

    public void updateAnalysis(Analysis analysis) throws SQLException {
        analysisDAO.updateAnalysis(analysis);
    }

    public void deleteAnalysis(int analysisId) throws SQLException {
        analysisDAO.deleteAnalysis(analysisId);
    }
}
