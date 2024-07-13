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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Account;
import model.Notification;
import model.Receipt;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class DeleteReceipt extends HttpServlet {

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
        HttpSession session = request.getSession();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 15);

        Date futureDate = calendar.getTime();
        Account account = (Account) session.getAttribute("account");
        String receiptID_raw = request.getParameter("receipt");
        ReceiptDAO rd = new ReceiptDAO();
        Receipt receipt = rd.getReceiptByReceiptId(Integer.parseInt(receiptID_raw));
        NotificationDAO notiDAO = new NotificationDAO();
        Notification noti = new Notification("Thông báo xóa phiếu thu," + receipt.getReceiptId(), futureDate, receipt.getAccountId(), account.getAccountID(), 0, 1);
        notiDAO.insertNotification(noti);
              AccountDAO ac = new AccountDAO();
        if (account == null) {
            response.sendRedirect("LoginServlet"); // Redirect to login page if account is null
            return;
        }
        String lod_raw = (String) session.getAttribute("lodgingID");
        System.out.println(lod_raw);
        int lod = Integer.parseInt(lod_raw);

        List<Receipt> listReceipt = rd.getAllReceiptById(lod);

        for (Receipt receipt1 : listReceipt) {
            System.out.println("Hello" + receipt1);
        }
        List<Account> listAccount = ac.getAllAccountA();
        request.setAttribute("listReceipt", listReceipt);
        request.setAttribute("listAccount", listAccount);
        int numberPerPage = 5;
        String curentPageRaw = request.getParameter("curentPage");
        Pagination<Receipt> pagination = new Pagination<>(curentPageRaw, numberPerPage, listReceipt);
        for (Receipt receipt2 : pagination.getListObject()) {
            System.out.println("Manager: " + receipt2);
        }
        session.removeAttribute("pagination");
        session.setAttribute("pagination", pagination);
        request.getRequestDispatcher("view/manager/list-receipt.jsp").forward(request, response);

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
