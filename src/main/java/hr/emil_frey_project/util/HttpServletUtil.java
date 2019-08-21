package hr.emil_frey_project.util;

import org.json.JSONArray;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpServletUtil extends HttpServlet {
    protected String getRequestBody(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    @SuppressWarnings("SameParameterValue")
    protected List<Integer> getParameterList(String key, JSONArray parameters) {
        List<Integer> parameterList = new ArrayList<>();
        for (int i = 0; i < parameters.length(); i++) {
            parameterList.add(parameters.getJSONObject(i).getInt(key));
        }
        return parameterList;
    }

    protected void sendResponse(HttpServletResponse response, Object json) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter printer = response.getWriter();
            printer.println(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
