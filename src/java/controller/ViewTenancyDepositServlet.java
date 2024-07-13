/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ContractDAO;
import dal.InformationOfUserDAO;
import dal.LodgingHousesDAO;
import dal.ReceiptDAO;
import dal.RoomDAO;
import dal.TenancyDepositDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Receipt;

/**
 *
 * @author Admin
 */
public class ViewTenancyDepositServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewTenancyDepositServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewTenancyDepositServlet at " + request.getContextPath() + "</h1>");
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
        TenancyDepositDAO tenancyDepositDAO = new TenancyDepositDAO();
        InformationOfUserDAO informationOfUserDAO = new InformationOfUserDAO();
        LodgingHousesDAO lodgingHousesDAO = new LodgingHousesDAO();
        RoomDAO roomDAO = new RoomDAO();
        String roomId = request.getParameter("roomId");

        LocalDate currentDate = LocalDate.now();
        Date bookingDateRaw = java.sql.Date.valueOf(currentDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String bookingDate = dateFormat.format(bookingDateRaw);
        LocalDate arriveDate = null;
        String arriveDateRaw = null;
        int tenancyDepositId = 0;

        Receipt receipt;
        ContractDAO contractDAO = new ContractDAO();
        ReceiptDAO receiptDAO = new ReceiptDAO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        Date date = null;
        try {
            if (tenancyDepositDAO.getTenancyDepositByRoomId(roomId) != null) {
                //arriveDate = dateFormat.parse(tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getArriveDate());
                arriveDateRaw = tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getArriveDate();
                arriveDate = LocalDate.parse(arriveDateRaw);
                tenancyDepositId = tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getTenancyDepositID();
                date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tenancyDepositDAO.getTenancyDepositByRoomId(roomId) != null) {
            if (arriveDate.isBefore(currentDate)
                    && tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getStatusDeposit() == 0
                    && tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getStatusDelete() == 0) {
                long daysBetween = ChronoUnit.DAYS.between(arriveDate, currentDate);
                boolean isDelete = tenancyDepositDAO.deleteTenancyDepositByID(tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getTenancyDepositID());
                if (isDelete) {
                    request.setAttribute("notice", "Hợp đồng đã tự động xóa khi không đến đúng hạn " + daysBetween + " ngày.");
                    request.setAttribute("tenancyDepositByRoomID", tenancyDepositDAO.getTenancyDepositByRoomId(roomId));
                    request.getRequestDispatcher("view/manager/view-tenancy-deposit.jsp").forward(request, response);
                }

            } else if (arriveDate.isBefore(currentDate)
                    && tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getStatusDeposit() == 1
                    && tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getStatusDelete() == 0) {
                long daysBetween = ChronoUnit.DAYS.between(arriveDate, currentDate);
                boolean isDelete = tenancyDepositDAO.deleteTenancyDepositByID(tenancyDepositDAO.getTenancyDepositByRoomId(roomId).getTenancyDepositID());
                if (isDelete) {
                    receipt = new Receipt();
                    receipt.setDateTime(date);
                    receipt.setDescription("Tiền nhận được do hủy hợp đồng từ phòng : " + tenancyDepositDAO.getTenancyDepositId(tenancyDepositId).getRoomID().getRoomName());
                    receipt.setLodgingHouseId(tenancyDepositDAO.getTenancyDepositId(tenancyDepositId).getRoomID().getLodgingHouse().getLodgingHouseId());
                    receipt.setPrice((int) tenancyDepositDAO.getTenancyDepositId(tenancyDepositId).getDepositMoney());
                    receipt.setStatus(true);
                    receiptDAO.insertReceiptWithDeposit(receipt);
                    request.setAttribute("notice", "Hợp đồng đã tự động xóa khi không đến đúng hạn " + daysBetween + " ngày.");
                    request.setAttribute("tenancyDepositByRoomID", tenancyDepositDAO.getTenancyDepositByRoomId(roomId));
                    request.getRequestDispatcher("view/manager/view-tenancy-deposit.jsp").forward(request, response);
                }
                //response.sendRedirect("view-tenancy-deposit?roomId=" + roomId);
            } else {
                request.setAttribute("room", roomDAO.getRoomsById(roomId));
                request.setAttribute("datePrintContract", bookingDate);
                request.setAttribute("lodgingHouseById", lodgingHousesDAO.getLodgingHouseById(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId()));
                request.setAttribute("managerAccount", tenancyDepositDAO.getAccountManagerByLodgingHouseID(roomDAO.getRoomsById(roomId).getLodgingHouse().getLodgingHouseId()));
                request.setAttribute("managerInformation",
                        informationOfUserDAO
                                .getInformationByAccountID(
                                        tenancyDepositDAO.getAccountManagerByLodgingHouseID(
                                                roomDAO.getRoomsById(roomId)
                                                        .getLodgingHouse()
                                                        .getLodgingHouseId()).getAccountID()));
                
                request.setAttribute("tenancyDepositByRoomID", tenancyDepositDAO.getTenancyDepositByRoomId(roomId));
                request.getRequestDispatcher("view/manager/view-tenancy-deposit.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("tenantService", contractDAO.getContractByRoomId(roomId));
            request.setAttribute("tenancyDepositByRoomID", tenancyDepositDAO.getTenancyDepositByRoomId(roomId));
            request.getRequestDispatcher("view/manager/view-tenancy-deposit.jsp").forward(request, response);
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
