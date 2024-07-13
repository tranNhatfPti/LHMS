/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ContractDAO;
import dal.LodgingHousesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Contract;
import utils.Pagination;

/**
 *
 * @author admin
 */
public class ViewOldContractTenantServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewOldContractTenantServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewOldContractTenantServlet at " + request.getContextPath() + "</h1>");
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
        Account account = (Account) session.getAttribute("account");
        ContractDAO contractDAO = new ContractDAO();
        List<Contract> listContractOfTenant = contractDAO.getOldContractForTenant(account.getAccountID());
        String curentPageRaw = request.getParameter("curentPage");
        
        int numberPerPage = 4;
        Pagination paginationContractOfTenantAccept = new Pagination(curentPageRaw, numberPerPage, listContractOfTenant);
        request.setAttribute("paginationContractOfTenantAccept", paginationContractOfTenantAccept);
        request.getRequestDispatcher("view/tenant/view-old-contract-tenant.jsp").forward(request, response);
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
        String keyWord = request.getParameter("keyword");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        ContractDAO contractDAO = new ContractDAO();
        List<Contract> listContractOfTenant = contractDAO.getOldContractForTenantByKeyword(keyWord, account.getAccountID());
        String curentPageRaw = request.getParameter("curentPage");

        int numberPerPage = 4;
        Pagination<Contract> paginationContractOfTenantAccept = new Pagination(curentPageRaw, numberPerPage, listContractOfTenant);

        PrintWriter out = response.getWriter();

        out.println("<table id=\"sampleTable\" class=\"table table-hover table-bordered js-copytextarea\">");
        out.println("    <thead>");
        out.println("        <tr>");
        out.println("            <th>Email người thuê</th>");
        out.println("            <th>Phòng</th>");
        out.println("            <th>Ngày bắt đầu</th>");
        out.println("            <th>Ngày kết thúc</th>");
        out.println("            <th>Tiền cọc</th>");
        out.println("            <th>Xem hợp đồng</th>");
        out.println("        </tr>");
        out.println("    </thead>");
        out.println("    <tbody>");

        for (Contract o : paginationContractOfTenantAccept.getListObject()) {
            out.println("        <tr>");
            out.println("            <td>" + o.getTenantId().getEmail() + "</td>");
            out.println("            <td>" + o.getRoom().getRoomName()+ "</td>");
            out.println("            <td>" + o.getDateFrom() + "</td>");
            out.println("            <td>" + o.getDateTo() + "</td>");
            out.println("            <td>" + o.getContractDeposit() + "</td>");
            out.println("            <td class=\"table-td-center\">");
            out.println("                <button class=\"btn btn-primary send-form-btn\" type=\"button\" ");
            out.println("                    data-room=\"" + o.getRoom().getRoomId() + "\" ");
            out.println("                    data-email=\"" + o.getTenantId().getEmail() + "\" ");
            out.println("                    data-date-from=\"" + o.getDateFrom() + "\" ");
            out.println("                    data-date-to=\"" + o.getDateTo() + "\" ");
            out.println("                    data-deposit=\"" + o.getContractDeposit() + "\">");
            out.println("                    <i class='bx bx-edit'></i>");
            out.println("                </button>");
            out.println("            </td>");
            out.println("        </tr>");
        }

        out.println("    </tbody>");
        out.println("</table>");

        // Pagination
        out.println("<div class=\"pagination\">");
        int currentPage = paginationContractOfTenantAccept.getCurentPage();
        if (currentPage > 1) {
            out.println("    <a href=\"javascript:void(0)\" onclick=\"loadPage(" + (currentPage - 1) + ")\">&laquo;</a>");
        }

        for (int num = paginationContractOfTenantAccept.getStart(); num <= paginationContractOfTenantAccept.getEnd(); num++) {
            if (num != 0) {
                if (num == currentPage) {
                    out.println("    <a href=\"javascript:void(0)\" class=\"active\" onclick=\"loadPage(" + num + ")\">" + num + "</a>");
                } else {
                    out.println("    <a href=\"javascript:void(0)\" onclick=\"loadPage(" + num + ")\">" + num + "</a>");
                }
            }
        }

        if (paginationContractOfTenantAccept.getNumberOfPage() > currentPage) {
            out.println("    <a href=\"javascript:void(0)\" onclick=\"loadPage(" + (currentPage + 1) + ")\">&raquo;</a>");
        }

        out.println("</div>");
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
