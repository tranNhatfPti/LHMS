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

import model.LodgingHouse;
import model.Notification;

/**
 *
 * @author admin
 */
public class DetailNotification extends HttpServlet {

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
            out.println("<title>Servlet DetailNotification</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailNotification at " + request.getContextPath() + "</h1>");
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
        String notiId = request.getParameter("notificationId");
        NotificationDAO noDAO = new NotificationDAO();
        Notification no = noDAO.getNotificationById(notiId);
        String[] detail = no.getNotificationMessage().split(",");
        String lodgingHouseId_raw = detail[2];
        int lodgingHouseId = Integer.parseInt(lodgingHouseId_raw);
        LodgingHousesDAO lodDAO = new LodgingHousesDAO();
        LodgingHouse lod = lodDAO.getLodgingHouseById(lodgingHouseId);
        
        String fullName = lod.getWard();
        String[] location = fullName.split(",");
        lod.setWard(location[1]);
        System.out.println(lod.getWard());
        no.setNotificationMessage(detail[0] + " " + detail[1]);
        request.setAttribute("lod", lod);
        request.setAttribute("noti", no);
        request.getRequestDispatcher("view/tenant/detail-notification-waiting-room.jsp").forward(request, response);
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
        AccountDAO ad = new AccountDAO();
        // Xử lý logic dựa trên action (check hoặc cross)
        if ("accept".equals(action)) {
            d.confirmNotification(newNotificaiton);
            ad.updateRoleAccount(newNotificaiton.getReceiverId());
            lod.updateManagerLodgingHouse(newNotificaiton.getReceiverId(), lodgingHouseId_raw);
            
            response.sendRedirect("home-manager?LodgingHouseID=" + lodgingHouseId_raw);
        } else if ("reject".equals(action)) {
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
