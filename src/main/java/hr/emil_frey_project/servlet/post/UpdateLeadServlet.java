package hr.emil_frey_project.servlet.post;

import hr.emil_frey_project.dao.LeadDao;
import hr.emil_frey_project.util.HttpServletUtil;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UpdateLeadServlet", urlPatterns = "/updateLead")
public class UpdateLeadServlet extends HttpServletUtil {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject(getRequestBody(request));
        List<Integer> carIds = getParameterList("id", json.getJSONArray("carIds"));
        boolean result = new LeadDao().updateLead(json.getInt("id"), json.getString("firstName"), json.getString("lastName"), carIds);
        sendResponse(response, new JSONObject("{\"success\":" + result + "}"));
    }
}
