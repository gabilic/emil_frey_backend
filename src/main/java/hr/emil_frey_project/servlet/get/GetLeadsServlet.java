package hr.emil_frey_project.servlet.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.emil_frey_project.dao.LeadDao;
import hr.emil_frey_project.model.Lead;
import hr.emil_frey_project.util.HttpServletUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetLeadsServlet", urlPatterns = "/leads")
public class GetLeadsServlet extends HttpServletUtil {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Map<String, String[]> parameters = request.getParameterMap();
        List<Lead> listOfLeads;
        if (parameters.containsKey("firstName") && parameters.containsKey("lastName")) {
            listOfLeads = new LeadDao().getLeadsByName(request.getParameter("firstName"), request.getParameter("lastName"));
        } else {
            listOfLeads = new LeadDao().getAllLeads();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        sendResponse(response, objectMapper.writeValueAsString(new ArrayList<>(new HashSet<>(listOfLeads))));
    }
}
