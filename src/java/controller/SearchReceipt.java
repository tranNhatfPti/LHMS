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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.Account;

import model.Receipt;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class SearchReceipt extends HttpServlet {

    String searchInfo, searchtype, dateTo, dateFrom;

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
        String info = request.getParameter("searchText");
        String typeOfSearch = request.getParameter("searchType");

        if (info == null) {
            info = searchInfo;
        } else {
            searchInfo = info;
        }
        // Print the search parameters for debugging
        System.out.println("Search Query: " + info);
        System.out.println("Search Type: " + typeOfSearch);

        // Initialize the list to hold the search results
        List<Receipt> list = new ArrayList<>();

        // Initialize the DAO
        ReceiptDAO rd = new ReceiptDAO();
        AccountDAO ad = new AccountDAO();
        List<Account> listAc = ad.getAllAccountA();
        HttpSession session = request.getSession();
        String lodID = (String) session.getAttribute("lodgingID");
        int lodId = Integer.parseInt(lodID);
        // Perform the search based on the search type
        if (info.isEmpty()) {

            list = rd.getAllReceiptById(lodId);
        } else {
            if (typeOfSearch.equalsIgnoreCase("account")) {
                list = rd.getReceiptByAccount(info);
            } else if (typeOfSearch.equalsIgnoreCase("description")) {
                list = rd.getReceiptByDescription(info, lodId);
            } else if (typeOfSearch.equalsIgnoreCase("price")) {
                list = rd.getReceiptByPrice(info, lodId);
            }
        }

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
        Pagination<Receipt> pagination = new Pagination<>(curentPageRaw, numberPerPage, list);
        for (Receipt receipt : pagination.getListObject() ) {
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
        String dateMin = request.getParameter("dateMin");
        String dateMax = request.getParameter("dateMax");
        if (dateMin != null && dateMax != null) {
            dateFrom = dateMin;

            dateTo = dateMax;
        } else {
            dateMin = dateFrom;
            dateMax = dateTo;
        }
        // Initialize the list to hold the search results
        List<Receipt> list;

        // Initialize the DAO
        ReceiptDAO rd = new ReceiptDAO();
        AccountDAO ad = new AccountDAO();
        List<Account> listAc = ad.getAllAccountA();
        HttpSession session = request.getSession();
        String lodID = (String) session.getAttribute("lodgingID");
        System.out.println("sssss" + lodID);
        int lodId = Integer.parseInt(lodID);
        // Perform the search based on the search type
        if (dateMax.isEmpty() && dateMin.isEmpty()) {

            list = rd.getAllReceiptById(lodId);
        } else {

            list = rd.getReceiptByDate(dateMin, dateMax, lodId);

        }
        for (Receipt receipt : list) {
            System.out.println("Do post resceip" + receipt);
        }

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
        Pagination<Receipt> pagination = new Pagination<>(curentPageRaw, numberPerPage, list);
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
