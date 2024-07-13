/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ReceiptDAO;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.Receipt;

/**
 *
 * @author Admin
 */
public class ChangeStatusTenancyDepositServlet extends HttpServlet {

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
            out.println("<title>Servlet ChangeStatusTenancyDepositServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeStatusTenancyDepositServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        String services = request.getParameter("services");
        HttpSession session = request.getSession();
        String tenancyDepositIDRaw = request.getParameter("tenancyDepositID");
        TenancyDepositDAO tenancyDepositDAO = new TenancyDepositDAO();
        ReceiptDAO receiptDAO = new ReceiptDAO();
        
        
        Receipt receipt;
        LocalDate currentDate = LocalDate.now();
        // Định dạng theo dd-MM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);

        // Chuyển đổi từ String sang Date (lưu ý: Date không hỗ trợ định dạng ngày tháng, nó chỉ lưu trữ thời điểm)
        int tenancyDepositID = 0;
        try {
            if (tenancyDepositIDRaw != null) {
                tenancyDepositID = Integer.parseInt(tenancyDepositIDRaw);
                if (services.equals("accept")) {
                    if (tenancyDepositDAO.updateAcceptStatusDepositByID(tenancyDepositID)) {
                        response.sendRedirect("view-tenancy-deposit?roomId=" + session.getAttribute("roomId"));
                    } else {
                        out.println("Error!");
                    }
                }
                if (services.equals("cancel")) {
                    if (tenancyDepositDAO.updateCancelStatusDepositByID(tenancyDepositID)) {
                        receipt = new Receipt();
                        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(formattedDate);
                        receipt.setDateTime(date);
                        receipt.setDescription("Tiền nhận được do hủy hợp đồng từ phòng " + tenancyDepositDAO.getTenancyDepositId(tenancyDepositID).getRoomID().getRoomName());
                        receipt.setLodgingHouseId(tenancyDepositDAO.getTenancyDepositId(tenancyDepositID).getRoomID().getLodgingHouse().getLodgingHouseId());
                        receipt.setPrice((int)tenancyDepositDAO.getTenancyDepositId(tenancyDepositID).getDepositMoney());
                        receiptDAO.insertReceipt(receipt);
                        response.sendRedirect("view-tenancy-deposit?roomId=" + session.getAttribute("roomId"));
                    } else {
                        out.println("Error!");
                    }
                }
                if(services.equals("confirm")) {
                    if(tenancyDepositDAO.updateConfirmStatusDepositByID(tenancyDepositID)) {
                        response.sendRedirect("view-tenancy-deposit?roomId=" + session.getAttribute("roomId"));
                    }else {
                        out.println("Error!");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
