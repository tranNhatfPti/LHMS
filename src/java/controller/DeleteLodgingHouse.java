/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ContractDAO;
import dal.LodgingHousesDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Contract;
import model.Room;

/**
 *
 * @author admin
 */
public class DeleteLodgingHouse extends HttpServlet {

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
            out.println("<title>Servlet DeleteLodgingHouse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteLodgingHouse at " + request.getContextPath() + "</h1>");
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
        String lodgingHouseID_raw = request.getParameter("lodgingHouseId");
        System.out.println("LodgingHouse: " + lodgingHouseID_raw);
        RoomDAO rd = new RoomDAO();
        int id = Integer.parseInt(lodgingHouseID_raw);

        List<Room> listRoom = rd.getRoomsByLodgingHouseId(id);
        ContractDAO cd = new ContractDAO();
        boolean hasActiveContract = false;
        Date currentDate = new Date();

        List<Contract> listContract = cd.getContract();
        for (Contract contract : listContract) {
            for (Room room : listRoom) {
                if ((contract.getRoom().getRoomId() == null ? room.getRoomId() == null : contract.getRoom().getRoomId().equals(room.getRoomId())) && contract.getDateTo().after(currentDate)) {
                    System.out.println("Active contract found: " + contract);
                    hasActiveContract = true;
                    break;  // Exit inner loop since we found an active contract
                }
            }
            if (hasActiveContract) {
                break;  // Exit outer loop since we found an active contract
            }
        }
        System.out.println("Has L : " + hasActiveContract);
        LodgingHousesDAO lodDAO = new LodgingHousesDAO();

        try {
            if (hasActiveContract) {
                // Handle case where active contracts prevent deletion
                response.sendRedirect("management-lodging-houses?index=1&deleteSuccess=false");
            } else {
                // Perform deletion if no active contracts were found
                boolean isDeleted = lodDAO.deleteLodgingHouse(id);
                if (isDeleted) {
                    response.sendRedirect("management-lodging-houses?index=1&deleteSuccess=true");
                } else {
                    response.sendRedirect("management-lodging-houses?index=1&deleteSuccess=false");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteLodgingHouse.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("management-lodging-houses?deleteSuccess=false&error=sql_exception");
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
