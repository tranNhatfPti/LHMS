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
public class SearchNotificationForTenant extends HttpServlet {

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
            out.println("<title>Servlet SearchNotificationForTenant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchNotificationForTenant at " + request.getContextPath() + "</h1>");
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
        String searchInfo = request.getParameter("search");
        System.out.println(searchInfo);
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String message = request.getParameter("Message");
        if (account == null) {
            response.sendRedirect("LoginServlet"); // Redirect to login page if account is null
            return;
        }
        NotificationDAO noti = new NotificationDAO();
        List<Notification> list = noti.searchNotificationForTenant(searchInfo, account.getAccountID());
        for (Notification notification : list) {
            String[] detail = notification.getNotificationMessage().split(",");
            notification.setNotificationMessage(detail[0] + " " + detail[1]);
        }
        response.setContentType("text/html;charset=UTF-8");
        String curentPageRaw = request.getParameter("curentPage");
        int numberPerPage = 7;
        Pagination<Notification> pagination = new Pagination<>(curentPageRaw, numberPerPage, list);
        PrintWriter out = response.getWriter();

        out.println("<table id=\"table-body\">");
        out.println("<thead class=\"thead-dark\">");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Thông báo</th>");
        out.println("<th>Ngày hết hạn xác nhận</th>");
        out.println("<th>Phản hồi</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        for (Notification notification : pagination.getListObject()) {
            out.println("<tr>");
            out.println("<td>" + notification.getNotificationId() + "</td>");
            out.println("<td><a href=\"detail-notification?notificationId=" + notification.getNotificationId() + "\">");
            out.println(notification.getNotificationMessage());
            out.println("</a></td>");
            out.println("<td>" + notification.getNotificationDateTime() + "</td>");
            out.println("<td>");
            if (notification.isConfirmationStatus() == 1) {
                out.println("Đã hủy xác nhận");
            } else {
                out.println("<a href=\"update-notification?notificationId=" + notification.getNotificationId() + "&amp;action=check\" class=\"check-notification\">");
                out.println("<span>&#10003;</span>"); // Check mark symbol
                out.println("</a>");
                out.println("<a href=\"update-notification?notificationId=" + notification.getNotificationId() + "&amp;action=cross\" class=\"cross-notification\">");
                out.println("<span>&#10007;</span>"); // Cross mark symbol
                out.println("</a>");
            }
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");

        out.print("<div class=\"pagination\">");
        if (pagination.getCurentPage() > 1) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() - 1) + "\" value=\"" + (pagination.getCurentPage() - 1) + "\">&laquo;</a>");
        }
        for (int num = pagination.getStart(); num <= pagination.getEnd(); num++) {
            if (num != 0) {
                if (num == pagination.getCurentPage()) {
                    out.print("<a class=\"pagination-btn active\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                } else {
                    out.print("<a class=\"pagination-btn\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                }
            }
        }
        if (pagination.getNumberOfPage() > pagination.getCurentPage()) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() + 1) + "\" value=\"" + (pagination.getCurentPage() + 1) + "\">&raquo;</a>");
        }
        out.print("</div>");
        out.close();
        out.close();

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
        processRequest(request, response);
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
