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
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Notification;
import model.Receipt;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class SearchNotification extends HttpServlet {

    String searchInfo;

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
            out.println("<title>Servlet SearchNotification</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchNotification at " + request.getContextPath() + "</h1>");
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
        if (searchInfo != null) {
            this.searchInfo = searchInfo;
        } else {
            searchInfo = this.searchInfo;
        }
        System.out.println(searchInfo);

        NotificationDAO noti = new NotificationDAO();
        List<Notification> list=new ArrayList<>();
       
        String curentPageRaw = request.getParameter("curentPage");
        int numberPerPage = 7;

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (searchInfo.isEmpty()) {
            list = noti.getNotificationBySenderId(account.getAccountID());
        }
        
        else{
            list = noti.searchNotificationBySender(searchInfo,account.getAccountID());
        }
         for (Notification notification : list) {
            String[] detail = notification.getNotificationMessage().split(",");
            notification.setNotificationMessage("Thông báo mời quản lí" + " " + detail[1]);
        }
        Pagination<Notification> pagination = new Pagination<>(curentPageRaw, numberPerPage, list);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<style>");
        out.println(".waiting { color: orange; }");
        out.println(".cancelled { color: red; }");
        out.println(".confirmed { color: green; }");
        out.println("</style>");

        out.println("<table id=\"table table-striped\">");
        out.println("<thead class=\"thead-dark\">");
//    <table class="table table-striped" 
//                    <thead class="thead-dark">
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
            out.println("<td><a href=\"detail-notification?notificationId=" + notification.getNotificationId() + "\">"
                    + notification.getNotificationMessage() + "</a></td>");
            out.println("<td>" + notification.getNotificationDateTime() + "</td>");
            out.println("<td>");

            // Java equivalent of <c:choose> block
            if (notification.isConfirmationStatus() == 0) {
                out.println("<span class=\"waiting\">Đang chờ xác nhận</span>");
            } else if (notification.isConfirmationStatus() == 1) {
                out.println("<span class=\"cancelled\">Đã hủy xác nhận</span>");
            } else if (notification.isConfirmationStatus() == 2) {
                out.println("<span class=\"confirmed\">Đã xác nhận</span>");
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
        String searchInfo = request.getParameter("search");
        if (searchInfo != null) {
            this.searchInfo = searchInfo;
        } else {
            searchInfo = this.searchInfo;
        }
        System.out.println(searchInfo);

        NotificationDAO noti = new NotificationDAO();
        List<Notification> list=new ArrayList<>();
        for (Notification notification : list) {
            String[] detail = notification.getNotificationMessage().split(",");
            notification.setNotificationMessage("Thông báo mời quản lí" + " " + detail[1]);
        }
        String curentPageRaw = request.getParameter("curentPage");
        int numberPerPage = 7;

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (searchInfo.isEmpty()) {
            list = noti.getNotificationBySenderId(account.getAccountID());
        }
        
        else{
            list = noti.searchNotification(searchInfo);
        }
        Pagination<Notification> pagination = new Pagination<>(curentPageRaw, numberPerPage, list);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<style>");
        out.println(".waiting { color: orange; }");
        out.println(".cancelled { color: red; }");
        out.println(".confirmed { color: green; }");
        out.println("</style>");

        out.println("<table id=\"table table-striped\">");
        out.println("<thead class=\"thead-dark\">");
//    <table class="table table-striped" 
//                    <thead class="thead-dark">
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
            out.println("<td><a href=\"detail-notification?notificationId=" + notification.getNotificationId() + "\">"
                    + notification.getNotificationMessage() + "</a></td>");
            out.println("<td>" + notification.getNotificationDateTime() + "</td>");
            out.println("<td>");

            // Java equivalent of <c:choose> block
            if (notification.isConfirmationStatus() == 0) {
                out.println("<span class=\"waiting\">Đang chờ xác nhận</span>");
            } else if (notification.isConfirmationStatus() == 1) {
                out.println("<span class=\"cancelled\">Đã hủy xác nhận</span>");
            } else if (notification.isConfirmationStatus() == 2) {
                out.println("<span class=\"confirmed\">Đã xác nhận</span>");
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
