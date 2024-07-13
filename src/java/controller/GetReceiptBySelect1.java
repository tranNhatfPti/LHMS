/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.ReceiptDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import model.Account;
import model.Receipt;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class GetReceiptBySelect1 extends HttpServlet {
String selected ;
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
            out.println("<title>Servlet GetReceiptBySelect</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetReceiptBySelect at " + request.getContextPath() + "</h1>");
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
        String select = request.getParameter("selected");
        
        if(select !=null){
            selected=select;
                    
        }
        else{
            select=selected;
        }
        ReceiptDAO receip = new ReceiptDAO();
        HttpSession session = request.getSession();
        String lod_raw = (String) session.getAttribute("lodgingID");
        System.out.println(lod_raw);
        int lod = Integer.parseInt(lod_raw);
        List<Receipt> listReceipt = null;
        if (select.equalsIgnoreCase("Lowest")) {
            listReceipt = receip.getAllReceiptPriceAsc(lod);
        } else if (select.equalsIgnoreCase("Highest")) {
            listReceipt = receip.getAllReceiptPriceDes(lod);
        } else if (select.equalsIgnoreCase("DateHighest")) {
            listReceipt = receip.getAllReceiptDateDes(lod);
        } else if (select.equalsIgnoreCase("DateLowest")) {
            listReceipt = receip.getAllReceiptDateAsc(lod);
        }
     else if (select.equalsIgnoreCase("All")) {
            listReceipt=receip.getAllReceiptById(lod);
        }
        AccountDAO ad = new AccountDAO();
        List<Account> listAc = ad.getAllAccountA();
          PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-hover table-bordered\" id=\"\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th width=\"10\"><input type=\"checkbox\" id=\"all\"></th>");
        out.println("<th>ID</th>");
        out.println("<th>Mô tả</th>");
        out.println("<th>Số tiền</th>");
        out.println("<th>Thời gian</th>");
        out.println("<th>Email</th>");
        out.println("<th>Trạng thái</th>"); // Fixed missing opening <th> tag
        out.println("<th>Hành động</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        String curentPageRaw = request.getParameter("curentPage");

        int numberPerPage = 5;
        Pagination<Receipt> pagination = new Pagination<>(curentPageRaw, numberPerPage, listReceipt);
        for (Receipt receipt : pagination.getListObject() )  {
            out.println("<tr>");
            out.println("<td width=\"10\"><input type=\"checkbox\" name=\"check1\" value=\"" + receipt.getReceiptId() + "\"></td>");
            out.println("<td>" + receipt.getReceiptId() + "</td>");
            out.println("<td>" + receipt.getDescription() + "</td>");
            out.println("<td>");
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat formatter = NumberFormat.getNumberInstance(localeVN);
            formatter.setMinimumFractionDigits(0);
            formatter.setMaximumFractionDigits(0);
            out.println(formatter.format(receipt.getPrice()));
            out.println("</td>");
            out.println("<td>" + receipt.getDateTime() + "</td>");
            // Find the account with the matching ID
            for (Account acc : listAc) {
                if (receipt.getAccountId() == acc.getAccountID()) {
                    out.println("<td>" + acc.getEmail() + "</td>");
                    break; // Stop the loop once we've found the account
                }
            }
            out.println("<td>");

            if (receipt.isStatus()) {
                out.println("<button class=\"non-clickable-button\" style=\"background: greenyellow; color: white;\">Đã Thanh Toán</button>");
            } else {
                out.println("<button class=\"non-clickable-button\" style=\"background: red; color: white;\">Chưa Thanh Toán</button>");
            }

            out.println("</td>");

            out.println("<td>");
            out.println("<button class=\"btn btn-primary btn-sm trash\" type=\"button\" title=\"Xóa\" onclick=\"myFunction(this)\"><i class=\"fas fa-trash-alt\"></i></button>");
            out.println("   <a href=\"update-receipt?receiptId=" + receipt.getReceiptId() + "\" class=\"button-like\">Update</a>");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.print("<div class=\"pagination\">");
        if (pagination.getCurentPage() > 1) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() - 1) + "\" value=\"" + (pagination.getCurentPage() - 1) + "\">&laquo;</a>");
        }
        for (int num = pagination.getStart(); num <= pagination.getEnd(); num++) {
            if (num != 0) {
                if (num == pagination.getCurentPage()) {
                    out.print("<a class=\"pagination-btn active\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                } else {
                    out.print("<a class=\"pagination-btn\" data-page=\"" + num + "\" value=\"" + num + "\">" + num + "</a>");
                }
            }
        }
        if (pagination.getNumberOfPage() > pagination.getCurentPage()) {
            out.print("<a class=\"pagination-btn\" data-page=\"" + (pagination.getCurentPage() + 1) + "\" value=\"" + (pagination.getCurentPage() + 1) + "\">&raquo;</a>");
        }
        out.print("</div>");
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
