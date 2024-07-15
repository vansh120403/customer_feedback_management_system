package services;

import dao.ResponseDAO;
import models.Response;
import java.sql.SQLException;
import java.util.List;

public class ResponseService {
    private final ResponseDAO responseDAO;

    public ResponseService(ResponseDAO responseDAO) {
        this.responseDAO = responseDAO;
    }

    public void addResponse(Response response){
        responseDAO.addResponse(response);
    }

    public Response getResponse(int responseId) throws SQLException {
        return responseDAO.getResponse(responseId);
    }

    public List<Response> getAllResponses() throws SQLException {
        return responseDAO.getAllResponses();
    }

    public void updateResponse(Response response) throws SQLException {
        responseDAO.updateResponse(response);
    }

    public void deleteResponse(int responseId) throws SQLException {
        responseDAO.deleteResponse(responseId);
    }
}
