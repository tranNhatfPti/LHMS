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
import java.util.Date;
import model.Account;
import model.Notification;
import model.Receipt;

/**
 *
 * @author admin
 */
public class DetailReceipt extends HttpServlet {

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
            out.println("<title>Servlet DetailReceipt</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailReceipt at " + request.getContextPath() + "</h1>");
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
        String notiID = request.getParameter("notificationId");
        NotificationDAO d = new NotificationDAO();
        Notification newNotificaiton = d.getNotificationById(notiID);
        if (newNotificaiton.getNotificationMessage().contains("Thông báo xóa phiếu thu")) {
            System.out.println("Detail 1 : " + newNotificaiton);
            AccountDAO ad = new AccountDAO();
            Account a = ad.getAccountById(newNotificaiton.getSenderId());
            String[] detail = newNotificaiton.getNotificationMessage().split(",");
            System.out.println("Detail id  5: " + detail[1]);
            ReceiptDAO rd = new ReceiptDAO();
            Receipt r = rd.getReceiptByReceiptId(Integer.parseInt(detail[1]));
            if (r != null) {
                System.out.println("Detail 2 : " + r);
                System.out.println("Detail 3: " + a);
                request.setAttribute("account", a);
                request.setAttribute("notification", newNotificaiton);
                request.setAttribute("oldReceipt", r);
                request.getRequestDispatcher("view/tenant/detail-receipt.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("view/tenant/error-detail.jsp").forward(request, response);
            }
        } else if (newNotificaiton.getNotificationMessage().contains("Thông báo thay đổi phiếu thu")) {
            AccountDAO ad = new AccountDAO();
            Account a = ad.getAccountById(newNotificaiton.getSenderId());
            String[] detail = newNotificaiton.getNotificationMessage().split(",");
            System.out.println("Detail id  5: " + detail[1]);
            ReceiptDAO rd = new ReceiptDAO();
            int price = Integer.parseInt(detail[3]);
            Date dateTime = new Date();
            Receipt oldReceipt = rd.getReceiptByReceiptId(Integer.parseInt(detail[1]));
            Receipt newReceipt = new Receipt(oldReceipt.getReceiptId(), price, dateTime, detail[2], oldReceipt.getAccountId(), oldReceipt.getLodgingHouseId(), true);
            if (oldReceipt != null) {
                System.out.println("Detail 2 : " + oldReceipt);
                System.out.println("Detail 3: " + a);
                request.setAttribute("account", a);
                request.setAttribute("notification", newNotificaiton);
                request.setAttribute("oldReceipt", oldReceipt);
                request.setAttribute("newReceipt", newReceipt);
                request.getRequestDispatcher("view/tenant/detail-receipt.jsp").forward(request, response);
            }

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
