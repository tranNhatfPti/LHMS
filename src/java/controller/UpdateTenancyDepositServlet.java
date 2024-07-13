/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.TenancyDepositDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class UpdateTenancyDepositServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateTenancyDepositServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateTenancyDepositServlet at " + request.getContextPath () + "</h1>");
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
        PrintWriter out = response.getWriter();
        String id_raw = request.getParameter("id");
        TenancyDepositDAO tenancyDepositDAO = new TenancyDepositDAO();
        int id = 0;
        try {
            if(id_raw != null) {
                id = Integer.parseInt(id_raw);
                request.setAttribute("tenancyDeposit",tenancyDepositDAO.getTenancyDepositId(id));
            }
        } catch (Exception e) {
        }
        request.getRequestDispatcher("view/manager/update-tenancy-deposit.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesseion = request.getSession();
        TenancyDepositDAO tenancyDepositDAO = new TenancyDepositDAO();
        
        LocalDate currentDate = LocalDate.now();
//        Date bookingDateRaw = java.sql.Date.valueOf(currentDate);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String bookingDate = dateFormat.format(bookingDateRaw);
        String bookingDate = null;
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String depositMoneyRaw = request.getParameter("depositMoney");
        String dateOfBirthRaw = request.getParameter("dateOfBirth");
        String cic = request.getParameter("cic");
        String arriveDateRaw = null;
        String description = request.getParameter("description");
        String idRaw = request.getParameter("id");
        
        String roomIdRaw = (String) sesseion.getAttribute("roomId");
        double depositMoney = 0;
        int id = 0;
        try {
            if(roomIdRaw != null && depositMoneyRaw != null && dateOfBirthRaw != null) {
                depositMoney = Double.parseDouble(depositMoneyRaw);
                id = Integer.parseInt(idRaw);
                
                if(tenancyDepositDAO.updateTenancyDeposit(id,name, email, 
                        phoneNumber, dateOfBirthRaw, 
                        tenancyDepositDAO.getTenancyDepositId(id).getBookingDate(),
                        tenancyDepositDAO.getTenancyDepositId(id).getArriveDate(),
                        depositMoney, roomIdRaw,
                        description, 0, cic)) {
                    response.sendRedirect("view-tenancy-deposit?roomId="+sesseion.getAttribute("roomId"));
                } else {
                    out.println("Error!");
                } 
            } else {
                out.print(roomIdRaw + "  1111");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
