/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.ContractDAO;
import dal.InformationOfUserDAO;
import dal.LodgingHousesDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Contract;
import model.InformationOfUser;
import model.LodgingHouse;
import model.Room;

/**
 *
 * @author admin
 */
public class SendContractServlet extends HttpServlet {

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
            out.println("<title>Servlet SendContractServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendContractServlet at " + request.getContextPath() + "</h1>");
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        RoomDAO roomDAO = new RoomDAO();
        AccountDAO accountDAO = new AccountDAO();
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        ContractDAO contractDAO = new ContractDAO();
        String deposit = request.getParameter("deposit");
        String lodgingHouseIdRaw = request.getParameter("lodgingHouseId");
        String roomId = request.getParameter("room");
        String managerIdRaw = request.getParameter("managerId");
        String email = request.getParameter("email");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");

        try {
            int managerId = Integer.parseInt(managerIdRaw);
            long deposit2 = Long.parseLong(deposit);
            Room room = roomDAO.getRoomsById(roomId);
            Account tenant = accountDAO.getAccountByUserEmail(email);
            Account manager = accountDAO.getAccountById(managerId);
            Date dateFromConvert = dateFormat.parse(dateFrom);
            Date dateToConvert = dateFormat.parse(dateTo);

            Contract contractCheck = contractDAO.getContractToHandle(roomId, tenant.getAccountID(), dateFrom, dateTo, 1);
            if (contractCheck != null) {//đã tồn tại
                contractCheck.setStatusDelete(1);
                contractCheck.setStatusAccept(1);       
                contractCheck.setRoleCreateContract(2);
                contractCheck.setContractDeposit(deposit2);
                contractDAO.updateContract(contractCheck);
            } else {//chưa tồn tại
                Contract contract = new Contract(room, tenant, manager, dateFromConvert, dateToConvert, 1, 1, deposit2, 1, 2);
                contractDAO.insertContract(contract);
            }
            room.setStatus(1);
            roomDAO.updateRoom(room);

            response.sendRedirect("room-detail-servlet?id=" + room.getRoomId());
        } catch (NumberFormatException e) {
            System.out.println(e);
        } catch (ParseException ex) {
            Logger.getLogger(SendContractServlet.class.getName()).log(Level.SEVERE, null, ex);
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
