/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LodgingHousesDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.LodgingHouse;
import model.Room;

/**
 *
 * @author admin
 */
public class AddRoomServlet extends HttpServlet {

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
            out.println("<title>Servlet AddRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRoomServlet at " + request.getContextPath() + "</h1>");
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
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        String lodgingHouseIdRaw = (String) session.getAttribute("lodgingID");
        System.out.println(lodgingHouseIdRaw);
        int lodgingHouseId;
        try {
            lodgingHouseId = Integer.parseInt(lodgingHouseIdRaw);
        } catch (NumberFormatException e) {
            lodgingHouseId = 1;
        }

        LodgingHouse lodgingHouse = lodgingHousesDAO.getLodgingHouseById(lodgingHouseId);
        System.out.println(lodgingHouse.getNameLodgingHouse());
        request.setAttribute("lodgingHouse", lodgingHouse);
        request.getRequestDispatcher("view/landlord/add-room.jsp").forward(request, response);

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
        HttpSession session = request.getSession();
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        RoomDAO roomDAO = new RoomDAO();

        PrintWriter out = response.getWriter();
        String roomName = request.getParameter("name");
        String priceRaw = request.getParameter("price");
        String maxQuantityRaw = request.getParameter("maxOfQuantity");
        String description = request.getParameter("description");
        String image = request.getParameter("avatar");

        String lodgingHouseIdRaw = (String) session.getAttribute("lodgingID");
        int lodgingHouseId;
        try {
            lodgingHouseId = Integer.parseInt(lodgingHouseIdRaw);
            LodgingHouse lodgingHouse = lodgingHousesDAO.getLodgingHouseById(lodgingHouseId);
            double price = Double.parseDouble(priceRaw);
            int maxOfQuantity = Integer.parseInt(maxQuantityRaw);
            //status = 0 : trống / status = 1 : có
            //status delete = 1: oke / status delete = 0: xóa
            String roomId = lodgingHouseIdRaw + "_" + (roomDAO.getNumberRoomInLodgingHouse(lodgingHouseId) + 1);
            Room room = new Room(roomId, price, maxOfQuantity, 0, image, description, lodgingHouse, 1, roomName);
            roomDAO.insertRoom(room);
            response.sendRedirect("home-manager");

        } catch (NumberFormatException e) {
            out.print("Lỗi");
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
