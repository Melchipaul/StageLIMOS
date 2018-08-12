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
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author nduwayo
 */
@WebServlet(urlPatterns = {"/ApacheLivyInteractiveApi"})
public class ApacheLivyInteractiveApi extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static int session_id = -1;
    private static int statement_id = -1;
    private static String stateSession = "starting";
    private static String stateExecution = "waiting";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        ApacheLivyBase apacheLivy = new ApacheLivyBase();
        session_id = apacheLivy.createSession("spark");
        System.out.println(session_id);
        
        while (!stateSession.equals("idle")) {
            stateSession = apacheLivy.createState(apacheLivy.getSession_id(), "spark");
        }
        
        System.out.println(apacheLivy.getStateSession());
        String code = request.getParameter("myCode");
        statement_id = apacheLivy.codeExecution(session_id, code.toString());
        System.out.println(statement_id);
        
        JSONObject myResult = new JSONObject();
        JSONObject myResponse = new JSONObject();
        while (!stateExecution.equals("available")) {
            myResult = apacheLivy.getResults(session_id, statement_id);
            stateExecution = apacheLivy.getStateExecution();
        }
        myResponse.put("session_id", session_id);
        myResponse.put("output", myResult.getJSONObject("output"));
        out.print(myResponse);
        //request.setAttribute("machaine", machaine);

        //this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
