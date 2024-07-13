/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.AccountDAO;
import java.util.List;
import model.Account;
import model.TypeOfInvestmentCosts;
import utils.Pagination;
/**
 *
 * @author admin
 */
public class SearchTenantServlet extends HttpServlet {

    String keyWordSave;

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
            out.println("<title>Servlet SearchTenantServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchTenantServlet at " + request.getContextPath() + "</h1>");
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
        String keyWord = request.getParameter("keyword");
        if (keyWord == null) {
            keyWord = keyWordSave;
        } else {
            keyWordSave = keyWord;
        }
        AccountDAO accountDAO = new AccountDAO();
        List<Account> listAccount = accountDAO.getAccountKeyWord(keyWord);
        int numberPerPage = 4;
        String curentPageRaw = request.getParameter("curentPage");
        Pagination<Account> pagination = new Pagination<>(curentPageRaw, numberPerPage, listAccount);
        System.out.println("key: " + keyWord);
        System.out.println(listAccount.size());
        PrintWriter out = response.getWriter();
               
        out.println("<table class=\"table table-hover table-bordered js-copytextarea\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"sampleTable\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th width=\"150\">Email</th>");
        out.println("<th width=\"100\">Tính năng</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        
            
              
        for (Account account : pagination.getListObject()) {
            out.println("<tr>");
           
            out.println("<td>" + account.getEmail() + "</td>");
            out.println("<td class=\"table-td-center\" >");
            out.println("<button class=\"btn btn-primary btn-sm trash\" type=\"button\" title=\"Xóa\" data-id=\"" + account.getAccountID() + "\">");
            out.println("<i class=\"fas fa-trash-alt\"></i>");
            out.println("</button>");
            out.println("<button class=\"btn btn-primary btn-sm edit\" type=\"button\" title=\"Sửa\" id=\"show-emp\" onclick=\"editRowById(" + account.getAccountID() + ")\">");
            out.println("<i class=\"fas fa-edit\"></i>");
            out.println("</button>");
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
