/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.LodgingHousesDAO;
import dal.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import model.LodgingHouse;
import model.Notification;

/**
 *
 * @author admin
 */
public class UpdateNotification extends HttpServlet {

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
        String notificationId = request.getParameter("notificationId");
        System.out.println(notificationId);
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        NotificationDAO d = new NotificationDAO();
        Notification newNotificaiton = d.getNotificationById(notificationId);
        String[] detail = newNotificaiton.getNotificationMessage().split(",");
        String lodgingHouseId_raw = detail[2];
        LodgingHousesDAO lod = new LodgingHousesDAO();
        int lodgingHouseID = Integer.parseInt(lodgingHouseId_raw);
        LodgingHouse l = lod.getLodgingHouseById(lodgingHouseID);
        AccountDAO ad = new AccountDAO();
        // Xử lý logic dựa trên action (check hoặc cross)
        if ("check".equals(action)) {
            if (l.getManageId() == 0) {
                d.confirmNotification(newNotificaiton);
                ad.updateRoleAccount(newNotificaiton.getReceiverId());
                lod.updateManagerLodgingHouse(newNotificaiton.getReceiverId(), lodgingHouseId_raw);

                response.sendRedirect("home-manager?LodgingHouseID=" + lodgingHouseId_raw);
            } else {
                request.setAttribute("Message", "Nhà trọ "+l.getNameLodgingHouse()+"đã tìm được quản lí");
                d.RejectNotification(newNotificaiton);
                response.sendRedirect("list-notification?Message=" + URLEncoder.encode("Nhà trọ đã tìm được quản lí", "UTF-8"));

            }
        } else if ("cross".equals(action)) {
            d.RejectNotification(newNotificaiton);
            response.sendRedirect("list-notification");
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
        String notificationId = request.getParameter("notificationId");
        System.out.println(notificationId);
        String action = request.getParameter("action");
        NotificationDAO d = new NotificationDAO();
        Notification newNotificaiton = d.getNotificationById(notificationId);
        String[] detail = newNotificaiton.getNotificationMessage().split(",");
        String lodgingHouseId_raw = detail[2];
        LodgingHousesDAO lod = new LodgingHousesDAO();
        int lodgingHouseID = Integer.parseInt(lodgingHouseId_raw);
        LodgingHouse l = lod.getLodgingHouseById(lodgingHouseID);
        AccountDAO ad = new AccountDAO();
        // Xử lý logic dựa trên action (check hoặc cross)
        if ("check".equals(action)) {
            if (l.getManageId() == 0) {
                d.confirmNotification(newNotificaiton);
                ad.updateRoleAccount(newNotificaiton.getReceiverId());
                lod.updateManagerLodgingHouse(newNotificaiton.getReceiverId(), lodgingHouseId_raw);

                response.sendRedirect("home-manager?LodgingHouseID=" + lodgingHouseId_raw);
            } else {
                request.setAttribute("Message", "Nhà trọ đã tìm được quản lí");
                d.RejectNotification(newNotificaiton);
                response.sendRedirect("list-notification?Message=" + URLEncoder.encode("Nhà trọ đã tìm được quản lí", "UTF-8"));

            }
        } else if ("cross".equals(action)) {
            d.RejectNotification(newNotificaiton);
            response.sendRedirect("list-notification");
        }

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
