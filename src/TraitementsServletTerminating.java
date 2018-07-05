/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nduwayo
 */
@WebServlet(name = "TraitementsServletTerminating", urlPatterns = {"/TraitementsServletTerminating"})
public class TraitementsServletTerminating extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
       
    }

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
      Traitements traitement =  new Traitements(request);
      String machaine = "{\n" +
"    chart: {\n" +
"        container: \"#tree-terminating\"\n" +
"    },\n" +
"    \n" +
"    nodeStructure: { "+traitement.getJsonTerminate()+"}\n" +
"}";
      JsonParser parser = new JsonParser();
      JsonObject obj = parser.parse(machaine).getAsJsonObject();
      PrintWriter out = response.getWriter();
      out.print(obj);
      //request.setAttribute("machaine", machaine);

        //this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
