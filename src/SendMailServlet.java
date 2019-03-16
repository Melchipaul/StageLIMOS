/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.EmailValidator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author nduwayo
 */
@WebServlet(urlPatterns = {"/SendMailServlet"})
public class SendMailServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Session session;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
    @Resource(name="mail/gmail")
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String message = "";
        PrintWriter out = response.getWriter();
        Map hm = new HashMap<>();
        hm.put("firstname", "");
        hm.put("name", "");
        hm.put("email", "");
        hm.put("phone", "");
        hm.put("message", "");
        hm.put("firstnameError", "");
        hm.put("nameError", "");
        hm.put("emailError", "");
        hm.put("phoneError", "");
        hm.put("messageError", "");
        hm.put("isSuccess", false);
        if (request.getMethod().equals("POST")) {
            hm.put("firstname", request.getParameter("firstname").trim());
            hm.put("name", request.getParameter("name").trim());
            hm.put("email", request.getParameter("email").trim());
            hm.put("phone", request.getParameter("phone").trim());
            hm.put("message", request.getParameter("message").trim());
            hm.put("isSuccess", true);

            if (hm.get("firstname").toString().equals("")) {
                hm.put("firstnameError", " Le champs prénom ne peut pas être vide !");
                hm.put("isSuccess", false);
            } else {
                message += "Firstname : " + hm.get("firstname").toString() + "\n";

            }
            if (hm.get("name").toString().equals("")) {
                hm.put("nameError", " Le champs nom ne peut pas être vide !");
                hm.put("isSuccess", false);
            } else {
                message += "Name : " + hm.get("name").toString() + "\n";

            }
            if (!isMail(hm.get("email").toString())) {
                hm.put("emailError", "Cet email n'a pas un format valide !");
                hm.put("isSuccess", false);
            } else {
                message += "Email : " + hm.get("email").toString() + "\n";

            }
            if (!isPhone(hm.get("phone").toString()) && !hm.get("phone").toString().equals("")) {
                hm.put("phoneError", "Votre numéro de téléphone n'est pas valide!");
                hm.put("isSuccess", false);
            } else {
                message += "Phone : " + hm.get("phone").toString() + "\n";

            }
            if (hm.get("message").toString().equals("")) {
                hm.put("messageError", " Le champs message ne peut pas être vide !");
                hm.put("isSuccess", false);
            } else {
                message += "Message : " + hm.get("message").toString() + "\n";

            }

            if ((Boolean) hm.get("isSuccess")) {
                System.out.println("Send start");
                
		Message msg = new MimeMessage(session);
		try {
			msg.setSubject("Contact UDAF");
			msg.setText(message);
			msg.setRecipients(RecipientType.TO, InternetAddress.parse("limosudaf@gmail.com"));
			
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		System.out.println("Send finished");

            }

        }
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        json = mapper.writeValueAsString(hm);
        System.out.println(json);
        out.print(json);

    }

    Boolean isPhone(String phone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumber FrenchNumberProto = new PhoneNumber();
        try {
            FrenchNumberProto = phoneUtil.parse(phone, "FR");
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return phoneUtil.isValidNumber(FrenchNumberProto);
    }

    Boolean isMail(String email) {
        Pattern regexPattern;
        Matcher regMatcher;
        regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        regMatcher = regexPattern.matcher(email);
        return regMatcher.matches();
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
