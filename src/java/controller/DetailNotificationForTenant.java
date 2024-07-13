package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import dal.LodgingHousesDAO;
import dal.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LodgingHouse;
import model.Notification;

/**
 *
 * @author admin
 */
@WebServlet(urlPatterns={"/detail-notification-tenant"})
public class DetailNotificationForTenant extends HttpServlet {
   
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
            out.println("<title>Servlet DetailNotificationForTenant</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailNotificationForTenant at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("view/tenant/detail-notification-tenant.jsp").forward(request, response);
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
