/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.NotificationDAO;
import dal.ReceiptDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Notification;
import model.Receipt;

/**
 *
 * @author admin
 */
public class UpdateNotificationForTenant extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("ĐÃ VÀO ĐẾN UP DATE");
        String notificationId = request.getParameter("notificationId");
        System.out.println(notificationId);
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        NotificationDAO d = new NotificationDAO();
        Notification newNotificaiton = d.getNotificationById(notificationId);

        System.out.println("Level 1  ");

        String[] detail = newNotificaiton.getNotificationMessage().split(",");
        ReceiptDAO rd = new ReceiptDAO();
        switch (detail[0]) {
            case "Thông báo phiếu thu tiền mới" -> {
                String lodgingHouseId_raw = detail[3];
                int lodgingHouseID = Integer.parseInt(lodgingHouseId_raw);

                Date dateTime = new Date();
                int price = Integer.parseInt(detail[2]);
                if ("check".equals(action)) {
                    Receipt r = new Receipt(price, dateTime, detail[1], newNotificaiton.getReceiverId(), lodgingHouseID, false);
                    rd.insertReceipt(r);
                    d.confirmNotification(newNotificaiton);

                } else if ("cross".equals(action)) {
                    d.RejectNotification(newNotificaiton);
                }
            }
            case "Thông báo xóa phiếu thu" -> {

                if ("check".equals(action)) {

                    System.out.println("Id" + detail[1]);
                    rd.deleteReceipt(Integer.parseInt(detail[1]));
                    d.confirmNotification(newNotificaiton);

                } else if ("cross".equals(action)) {
                    d.RejectNotification(newNotificaiton);

                }
            }
            case "Thông báo thay đổi phiếu thu" -> {
                if ("check".equals(action)) {

                    System.out.println("Thay dổi phieu thu");
                    int price = Integer.parseInt(detail[3]);
                    Date dateTime = new Date();
                    Receipt oldReceipt = rd.getReceiptByReceiptId(Integer.parseInt(detail[1]));
                    Receipt newReceipt = new Receipt(oldReceipt.getReceiptId(), price, dateTime, detail[2], oldReceipt.getAccountId(), oldReceipt.getLodgingHouseId(), true);

                    rd.updateReceip(newReceipt);
                    d.confirmNotification(newNotificaiton);
                    System.out.println("Level 3  ");

                } else if ("cross".equals(action)) {
                    d.RejectNotification(newNotificaiton);

                }

            }
            default ->
                throw new AssertionError();
        }

        response.sendRedirect("list-notification-for-tenant");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateNotificationForTenant.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        System.out.println("ĐÃ VÀO ĐẾN UP DATE");
        String notificationId = request.getParameter("notificationId");
        System.out.println(notificationId);
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        NotificationDAO d = new NotificationDAO();
        Notification newNotificaiton = d.getNotificationById(notificationId);

        System.out.println("Level 1  ");

        String[] detail = newNotificaiton.getNotificationMessage().split(",");
        ReceiptDAO rd = new ReceiptDAO();
        switch (detail[0]) {
            case "Thông báo phiếu thu tiền mới" -> {
                String lodgingHouseId_raw = detail[3];
                int lodgingHouseID = Integer.parseInt(lodgingHouseId_raw);
                System.out.println("Level 2 ");

                Date dateTime = new Date();
                int price = Integer.parseInt(detail[2]);
                if ("check".equals(action)) {
                    Receipt r = new Receipt(price, dateTime, detail[1], newNotificaiton.getReceiverId(), lodgingHouseID, false);
                    System.out.println("Update" + r);
                    rd.insertReceipt(r);
                    d.confirmNotification(newNotificaiton);

                } else if ("cross".equals(action)) {
                    d.RejectNotification(newNotificaiton);
                }
            }
            case "Thông báo xóa phiếu thu" -> {
                if ("check".equals(action)) {

                    System.out.println("Id" + detail[1]);
                    try {
                        rd.deleteReceipt(Integer.parseInt(detail[1]));
                    } catch (SQLException ex) {
                        Logger.getLogger(UpdateNotificationForTenant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    d.confirmNotification(newNotificaiton);
                    System.out.println("Level 3  ");

                } else if ("cross".equals(action)) {
                    d.RejectNotification(newNotificaiton);

                }
            }
            case "Thông báo thay đổi phiếu thu" -> {
                 if ("check".equals(action)) {

                    System.out.println("Thay dổi phieu thu");
                    int price = Integer.parseInt(detail[3]);
                    Date dateTime = new Date();
                    Receipt oldReceipt = rd.getReceiptByReceiptId(Integer.parseInt(detail[1]));
                    Receipt newReceipt = new Receipt(oldReceipt.getReceiptId(), price, dateTime, detail[2], oldReceipt.getAccountId(), oldReceipt.getLodgingHouseId(), true);

                    rd.updateReceip(newReceipt);
                    d.confirmNotification(newNotificaiton);
                    System.out.println("Level 3  ");

                } else if ("cross".equals(action)) {
                    d.RejectNotification(newNotificaiton);

                }
            }
            default ->
                throw new AssertionError();
        }

        response.sendRedirect("list-notification-for-tenant");

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
