/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Notification;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class ListNotificationForTenant extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListNotificationForTenant</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListNotificationForTenant at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
               HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String message = request.getParameter("Message");
        if (account == null) {
            response.sendRedirect("LoginServlet"); // Redirect to login page if account is null
            return;
        }
        NotificationDAO notificationDAO = new NotificationDAO();
        List<Notification> listN = notificationDAO.getNotificationByReveiverId(account.getAccountID());
            int numberPerPage = 7;
        String curentPageRaw = request.getParameter("curentPage");
        Pagination<Notification> pagination = new Pagination<>(curentPageRaw, numberPerPage, listN);
        
        for (Notification notification : listN) {
            String[] detail = notification.getNotificationMessage().split(",");
            notification.setNotificationMessage(detail[0] + " " + detail[1]);
        }
        System.out.println(message);
        
             session.removeAttribute("pagination");
        request.setAttribute("message", message);
       session.setAttribute("pagination", pagination);
        request.getRequestDispatcher("view/tenant/list-notification-for-tenant.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
