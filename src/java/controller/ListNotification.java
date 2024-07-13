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

/**
 *
 * @author admin
 */
public class ListNotification extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet ListNotification</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListNotification at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String message = request.getParameter("Message");
        if (account == null) {
            response.sendRedirect("LoginServlet"); // Redirect to login page if account is null
            return;
        }
        NotificationDAO notificationDAO = new NotificationDAO();
        List<Notification> listN = notificationDAO.getNotificationByReveiverId(account.getAccountID());
        for (Notification notification : listN) {
            String[] detail = notification.getNotificationMessage().split(",");
            notification.setNotificationMessage(detail[0] + " " + detail[1]);

        }
        System.out.println(message);
        request.setAttribute("message", message);
        request.setAttribute("listN", listN);
        request.getRequestDispatcher("view/tenant/list-notification-waiting-room.jsp").forward(request, response);

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
        try {
            String notificationId = request.getParameter("notificationId");
            String action = request.getParameter("action");

            System.out.println("Received action: " + action + " for notification ID: " + notificationId);

            HttpSession session = request.getSession();
            NotificationDAO d = new NotificationDAO();
            Notification newNotification = d.getNotificationById(notificationId);
            if (newNotification == null) {
                throw new Exception("Notification not found with ID: " + notificationId);
            }

            String[] detail = newNotification.getNotificationMessage().split(",");
            if (detail.length < 3) {
                throw new Exception("Invalid notification message format: " + newNotification.getNotificationMessage());
            }
            String lodgingHouseId_raw = detail[2];

            if ("check".equals(action)) {
                session.setAttribute("lodgingID", lodgingHouseId_raw);
                d.confirmNotification(newNotification);
                response.sendRedirect("Test");
            } else if ("cross".equals(action)) {
                d.RejectNotification(newNotification);
                response.setContentType("text/plain");
                response.getWriter().write("success");
            } else {
                throw new Exception("Invalid action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }

        // Trả về kết quả (nếu cần)
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
