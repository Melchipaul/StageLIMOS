/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author nduwayo
 */
@WebServlet(urlPatterns = {"/ApacheLivyInteractiveApiSession"})
public class ApacheLivyInteractiveApiSession extends HttpServlet {

    static int session_id = -1;
    static int statement_id = -1;
    static String stateSession = "starting";
    static String stateExecution = "waiting";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
          ApacheLivyBase apacheLivy = new ApacheLivyBase();
        JSONObject myResult = new JSONObject();
        String code = request.getParameter("myCode");
                statement_id = apacheLivy.codeExecution(Integer.parseInt(request.getParameter("session_id")), code);
                 while (!stateExecution.equals("available")) {
                myResult = apacheLivy.getResults(Integer.parseInt(request.getParameter("session_id")), statement_id);
                stateExecution = apacheLivy.getStateExecution();
PrintWriter out = response.getWriter();
        out.print(myResult);
    }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
