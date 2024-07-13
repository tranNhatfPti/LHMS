/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AccountDAO;
import dal.NotificationDAO;
import dal.ReceiptDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Notification;
import model.Receipt;

/**
 *
 * @author admin
 */
public class UpdateReceipt extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateReceipt</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateReceipt at " + request.getContextPath () + "</h1>");
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
        String receiptId=request.getParameter("receiptId");
        int id =Integer.parseInt(receiptId);
        ReceiptDAO rd=new ReceiptDAO();
        Receipt r=rd.getReceiptByReceiptId(id);
       request.setAttribute("receipt", r);
       
       AccountDAO accountDAO = new AccountDAO();
        List<Account> listAccount = accountDAO.getTenantAccount();
        request.setAttribute("listAccount", listAccount);
       request.getRequestDispatcher("/view/manager/update-receipt.jsp").forward(request, response);
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
      
        String emailSender = request.getParameter("emailSender");
        System.out.println(emailSender);
        String emailTenant = request.getParameter("tenantEmail");
        System.out.println(emailTenant);
        String price = request.getParameter("price");

        System.out.println(price);
        String description = request.getParameter("description");
        System.out.println(description);
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String lodgingHouseId=(String) session.getAttribute("lodgingID");
        System.out.println("lodgingID"+lodgingHouseId);
       
        String[] emails = emailTenant.split(",");
        Date currentDate = new Date();
        AccountDAO accountDAO = new AccountDAO();

        for (String email : emails) {
            email = email.trim();
            Account receiverAccount = accountDAO.getAccountByUserEmail(email);
            if (receiverAccount != null) {
                int receiverId = receiverAccount.getAccountID();
               String receiptID_raw=request.getParameter("receiptID");
                Notification notification = new Notification("Thông báo thay đổi phiếu thu,"+receiptID_raw+","+description +","+price+","+lodgingHouseId, currentDate, receiverId, account.getAccountID(), 0,1);
                NotificationDAO notificationDAO = new NotificationDAO();
                notificationDAO.insertNotification(notification);
                 response.sendRedirect("/ManageLodgingHouse/home-manager?LodgingHouseID=" + lodgingHouseId);
            } else {
                System.out.println("Invalid email address: " + email);
            }
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
